package fr.fifoube.main.events.client;


import fr.fifoube.main.ModEconomyInc;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

	/*@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onDrawBlockHighlightEvent(DrawBlockHighlightEvent event)
	{	
			PlayerEntity playerIn = event.p;
			World worldIn = even;
			if(event.getTarget().getType() ==  event.getTarget().getType().ENTITY)
			{
				return;
			}
			else
			{
				if(worldIn != null)
				{
					BlockState blockstate = worldIn.getBlockState(new BlockPos(event.getTarget().getHitVec()));
					if(blockstate.getBlock() == BlocksRegistery.BLOCK_SELLER)
					{
						TileEntityBlockSeller te = (TileEntityBlockSeller)worldIn.getTileEntity(event.getTarget().getBlockPos());
						if(te != null)
						{
							if(te.getCreated())
							{
								int x = blockstate.getBlock().pos
								int y = event.getTarget().getBlockPos().getY();
								int z = event.getTarget().getBlockPos().getZ();
								float i = 0f;
								float j = 0.0F;
								RenderManager renderM = Minecraft.getMinecraft().getRenderManager();
								GL11.glPushMatrix();
								GlStateManager.enableRescaleNormal();
								RenderHelper.enableStandardItemLighting();
								if(te.getStackInSlot(0).getUnlocalizedName().substring(0, 4).equals("tile"))
								{
									i = 0.1F;
								}
								if(te.getFacing().substring(0, 4).equals("west"))
								{
									j = 94F;
								}
								else if(te.getFacing().substring(0, 4).equals("east"))
								{
									j = 31.5F;
								}
								else if(te.getFacing().equals("north"))
								{
									j = 188F;
								}
								ItemStack stack = new ItemStack(te.getStackInSlot(0).getItem(), 1, te.getStackInSlot(0).getMetadata());
								if(te.getAmount() == 0)
								{
									stack = new ItemStack(Blocks.BARRIER, 1, 0);
								}
								EntityItem entItem = new EntityItem(worldIn, x + 0.5, y + i, z + 0.5, stack);
								entItem.hoverStart = 0.0F;
								renderM.renderEntityStatic(entItem, 1.0F * j, false);
								RenderHelper.disableStandardItemLighting();
								GlStateManager.disableRescaleNormal();
								GL11.glPopMatrix();		
							}
						}
					}
				}
			}
		}*/
	
	
	
}
