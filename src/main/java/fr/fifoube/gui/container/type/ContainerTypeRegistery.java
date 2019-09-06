package fr.fifoube.gui.container.type;

import fr.fifoube.gui.container.ContainerSeller;
import fr.fifoube.gui.container.ContainerVault;
import fr.fifoube.gui.container.ContainerVault2by2;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerTypeRegistery {
	
	@ObjectHolder("economyinc:containerseller")
	public static final ContainerType<ContainerSeller> SELLER_TYPE = null;
	@ObjectHolder("economyinc:containersellerbuy")
	public static final ContainerType<ContainerSeller> SELLERBUY_TYPE = null;
	@ObjectHolder("economyinc:containervault")
	public static final ContainerType<ContainerVault> VAULT_TYPE = null;
	@ObjectHolder("economyinc:containervault2by2")
	public static final ContainerType<ContainerVault2by2> VAULT2BY2_TYPE = null;
    
	@SubscribeEvent
	public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().register(IForgeContainerType.create(ContainerSeller::new).setRegistryName("container_seller"));
		event.getRegistry().register(IForgeContainerType.create(ContainerSeller::new).setRegistryName("container_sellerbuy"));
		event.getRegistry().register(IForgeContainerType.create(ContainerVault::new).setRegistryName("container_vault"));
		event.getRegistry().register(IForgeContainerType.create(ContainerVault2by2::new).setRegistryName("container_vault2by2"));


	}
	
}
