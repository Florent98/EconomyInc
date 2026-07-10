package fr.fifoube.main.economy;

import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.capabilities.IMoney;
import fr.fifoube.main.config.ConfigFile;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PaymentService {

    private static final Map<UUID, Long> LAST_PAY = new ConcurrentHashMap<>();

    private PaymentService() {
    }

    public static boolean isValidPayAmount(double amount) {
        return amount >= ConfigFile.payMinAmount && amount <= ConfigFile.payMaxAmount;
    }

    public static boolean pay(ServerPlayer sender, ServerPlayer receiver, double amount) {
        long wholeAmount = MoneyFormat.toWhole(amount);
        if (wholeAmount <= 0) {
            sender.sendSystemMessage(Component.translatable("commands.pay.invalidAmount", ConfigFile.payMinAmount, ConfigFile.payMaxAmount));
            return false;
        }
        if (sender.getUUID().equals(receiver.getUUID())) {
            sender.sendSystemMessage(Component.translatable("commands.pay.self"));
            return false;
        }
        if (!isValidPayAmount(wholeAmount)) {
            sender.sendSystemMessage(Component.translatable("commands.pay.invalidAmount", ConfigFile.payMinAmount, ConfigFile.payMaxAmount));
            return false;
        }
        long now = System.currentTimeMillis();
        Long last = LAST_PAY.get(sender.getUUID());
        if (last != null && now - last < ConfigFile.payCooldownSeconds * 1000L) {
            sender.sendSystemMessage(Component.translatable("commands.pay.cooldown", ConfigFile.payCooldownSeconds));
            return false;
        }
        IMoney senderAccount = sender.getCapability(CapabilityMoney.MONEY_CAPABILITY).orElse(null);
        IMoney receiverAccount = receiver.getCapability(CapabilityMoney.MONEY_CAPABILITY).orElse(null);
        if (senderAccount == null || receiverAccount == null) {
            return false;
        }
        if (senderAccount.getMoney() < wholeAmount) {
            sender.sendSystemMessage(Component.translatable("title.insufficientFunds"));
            return false;
        }
        double senderBefore = senderAccount.getMoney();
        double receiverBefore = receiverAccount.getMoney();
        senderAccount.setMoney(senderBefore - wholeAmount);
        receiverAccount.setMoney(receiverBefore + wholeAmount);
        LAST_PAY.put(sender.getUUID(), now);

        String receiverName = receiver.getDisplayName().getString();
        String senderName = sender.getDisplayName().getString();
        TransactionHistoryService.record(sender, senderAccount, TransactionType.PAY_SENT, wholeAmount, receiverName);
        TransactionHistoryService.record(receiver, receiverAccount, TransactionType.PAY_RECEIVED, wholeAmount, senderName);

        ModEconomyInc.LOGGER_MONEY.info(senderName + " paid " + wholeAmount + " to " + receiverName
                + ". Sender balance: " + MoneyFormat.display(senderBefore) + " -> " + MoneyFormat.display(senderAccount.getMoney())
                + ". Receiver balance: " + MoneyFormat.display(receiverBefore) + " -> " + MoneyFormat.display(receiverAccount.getMoney())
                + ". [Sender: " + senderName + ", Receiver: " + receiverName + "]");

        sender.sendSystemMessage(Component.translatable("commands.pay.sent", wholeAmount, receiverName));
        receiver.sendSystemMessage(Component.translatable("commands.pay.received", wholeAmount, senderName));
        return true;
    }
}
