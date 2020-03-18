package fr.fifoube.blocks.tileentity.specialrenderer.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;

import fr.fifoube.blocks.BlocksRegistery;
import fr.fifoube.blocks.tileentity.TileEntityBlockBills;
import fr.fifoube.blocks.tileentity.TileEntityBlockVault;
import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TileEntityInventoryRenderHelper extends ItemStackTileEntityRenderer {

    private TileEntityBlockBills teBills = new TileEntityBlockBills();
    private static ItemStackTileEntityRenderer instanceF = instance;
       
    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
    	
	      Block block = Block.getBlockFromItem(itemStackIn.getItem());   	  
	  	  if(block == BlocksRegistery.BLOCK_BILLS)
	  	  {
	  		  TileEntityRendererDispatcher.instance.renderItem(this.teBills, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	  	  }
	  	  else 
	  	  {
	  	      super.render(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	  	  }
    }
 
}
