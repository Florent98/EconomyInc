package fr.fifoube.main.events.client;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import fr.fifoube.blocks.BlockSeller;
import fr.fifoube.blocks.BlocksRegistery;
import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.config.ConfigFile;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;


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
				if(block == BlocksRegistery.BLOCK_SELLER)
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
							
				
				            matrixStack.scale(0.5F, 0.5F, 0.5F);
				            matrixStack.translate(x + 0.5D, y + i, z + 0.5D);
				            
							
							/*ItemEntity entItem = new ItemEntity(world, x + 0.5F, y + i, z + 0.5F, stack);
							
							try {
								ObfuscationReflectionHelper.setPrivateValue(ItemEntity.class, entItem, 0, "field_70290_d");
							} catch (Exception e) {
								System.out.println("Could not find the field hoverStart also known as field_70290_d.");
							}*/
							renderM.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, 1, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
						    matrixStack.pop();						
						   }
					}
				}
		}
	}
	
	
	
}
