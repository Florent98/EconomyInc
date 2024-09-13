/** 
 *  Copyright 2020, Turrioni Florent, All rights reserved.
 *  
 * 	This program is copyrighted for all the files and code 
 * 	included in this program. No reuse, modification or 
 * 	reselling is authorized without any legal document 
 *  approved by the owner*.
 * 
 * 	*Owner : Turrioni Florent resident in Belgium and 
 *  contactable at florent_turrioni@hotmail.com
 *  
 * */

package fr.fifoube.blocks.blockentity;

import fr.fifoube.blocks.BlocksRegistry;
import fr.fifoube.blocks.blockentity.specialrenderer.BlockEntityBillsSpecialRenderer;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypeRegistery {
	
	
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ModEconomyInc.MOD_ID);

    public static final RegistryObject<BlockEntityType<BlockEntityVault>> TILE_VAULT = REGISTER
    		.register("block_vault_te", () -> BlockEntityType.Builder.of(BlockEntityVault::new, BlocksRegistry.BLOCK_VAULT.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<BlockEntityVault2by2>> TILE_VAULT_2BY2 = REGISTER
    		.register("block_vault2by2_te", () -> BlockEntityType.Builder.of(BlockEntityVault2by2::new, BlocksRegistry.BLOCK_VAULT_2BY2.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<BlockEntitySeller>> TILE_SELLER = REGISTER
    		.register("block_seller_te", () -> BlockEntityType.Builder.of(BlockEntitySeller::new, BlocksRegistry.BLOCK_SELLER.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<BlockEntityChanger>> TILE_CHANGER = REGISTER
    		.register("block_changer_te", () -> BlockEntityType.Builder.of(BlockEntityChanger::new, BlocksRegistry.BLOCK_CHANGER.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<BlockEntityBuyer>> TILE_BUYER = REGISTER
    		.register("block_buyer_te", () -> BlockEntityType.Builder.of(BlockEntityBuyer::new, BlocksRegistry.BLOCK_BUYER.get()).build(null));

    public static final RegistryObject<BlockEntityType<BlockEntityBills>> TILE_BILLS = REGISTER
            .register("block_bills_te", () -> BlockEntityType.Builder.of(BlockEntityBills::new, BlocksRegistry.BLOCK_BILLS.get()).build(null));

    @OnlyIn(Dist.CLIENT)
    public static void renderBESR() {

        BlockEntityRenderers.register(TILE_BILLS.get(), BlockEntityBillsSpecialRenderer::new);

    }
}

