/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks;

import fr.fifoube.main.ModEconomyInc;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlocksRegistry{
	
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_vault")
	public static final Block BLOCK_VAULT = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_vault2by2")
	public static final Block BLOCK_VAULT_2BY2 = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_atm")
	public static final Block BLOCK_ATM = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_changer")
	public static final Block BLOCK_CHANGER = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_seller")
	public static final Block BLOCK_SELLER = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_bills")
	public static final Block BLOCK_BILLS = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_buyer")
	public static final Block BLOCK_BUYER = null;

	

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		
		event.getRegistry().register(new BlockVault(Block.Properties.create(Material.IRON).hardnessAndResistance(-1.0F, 3600000.0F)).setRegistryName("block_vault"));
		event.getRegistry().register(new BlockVault2by2(Block.Properties.create(Material.IRON).hardnessAndResistance(-1.0F, 3600000.0F)).setRegistryName("block_vault2by2"));
		event.getRegistry().register(new BlockATM(Block.Properties.create(Material.IRON).hardnessAndResistance(-1.0F, 3600000.0F)).setRegistryName("block_atm"));
		event.getRegistry().register(new BlockChanger(Block.Properties.create(Material.IRON).hardnessAndResistance(-1.0F, 3600000.0F)).setRegistryName("block_changer"));
		event.getRegistry().register(new BlockSeller(Block.Properties.create(Material.WOOD).hardnessAndResistance(-1.0F, 3600000.0F)).setRegistryName("block_seller"));
		event.getRegistry().register(new BlockBills(Block.Properties.create(Material.IRON)).setRegistryName("block_bills"));
		event.getRegistry().register(new BlockBuyer(Block.Properties.create(Material.WOOD).hardnessAndResistance(-1.0F, 3600000.0F)).setRegistryName("block_buyer"));

	}
	
		
	@SubscribeEvent
	public static void registerItemsBlocks(final RegistryEvent.Register<Item> event) {
		
		event.getRegistry().register(new BlockItem(BLOCK_VAULT, new Item.Properties().group(ModEconomyInc.EIC)).setRegistryName(BLOCK_VAULT.getRegistryName()));
		event.getRegistry().register(new BlockItem(BLOCK_VAULT_2BY2, new Item.Properties()).setRegistryName(BLOCK_VAULT_2BY2.getRegistryName()));
		event.getRegistry().register(new BlockItem(BLOCK_ATM, new Item.Properties().group(ModEconomyInc.EIC)).setRegistryName(BLOCK_ATM.getRegistryName()));
		event.getRegistry().register(new BlockItem(BLOCK_CHANGER, new Item.Properties().group(ModEconomyInc.EIC)).setRegistryName(BLOCK_CHANGER.getRegistryName()));
		event.getRegistry().register(new BlockItem(BLOCK_SELLER, new Item.Properties().group(ModEconomyInc.EIC)).setRegistryName(BLOCK_SELLER.getRegistryName()));
		event.getRegistry().register(new BlockItem(BLOCK_BILLS, new Item.Properties().group(ModEconomyInc.EIC)).setRegistryName(BLOCK_BILLS.getRegistryName()));
		event.getRegistry().register(new BlockItem(BLOCK_BUYER, new Item.Properties().group(ModEconomyInc.EIC)).setRegistryName(BLOCK_BUYER.getRegistryName()));

	}
}
