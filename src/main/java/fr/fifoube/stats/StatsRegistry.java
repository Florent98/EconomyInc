package fr.fifoube.stats;

import fr.fifoube.main.ModEconomyInc;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StatsRegistry {

    public static ResourceLocation CHANGED_GOLD_TO_MONEY;

    @SubscribeEvent
    public static void registerAll(RegistryEvent.Register<StatType<?>> event) {
        if (event.getName().equals(ForgeRegistries.STAT_TYPES.getRegistryName())) {
            CHANGED_GOLD_TO_MONEY = register("changed_gold_money", IStatFormatter.DEFAULT);
        }
    }

    private static ResourceLocation register(String name, IStatFormatter formatter) {
        ResourceLocation id = new ResourceLocation(ModEconomyInc.MOD_ID, name);
        Registry.register(Registry.CUSTOM_STAT, name, id);
        Stats.CUSTOM.get(id, formatter);
        return id;
    }

}
