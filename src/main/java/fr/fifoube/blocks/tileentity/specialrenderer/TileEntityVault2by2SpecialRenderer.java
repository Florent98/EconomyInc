package fr.fifoube.blocks.tileentity.specialrenderer;

import com.mojang.blaze3d.platform.GlStateManager;

import fr.fifoube.blocks.models.ModelVault2by2;
import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityVault2by2SpecialRenderer extends TileEntityRenderer<TileEntityBlockVault2by2>
{
	
	private static ModelVault2by2 modelBlock = new ModelVault2by2();
	public static ResourceLocation texture;
	
	@Override
	public void render(TileEntityBlockVault2by2 te, double x, double y, double z, float partialTicks, int destroyStage) {
		
		checkTextures(te);
        GlStateManager.pushMatrix();
        setLightmapDisabled(true);
        translateFromDirection(te, x, y, z);
        GlStateManager.color3f(1.0F, 1.0F, 1.0F);
		GlStateManager.rotatef(-90F, 0.0F, 1.0F, 0.0F);
		switch (te.getDirection()) {
		case 0:
			GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 1:
			GlStateManager.rotatef(90.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 2:
			GlStateManager.rotatef(0.0F, 0.0F, 1.0F, 0.0F);
			break;
		case 3:
			GlStateManager.rotatef(-90.0F, 0.0F, 1.0F, 0.0F);
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
	public boolean isGlobalRenderer(TileEntityBlockVault2by2 te) {
		return true;
	}
	
	public void checkTextures(TileEntityBlockVault2by2 te)
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
	
	public void translateFromDirection(TileEntityBlockVault2by2 te, double x, double y, double z)
	{
       switch (te.getDirection()) {
		case 0:
	        GlStateManager.translated(x, y + 0.5, z);
			break;
		case 1:
	        GlStateManager.translated(x + 1, y + 0.5, z);
			break;
		case 2:
	        GlStateManager.translated(x + 1, y + 0.5, z + 1);
			break;
		case 3:
	        GlStateManager.translated(x, y + 0.5, z + 1);
			break;
	
		default:
			break;
       }

	}
}
