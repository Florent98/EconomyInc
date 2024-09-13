/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks;

import fr.fifoube.items.ItemsRegistery;
import fr.fifoube.items.block.ItemBlockBills;
import fr.fifoube.main.EconomyIncModTab;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlocksRegistry{
	
	public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, ModEconomyInc.MOD_ID);
	
	public static final RegistryObject<Block> BLOCK_VAULT = register("block_vault", () -> new BlockVault(Block.Properties.of(Material.METAL).strength(-1.0F, 3600000.0F)), new Item.Properties().tab(EconomyIncModTab.ECONOMYINC_TAB));
	public static final RegistryObject<Block> BLOCK_VAULT_2BY2 = register("block_vault2by2", () -> new BlockVault2by2(Block.Properties.of(Material.METAL).strength(-1.0F, 3600000.0F)), new Item.Properties());
	public static final RegistryObject<Block> BLOCK_ATM = register("block_atm", () -> new BlockATM(Block.Properties.of(Material.METAL).strength(-1.0F, 3600000.0F)), new Item.Properties().tab(EconomyIncModTab.ECONOMYINC_TAB));
	public static final RegistryObject<Block> BLOCK_CHANGER = register("block_changer", () -> new BlockChanger(Block.Properties.of(Material.METAL).strength(-1.0F, 3600000.0F)), new Item.Properties().tab(EconomyIncModTab.ECONOMYINC_TAB));
	public static final RegistryObject<Block> BLOCK_SELLER = register("block_seller", () -> new BlockSeller(Block.Properties.of(Material.WOOD).strength(-1.0F, 3600000.0F)), new Item.Properties().tab(EconomyIncModTab.ECONOMYINC_TAB));
	public static final RegistryObject<Block> BLOCK_BUYER = register("block_buyer", () -> new BlockBuyer(Block.Properties.of(Material.METAL).strength(-1.0F, 3600000.0F)), new Item.Properties().tab(EconomyIncModTab.ECONOMYINC_TAB));


    public static final RegistryObject<Block> BLOCK_BILLS = registerWithCustomBlockItem("block_bills", () -> new BlockBills(Block.Properties.of(Material.WOOD)), EconomyIncModTab.ECONOMYINC_TAB);

    private static RegistryObject<Block> register(String name, Supplier<Block> block, Item.Properties properties)
    {
        RegistryObject<Block> registryObject = REGISTER.register(name, block);
        ItemsRegistery.REGISTER.register(name, () -> new BlockItem(registryObject.get(), properties));
        return registryObject;
    }

    private static RegistryObject<Block> registerWithCustomBlockItem(String name, Supplier<Block> block, CreativeModeTab tab)
    {
        RegistryObject<Block> registryObject = REGISTER.register(name, block);
        if(name.equals("block_bills")){
            ItemsRegistery.REGISTER.register(name, () -> new ItemBlockBills(registryObject.get(), new Item.Properties().tab(tab)));
        }
        return registryObject;
    }

}
