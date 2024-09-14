/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui.container.type;

import fr.fifoube.blocks.blockentity.*;
import fr.fifoube.gui.container.*;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeRegistery {
	

    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ModEconomyInc.MOD_ID);
    
    
    public static final RegistryObject<MenuType<MenuSeller>> SELLER_TYPE = register("containerseller", (IContainerFactory<MenuSeller>) (windowId, playerInventory, data) -> {
        BlockEntitySeller sellerTE = (BlockEntitySeller) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new MenuSeller(windowId, playerInventory, sellerTE);
    });

    public static final RegistryObject<MenuType<MenuSellerBuy>> SELLERBUY_TYPE = register("containersellerbuy", (IContainerFactory<MenuSellerBuy>) (windowId, playerInventory, data) -> {
        BlockEntitySeller sellerbuyTE = (BlockEntitySeller) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new MenuSellerBuy(windowId, playerInventory, sellerbuyTE);
    });
    
    public static final RegistryObject<MenuType<MenuVault>> VAULT_TYPE = register("containervault", (IContainerFactory<MenuVault>) (windowId, playerInventory, data) -> {
        BlockEntityVault vaultTE = (BlockEntityVault) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new MenuVault(windowId, playerInventory, playerInventory.player, vaultTE);
    });

    public static final RegistryObject<MenuType<MenuVault2by2>> VAULT2BY2_TYPE = register("containervault2by2", (IContainerFactory<MenuVault2by2>) (windowId, playerInventory, data) -> {
    	BlockEntityVault2by2 vault2by2TE = (BlockEntityVault2by2) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new MenuVault2by2(windowId, playerInventory, playerInventory.player, vault2by2TE);
    });

    public static final RegistryObject<MenuType<MenuChanger>> CHANGER_TYPE = register("containerchanger", (IContainerFactory<MenuChanger>) (windowId, playerInventory, data) -> {
        BlockEntityChanger changerTE = (BlockEntityChanger) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new MenuChanger(windowId, playerInventory, changerTE);
    });
    
    public static final RegistryObject<MenuType<MenuBuyer>> BUYER_TYPE = register("containerbuyer", (IContainerFactory<MenuBuyer>) (windowId, playerInventory, data) -> {
        BlockEntityBuyer buyerTE = (BlockEntityBuyer) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new MenuBuyer(windowId, playerInventory, buyerTE);
    });
    
    public static final RegistryObject<MenuType<MenuBuyerCreation>> BUYER_CREA_TYPE = register("containercreabuyer", (IContainerFactory<MenuBuyerCreation>) (windowId, playerInventory, data) -> {
        BlockEntityBuyer buyerCreaTE = (BlockEntityBuyer) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new MenuBuyerCreation(windowId, playerInventory, buyerCreaTE);
    });
    
    
    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String key, MenuType.MenuSupplier<T> supplier)
    {
        MenuType<T> type = new MenuType<>(supplier, FeatureFlags.DEFAULT_FLAGS);
        return REGISTER.register(key, () -> type);
    }
}
