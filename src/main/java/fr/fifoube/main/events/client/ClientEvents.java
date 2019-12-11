package fr.fifoube.main.events.client;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import fr.fifoube.blocks.BlocksRegistery;
import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import fr.fifoube.main.ConfigFile;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;


@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

	@SuppressWarnings("static-access")
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onDrawBlockHighlightEvent(DrawBlockHighlightEvent event)
	{	
		if(ConfigFile.canPreviewItemInBlock)
			if(event.getTarget().getType() ==  event.getTarget().getType().BLOCK)
			{
				World world = Minecraft.getInstance().world;
				BlockPos pos = new BlockPos(event.getTarget().getHitVec()); 
				Block block = world.getBlockState(pos).getBlock();
				if(block == BlocksRegistery.BLOCK_SELLER)
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
							EntityRendererManager renderM = Minecraft.getInstance().getRenderManager();
							GL11.glPushMatrix();
							GlStateManager.enableRescaleNormal();
							RenderHelper.enableStandardItemLighting();
							if(te.getFacing().substring(0, 4).equals("west"))
							{
								j = 31.5F;
							}
							else if(te.getFacing().substring(0, 4).equals("east"))
							{
								j = 94F;
							}
							else if(te.getFacing().equals("south"))
							{
								j = 188F;
							}
							ItemStack stack = new ItemStack(te.getStackInSlot(0).getItem(), 1);
							if(te.getAmount() == 0)
							{
								stack = new ItemStack(Blocks.BARRIER, 1);
							}
							ItemEntity entItem = new ItemEntity(world, x + 0.5F, y + i, z + 0.5F, stack);
							try {
								ObfuscationReflectionHelper.setPrivateValue(ItemEntity.class, entItem, 0, "field_70290_d");
							} catch (Exception e) {
								System.out.println("Could not find the field hoverStart also known as field_70290_d.");
							}
							renderM.renderEntityStatic(entItem, 1.0F * j, false);
							RenderHelper.disableStandardItemLighting();
							GlStateManager.disableRescaleNormal();
							GL11.glPopMatrix();		
						}
					}
				}
			}
			else
			{
				return;
			}
		}
	
	
	
}
