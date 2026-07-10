package fr.fifoube.main.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.economy.MoneyFormat;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class CommandMoney {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("money")
                        .requires(src -> src.isPlayer())
                        .executes(ctx -> showBalance(ctx.getSource()))
        );
        dispatcher.register(
                Commands.literal("solde")
                        .requires(src -> src.isPlayer())
                        .executes(ctx -> showBalance(ctx.getSource()))
        );
    }

    private static int showBalance(CommandSourceStack src) throws CommandSyntaxException {
        ServerPlayer player = src.getPlayerOrException();
        player.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data ->
                src.sendSuccess(() -> Component.translatable("commands.money.balance", MoneyFormat.display(data.getMoney())), false));
        return 1;
    }
}
