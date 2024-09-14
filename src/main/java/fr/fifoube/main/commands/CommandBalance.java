/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class CommandBalance {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

		LiteralCommandNode<CommandSourceStack> literalcommandnode = dispatcher.register(Commands.literal("balance").requires((src) -> {
			return src.hasPermission(2);
						}).then(
								Commands.literal("add")
										.then(
												Commands.argument("players", EntityArgument.players())
														.then(
																Commands.argument("money", DoubleArgumentType.doubleArg(0))
																		.executes(ctx -> addToBalance(ctx.getSource(), EntityArgument.getEntities(ctx, "players"), DoubleArgumentType.getDouble(ctx, "money")))
														)
										)
						)
						.then(
								Commands.literal("remove")
										.then(
												Commands.argument("players", EntityArgument.players())
														.then(
																Commands.argument("money", DoubleArgumentType.doubleArg(0))
																		.executes(ctx -> removeFromBalance(ctx.getSource(), EntityArgument.getEntities(ctx, "players"), DoubleArgumentType.getDouble(ctx, "money")))
														)
										)
						)
						.then(
								Commands.literal("set")
										.then(
												Commands.argument("players", EntityArgument.players())
														.then(
																Commands.argument("money", DoubleArgumentType.doubleArg(0))
																		.executes(ctx -> setBalance(ctx.getSource(), EntityArgument.getEntities(ctx, "players"), DoubleArgumentType.getDouble(ctx, "money")))
														)
										)
						)
						.then(
								Commands.literal("check")
										.then(
												Commands.argument("players", EntityArgument.players())
														.executes(ctx -> checkFromBalance(ctx.getSource(), EntityArgument.getEntities(ctx, "players")))
										)
						)
		);
		dispatcher.register(Commands.literal("bal").requires((src) -> {
			return src.hasPermission(2);
		}).redirect(literalcommandnode));
	}


	private static int addToBalance(CommandSourceStack src, Collection<? extends Entity> targets, double money)
	{
		targets.forEach(e -> {
			if(e instanceof Player)
			{
				ServerPlayer playerMP = (ServerPlayer)e;
				playerMP.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> {
					ModEconomyInc.LOGGER_MONEY.info(playerMP.getDisplayName().getString() + " has received " + money + ". Balance was at " + data.getMoney() + ", balance is now " + (data.getMoney() + money) + "." + "[UUID: " + playerMP.getUUID() + ",Command exectuor: " + src.getDisplayName().getString() + ", UUID: "+ src.getEntity().getUUID() +"]");
					data.setMoney(data.getMoney() + money);
				});
				src.sendSuccess(Component.translatable("commands.balance.added", money, playerMP.getDisplayName().getString()), false);
			}
		});

	 	return 0;
	}

	private static int removeFromBalance(CommandSourceStack src, Collection<? extends Entity> targets, double money)
	{
		targets.forEach(e -> {
			if(e instanceof Player)
			{
				ServerPlayer playerMP = (ServerPlayer)e;
				playerMP.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
					ModEconomyInc.LOGGER_MONEY.info(playerMP.getDisplayName().getString() + " has been withdrawn " + money + ". Balance was at " + data.getMoney() + ", balance is now " + (data.getMoney() - money) + "." + "[UUID: " + playerMP.getUUID() + ",Command exectuor: " + src.getDisplayName().getString() + ", UUID: "+ src.getEntity().getUUID() +"]");
					data.setMoney(data.getMoney() - money);
				});
				src.sendSuccess(Component.translatable("commands.balance.withdraw", money, playerMP.getDisplayName().getString()), false);
			}
		});
	 	return 0;
	}

	private static int setBalance(CommandSourceStack src, Collection<? extends Entity> targets, double money)
	{
		targets.forEach(e -> {
			if(e instanceof Player)
			{
				ServerPlayer playerMP = (ServerPlayer)e;
				playerMP.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> {
					ModEconomyInc.LOGGER_MONEY.info(playerMP.getDisplayName().getString() + " money was define at : " + money + ". Balance was at " + data.getMoney() + ", balance is now " + money + "." + "[UUID: " + playerMP.getUUID() + ",Command exectuor: " + src.getDisplayName().getString() + ", UUID: "+ src.getEntity().getUUID() +"]");
					data.setMoney(money);
				});
				src.sendSuccess(Component.translatable("commands.balance.set", playerMP.getDisplayName().getString(), money), false);
			}
		});

		return 0;
	}

	private static int checkFromBalance(CommandSourceStack src, Collection<? extends Entity> targets)
	{
		targets.forEach(e -> {
			if(e instanceof Player)
			{
				ServerPlayer playerMP = (ServerPlayer)e;
				playerMP.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
					src.sendSuccess(Component.translatable("commands.balance.funds", playerMP.getDisplayName().getString(), data.getMoney()), false);
				});
			}
		});
		return 0;
	}
}
