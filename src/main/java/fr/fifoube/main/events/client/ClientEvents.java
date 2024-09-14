/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.events.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fr.fifoube.blocks.BlockBuyer;
import fr.fifoube.blocks.BlockSeller;
import fr.fifoube.blocks.BlocksRegistry;
import fr.fifoube.blocks.blockentity.BlockEntityBuyer;
import fr.fifoube.blocks.blockentity.BlockEntitySeller;
import fr.fifoube.blocks.blockentity.BlockEntityTypeRegistery;
import fr.fifoube.blocks.blockentity.specialrenderer.BlockEntityBillsSpecialRenderer;
import fr.fifoube.blocks.models.ModelBills;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.config.ConfigFile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3fc;


@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
	
	@SubscribeEvent
	public void onDrawBlockHighlightEvent(RenderHighlightEvent.Block event)
	{
		if(ConfigFile.canPreviewItemInBlock)
		{
			Level world = Minecraft.getInstance().level;
			BlockPos pos = event.getTarget().getBlockPos();
			Block block = world.getBlockState(pos).getBlock();
			if(block == BlocksRegistry.BLOCK_SELLER.get())
				{
					BlockEntitySeller te = (BlockEntitySeller)world.getBlockEntity(pos);
					if(te != null)
					{
						if(te.getCreated())
						{
							int x = pos.getX();
							int y = pos.getY();
							int z = pos.getZ();
							ItemRenderer renderM = Minecraft.getInstance().getItemRenderer();
							PoseStack matrixStack = event.getPoseStack();
							MultiBufferSource buffer = event.getMultiBufferSource();
							buffer.getBuffer(RenderType.solid());
							Direction direction = (Direction)world.getBlockState(pos).getValue(BlockSeller.FACING);
							matrixStack.pushPose();					
							ItemStack stack = new ItemStack(te.getInventory().getStackInSlot(0).getItem(), 1);
							if(te.getAmount() == 0)
							{
								stack = new ItemStack(Blocks.BARRIER, 1);
							}
							Vec3 vec = event.getCamera().getPosition();
						    matrixStack.translate(-vec.x, -vec.y, -vec.z);
							matrixStack.translate(x + 0.5, y + 0.5, z + 0.5);
				            matrixStack.scale(0.5F, 0.5F, 0.5F);
				            switch (direction) {
							case NORTH:
								matrixStack.mulPose(Axis.YP.rotationDegrees(180f));
								break;
							case SOUTH:
								matrixStack.mulPose(Axis.YP.rotationDegrees(0f));
								break;	
							case WEST:
								matrixStack.mulPose(Axis.YP.rotationDegrees(-90f));
								break;
							case EAST:
								matrixStack.mulPose(Axis.YP.rotationDegrees(90f));
								break;
							default:
								matrixStack.mulPose(Axis.YP.rotationDegrees(180f));
								break;
							}
							renderM.renderStatic(stack, ItemDisplayContext.FIXED, 15728850,  OverlayTexture.NO_OVERLAY, matrixStack, buffer, world, (int)pos.asLong());
							matrixStack.popPose();
						   }
					}
				}
			else if(block == BlocksRegistry.BLOCK_BUYER.get())
			{
				BlockEntityBuyer te = (BlockEntityBuyer)world.getBlockEntity(pos);
				if(te != null)
				{
					if(te.isCreated())
					{
						int x = pos.getX();
						int y = pos.getY();
						int z = pos.getZ();
						float i = 0.1f;
						float j = 0.0F;
						ItemRenderer renderM = Minecraft.getInstance().getItemRenderer();
						PoseStack matrixStack = event.getPoseStack();
						MultiBufferSource buffer = event.getMultiBufferSource();
						Direction direction = (Direction)world.getBlockState(pos).getValue(BlockBuyer.FACING);
						matrixStack.pushPose();
						ItemStack stack = new ItemStack(te.getItemStackToBuy().getItem(), 1);
						Vec3 vec = event.getCamera().getPosition();
						matrixStack.translate(-vec.x, -vec.y, -vec.z);
						matrixStack.translate(x + 0.5, y + 0.5, z + 0.5);
						matrixStack.scale(0.5F, 0.5F, 0.5F);
						switch (direction) {
							case NORTH:
								matrixStack.mulPose(Axis.YP.rotationDegrees(180f));
								break;
							case SOUTH:
								matrixStack.mulPose(Axis.YP.rotationDegrees(0f));
								break;
							case WEST:
								matrixStack.mulPose(Axis.YP.rotationDegrees(-90f));
								break;
							case EAST:
								matrixStack.mulPose(Axis.YP.rotationDegrees(90f));
								break;
							default:
								matrixStack.mulPose(Axis.YP.rotationDegrees(180f));
								break;
						}
						renderM.renderStatic(stack, ItemDisplayContext.FIXED, 15728850,  OverlayTexture.NO_OVERLAY, matrixStack, buffer, world, (int)pos.asLong());
						matrixStack.popPose();
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {

		event.registerBlockEntityRenderer(BlockEntityTypeRegistery.TILE_BILLS.get(), BlockEntityBillsSpecialRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefintions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModelBills.LAYER_LOCATION, ModelBills::createDefinition);
	}
	
}
