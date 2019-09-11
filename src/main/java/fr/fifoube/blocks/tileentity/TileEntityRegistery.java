package fr.fifoube.blocks.tileentity;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import fr.fifoube.blocks.tileentity.specialrenderer.TileEntityBlockBillsSpecialRenderer;
import fr.fifoube.blocks.tileentity.specialrenderer.TileEntityVault2by2SpecialRenderer;
import fr.fifoube.blocks.tileentity.specialrenderer.TileEntityVaultSpecialRenderer;
import fr.fifoube.blocks.tileentity.specialrenderer.inventory.TileEntityInventoryRenderHelper;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
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
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_seller")
	public static final TileEntityType<?> TILE_SELLER = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":block_bills")
	public static final TileEntityType<?> TILE_BILLS = null;

	@SubscribeEvent
	public static void registerTileEntity(final RegistryEvent.Register<TileEntityType<?>> event) 
	{
		event.getRegistry().register(buildTileType(ModEconomyInc.MOD_ID + ":block_vault_te", TileEntityType.Builder.create(TileEntityBlockVault::new)).setRegistryName("block_vault_te"));
		event.getRegistry().register(buildTileType(ModEconomyInc.MOD_ID + ":block_vault2by2_te", TileEntityType.Builder.create(TileEntityBlockVault2by2::new)).setRegistryName("block_vault2by2_te"));
		event.getRegistry().register(buildTileType(ModEconomyInc.MOD_ID + ":block_seller", TileEntityType.Builder.create(TileEntityBlockSeller::new)).setRegistryName("block_seller"));
		event.getRegistry().register(buildTileType(ModEconomyInc.MOD_ID + ":block_bills", TileEntityType.Builder.create(TileEntityBlockBills::new)).setRegistryName("block_bills"));

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
        //ItemStackTileEntityRenderer.instance = new TileEntityInventoryRenderHelper();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockVault.class, new TileEntityVaultSpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockVault2by2.class, new TileEntityVault2by2SpecialRenderer());	
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockBills.class, new TileEntityBlockBillsSpecialRenderer());
    }
}
