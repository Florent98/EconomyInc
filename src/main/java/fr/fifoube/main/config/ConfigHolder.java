/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.config;

import org.apache.commons.lang3.tuple.Pair;

import fr.fifoube.main.ModEconomyInc;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

@EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ConfigHolder {
 
	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final ForgeConfigSpec SERVER_SPEC;
	static final ClientConfig CLIENT;
	static final ServerConfig SERVER;
	static {
		{
			final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
			CLIENT = specPair.getLeft();
			CLIENT_SPEC = specPair.getRight();
		}
		{
			final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
			SERVER = specPair.getLeft();
			SERVER_SPEC = specPair.getRight();
		}
	}
 
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
		
		final ModConfig config = event.getConfig();
		if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
			ConfigHelper.bakeClient(config);
		} else if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
			ConfigHelper.bakeServer(config);
		}
	}
	
}
