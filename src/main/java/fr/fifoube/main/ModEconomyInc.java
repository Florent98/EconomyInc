/*******************************************************************************
/***
 *  Mod created by Florent T. also known as FlorentLeBelge
 *  Officials threads of this mod are
 * 	https://minecraft.curseforge.com/projects/economy-inc
 * 	https://www.planetminecraft.com/mod/economy-inc/
 * 	https://www.minecraftforgefrance.fr/showthread.php?tid=4715
 *  For more info read terms and conditions and also read https://account.mojang.com/documents/minecraft_eula parts with MOD
 */
 /*******************************************************************************/
package fr.fifoube.main;

import fr.fifoube.blocks.BlocksRegistry;
import fr.fifoube.blocks.tileentity.TileEntityRegistery;
import fr.fifoube.gui.GuiRegistery;
import fr.fifoube.gui.container.type.ContainerTypeRegistery;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.commands.CommandBalance;
import fr.fifoube.main.commands.CommandsPlots;
import fr.fifoube.main.commands.CommandsPlotsBuy;
import fr.fifoube.main.config.ConfigHolder;
import fr.fifoube.main.events.client.ClientEvents;
import fr.fifoube.main.events.server.ServerEvents;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModEconomyInc.MOD_ID)
public class ModEconomyInc {

		public static final String MOD_ID = "economyinc";
		public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
		public static final ItemGroup EIC = new ItemGroupEIC("eic");
		
		public ModEconomyInc() {

			
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
			ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);	
			
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
			MinecraftForge.EVENT_BUS.addListener(this::serverStartingEvent);
	        MinecraftForge.EVENT_BUS.addListener(this::onCommandRegister);

			FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, ContainerTypeRegistery::registerContainers);
			
			

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
			RenderTypeLookup.setRenderLayer(BlocksRegistry.BLOCK_SELLER, RenderType.getCutoutMipped());
			RenderTypeLookup.setRenderLayer(BlocksRegistry.BLOCK_BUYER, RenderType.getCutoutMipped());
			RenderTypeLookup.setRenderLayer(BlocksRegistry.BLOCK_BILLS, RenderType.getCutout());
		}
		
		//SERVER START
		private void serverStartingEvent(final FMLServerStartingEvent event)
		{
			MinecraftForge.EVENT_BUS.register(new ServerEvents());
		}
		
		//COMMANDS
		public void onCommandRegister(RegisterCommandsEvent event)
		{
			CommandBalance.register(event.getDispatcher());
			CommandsPlots.register(event.getDispatcher());
			CommandsPlotsBuy.register(event.getDispatcher());
		}
		
}
