/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.capabilities;

import java.util.Map;
import java.util.WeakHashMap;

import fr.fifoube.main.ModEconomyInc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityMoney {

	public static final ResourceLocation CAP_KEY = new ResourceLocation(ModEconomyInc.MOD_ID, "money");
	
	@CapabilityInject(IMoney.class)
	public static final Capability<IMoney> MONEY_CAPABILITY = null;
	
	private static final Map<Entity, IMoney> INVALIDATED_CAPS = new WeakHashMap<>();

	public static void register()
	{
	    CapabilityManager.INSTANCE.register(IMoney.class, new DefaultMoneyStorage(), MoneyHolder::new);
	}
	
	@SubscribeEvent
	public static void attachToPlayer(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof PlayerEntity && !event.getObject().world.isRemote)
		{
			PlayerEntity player = (PlayerEntity) event.getObject();
				IMoney holder;
		        if(event.getObject() instanceof ServerPlayerEntity)
		        {
		            holder = new PlayerMoneyHolder((ServerPlayerEntity)player);
		        }
		        else
		        {
		            holder = CapabilityMoney.MONEY_CAPABILITY.getDefaultInstance();
		        }
				PlayerMoneyWrapper wrapper = new PlayerMoneyWrapper(holder);
				event.addCapability(CAP_KEY, wrapper);
	            event.addListener(() -> wrapper.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(cap -> INVALIDATED_CAPS.put(event.getObject(), cap)));
		}
	}
	
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		if(!event.getPlayer().world.isRemote)
		{
			event.getPlayer().getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> { 
				
				data.setMoney(data.getMoney());
				data.setLinked(data.getLinked());
			});
		}
	}
		
	@SubscribeEvent
	public static void copyCapabilities(PlayerEvent.Clone event)
	{
		if(!event.getPlayer().world.isRemote)
	    if(event.isWasDeath())
	    {
	        event.getPlayer().getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(newCapa -> {
	            if(INVALIDATED_CAPS.containsKey(event.getOriginal()))
	            {
	                INBT nbt = CapabilityMoney.MONEY_CAPABILITY.writeNBT(INVALIDATED_CAPS.get(event.getOriginal()), null);
	                CapabilityMoney.MONEY_CAPABILITY.readNBT(newCapa, null, nbt);
	            }
	        });
	    }
	}
	
	@SubscribeEvent
	public static void onPlayerRespawn(PlayerRespawnEvent event)
	{
		if(!event.getPlayer().world.isRemote)
		{
			event.getPlayer().getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> { 
				
				data.setMoney(data.getMoney());
				data.setLinked(data.getLinked());
			});
		}
	}
}
