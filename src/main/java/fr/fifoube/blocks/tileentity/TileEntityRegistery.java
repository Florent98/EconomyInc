package fr.fifoube.blocks.tileentity;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import fr.fifoube.blocks.BlocksRegistery;
import fr.fifoube.blocks.tileentity.specialrenderer.TileEntityBlockBillsSpecialRenderer;
import fr.fifoube.blocks.tileentity.specialrenderer.TileEntityVault2by2SpecialRenderer;
import fr.fifoube.blocks.tileentity.specialrenderer.TileEntityVaultSpecialRenderer;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
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
	public static final TileEntityType<?> TILE_BLOCKVAULT = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_vault2by2_te")
	public static final TileEntityType<?> TILE_BLOCKVAULT_2BY2 = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_bills_te")
	public static final TileEntityType<?> TILE_BILLS = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_seller_te")
	public static final TileEntityType<?> TILE_SELLER = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_changer_te")
	public static final TileEntityType<?> TILE_CHANGER = null;

	@SubscribeEvent
	public static void registerTileEntity(final RegistryEvent.Register<TileEntityType<?>> event) 
	{
		event.getRegistry().register(buildTileType(ModEconomyInc.MOD_ID + ":block_vault_te", TileEntityType.Builder.create((TileEntityBlockVault::new), BlocksRegistery.BLOCK_VAULT)).setRegistryName("block_vault_te"));
		event.getRegistry().register(buildTileType(ModEconomyInc.MOD_ID + ":block_vault2by2_te", TileEntityType.Builder.create(TileEntityBlockVault2by2::new, BlocksRegistery.BLOCK_VAULT_2BY2)).setRegistryName("block_vault2by2_te"));
		event.getRegistry().register(buildTileType(ModEconomyInc.MOD_ID + ":block_bills_te", TileEntityType.Builder.create(TileEntityBlockBills::new, BlocksRegistery.BLOCK_BILLS)).setRegistryName("block_bills_te"));
		event.getRegistry().register(buildTileType(ModEconomyInc.MOD_ID + ":block_seller_te", TileEntityType.Builder.create(TileEntityBlockSeller::new, BlocksRegistery.BLOCK_SELLER)).setRegistryName("block_seller_te"));
		event.getRegistry().register(buildTileType(ModEconomyInc.MOD_ID + ":block_changer_te", TileEntityType.Builder.create(TileEntityBlockChanger::new, BlocksRegistery.BLOCK_CHANGER)).setRegistryName("block_changer_te"));

	}
	
	public static <T extends TileEntity> TileEntityType<T> buildTileType(String id, TileEntityType.Builder<T> builder) {
		
		Type<?> type = null;

		try {
			type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(1631)).getChoiceType(TypeReferences.BLOCK_ENTITY, id);
		} catch (IllegalArgumentException illegalstateexception) {
			if (SharedConstants.developmentMode) {
				throw illegalstateexception;
			}
		}

		TileEntityType<T> tileentitytype = builder.build(type);
		return tileentitytype;
	
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void registerTileRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockVault.class, new TileEntityVaultSpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockVault2by2.class, new TileEntityVault2by2SpecialRenderer());	
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockBills.class, new TileEntityBlockBillsSpecialRenderer());
    }
}
