package fr.fifoube.blocks.tileentity.specialrenderer;


import com.mojang.blaze3d.platform.GlStateManager;

import fr.fifoube.blocks.models.ModelVault;
import fr.fifoube.blocks.tileentity.TileEntityBlockVault;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityVaultSpecialRenderer extends TileEntityRenderer<TileEntityBlockVault>
{
	
	private static ModelVault modelBlock = new ModelVault();
	public static ResourceLocation texture;
	
	@Override
	public void render(TileEntityBlockVault te, double x, double y, double z, float partialTicks, int destroyStage) {
		
		checkTextures(te);
		GlStateManager.pushMatrix();
		setLightmapDisabled(true);
	    GlStateManager.translated(x + 0.5F, y + 0.75F, z + 0.5F);
        GlStateManager.rotatef(180F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scaled(0.5, 0.5, 0.5);   
		switch (te.getDirection()) {
		case 0:
			GlStateManager.rotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 1:
			GlStateManager.rotatef(0.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 2:
			GlStateManager.rotatef(90.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 3:
			GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
			break;
		default:
			GlStateManager.rotatef(0.0F, 0.0F, 1.0F, 0.0F);
			break;
		}
        bindTexture(texture);
        modelBlock.renderAll();
        GlStateManager.popMatrix();
	}
	
	@Override
	public boolean isGlobalRenderer(TileEntityBlockVault te) {
		return true;
	}
	
	public void checkTextures(TileEntityBlockVault te)
	{
		if(te.hasItems())
		{
			texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_vault.png");
		}
		else
		{
			texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_vault_nogold.png");	
		}	
	}
}
