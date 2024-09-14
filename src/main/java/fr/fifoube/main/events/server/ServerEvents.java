/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.events.server;

import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.config.ConfigFile;
import fr.fifoube.world.saveddata.ChunksWorldSavedData;
import fr.fifoube.world.saveddata.PlotsChunkData;
import fr.fifoube.world.saveddata.PlotsData;
import fr.fifoube.world.saveddata.PlotsWorldSavedData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Mod.EventBusSubscriber(value = Dist.DEDICATED_SERVER, modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerEvents {


	
	@SubscribeEvent
    public void onPlacedBlock(BlockEvent.EntityPlaceEvent event)
    {
		ServerPlayer player = null;
		if(event.getEntity() instanceof ServerPlayer)
		{
			player = (ServerPlayer) event.getEntity();
		}
		if(player != null)
		{
	    	ServerLevel worldIn = player.getLevel();
	    	List<ChunkPos> listPos = new ArrayList<ChunkPos>();
	    	DimensionDataStorage storage = worldIn.getDataStorage();
	    	ChunksWorldSavedData data = (ChunksWorldSavedData)storage.get(ChunksWorldSavedData::new, ChunksWorldSavedData.DATA_NAME);
	    	if(data != null)
	    	{
	    		List<PlotsChunkData> listC = data.getListContainer();
	    		for (int i = 0; i < listC.size(); i++) 
	    		{
	    			 PlotsChunkData plotsChunkData = listC.get(i);
					 for (int j = 0; j < plotsChunkData.getList().size(); j++) 
					 {			
							 String s = plotsChunkData.getList().get(j);
							 int xPos = Integer.valueOf(s.substring(s.indexOf("[") + 1, s.indexOf(",")));
							 int zPos = Integer.valueOf(s.substring(s.indexOf(",") + 2, s.indexOf("]")));
							 listPos.add(new ChunkPos(xPos, zPos));
					 }
				}
	    	}
	    	for(ChunkPos pos : listPos)
	    	{
	    		if(new ChunkPos(event.getPos()).equals(pos))
	    		{
	    			Vec3 vec = new Vec3(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
	    	    	List<AABB> listAABB = new ArrayList<AABB>();
	    	    	PlotsWorldSavedData plotsDataWSD = (PlotsWorldSavedData)storage.get(PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
	    	    	UUID uuidOwner = null;
	    	    	if(plotsDataWSD != null)
	    	    	{
	    	    		List<PlotsData> listC = plotsDataWSD.getListContainer();
	    	    		for (int i = 0; i < listC.size(); i++) 
	    	    		{
							PlotsData plotsData = plotsDataWSD.getListContainer().get(i);
							for (int j = 0; j < plotsData.getList().size(); j++) 
							{
								uuidOwner = UUID.fromString(plotsData.getList().get(1));
								int xPosFirst = Integer.valueOf(plotsData.getList().get(2));
								int zPosFirst = Integer.valueOf(plotsData.getList().get(3));
								int xPosSecond = Integer.valueOf(plotsData.getList().get(4));
								int zPosSecond = Integer.valueOf(plotsData.getList().get(5));
								listAABB.add(new AABB(xPosFirst, 0, zPosFirst, xPosSecond, 255, zPosSecond).expandTowards(2, 1, 2));
							}
						}
	    	    	}
	    	    	for(AABB checker : listAABB)
	    	    	{
	    	    		if(checker.contains(vec))
	    	    		{
	    	    			if(uuidOwner != null)
	    	    			if(!player.getUUID().equals(uuidOwner))
	    	    			{
	    	    				if(player.hasPermissions(4))
	    	    				{
	    	    					event.setCanceled(false);
	    	    				}
	    	    				else
	    	    				{
	    	    					event.setCanceled(true);
	    	    				}
	    	    			}
	    	    		}
	    	    	}
	    		}
	    	}
		}
    }
    
    @SubscribeEvent
    public void onBreakBlock(BlockEvent.BreakEvent event)
    { 	
    	ServerPlayer player = null;
		if(event.getPlayer() instanceof ServerPlayer)
		{
			player = (ServerPlayer) event.getPlayer();
		}
		if(player != null)
		{
	    	ServerLevel worldIn = player.getLevel();
	    	List<ChunkPos> listPos = new ArrayList<ChunkPos>();
	    	DimensionDataStorage storage = worldIn.getDataStorage();
	    	ChunksWorldSavedData data = (ChunksWorldSavedData)storage.get(ChunksWorldSavedData::new, ChunksWorldSavedData.DATA_NAME);
	    	if(data != null)
	    	{
	    		List<PlotsChunkData> listC = data.getListContainer();
	    		for (int i = 0; i < listC.size(); i++) 
	    		{
	    			 PlotsChunkData plotsChunkData = listC.get(i);
					 for (int j = 0; j < plotsChunkData.getList().size(); j++) 
					 {			
							 String s = plotsChunkData.getList().get(j);
							 int xPos = Integer.valueOf(s.substring(s.indexOf("[") + 1, s.indexOf(",")));
							 int zPos = Integer.valueOf(s.substring(s.indexOf(",") + 2, s.indexOf("]")));
							 listPos.add(new ChunkPos(xPos, zPos));
					 }
				}
	    	}
	    	for(ChunkPos pos : listPos)
	    	{
	    		if(new ChunkPos(event.getPos()).equals(pos))
	    		{
	    			Vec3 vec = new Vec3(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
	    	    	List<AABB> listAABB = new ArrayList<AABB>();
	    	    	PlotsWorldSavedData plotsDataWSD = (PlotsWorldSavedData)storage.get(PlotsWorldSavedData::new, PlotsWorldSavedData.DATA_NAME);
	    	    	UUID uuidOwner = null;
	    	    	if(plotsDataWSD != null)
	    	    	{
	    	    		List<PlotsData> listC = plotsDataWSD.getListContainer();
	    	    		for (int i = 0; i < listC.size(); i++) 
	    	    		{
							PlotsData plotsData = plotsDataWSD.getListContainer().get(i);
							for (int j = 0; j < plotsData.getList().size(); j++) 
							{
								uuidOwner = UUID.fromString(plotsData.getList().get(1));
								int xPosFirst = Integer.valueOf(plotsData.getList().get(2));
								int zPosFirst = Integer.valueOf(plotsData.getList().get(3));
								int xPosSecond = Integer.valueOf(plotsData.getList().get(4));
								int zPosSecond = Integer.valueOf(plotsData.getList().get(5));
								listAABB.add(new AABB(xPosFirst, 0, zPosFirst, xPosSecond, 255, zPosSecond).expandTowards(2, 1, 2));
							}
						}
	    	    	}
	    	    	for(AABB checker : listAABB)
	    	    	{
	    	    		if(checker.contains(vec))
	    	    		{
	    	    			if(uuidOwner != null)
	    	    			if(!event.getPlayer().getUUID().equals(uuidOwner))
	    	    			{
	    	    				if(player.hasPermissions(4))
	    	    				{
	    	    					event.setCanceled(false);
	    	    				}
	    	    				else
	    	    				{
	    	    					event.setCanceled(true);
	    	    				}
	    	    			}

	    	    		}
	    	    	}
	    		}
	    	}
	    }
    }

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {

		if(ConfigFile.doSpecificMobsGiveMoney) {
			LivingEntity entity = event.getEntity();
			if (event.getSource().getEntity() instanceof Player player) {

				double money;
				if (entity instanceof Zombie) {
					money = ConfigFile.zombieMoney;
				} else if (entity instanceof Skeleton) {
					money = ConfigFile.skeletonMoney;
				} else if (entity instanceof Creeper) {
					money = ConfigFile.creeperMoney;
				} else if (entity instanceof Spider) {
					money = ConfigFile.spiderMoney;
				} else if (entity instanceof Witch) {
					money = ConfigFile.witchMoney;
				} else { money = 0; }
				player.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> {
					data.addMoney(money);
				});
			}
		}
		else if(ConfigFile.doMobsGiveMoney)
		{
			LivingEntity entity = event.getEntity();
			if (event.getSource().getEntity() instanceof Player player) {
				if (entity instanceof Monster)
					player.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> {
						data.addMoney(ConfigFile.mobsMoney);
					});
			}
		}
	}
}
