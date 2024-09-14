/** 
 *  Copyright 2020, Turrioni Florent, All rights reserved.
 *  
 * 	This program is copyrighted for all the files and code 
 * 	included in this program. No reuse, modification or 
 * 	reselling is authorized without any legal document 
 *  approved by the owner*.
 * 
 * 	*Owner : Turrioni Florent resident in Belgium and 
 *  contactable at florent_turrioni@hotmail.com
 *  
 * */

package fr.fifoube.main.capabilities;

import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.packets.PacketMoneyData;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityMoney {

	public static final ResourceLocation CAP_KEY = new ResourceLocation(ModEconomyInc.MOD_ID, "money");
	public static final Capability<IMoney> MONEY_CAPABILITY = null;

	@SubscribeEvent
	public static void attachToPlayer(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof Player)
		{
				MoneyHolder holder;
		        if(event.getObject() instanceof ServerPlayer)
		        {
		            holder = new PlayerMoneyHolder((ServerPlayer)event.getObject());
		        }
		        else
		        {
		            holder = new MoneyHolder();
		        }
				PlayerMoneyWrapper wrapper = new PlayerMoneyWrapper(holder);
				event.addCapability(CAP_KEY, wrapper);

		}
	}
	
    @SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event) {
    	
    	Player oldPlayer = event.getOriginal();
        oldPlayer.revive();
        Player newPlayer = event.getEntity();
        oldPlayer.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(oldData -> { 	
        	newPlayer.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> data.setMoney(oldData.getMoney()));
        });
	}
		
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		if(!event.getEntity().level.isClientSide)
			event.getEntity().getCapability(MONEY_CAPABILITY).ifPresent(data -> { 
				data.setMoney(data.getMoney());
			});
	}
	
	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
	{
		if(!event.getEntity().level.isClientSide && event.getEntity() instanceof ServerPlayer)
		{
			ServerPlayer player = (ServerPlayer)event.getEntity();
			event.getEntity().getCapability(MONEY_CAPABILITY, null).ifPresent(data -> {
				
				PacketsRegistery.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new PacketMoneyData(data.getMoney()));
			});	
		}

	}
	
	@SubscribeEvent
	public static void onDimensionTravel(PlayerChangedDimensionEvent event)
	{
		if(!event.getEntity().level.isClientSide)
			event.getEntity().getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> { 
				data.setMoney(data.getMoney());
			});
	}
	
	
}
