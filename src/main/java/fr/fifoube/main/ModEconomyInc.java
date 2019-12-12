package fr.fifoube.main;

/***
 * 	Florent T. also know as Fifou_BE
 *  Mod created by Florent T. also known as Fifou_BE
 *  Officials threads of this mod are
	 * 	https://minecraft.curseforge.com/projects/economy-inc
	 * 	https://www.planetminecraft.com/mod/economy-inc/
	 * 	https://www.minecraftforgefrance.fr/showthread.php?tid=4715
 *  For more info read terms and conditions and also read https://account.mojang.com/documents/minecraft_eula parts with MOD
 */

import java.util.logging.LogManager;
import java.util.logging.Logger;

import fr.fifoube.blocks.tileentity.TileEntityRegistery;
import fr.fifoube.gui.GuiRegistery;
import fr.fifoube.gui.container.type.ContainerTypeRegistery;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.commands.CommandBalance;
import fr.fifoube.main.commands.CommandsPlots;
import fr.fifoube.main.commands.CommandsPlotsBuy;
import fr.fifoube.main.events.client.ClientEvents;
import fr.fifoube.main.events.server.ServerEvents;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("economyinc")
public class ModEconomyInc {

		public static final String MOD_ID = "economyinc";
		public static final Logger LOGGER = LogManager.getLogManager().getLogger(MOD_ID);
		public static final ItemGroup EIC = new ItemGroupEIC("eic");
		
		public ModEconomyInc() {
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadConfig);
			FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, ContainerTypeRegistery::registerContainers);
			MinecraftForge.EVENT_BUS.addListener(this::serverStartingEvent);
			ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigFile.SERVER_SPEC);
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigFile.CLIENT_SPEC);
						
		}
		
		
		//CONFIG EVENT
		public void loadConfig(ModConfig.ModConfigEvent event)
		{
			ModConfig config = event.getConfig();
			if (!ModEconomyInc.MOD_ID.equals(config.getModId()))
				return;
			if (config.getSpec() == ConfigFile.CLIENT_SPEC)
				ConfigFile.refreshClient();
			else if (config.getSpec() == ConfigFile.SERVER_SPEC)
				ConfigFile.refreshServer();
		}
		//SETUP COMMON
		private void setup(final FMLCommonSetupEvent event) {
			PacketsRegistery.registerNetworkPackets();
			CapabilityMoney.register();

		}
		//CLIENT
		private void clientSetup(final FMLClientSetupEvent event) {
			GuiRegistery.register();
			TileEntityRegistery.registerTileRenderer();
			MinecraftForge.EVENT_BUS.register(new ClientEvents());
			
		}
		
		
		private void serverStartingEvent(FMLServerStartingEvent event)
		{
			CommandBalance.register(event.getCommandDispatcher());
			CommandsPlots.register(event.getCommandDispatcher());
			CommandsPlotsBuy.register(event.getCommandDispatcher());
			MinecraftForge.EVENT_BUS.register(new ServerEvents());

		}
	
		
}
