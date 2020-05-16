/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.capabilities;

import java.util.Map;
import java.util.WeakHashMap;

import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.packets.PacketMoneyData;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityMoney {

	public static final ResourceLocation CAP_KEY = new ResourceLocation(ModEconomyInc.MOD_ID, "money");
	
	@CapabilityInject(IMoney.class)
	public static final Capability<IMoney> MONEY_CAPABILITY = null;
	
	public static void register()
	{
	    CapabilityManager.INSTANCE.register(IMoney.class, new DefaultMoneyStorage(), MoneyHolder::new);
	}
	
	@SubscribeEvent
	public static void attachToPlayer(AttachCapabilitiesEvent<Entity> event)
	{
		event.getObject().world.getProfiler().startSection("AttachEvent");
		if(event.getObject() instanceof PlayerEntity)
		{
				IMoney holder;
		        if(event.getObject() instanceof ServerPlayerEntity)
		        {
		            holder = new PlayerMoneyHolder((ServerPlayerEntity)event.getObject());
		        }
		        else
		        {
		            holder = CapabilityMoney.MONEY_CAPABILITY.getDefaultInstance();
		        }
				PlayerMoneyWrapper wrapper = new PlayerMoneyWrapper(holder);
				event.addCapability(CAP_KEY, wrapper);

		}
		event.getObject().world.getProfiler().endSection();
	}
	
    @SubscribeEvent
	public static void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
    	
		event.getPlayer().world.getProfiler().startSection("PlayerClone");
    	PlayerEntity oldPlayer = event.getOriginal();
        oldPlayer.revive();
        PlayerEntity newPlayer = event.getPlayer();
        oldPlayer.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(oldData -> { 	
        	newPlayer.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> data.setMoney(oldData.getMoney()));
        });
        event.getPlayer().world.getProfiler().endSection();
	}
		
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		event.getPlayer().world.getProfiler().startSection("Playerlogin");
		if(!event.getPlayer().world.isRemote)
			event.getPlayer().getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> { 
				data.setMoney(data.getMoney());
			});
		event.getPlayer().world.getProfiler().endSection();
	}
	
	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
	{
		event.getPlayer().world.getProfiler().startSection("PlayerRespawn");
		if(!event.getPlayer().world.isRemote && event.getPlayer() instanceof ServerPlayerEntity)
		{
			ServerPlayerEntity player = (ServerPlayerEntity)event.getPlayer();
			event.getPlayer().getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
				
				PacketsRegistery.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new PacketMoneyData(data.getMoney()));
			});	
		}
		event.getPlayer().world.getProfiler().endSection();

	}
	

		
}
