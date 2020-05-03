/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.commands;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import fr.fifoube.main.capabilities.CapabilityMoney;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandBalance {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		
		dispatcher.register(
				LiteralArgumentBuilder.<CommandSource>literal("balance")
				.requires(src -> src.hasPermissionLevel(2))
				.then(
						Commands.literal("add")
						.then(
							  Commands.argument("players", EntityArgument.players())
							  .then(
								    Commands.argument("money", DoubleArgumentType.doubleArg(0))
								    .executes(ctx -> addToBalance(ctx.getSource(), EntityArgument.getEntitiesAllowingNone(ctx, "players"), DoubleArgumentType.getDouble(ctx, "money")))
							  )
						)
				 )
				.then(
						Commands.literal("remove")
						.then(
							  Commands.argument("players", EntityArgument.players())
							  .then(
								    Commands.argument("money", DoubleArgumentType.doubleArg(0))
								    .executes(ctx -> removeFromBalance(ctx.getSource(), EntityArgument.getEntitiesAllowingNone(ctx, "players"), DoubleArgumentType.getDouble(ctx, "money")))
							  )
						)
				 )
				.then(
					    Commands.literal("check")
					    .then(
						    Commands.argument("players", EntityArgument.players())
							.executes(ctx -> checkFromBalance(ctx.getSource(), EntityArgument.getEntitiesAllowingNone(ctx, "players")))  
						)
				)
		);
	}
	
	private static int addToBalance(CommandSource src, Collection<? extends Entity> targets, double money)
	{
		targets.forEach(e -> {
			if(e instanceof PlayerEntity)
			{
				ServerPlayerEntity playerMP = (ServerPlayerEntity)e;
				playerMP.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
					data.setMoney(data.getMoney() + money);
				});
				src.sendFeedback(new TranslationTextComponent("commands.balance.added", money, playerMP.getDisplayName().getFormattedText()), false);
			}	
		});

	 	return 0;
	}
	
	private static int removeFromBalance(CommandSource src, Collection<? extends Entity> targets, double money)
	{
		targets.forEach(e -> {
			if(e instanceof PlayerEntity)
			{
				ServerPlayerEntity playerMP = (ServerPlayerEntity)e;
				playerMP.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
					data.setMoney(data.getMoney() - money);
				});
				src.sendFeedback(new TranslationTextComponent("commands.balance.withdraw", money, playerMP.getDisplayName().getFormattedText()), false);
			}	
		});
	 	return 0;
	}
	
	private static int checkFromBalance(CommandSource src, Collection<? extends Entity> targets)
	{
		targets.forEach(e -> {
			if(e instanceof PlayerEntity)
			{
				ServerPlayerEntity playerMP = (ServerPlayerEntity)e;
				playerMP.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
					src.sendFeedback(new TranslationTextComponent("commands.balance.funds", playerMP.getDisplayName().getFormattedText(), data.getMoney()), false);
				});
			}	
		});
		return 0;		
	}
}
