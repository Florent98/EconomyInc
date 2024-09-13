/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.world.saveddata.PlotsData;
import fr.fifoube.world.saveddata.PlotsWorldSavedData;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class CommandsPlotsBuy {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		
		dispatcher.register(
				LiteralArgumentBuilder.<CommandSourceStack>literal("plotbuy")
				.then(
						Commands.literal("buy")
						.requires(src -> src.hasPermission(0))
						.then(
							Commands.argument("plotname", StringArgumentType.string())
							.executes(ctx -> requireBuy(ctx.getSource(), StringArgumentType.getString(ctx, "plotname")))
						)
				 )
		);
	}
	
	public static int requireBuy(CommandSourceStack src, String plotName)
	{
    	boolean canProceedBuy = false;
    	int indexToProceedBuy = -1;
    	ServerPlayer player = null;
    	
		try {
			player = src.getPlayerOrException();
			} catch (CommandSyntaxException e) {
			e.printStackTrace();
			}
    	if(player != null)
    	{	
    		String uuid = player.getStringUUID();
    		ServerLevel worldIn = player.getLevel();
    		DimensionDataStorage storage = worldIn.getDataStorage();
			PlotsWorldSavedData dataWorld = (PlotsWorldSavedData)storage.computeIfAbsent(PlotsWorldSavedData::new, PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
			if(dataWorld != null)
			{
				for (int i = 0; i < dataWorld.getListContainer().size(); i++) 
				{
					PlotsData plotsData = dataWorld.getListContainer().get(i);
					if(plotsData != null)
					if(plotsData.getList().get(0).equals(plotName))
					{
						boolean bought = plotsData.getBought();
						if(!bought)
						{
							indexToProceedBuy = i;
							canProceedBuy = true;
						}
						else
						{
							src.sendFailure(new TranslatableComponent("commands.plotbuy.alreadybought"));
						}
					}
				}
			}
			if(canProceedBuy && indexToProceedBuy != -1)
			{
				ServerPlayer s = player;
				PlotsData plotsData = dataWorld.getListContainer().get(indexToProceedBuy);
				player.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> {
				
					double playerMoney = data.getMoney();
					if(playerMoney >= plotsData.price)
					{
						plotsData.bought = true;
						plotsData.owner = uuid;		
						dataWorld.setDirty();
						double newMoney = playerMoney - plotsData.price;
						data.setMoney(newMoney);
						replaceSign(worldIn, plotsData.xPosFirst, plotsData.yPos, plotsData.zPosFirst, plotsData.xPosSecond, plotsData.zPosSecond, plotsData.name, plotsData.owner);
						ModEconomyInc.LOGGER_MONEY.info(s.getDisplayName().getString() + " has bought plot " + plotsData.name + ". Balance was at " + data.getMoney() + ", balance is now " + (data.getMoney() - plotsData.price) + "." + "[UUID: " + s.getUUID() + ",PlotID: " + plotsData.name +"]");
						src.sendSuccess(new TranslatableComponent("commands.plotbuy.success"), false);
					}	
					else
					{
			    		src.sendFailure(new TranslatableComponent("commands.plot.noEnoughMoney"));
					}
				});		
			}
    	}
    	else
    	{
    		src.sendFailure(new TranslatableComponent("commands.plot.noplayer"));
    	}
		return 0;
	}
	
	public static Vec3 getCenter(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
	{
		return new Vec3(minX + (maxX - minX) * 0.5D, minY + (maxY - minY) * 0.5D, minZ + (maxZ - minZ) * 0.5D);
	}
	
	public static void replaceSign(ServerLevel worldIn, int xPosFirst, int yPos, int zPosFirst, int xPosSecond, int zPosSecond, String name, String owner)
	{
		Player playerIn = worldIn.getPlayerByUUID(UUID.fromString(owner));
		if(playerIn != null)
		{
			Vec3 vec = getCenter(xPosFirst, yPos, zPosFirst, xPosSecond, yPos, zPosSecond);
			BlockPos posSign = new BlockPos(vec);
			
			worldIn.destroyBlock(posSign, false);
			worldIn.setBlock(posSign, Blocks.OAK_SIGN.defaultBlockState(), 2);
			
			BlockEntity tileEntityIn = new SignBlockEntity(posSign, Blocks.OAK_SIGN.defaultBlockState());
			tileEntityIn.clearRemoved();
			
			worldIn.setBlockEntity(tileEntityIn);
			
			SignBlockEntity signTe = (SignBlockEntity)worldIn.getBlockEntity(posSign);
			
			if(signTe != null)
			{
				signTe.setMessage(0 , new TextComponent("[" + name + "]").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLUE));
				signTe.setMessage(1 , new TextComponent("Owned by").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLACK));
				signTe.setMessage(2 , new TextComponent(playerIn.getDisplayName().getString()).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLACK));
				signTe.setMessage(3 , new TextComponent("[SOLD]").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.RED));
				signTe.setChanged();
			}
		}
		
	}


}
