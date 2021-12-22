/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui.container.type;

import fr.fifoube.gui.container.*;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerTypeRegistery {

    @ObjectHolder(ModEconomyInc.MOD_ID + ":containerseller")
    public static final ContainerType<ContainerSeller> SELLER_TYPE = null;
    @ObjectHolder(ModEconomyInc.MOD_ID + ":containersellerbuy")
    public static final ContainerType<ContainerSeller> SELLERBUY_TYPE = null;
    @ObjectHolder(ModEconomyInc.MOD_ID + ":containervault")
    public static final ContainerType<ContainerVault> VAULT_TYPE = null;
    @ObjectHolder(ModEconomyInc.MOD_ID + ":containervault2by2")
    public static final ContainerType<ContainerVault2by2> VAULT2BY2_TYPE = null;
    @ObjectHolder(ModEconomyInc.MOD_ID + ":containerchanger")
    public static final ContainerType<ContainerChanger> CHANGER_TYPE = null;
    @ObjectHolder(ModEconomyInc.MOD_ID + ":containerbuyer")
    public static final ContainerType<ContainerBuyer> BUYER_TYPE = null;
    @ObjectHolder(ModEconomyInc.MOD_ID + ":containerbuyercreation")
    public static final ContainerType<ContainerBuyerCreation> BUYER_CREATION_TYPE = null;

    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(IForgeContainerType.create(ContainerSeller::new).setRegistryName("containerseller"));
        event.getRegistry().register(IForgeContainerType.create(ContainerSeller::new).setRegistryName("containersellerbuy"));
        event.getRegistry().register(IForgeContainerType.create(ContainerVault::new).setRegistryName("containervault"));
        event.getRegistry().register(IForgeContainerType.create(ContainerVault2by2::new).setRegistryName("containervault2by2"));
        event.getRegistry().register(IForgeContainerType.create(ContainerChanger::new).setRegistryName("containerchanger"));
        event.getRegistry().register(IForgeContainerType.create(ContainerBuyer::new).setRegistryName("containerbuyer"));
        event.getRegistry().register(IForgeContainerType.create(ContainerBuyerCreation::new).setRegistryName("containerbuyercreation"));


    }

}
