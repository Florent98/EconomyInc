package fr.fifoube.blocks.tileentity.specialrenderer;

import com.mojang.blaze3d.platform.GlStateManager;

import fr.fifoube.blocks.models.ModelBills;
import fr.fifoube.blocks.tileentity.TileEntityBlockBills;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityBlockBillsSpecialRenderer extends TileEntityRenderer<TileEntityBlockBills> {

	private static ModelBills modelBlock = new ModelBills();
	public static ResourceLocation texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_0.png");
	
	
	@Override
	public void render(TileEntityBlockBills te, double posX, double posY, double posZ, float partialTicks, int destroyStage) {
		
		checkBillRef(te);
		GlStateManager.pushMatrix();
		setLightmapDisabled(true);
        switch (te.getDirection()) {
		case 0:
			GlStateManager.translated(posX + 0.125F, posY + 0.530F, posZ + 0.250F);
			break;
		case 1:
			GlStateManager.translated(posX + 0.750F, posY + 0.530F, posZ + 0.125F);
			break;
		case 2:
			GlStateManager.translated(posX + 0.875F, posY + 0.530F, posZ + 0.750F);
			break;
		case 3:
			GlStateManager.translated(posX + 0.250F, posY + 0.530F, posZ + 0.875F);
			break;
		default:
			break;
		}
		GlStateManager.scaled(0.3330, 0.3330, 0.3330);   
        GlStateManager.rotatef(180F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotatef(90F * te.getDirection(), 0.0F, 1.0F, 0.0F);
        bindTexture(texture);
        modelBlock.renderAll(te);
        GlStateManager.popMatrix();
	}
	
	@Override
	public boolean isGlobalRenderer(TileEntityBlockBills te) {
		return true;
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
