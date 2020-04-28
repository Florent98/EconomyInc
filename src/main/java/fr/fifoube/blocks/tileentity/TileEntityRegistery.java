/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks.tileentity;

import fr.fifoube.blocks.BlocksRegistry;
import fr.fifoube.blocks.tileentity.specialrenderer.TileEntityBlockBillsSpecialRenderer;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntityRegistery {
	
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_vault_te")
	public static final TileEntityType<TileEntityBlockVault> TILE_BLOCKVAULT = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_vault2by2_te")
	public static final TileEntityType<TileEntityBlockVault2by2> TILE_BLOCKVAULT_2BY2 = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_seller_te")
	public static final TileEntityType<TileEntityBlockSeller> TILE_SELLER = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_changer_te")
	public static final TileEntityType<TileEntityBlockChanger> TILE_CHANGER = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_bills_te")
	public static final TileEntityType<TileEntityBlockBills> TILE_BILLS = null;
	
	
	@SubscribeEvent
	public static void registerTileEntity(final RegistryEvent.Register<TileEntityType<?>> event) 
	{
		event.getRegistry().register(TileEntityType.Builder.create(TileEntityBlockVault::new, BlocksRegistry.BLOCK_VAULT).build(null).setRegistryName(ModEconomyInc.MOD_ID + ":block_vault_te"));	
		event.getRegistry().register(TileEntityType.Builder.create(TileEntityBlockVault2by2::new, BlocksRegistry.BLOCK_VAULT_2BY2).build(null).setRegistryName(ModEconomyInc.MOD_ID + ":block_vault2by2_te"));
		event.getRegistry().register(TileEntityType.Builder.create(TileEntityBlockSeller::new, BlocksRegistry.BLOCK_SELLER).build(null).setRegistryName(ModEconomyInc.MOD_ID + ":block_seller_te"));
		event.getRegistry().register(TileEntityType.Builder.create(TileEntityBlockChanger::new, BlocksRegistry.BLOCK_CHANGER).build(null).setRegistryName(ModEconomyInc.MOD_ID + ":block_changer_te"));
		event.getRegistry().register(TileEntityType.Builder.create(TileEntityBlockBills::new, BlocksRegistry.BLOCK_BILLS).build(null).setRegistryName(ModEconomyInc.MOD_ID + ":block_bills_te"));

	}
	
	@OnlyIn(Dist.CLIENT)
	public static void registerTileRenderer() {
		
      ClientRegistry.bindTileEntityRenderer(TILE_BILLS, TileEntityBlockBillsSpecialRenderer::new);
    }
}
