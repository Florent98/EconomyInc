package fr.fifoube.blocks.tileentity.specialrenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import fr.fifoube.blocks.models.ModelBills;
import fr.fifoube.blocks.tileentity.TileEntityBlockBills;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.EmptyModelData;

public class TileEntityBlockBillsSpecialRenderer extends TileEntityRenderer<TileEntityBlockBills> {


	private static ResourceLocation texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_0.png");
	private static ModelBills modelBlock;
	
	public TileEntityBlockBillsSpecialRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}
	
	@Override
	public void render(TileEntityBlockBills tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        World world = tileEntityIn.getWorld();
        BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
		checkBillRef(tileEntityIn);
		modelBlock = new ModelBills(RenderType::getEntitySolid);
		Material material = new Material(texture, texture);
		matrixStackIn.push();
        switch (tileEntityIn.getDirection()) {
		case 0:
			matrixStackIn.translate(0.125F, 0.530F, 0.250F);
			break;
		case 1:
			matrixStackIn.translate(0.750F, 0.530F, 0.125F);
			break;
		case 2:
			matrixStackIn.translate(0.875F, 0.530F, 0.750F);
			break;
		case 3:
			matrixStackIn.translate(0.250F, 0.530F, 0.875F);
			break;
		default:
			break;
		}
        matrixStackIn.scale(0.3330f, 0.3330f, 0.3330f);   
        matrixStackIn.rotate(new Quaternion(1.0F, 0F, 0F, 180F));
        matrixStackIn.rotate(new Quaternion(0F, 1.0F, 0F, 90F * tileEntityIn.getDirection()));
        //modelBlock.renderAll(tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();		
	}
	
	public void checkBillRef(TileEntityBlockBills tile)
	{		
		switch (tile.getBillRef()) {
		case "item.economyinc.item_oneb":
			texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_1.png");
			break;
		case "item.economyinc.item_fiveb":
			texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_5.png");
			break;
		case "item.economyinc.item_tenb":
			texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_10.png");
			break;
		case "item.economyinc.item_twentyb":
			texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_20.png");
			break;
		case "item.economyinc.item_fiftybe":
			texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_50.png");
			break;
		case "item.economyinc.item_hundreedb":
			texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_100.png");
			break;
		case "item.economyinc.item_twohundreedb":
			texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_200.png");
			break;
		case "item.economyinc.item_fivehundreedb":
			texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_500.png");
			break;
		default:
			break;
		}
	}
	
	
}
