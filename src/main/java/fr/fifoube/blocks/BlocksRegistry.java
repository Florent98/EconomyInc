/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks;

import fr.fifoube.items.ItemsRegistery;
import fr.fifoube.items.block.ItemBlockBills;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlocksRegistry{
	
	public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, ModEconomyInc.MOD_ID);
	
	public static final RegistryObject<Block> BLOCK_VAULT = register("block_vault", () -> new BlockVault(metalProperties()), new Item.Properties());
	public static final RegistryObject<Block> BLOCK_VAULT_2BY2 = register("block_vault2by2", () -> new BlockVault2by2(metalProperties()), new Item.Properties());
	public static final RegistryObject<Block> BLOCK_ATM = register("block_atm", () -> new BlockATM(metalProperties()), new Item.Properties());
	public static final RegistryObject<Block> BLOCK_CHANGER = register("block_changer", () -> new BlockChanger(metalProperties()), new Item.Properties());
	public static final RegistryObject<Block> BLOCK_SELLER = register("block_seller", () -> new BlockSeller(woodProperties()), new Item.Properties());
	public static final RegistryObject<Block> BLOCK_BUYER = register("block_buyer", () -> new BlockBuyer(metalProperties()), new Item.Properties());


    public static final RegistryObject<Block> BLOCK_BILLS = registerWithCustomBlockItem("block_bills", () -> new BlockBills(billsProperties()));

    private static Block.Properties metalProperties() {
        return Block.Properties.of().mapColor(MapColor.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL);
    }

    private static Block.Properties woodProperties() {
        return Block.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD);
    }

    private static Block.Properties billsProperties() {
        return Block.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion();
    }

    private static RegistryObject<Block> register(String name, Supplier<Block> block, Item.Properties properties)
    {
        RegistryObject<Block> registryObject = REGISTER.register(name, block);
        ItemsRegistery.REGISTER.register(name, () -> new BlockItem(registryObject.get(), properties));
        return registryObject;
    }

    private static RegistryObject<Block> registerWithCustomBlockItem(String name, Supplier<Block> block)
    {
        RegistryObject<Block> registryObject = REGISTER.register(name, block);
        if(name.equals("block_bills")){
            ItemsRegistery.REGISTER.register(name, () -> new ItemBlockBills(registryObject.get(), new Item.Properties()));
        }
        return registryObject;
    }

}
