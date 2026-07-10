package fr.fifoube.main.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import fr.fifoube.items.ItemsRegistery;
import fr.fifoube.main.economy.PinUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class CommandCardPin {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("card")
                        .requires(src -> src.isPlayer())
                        .then(Commands.literal("pin")
                                .then(Commands.literal("clear")
                                        .executes(ctx -> clearPin(ctx.getSource())))
                                .then(Commands.argument("code", StringArgumentType.string())
                                        .executes(ctx -> setPin(ctx.getSource(), StringArgumentType.getString(ctx, "code")))))
        );
    }

    private static ItemStack findOwnedCard(ServerPlayer player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == ItemsRegistery.CREDITCARD.get()
                    && stack.hasTag()
                    && stack.getTag().getBoolean("Owned")
                    && player.getStringUUID().equals(stack.getTag().getString("OwnerUUID"))) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static int setPin(CommandSourceStack src, String code) {
        ServerPlayer player = src.getPlayer();
        if (player == null) {
            return 0;
        }
        ItemStack card = findOwnedCard(player);
        if (card.isEmpty()) {
            src.sendFailure(Component.translatable("commands.card.noCard"));
            return 0;
        }
        if (!PinUtil.isValidPinFormat(code)) {
            src.sendFailure(Component.translatable("commands.card.invalidPin"));
            return 0;
        }
        card.getTag().putBoolean("PinEnabled", true);
        card.getTag().putString("PinHash", PinUtil.hashPin(code, player.getStringUUID()));
        src.sendSuccess(() -> Component.translatable("commands.card.pinSet"), false);
        return 1;
    }

    private static int clearPin(CommandSourceStack src) {
        ServerPlayer player = src.getPlayer();
        if (player == null) {
            return 0;
        }
        ItemStack card = findOwnedCard(player);
        if (card.isEmpty()) {
            src.sendFailure(Component.translatable("commands.card.noCard"));
            return 0;
        }
        card.getTag().putBoolean("PinEnabled", false);
        card.getTag().remove("PinHash");
        src.sendSuccess(() -> Component.translatable("commands.card.pinCleared"), false);
        return 1;
    }
}
