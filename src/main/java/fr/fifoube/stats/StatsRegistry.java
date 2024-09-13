package fr.fifoube.stats;

import fr.fifoube.main.ModEconomyInc;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StatsRegistry {

	 public static ResourceLocation CHANGED_GOLD_TO_MONEY;
	
	 @SubscribeEvent
	 public static void registerAll(RegistryEvent.Register<StatType<?>> event)
	 { 
	    if (event.getName().equals(ForgeRegistries.STAT_TYPES.getRegistryName())) {
	       CHANGED_GOLD_TO_MONEY = register("changed_gold_money", StatFormatter.DEFAULT);
	    }
	 }

	    private static ResourceLocation register(String name, StatFormatter formatter) {
	        ResourceLocation id = new ResourceLocation(ModEconomyInc.MOD_ID, name);
	        Registry.register(Registry.CUSTOM_STAT, name, id);
	        Stats.CUSTOM.get(id, formatter);
	        return id;
	    }
	
}
