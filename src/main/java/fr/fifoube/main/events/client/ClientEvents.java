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
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
			BlockPos pos = event.getTarget().getPos();
			Block block = world.getBlockState(pos).getBlock();
				if(block == BlocksRegistry.BLOCK_SELLER)
				{
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
							Vec3d vec = event.getInfo().getProjectedView();
							matrixStack.translate(-vec.x, -vec.y, -vec.z);
							matrixStack.translate(x + 0.5, y + 0.5, z + 0.5);
				            matrixStack.scale(0.5F, 0.5F, 0.5F);
				            switch (direction) {
							case NORTH:
								matrixStack.rotate(new Quaternion(new Vector3f(0, 1.0f, 0), 180f, true));
								break;
							case SOUTH:
								matrixStack.rotate(new Quaternion(new Vector3f(0, 1.0f, 0), 0f, true));
								break;	
							case WEST:
								matrixStack.rotate(new Quaternion(new Vector3f(0, 1.0f, 0), -90f, true));
								break;
							case EAST:
								matrixStack.rotate(new Quaternion(new Vector3f(0, 1.0f, 0), 90f, true));
								break;
							default:
								matrixStack.rotate(new Quaternion(new Vector3f(0, 1.0f, 0), 180f, true));
								break;
							}
							renderM.renderItem(stack, TransformType.FIXED, 15728880, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
						    matrixStack.pop();						
						   }
					}
				}
		}
	}
	
	
	
}
