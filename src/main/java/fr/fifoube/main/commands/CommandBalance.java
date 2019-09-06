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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

public class CommandBalance {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		
		dispatcher.register(
				LiteralArgumentBuilder.<CommandSource>literal("balance")
				.requires(src -> src.hasPermissionLevel(2))
				.then(
						Commands.literal("add")
						.then(
							  Commands.argument("players", EntityArgument.multiplePlayers())
							  .then(
								    Commands.argument("money", DoubleArgumentType.doubleArg(0))
								    .executes(ctx -> addToBalance(ctx.getSource(), EntityArgument.getEntitiesAllowingNone(ctx, "players"), DoubleArgumentType.getDouble(ctx, "money")))
							  )
						)
				 )
				.then(
						Commands.literal("remove")
						.then(
							  Commands.argument("players", EntityArgument.multiplePlayers())
							  .then(
								    Commands.argument("money", DoubleArgumentType.doubleArg(0))
								    .executes(ctx -> removeFromBalance(ctx.getSource(), EntityArgument.getEntitiesAllowingNone(ctx, "players"), DoubleArgumentType.getDouble(ctx, "money")))
							  )
						)
				 )
				.then(
					    Commands.literal("check")
					    .then(
						    Commands.argument("players", EntityArgument.multiplePlayers())
							.executes(ctx -> checkFromBalance(ctx.getSource(), EntityArgument.getEntitiesAllowingNone(ctx, "players")))  
						)
				)
		);
	}
	
	private static int addToBalance(CommandSource src, Collection<? extends Entity> targets, double money)
	{
		targets.forEach(e -> {
			if(e instanceof EntityPlayer)
			{
				EntityPlayerMP playerMP = (EntityPlayerMP)e;
				playerMP.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
					data.setMoney(data.getMoney() + money);
				});
				src.sendFeedback(new TextComponentString(money + " were added to " + playerMP.getDisplayName().getFormattedText() + " account."), false);
			}	
		});

	 	return 0;
	}
	
	private static int removeFromBalance(CommandSource src, Collection<? extends Entity> targets, double money)
	{
		targets.forEach(e -> {
			if(e instanceof EntityPlayer)
			{
				EntityPlayerMP playerMP = (EntityPlayerMP)e;
				playerMP.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
					data.setMoney(data.getMoney() - money);
				});
				src.sendFeedback(new TextComponentString(money + " were withdrawn to " + playerMP.getDisplayName().getFormattedText() + " account."), false);
			}	
		});
	 	return 0;
	}
	
	private static int checkFromBalance(CommandSource src, Collection<? extends Entity> targets)
	{
		targets.forEach(e -> {
			if(e instanceof EntityPlayer)
			{
				EntityPlayerMP playerMP = (EntityPlayerMP)e;
				playerMP.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
					src.sendFeedback(new TextComponentString(data.getName() + " funds are " + data.getMoney()), false);
				});
			}	
		});
		return 0;		
	}
}