package fr.fifoube.main.capabilities;

import java.util.Map;
import java.util.WeakHashMap;

import fr.fifoube.main.ModEconomyInc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.capabilities.CapabilityInject;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID)
public class CapabilityMoney {
	
	@CapabilityInject(IMoney.class)
	public static final Capability<IMoney> MONEY_CAPABILITY = null;
	public static final ResourceLocation CAP_KEY = new ResourceLocation(ModEconomyInc.MOD_ID, "money");
	private static final Map<Entity, IMoney> INVALIDATED_CAPS = new WeakHashMap<>();

	public static class DefaultMoneyStorage implements IStorage<IMoney> {
		 
	    @Override
	    public INBTBase writeNBT(Capability<IMoney> capability, IMoney instance, EnumFacing side)
	    {
	    	final NBTTagCompound tag = new NBTTagCompound();
			tag.setDouble("money", instance.getMoney());
			tag.setBoolean("linked", instance.getLinked());
			tag.setString("name", instance.getName());
			tag.setString("onlineUUID", instance.getOnlineUUID());
			return tag;
	    }
	 
	    @Override
	    public void readNBT(Capability<IMoney> capability, IMoney instance, EnumFacing side, INBTBase nbt)
	    {
			final NBTTagCompound tag = (NBTTagCompound) nbt;
			instance.setMoney(tag.getDouble("money"));
			instance.setLinked(tag.getBoolean("linked"));
			instance.setName(tag.getString("name"));
			instance.setOnlineUUID(tag.getString("onlineUUID"));
	    }


	}
	
	public static void register() {
		
		CapabilityManager.INSTANCE.register(IMoney.class, new DefaultMoneyStorage(), MoneyHolder::new);
	}

	
	@SubscribeEvent
	public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event)
	{
			IMoney holder;
		    if(event.getObject() instanceof EntityPlayerMP)
		    {
		    	holder = new PlayerMoneyHolder((EntityPlayerMP)event.getObject());
		    }
		    else
		    {
		    	holder = CapabilityMoney.MONEY_CAPABILITY.getDefaultInstance();
		    }
	        MoneyWrapper wrapper = new MoneyWrapper(holder);
	        event.addCapability(CAP_KEY, wrapper);
	        
	        if(event.getObject() instanceof EntityPlayer)
	        {
	            event.addListener(() -> wrapper.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(cap -> INVALIDATED_CAPS.put(event.getObject(), cap)));
	        }
	}
	
	@SubscribeEvent
	public static void copyCapabilities(PlayerEvent.Clone event)
	{
	    if(event.isWasDeath())
	    {
	        event.getEntityPlayer().getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(newCapa -> {
	            if(CapabilityMoney.INVALIDATED_CAPS.containsKey(event.getOriginal()))
	            {
	                INBTBase nbt = MONEY_CAPABILITY.writeNBT(CapabilityMoney.INVALIDATED_CAPS.get(event.getOriginal()), null);
	                MONEY_CAPABILITY.readNBT(newCapa, null, nbt);
	            }
	        });
	    }
	}
	
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		if(!event.getPlayer().world.isRemote)
		{
			event.getPlayer().getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> { data.setMoney(data.getMoney());});
		}
	}
}
