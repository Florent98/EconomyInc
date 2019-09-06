package fr.fifoube.blocks.tileentity.specialrenderer.inventory;

import fr.fifoube.blocks.BlocksRegistery;
import fr.fifoube.blocks.tileentity.TileEntityBlockBills;
import fr.fifoube.blocks.tileentity.TileEntityBlockVault;
import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;


public class TileEntityInventoryRenderHelper extends ItemStackTileEntityRenderer {

    private TileEntityBlockVault teVault = new TileEntityBlockVault();
    private TileEntityBlockVault2by2 teVault2by2 = new TileEntityBlockVault2by2();
    private TileEntityBlockBills teBills = new TileEntityBlockBills();


    @Override
    public void renderByItem(ItemStack itemStackIn) {

    	  Block block = Block.getBlockFromItem(itemStackIn.getItem());
    	  if (block == BlocksRegistery.BLOCK_VAULT) 
  	      {
  	         TileEntityRendererDispatcher.instance.render(this.teVault, 0.0D, 0.0D, 0.0D, 0.0F);
  	      }  
    	  else if(block == BlocksRegistery.BLOCK_VAULT_2BY2)
    	  {
   	         TileEntityRendererDispatcher.instance.render(this.teVault2by2, 0.0D, 0.0D, 0.0D, 0.0F);
    	  }
    	  else if(block == BlocksRegistery.BLOCK_BILLS)
    	  {
   	         TileEntityRendererDispatcher.instance.render(this.teBills, 0.0D, 0.0D, 0.0D, 0.0F);
    	  }
	  	  else 
	  	  {
	  	      super.renderByItem(itemStackIn);
	  	  }
    }
}
