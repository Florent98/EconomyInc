package fr.fifoube.main.events.client;

import com.mojang.blaze3d.matrix.MatrixStack;

import fr.fifoube.blocks.BlockSeller;
import fr.fifoube.blocks.BlocksRegistry;
import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.config.ConfigFile;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onDrawBlockHighlightEvent(DrawHighlightEvent.HighlightBlock event)
	{	
		if(ConfigFile.canPreviewItemInBlock)
		{
			World world = Minecraft.getInstance().world;
			BlockPos pos = new BlockPos(event.getTarget().getPos()); 
			Block block = world.getBlockState(pos).getBlock();
				if(block == BlocksRegistry.BLOCK_SELLER)
				{
					BlockSeller seller = (BlockSeller) world.getBlockState(pos).getBlock();
					TileEntityBlockSeller te = (TileEntityBlockSeller)world.getTileEntity(pos);
					if(te != null)
					{
						if(te.getCreated())
						{
							int x = pos.getX();
							int y = pos.getY();
							int z = pos.getZ();
							float i = 0.1f;
							float j = 0.0F;
							ItemRenderer renderM = Minecraft.getInstance().getItemRenderer();
							MatrixStack matrixStack = event.getMatrix();
							IRenderTypeBuffer buffer = event.getBuffers();
							Direction direction = (Direction)world.getBlockState(pos).get(BlockSeller.FACING);
							matrixStack.push();					
							ItemStack stack = new ItemStack(te.getStackInSlot(0).getItem(), 1);
							if(te.getAmount() == 0)
							{
								stack = new ItemStack(Blocks.BARRIER, 1);
							}
							
							/*matrixStack.translate(x, y, z);
							renderM.renderItem(stack, TransformType.FIXED, 1, 1, matrixStack, buffer);*/
						    matrixStack.pop();						
						   }
					}
				}
		}
	}
	
	
	
}
