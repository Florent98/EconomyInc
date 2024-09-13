/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.fifoube.main.config.ConfigFile;
import fr.fifoube.world.saveddata.ChunksWorldSavedData;
import fr.fifoube.world.saveddata.PlotsChunkData;
import fr.fifoube.world.saveddata.PlotsData;
import fr.fifoube.world.saveddata.PlotsWorldSavedData;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CommandsPlots {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
			
			dispatcher.register(
					LiteralArgumentBuilder.<CommandSourceStack>literal("plot")
					.requires(src -> src.hasPermission(3))
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
										.executes(ctx -> teleportToPlot(ctx.getSource(), EntityArgument.getEntities(ctx, "players"), StringArgumentType.getString(ctx, "name"), true))
								)
							)
					)
			);
	}
	
	private static int teleportToPlot(CommandSourceStack src, Collection<? extends Entity> targets, String plotsName, boolean tpOther)
	{
    	int indexToProceedBuy = -1;
		ServerPlayer player = null;
		try {
			player = src.getPlayerOrException();
		} catch (CommandSyntaxException e) {e.printStackTrace();}
		
		if(player != null)
		{
			ServerPlayer playerTarget = player;
			ServerLevel worldIn = player.getLevel();
			DimensionDataStorage storage = worldIn.getDataStorage();
			PlotsWorldSavedData dataWorld = (PlotsWorldSavedData)storage.computeIfAbsent(PlotsWorldSavedData::new, PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
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
					Vec3 center = getCenter(plotsData.xPosFirst, plotsData.yPos, plotsData.zPosFirst, plotsData.xPosSecond, plotsData.yPos, plotsData.zPosSecond);
					if(!tpOther)
					{
						player.teleportTo(player.getLevel(), center.x, center.y, center.z, player.getYRot(), player.getXRot());
						src.sendSuccess(new TranslatableComponent("commands.plot.teleport.success", player.getDisplayName().getString(), plotsData.name), false);
					}
					else
					{
						targets.forEach(e -> {
							if(e instanceof ServerPlayer)
							{
								ServerPlayer playerMP = (ServerPlayer)e;
								playerMP.teleportTo(playerTarget.getLevel(), center.x, center.y, center.z, playerMP.getYRot(), playerMP.getXRot());
								src.sendSuccess(new TranslatableComponent("commands.plot.teleport.success", playerMP.getDisplayName().getString(), plotsData.name), false);
							}	
						});
					}
				}
				else
				{
					src.sendFailure(new TranslatableComponent("commands.plot.teleport.fail"));
				}
			}
		}
		return 0;
	}
	
	private static int assignPlotTo(CommandSourceStack src, ServerPlayer assignedPlayer, String plotsName) {
		
    	boolean canProceedBuy = false;
    	int indexToProceedBuy = -1;
		ServerPlayer player = null;

		try {
			player = src.getPlayerOrException();
		} catch (CommandSyntaxException e) {e.printStackTrace();}
		
		if(player != null)
		{
				ServerLevel worldIn = player.getLevel();
				DimensionDataStorage storage = worldIn.getDataStorage();
				PlotsWorldSavedData dataWorld = (PlotsWorldSavedData)storage.computeIfAbsent(PlotsWorldSavedData::new, PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
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
								src.sendFailure(new TranslatableComponent("commands.plotbuy.alreadybought"));
							}
						}
					}
				}
				if(canProceedBuy && indexToProceedBuy != -1)
				{
					PlotsData plotsData = dataWorld.getListContainer().get(indexToProceedBuy);
					plotsData.bought = true;
					plotsData.owner = assignedPlayer.getStringUUID();
					dataWorld.setDirty();
					CommandsPlotsBuy.replaceSign(worldIn, plotsData.xPosFirst, plotsData.yPos, plotsData.zPosFirst, plotsData.xPosSecond, plotsData.zPosSecond, plotsData.name, plotsData.owner);	
					saveAll(src, false);
					src.sendSuccess(new TranslatableComponent("commands.plot.assigned.success", player, assignedPlayer.getDisplayName().getString()), false);
				}
		}
		return 0;
	}

	public static int listPlot(CommandSourceStack src)
	{
    	ServerPlayer player = null;
    	
		try {
			player = src.getPlayerOrException();
		} catch (CommandSyntaxException e) {e.printStackTrace();}
		
		if(player != null)
		{
			DimensionDataStorage storage = player.getLevel().getDataStorage();
			PlotsWorldSavedData data = (PlotsWorldSavedData)storage.computeIfAbsent(PlotsWorldSavedData::new, PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
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
				src.sendSuccess(new TextComponent(name), false);
			}
		}
		
		return 0;
	}

	
	public static int createPlot(CommandSourceStack src, BlockPos from, BlockPos to, String name, double price)
    {
    	boolean canProceed = true;
    	ServerPlayer player = null;
    	
		try {
			player = src.getPlayerOrException();
			} catch (CommandSyntaxException e) {
			e.printStackTrace();
			}
		
		
		if(player != null)
		{
					if(Math.abs(to.getX() - from.getX()) < 26 && Math.abs(to.getZ() - from.getZ()) < 26)
					{
						DimensionDataStorage storage = player.getLevel().getDataStorage();
						ServerLevel worldIn = player.getLevel();
						PlotsWorldSavedData data = (PlotsWorldSavedData)storage.computeIfAbsent(PlotsWorldSavedData::new, PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
						if(data != null)
						{
							for (int i = 0; i < data.getListContainer().size(); i++) 
							{
								PlotsData plotsData = data.getListContainer().get(i);
								if(plotsData != null)
								if(plotsData.getList().get(0).equals(name))
								{
									canProceed = false;
									src.sendFailure(new TranslatableComponent("commands.plot.samename"));
								}
							}
						}
						if(canProceed)
						{
							createData(src, worldIn, name, player, from.getX(), from.getZ(), to.getX(), to.getZ(), from.getY(), price);
							src.sendSuccess(new TranslatableComponent("commands.plot.success"), false);
							saveAll(src, false);
						}

					}
					else
					{
						src.sendFailure(new TranslatableComponent("commands.plot.sizeexceed"));
					}
		}
		else
		{
			src.sendFailure(new TranslatableComponent("commands.plot.noplayer"));
		}
		return 0;
    }
	
	public static int removePlot(CommandSourceStack src, String namePlot)
    {
    	boolean canProceedRemove = false;
    	int indexToProceed = -1;
    	ServerPlayer player = null;
    	
		try {
			player = src.getPlayerOrException();
			} catch (CommandSyntaxException e) {
			e.printStackTrace();
			}
		
		DimensionDataStorage storage = player.getLevel().getDataStorage();
		PlotsWorldSavedData data = (PlotsWorldSavedData)storage.computeIfAbsent(PlotsWorldSavedData::new, PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
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
					src.sendSuccess(new TranslatableComponent("commands.plot.removed"), false);
					saveAll(src, false);
				}
				else
				{
					src.sendFailure(new TranslatableComponent("commands.plot.nomatch"));
				}
			}
		}
		if(canProceedRemove && indexToProceed != -1)
		{
			data.getListContainer().remove(indexToProceed);
			data.setDirty();
		}
		return 0;
    }
	
	
	private static void createData(CommandSourceStack src, ServerLevel worldIn, String name, ServerPlayer playerIn, int xPosFirst, int zPosFirst, int xPosSecond, int zPosSecond, int yPos, double priceIn) 
	{
		/** WORLD/DIMENSION DATA SAVING **/
		List<ChunkPos> listChunk = calculatingChunks(worldIn, xPosFirst, zPosFirst, xPosSecond, zPosSecond, yPos);
		PlotsData plotsData = new PlotsData(name, playerIn.getStringUUID(), xPosFirst, zPosFirst, xPosSecond, zPosSecond, yPos, priceIn, false);
		PlotsChunkData chunkData = new PlotsChunkData(listChunk);
		PlotsWorldSavedData storagePlots = worldIn.getDataStorage().computeIfAbsent(PlotsWorldSavedData::new, PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
		storagePlots.getListContainer().add(plotsData);
		storagePlots.setDirty();
		ChunksWorldSavedData storageChunk = worldIn.getDataStorage().computeIfAbsent(ChunksWorldSavedData::new, ChunksWorldSavedData::new, ChunksWorldSavedData.DATA_NAME);
		storageChunk.getListContainer().add(chunkData);
		storageChunk.setDirty();
		createBorders(src, worldIn, name, playerIn.getDisplayName().getString(), xPosFirst, zPosFirst, xPosSecond, zPosSecond, yPos, priceIn);
	}
	
	private static List<ChunkPos> calculatingChunks(Level worldIn, int xPosFirst, int zPosFirst, int xPosSecond, int zPosSecond, int yPos) 
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
				LevelChunk chunkIn = worldIn.getChunk(x, z);
				listChunk.add(chunkIn.getPos());
			}
		}
		return listChunk;
	}
	
	private static void createBorders(CommandSourceStack src, ServerLevel worldIn, String name, String senderName, int xPosFirst, int zPosFirst, int xPosSecond, int zPosSecond, int yPos, double priceIn)
	{
		AABB area = new AABB(new BlockPos(xPosFirst, yPos, zPosFirst), new BlockPos(xPosSecond, yPos, zPosSecond));
		AABB areaGrown = area.inflate(1.0D, 0.0D, 1.0D);
		Vec3 vec = getCenter(xPosFirst, yPos, zPosFirst, xPosSecond, yPos, zPosSecond);
		BlockPos posSign = new BlockPos(vec);
		
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
			src.sendFailure(new TranslatableComponent("commands.plot.wrongPlotBorder"));
		}
		
		worldIn.setBlockAndUpdate(posSign, Blocks.AIR.defaultBlockState());
		
		Vec3 minGrow = new Vec3(areaGrown.minX, yPos, areaGrown.minZ);
		Vec3 maxGrow = new Vec3(areaGrown.maxX, yPos, areaGrown.maxZ);
		
		Vec3 min = new Vec3(area.minX, yPos, area.minZ);
		Vec3 max = new Vec3(area.maxX, yPos, area.maxZ);
		
		Iterable<BlockPos> posToPlace = BlockPos.betweenClosed(new BlockPos(minGrow), new BlockPos(maxGrow));
		Iterable<BlockPos> posToRemove = BlockPos.betweenClosed(new BlockPos(min), new BlockPos(max));
		for(BlockPos posNew : posToPlace)
		{
			worldIn.setBlockAndUpdate(posNew, block.defaultBlockState());
		}
		for(BlockPos posNew : posToRemove)
		{
			worldIn.setBlockAndUpdate(posNew, Blocks.AIR.defaultBlockState());
		}
				
		SignBlockEntity tileEntityIn = new SignBlockEntity(posSign, Blocks.OAK_SIGN.defaultBlockState());
		tileEntityIn.clearRemoved();
		
		
		worldIn.setBlock(posSign, Blocks.OAK_SIGN.defaultBlockState(), 3);
		worldIn.setBlockEntity(tileEntityIn);
				
		if(tileEntityIn != null)
		{
			tileEntityIn.setMessage(0 , new TextComponent("[" + name + "]").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLUE));
			tileEntityIn.setMessage(1 , new TextComponent(senderName).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLACK));
			tileEntityIn.setMessage(2 , new TextComponent(String.valueOf(priceIn) + "$").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLACK));
			tileEntityIn.setMessage(3 , new TextComponent("[BUY]").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GREEN));
			tileEntityIn.setChanged();
		}
		
	}
	
	public static Vec3 getCenter(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
	{
		return new Vec3(minX + (maxX - minX) * 0.5D, minY + (maxY - minY) * 0.5D, minZ + (maxZ - minZ) * 0.5D);
	}
	
	 private static int saveAll(CommandSourceStack source, boolean flush) {
	      MinecraftServer minecraftserver = source.getServer();
	      minecraftserver.getPlayerList().saveAll();
	      boolean flag = minecraftserver.saveAllChunks(true, flush, true);
	      if(flag)
	      {
	    	  source.sendSuccess(new TranslatableComponent("commands.plot.saved"), false);
	    	  return 1;
	      }
	      else
	      {
	    	  source.sendFailure(new TranslatableComponent("commands.plot.errorsaved"));
	    	  return 0;
	      }
	   }
}
