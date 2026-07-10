package fr.fifoube.main.atm;

import fr.fifoube.blocks.BlockATM;
import fr.fifoube.items.IValue;
import fr.fifoube.items.ItemCreditCard;
import fr.fifoube.items.ItemsRegistery;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.IMoney;
import fr.fifoube.main.config.ConfigFile;
import fr.fifoube.main.economy.PinUtil;
import fr.fifoube.main.economy.TransactionHistoryService;
import fr.fifoube.main.economy.TransactionType;
import fr.fifoube.main.economy.MoneyFormat;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class AtmService {

    public static final long MAX_TRANSACTION_AMOUNT = 1_000_000_000L;
    private static final int ATM_RANGE = 5;
    private static final int[] DENOMINATIONS = {500, 200, 100, 50, 20, 10, 5, 1};

    private AtmService() {
    }

    public static boolean isValidAmount(long amount) {
        return amount > 0 && amount <= MAX_TRANSACTION_AMOUNT;
    }

    public static long calculateFee(long amount, double feePercent) {
        if (feePercent <= 0) {
            return 0;
        }
        return MoneyFormat.toWhole(amount * feePercent / 100.0);
    }

    public static double effectiveAtmWithdrawFeePercent() {
        return ConfigFile.enableAtmWithdrawFee ? ConfigFile.atmWithdrawFeePercent : 0.0;
    }

    public static double effectiveGoldConvertFeePercent() {
        return ConfigFile.enableGoldConvertFee ? ConfigFile.goldConvertFeePercent : 0.0;
    }

    public static boolean areAtmFeesEnabled() {
        return ConfigFile.enableAtmWithdrawFee && ConfigFile.atmWithdrawFeePercent > 0;
    }

    public static Optional<ItemStack> findOwnedCreditCard(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof ItemCreditCard && stack.hasTag()
                    && stack.getTag().getBoolean("Owned")
                    && player.getStringUUID().equals(stack.getTag().getString("OwnerUUID"))) {
                return Optional.of(stack);
            }
        }
        return Optional.empty();
    }

    public static boolean verifyPin(Player player, String pin) {
        Optional<ItemStack> card = findOwnedCreditCard(player);
        if (card.isEmpty()) {
            return false;
        }
        return PinUtil.verifyPin(card.get(), pin == null ? "" : pin, player.getStringUUID());
    }

    public static boolean requiresPin(Player player) {
        return findOwnedCreditCard(player).map(PinUtil::isPinEnabled).orElse(false);
    }

    public static boolean canUseAtm(Player player) {
        Optional<ItemStack> card = findOwnedCreditCard(player);
        if (card.isEmpty()) {
            return false;
        }
        if (ConfigFile.canAccessCardWithoutWT && card.get().getTag().getBoolean("Linked")) {
            return true;
        }
        return isNearAtm(player);
    }

    private static boolean isNearAtm(Player player) {
        Level level = player.level();
        BlockPos center = player.blockPosition();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int x = -ATM_RANGE; x <= ATM_RANGE; x++) {
            for (int y = -ATM_RANGE; y <= ATM_RANGE; y++) {
                for (int z = -ATM_RANGE; z <= ATM_RANGE; z++) {
                    cursor.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                    if (level.getBlockState(cursor).getBlock() instanceof BlockATM) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean deposit(ServerPlayer player, IMoney account, long amount, String pin) {
        if (!isValidAmount(amount) || !canUseAtm(player)) {
            return false;
        }
        if (!verifyPin(player, pin)) {
            player.sendSystemMessage(Component.translatable("title.invalidPin"));
            return false;
        }
        if (getInventoryBillValue(player) < amount) {
            player.sendSystemMessage(Component.translatable("title.insufficientBills"));
            return false;
        }
        if (!consumeBills(player, amount)) {
            player.sendSystemMessage(Component.translatable("title.insufficientBills"));
            return false;
        }
        double previous = account.getMoney();
        account.setMoney(previous + amount);
        TransactionHistoryService.record(player, account, TransactionType.DEPOSIT, amount, null);
        ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " deposited " + amount
                + ". Balance was " + MoneyFormat.display(previous) + ", balance is now " + MoneyFormat.display(account.getMoney())
                + ". [Player: " + player.getGameProfile().getName() + "]");
        return true;
    }

    public static boolean withdraw(ServerPlayer player, IMoney account, long amount, String pin, boolean confirmed) {
        if (!isValidAmount(amount) || !canUseAtm(player)) {
            return false;
        }
        if (!verifyPin(player, pin)) {
            player.sendSystemMessage(Component.translatable("title.invalidPin"));
            return false;
        }
        if (amount >= ConfigFile.atmConfirmThreshold && !confirmed) {
            player.sendSystemMessage(Component.translatable("title.withdrawConfirmRequired", amount));
            return false;
        }
        long fee = calculateFee(amount, effectiveAtmWithdrawFeePercent());
        long totalDebit = amount + fee;
        if (account.getMoney() < totalDebit) {
            player.sendSystemMessage(Component.translatable("title.insufficientFunds"));
            return false;
        }
        List<ItemStack> stacks = buildBillStacks(amount);
        if (!canFitItems(player, stacks)) {
            player.sendSystemMessage(Component.translatable("title.noInventoryPlace"));
            return false;
        }
        for (ItemStack stack : stacks) {
            player.addItem(stack);
        }
        double previous = account.getMoney();
        account.setMoney(previous - totalDebit);
        TransactionHistoryService.record(player, account, TransactionType.WITHDRAW, amount, fee > 0 ? "fee:" + fee : null);
        if (fee > 0) {
            TransactionHistoryService.record(player, account, TransactionType.FEE, fee, "withdraw");
        }
        ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " withdrew " + amount
                + (fee > 0 ? " (fee " + fee + ")" : "")
                + ". Balance was " + MoneyFormat.display(previous) + ", balance is now " + MoneyFormat.display(account.getMoney())
                + ". [Player: " + player.getGameProfile().getName() + "]");
        return true;
    }

    public static long getInventoryBillValue(Player player) {
        long total = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            Item item = stack.getItem();
            if (ItemsRegistery.availableBills().contains(item) && item instanceof IValue value) {
                total += (long) value.getValue() * stack.getCount();
            }
        }
        return total;
    }

    public static long maxWithdrawableBalance(double balance) {
        if (!ConfigFile.enableAtmWithdrawFee || ConfigFile.atmWithdrawFeePercent <= 0) {
            return (long) Math.floor(balance);
        }
        double divisor = 1.0 + ConfigFile.atmWithdrawFeePercent / 100.0;
        return (long) Math.floor(balance / divisor);
    }

    private static boolean consumeBills(Player player, long amount) {
        long remaining = amount;
        for (int denomination : DENOMINATIONS) {
            int toRemove = (int) Math.min(countBillItems(player, denomination), remaining / denomination);
            if (toRemove > 0) {
                removeBillItems(player, denomination, toRemove);
                remaining -= (long) denomination * toRemove;
            }
        }
        return remaining == 0;
    }

    private static int countBillItems(Player player, int denomination) {
        int count = 0;
        Item billItem = getBillItem(denomination);
        if (billItem == null) {
            return 0;
        }
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == billItem) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private static void removeBillItems(Player player, int denomination, int count) {
        Item billItem = getBillItem(denomination);
        if (billItem == null || count <= 0) {
            return;
        }
        int remaining = count;
        for (int i = 0; i < player.getInventory().getContainerSize() && remaining > 0; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == billItem) {
                int removed = Math.min(stack.getCount(), remaining);
                stack.shrink(removed);
                remaining -= removed;
            }
        }
    }

    private static Item getBillItem(int denomination) {
        return switch (denomination) {
            case 1 -> ItemsRegistery.ONEB.get();
            case 5 -> ItemsRegistery.FIVEB.get();
            case 10 -> ItemsRegistery.TENB.get();
            case 20 -> ItemsRegistery.TWENTYB.get();
            case 50 -> ItemsRegistery.FIFTYB.get();
            case 100 -> ItemsRegistery.HUNDREDB.get();
            case 200 -> ItemsRegistery.TWOHUNDREDB.get();
            case 500 -> ItemsRegistery.FIVEHUNDREDB.get();
            default -> null;
        };
    }

    private static List<ItemStack> buildBillStacks(long amount) {
        List<ItemStack> stacks = new ArrayList<>();
        long remaining = amount;
        for (int denomination : DENOMINATIONS) {
            long count = remaining / denomination;
            remaining %= denomination;
            while (count > 0) {
                int stackSize = (int) Math.min(count, 64);
                Item billItem = getBillItem(denomination);
                if (billItem != null) {
                    stacks.add(new ItemStack(billItem, stackSize));
                }
                count -= stackSize;
            }
        }
        return stacks;
    }

    private static boolean canFitItems(Player player, List<ItemStack> stacks) {
        for (ItemStack template : stacks) {
            if (!canFitStack(player, template)) {
                return false;
            }
        }
        return true;
    }

    private static boolean canFitStack(Player player, ItemStack template) {
        int remaining = template.getCount();
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot.isEmpty()) {
                remaining -= template.getMaxStackSize();
            } else if (ItemStack.isSameItemSameTags(slot, template)) {
                remaining -= template.getMaxStackSize() - slot.getCount();
            }
            if (remaining <= 0) {
                return true;
            }
        }
        return remaining <= 0;
    }
}
