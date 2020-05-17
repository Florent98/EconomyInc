/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.config.ConfigFile;
import fr.fifoube.world.saveddata.ChunksWorldSavedData;
import fr.fifoube.world.saveddata.PlotsChunkData;
import fr.fifoube.world.saveddata.PlotsData;
import fr.fifoube.world.saveddata.PlotsWorldSavedData;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraftforge.registries.ForgeRegistries;

public class CommandsPlots {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
			
			dispatcher.register(
					LiteralArgumentBuilder.<CommandSource>literal("plot")
					.requires(src -> src.hasPermissionLevel(4))
					.then(
							Commands.literal("create")
							.then(
								Commands.argument("from", BlockPosArgument.blockPos())
								  .then(
									Commands.argument("to", BlockPosArgument.blockPos())
										.then(
											Commands.argument("name", StringArgumentType.string())
											.then(
												Commands.argument("price", DoubleArgumentType.doubleArg(1.0))
												.executes(ctx -> createPlot(ctx.getSource(), BlockPosArgument.getLoadedBlockPos(ctx, "from"), BlockPosArgument.getLoadedBlockPos(ctx, "to"), StringArgumentType.getString(ctx, "name") ,DoubleArgumentType.getDouble(ctx, "price")))
													)
											 )
									   )
								 )
						 )
					.then(
							Commands.literal("remove")
							.then(
								Commands.argument("name", StringArgumentType.string())
								.executes(ctx -> removePlot(ctx.getSource(), StringArgumentType.getString(ctx, "name")))
								)
						)
					.then(
							Commands.literal("list")
							.executes(ctx -> listPlot(ctx.getSource()))
					)
					.then(
							Commands.literal("assign")
							.then(
								Commands.argument("player", EntityArgument.player())
								.then(
									Commands.argument("name", StringArgumentType.string())
									.executes(ctx -> assignPlotTo(ctx.getSource(), EntityArgument.getPlayer(ctx, "player"), StringArgumentType.getString(ctx, "name")))
								)

							)
					)
					.then(
							Commands.literal("teleport")
							.then(
								Commands.argument("name", StringArgumentType.string())
								.executes(ctx -> teleportToPlot(ctx.getSource(), null, StringArgumentType.getString(ctx, "name"), false))
								.then(
										Commands.argument("players", EntityArgument.players())
										.executes(ctx -> teleportToPlot(ctx.getSource(), EntityArgument.getEntitiesAllowingNone(ctx, "players"), StringArgumentType.getString(ctx, "name"), true))
								)
							)
					)
			);
	}
	
	private static int teleportToPlot(CommandSource src, Collection<? extends Entity> targets, String plotsName, boolean tpOther)
	{
    	int indexToProceedBuy = -1;
		ServerPlayerEntity player = null;
		try {
			player = src.asPlayer();
		} catch (CommandSyntaxException e) {e.printStackTrace();}
		
		if(player != null)
		{
			ServerPlayerEntity playerTarget = player;
			ServerWorld worldIn = player.getServerWorld();
			DimensionSavedDataManager storage = worldIn.getSavedData();
			PlotsWorldSavedData dataWorld = (PlotsWorldSavedData)storage.getOrCreate(PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
			if(dataWorld != null)
			{
				for (int i = 0; i < dataWorld.getListContainer().size(); i++) 
				{
					PlotsData plotsData = dataWorld.getListContainer().get(i);
					if(plotsData != null)
					if(plotsData.getList().get(0).equals(plotsName))
					{
						indexToProceedBuy = i;
					}
				}
				if(indexToProceedBuy != -1)
				{
					PlotsData plotsData = dataWorld.getListContainer().get(indexToProceedBuy);
					Vec3d center = getCenter(plotsData.xPosFirst, plotsData.yPos, plotsData.zPosFirst, plotsData.xPosSecond, plotsData.yPos, plotsData.zPosSecond);
					if(!tpOther)
					{
						player.teleport(player.getServerWorld(), center.x, center.y, center.z, player.rotationYaw, player.rotationPitch);
						src.sendFeedback(new TranslationTextComponent("commands.plot.teleport.success", player.getDisplayName().getFormattedText(), plotsData.name), false);
					}
					else
					{
						targets.forEach(e -> {
							if(e instanceof ServerPlayerEntity)
							{
								ServerPlayerEntity playerMP = (ServerPlayerEntity)e;
								playerMP.teleport(playerTarget.getServerWorld(), center.x, center.y, center.z, playerMP.rotationYaw, playerMP.rotationPitch);
								src.sendFeedback(new TranslationTextComponent("commands.plot.teleport.success", playerMP.getDisplayName().getFormattedText(), plotsData.name), false);
							}	
						});
					}
				}
				else
				{
					src.sendFeedback(new TranslationTextComponent("commands.plot.teleport.fail"), false);
				}
			}
		}
		return 0;
	}
	
	private static int assignPlotTo(CommandSource src, ServerPlayerEntity assignedPlayer, String plotsName) {
		
    	boolean canProceedBuy = false;
    	int indexToProceedBuy = -1;
		ServerPlayerEntity player = null;

		try {
			player = src.asPlayer();
		} catch (CommandSyntaxException e) {e.printStackTrace();}
		
		if(player != null)
		{
				ServerWorld worldIn = player.getServerWorld();
				DimensionSavedDataManager storage = worldIn.getSavedData();
				PlotsWorldSavedData dataWorld = (PlotsWorldSavedData)storage.getOrCreate(PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
				if(dataWorld != null)
				{
					for (int i = 0; i < dataWorld.getListContainer().size(); i++) 
					{
						PlotsData plotsData = dataWorld.getListContainer().get(i);
						if(plotsData != null)
						if(plotsData.getList().get(0).equals(plotsName))
						{
							boolean bought = plotsData.getBought();
							if(!bought)
							{
								indexToProceedBuy = i;
								canProceedBuy = true;
							}
							else
							{
								src.sendFeedback(new TranslationTextComponent("commands.plotbuy.alreadybought"), false);
							}
						}
					}
				}
				if(canProceedBuy && indexToProceedBuy != -1)
				{
					PlotsData plotsData = dataWorld.getListContainer().get(indexToProceedBuy);
					plotsData.bought = true;
					plotsData.owner = assignedPlayer.getUniqueID().toString();		
					dataWorld.markDirty();
					CommandsPlotsBuy.replaceSign(worldIn, plotsData.xPosFirst, plotsData.yPos, plotsData.zPosFirst, plotsData.xPosSecond, plotsData.zPosSecond, plotsData.name, plotsData.owner);	
					saveAll(src, false);
					src.sendFeedback(new TranslationTextComponent("commands.plot.assigned.success", player, assignedPlayer.getDisplayName().getFormattedText()), false);
				}
		}
		return 0;
	}

	public static int listPlot(CommandSource src)
	{
    	ServerPlayerEntity player = null;
    	
		try {
			player = src.asPlayer();
		} catch (CommandSyntaxException e) {e.printStackTrace();}
		
		if(player != null)
		{
			DimensionSavedDataManager storage = player.getServerWorld().getSavedData();
			PlotsWorldSavedData data = (PlotsWorldSavedData)storage.getOrCreate(PlotsWorldSavedData::new, ModEconomyInc.MOD_ID + "_PlotsData");
			List<String> namePlot = new ArrayList<String>();
			if(data != null)
			{
				for (int i = 0; i < data.getListContainer().size(); i++) 
				{
					PlotsData plotsData = data.getListContainer().get(i);
					if(plotsData != null)
					{
						namePlot.add(plotsData.getList().get(0));
					}
				}
				Collections.sort(namePlot);
				String name = "Plots name : ";
				for (int i = 0; i < namePlot.size(); i++) {
					
					String seperator = ",";
					if(i+1 == namePlot.size())
					{
						seperator = ".";
					}
					name += namePlot.get(i) + seperator; 
				}
				src.sendFeedback(new TranslationTextComponent(name), false);
			}
		}
		
		return 0;
	}

	
	public static int createPlot(CommandSource src, BlockPos from, BlockPos to, String name,double price)
    {
    	boolean canProceed = true;
    	ServerPlayerEntity player = null;
    	
		try {
			player = src.asPlayer();
			} catch (CommandSyntaxException e) {
			e.printStackTrace();
			}
		
		
		if(player != null)
		{
					if(Math.abs(to.getX() - from.getX()) < 26 && Math.abs(to.getZ() - from.getZ()) < 26)
					{
						DimensionSavedDataManager storage = player.getServerWorld().getSavedData();
						ServerWorld worldIn = player.getServerWorld();
						PlotsWorldSavedData data = (PlotsWorldSavedData)storage.getOrCreate(PlotsWorldSavedData::new, ModEconomyInc.MOD_ID + "_PlotsData");
						if(data != null)
						{
							for (int i = 0; i < data.getListContainer().size(); i++) 
							{
								PlotsData plotsData = data.getListContainer().get(i);
								if(plotsData != null)
								if(plotsData.getList().get(0).equals(name))
								{
									canProceed = false;
									src.sendFeedback(new TranslationTextComponent("commands.plot.samename"), false);
								}
							}
						}
						if(canProceed)
						{
							createData(src, worldIn, name, player, from.getX(), from.getZ(), to.getX(), to.getZ(), from.getY(), price);
							src.sendFeedback(new TranslationTextComponent("commands.plot.success"), false);
							saveAll(src, false);
						}

					}
					else
					{
						src.sendFeedback(new TranslationTextComponent("commands.plot.sizeexceed"), false);
					}
		}
		else
		{
			src.sendFeedback(new TranslationTextComponent("commands.plot.noplayer"), false);
		}
		return 0;
    }
	
	public static int removePlot(CommandSource src, String namePlot)
    {
    	boolean canProceedRemove = false;
    	int indexToProceed = -1;
    	ServerPlayerEntity player = null;
    	
		try {
			player = src.asPlayer();
			} catch (CommandSyntaxException e) {
			e.printStackTrace();
			}
		
		DimensionSavedDataManager storage = player.getServerWorld().getSavedData();
		PlotsWorldSavedData data = (PlotsWorldSavedData)storage.getOrCreate(PlotsWorldSavedData::new, ModEconomyInc.MOD_ID + "_PlotsData");
    	if(player != null)
		if(data != null)
		{

			for (int i = 0; i < data.getListContainer().size(); i++) 
			{
				PlotsData plotsData = data.getListContainer().get(i);
				if(plotsData != null)
				if(plotsData.getList().get(0).equals(namePlot))
				{
					indexToProceed = i;
					canProceedRemove = true;
					src.sendFeedback(new TranslationTextComponent("commands.plot.removed"), false);
					saveAll(src, false);
				}
				else
				{
					src.sendFeedback(new TranslationTextComponent("commands.plot.nomatch"), false);
				}
			}
		}
		if(canProceedRemove && indexToProceed != -1)
		{
			data.getListContainer().remove(indexToProceed);
			data.markDirty();
		}
		return 0;
    }
	
	
	private static void createData(CommandSource src, ServerWorld worldIn, String name, ServerPlayerEntity playerIn, int xPosFirst, int zPosFirst, int xPosSecond, int zPosSecond, int yPos, double priceIn) throws CommandException 
	{
		/** WORLD/DIMENSION DATA SAVING **/
		List<ChunkPos> listChunk = calculatingChunks(worldIn, xPosFirst, zPosFirst, xPosSecond, zPosSecond, yPos);
		PlotsData plotsData = new PlotsData(name, playerIn.getUniqueID().toString(), xPosFirst, zPosFirst, xPosSecond, zPosSecond, yPos, priceIn, false);
		PlotsChunkData chunkData = new PlotsChunkData(listChunk);
		PlotsWorldSavedData storagePlots = worldIn.getSavedData().getOrCreate(PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
		storagePlots.getListContainer().add(plotsData);
		storagePlots.markDirty();
		ChunksWorldSavedData storageChunk = worldIn.getSavedData().getOrCreate(ChunksWorldSavedData::new, ChunksWorldSavedData.DATA_NAME);
		storageChunk.getListContainer().add(chunkData);
		storageChunk.markDirty();
		createBorders(src, worldIn, name, playerIn.getDisplayName().getString(), xPosFirst, zPosFirst, xPosSecond, zPosSecond, yPos, priceIn);
	}
	
	private static List<ChunkPos> calculatingChunks(World worldIn, int xPosFirst, int zPosFirst, int xPosSecond, int zPosSecond, int yPos) 
	{
		List<ChunkPos> listChunk = new ArrayList<ChunkPos>();
		int minusXToTake;
		int minusZToTake;
		int maxXToTake;
		int maxZToTake;
		if(xPosFirst < xPosSecond)
		{
			minusXToTake = Math.floorDiv(xPosFirst, 16);
			maxXToTake = Math.floorDiv(xPosSecond, 16);
		}
		else
		{
			minusXToTake = Math.floorDiv(xPosSecond, 16);
			maxXToTake = Math.floorDiv(xPosFirst, 16);
			
		}
		if(zPosFirst < zPosSecond)
		{
			minusZToTake = Math.floorDiv(zPosFirst, 16);
			maxZToTake = Math.floorDiv(zPosSecond, 16);
		}
		else
		{
			minusZToTake = Math.floorDiv(zPosSecond, 16);
			maxZToTake = Math.floorDiv(zPosFirst, 16);
		}
		for(int x = minusXToTake; x <= maxXToTake; x++) 
		{
			for(int z = minusZToTake; z <= maxZToTake; z++)
			{
				Chunk chunkIn = worldIn.getChunk(x, z);
				listChunk.add(chunkIn.getPos());
			}
		}
		return listChunk;
	}
	
	private static void createBorders(CommandSource src, ServerWorld worldIn, String name, String senderName, int xPosFirst, int zPosFirst, int xPosSecond, int zPosSecond, int yPos, double priceIn)
	{
		AxisAlignedBB area = new AxisAlignedBB(new BlockPos(xPosFirst, yPos, zPosFirst), new BlockPos(xPosSecond, yPos, zPosSecond));
		AxisAlignedBB areaGrown = area.grow(1.0D, 0.0D, 1.0D);
		Vec3d vec = getCenter(xPosFirst, yPos, zPosFirst, xPosSecond, yPos, zPosSecond);
		BlockPos posSign = new BlockPos(vec.x, vec.y, vec.z);
		
		Block block = Blocks.SMOOTH_STONE_SLAB;
		String rl = ConfigFile.plotBorderBlock; 
		ResourceLocation location = new ResourceLocation(rl.substring(0, rl.indexOf(":")), rl.substring(rl.indexOf(":") + 1));
		if(ForgeRegistries.BLOCKS.getValue(location) != Blocks.AIR)
		{
			block = ForgeRegistries.BLOCKS.getValue(location);	
		}
		else
		{
			block = Blocks.SMOOTH_STONE_SLAB;
			src.sendFeedback(new TranslationTextComponent("commands.plot.wrongPlotBorder"), false);
		}
		
		worldIn.setBlockState(posSign, Blocks.AIR.getDefaultState());
		
		Iterable<BlockPos> posToPlace = BlockPos.getAllInBoxMutable(new BlockPos(areaGrown.minX, yPos, areaGrown.minZ), new BlockPos(areaGrown.maxX, yPos, areaGrown.maxZ));
		Iterable<BlockPos> posToRemove = BlockPos.getAllInBoxMutable(new BlockPos(area.minX, yPos, area.minZ), new BlockPos(area.maxX, yPos, area.maxZ));
		for(BlockPos posNew : posToPlace)
		{
			worldIn.setBlockState(posNew, block.getDefaultState());
		}
		for(BlockPos posNew : posToRemove)
		{
			worldIn.setBlockState(posNew, Blocks.AIR.getDefaultState());
		}
		
		worldIn.setBlockState(posSign, Blocks.OAK_SIGN.getDefaultState(), 2);
		
		TileEntity tileEntityIn = new SignTileEntity();
		tileEntityIn.validate();
		
		worldIn.setTileEntity(posSign, tileEntityIn);
		
		SignTileEntity signTe = (SignTileEntity)worldIn.getTileEntity(posSign);
		
		if(signTe != null)
		{
			signTe.signText[0] = new StringTextComponent("[" + name + "]").setStyle(new Style().setBold(true).setColor(TextFormatting.BLUE));
			signTe.signText[1] = new StringTextComponent(senderName).setStyle(new Style().setBold(true).setColor(TextFormatting.BLACK));
			signTe.signText[2] = new StringTextComponent(String.valueOf(priceIn) + "$").setStyle(new Style().setBold(true).setColor(TextFormatting.BLACK));
			signTe.signText[3] = new StringTextComponent("[BUY]").setStyle(new Style().setBold(true).setColor(TextFormatting.GREEN));
			signTe.markDirty();
		}
		
	}
	
	public static Vec3d getCenter(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
	{
		return new Vec3d(minX + (maxX - minX) * 0.5D, minY + (maxY - minY) * 0.5D, minZ + (maxZ - minZ) * 0.5D);
	}
	
	 private static int saveAll(CommandSource source, boolean flush) {
	      MinecraftServer minecraftserver = source.getServer();
	      minecraftserver.getPlayerList().saveAllPlayerData();
	      boolean flag = minecraftserver.save(true, flush, true);
	      if(flag)
	      {
	    	  source.sendFeedback(new TranslationTextComponent("commands.plot.saved"), false);
	    	  return 1;
	      }
	      else
	      {
	    	  source.sendFeedback(new TranslationTextComponent("commands.plot.errorsaved"), false);
	    	  return 0;
	      }
	   }
}
