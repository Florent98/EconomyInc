package fr.fifoube.items;

import java.util.List;

import fr.fifoube.main.capabilities.CapabilityMoney;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemWireless extends Item {

	public ItemWireless(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		
		ItemStack itemStackIn = player.getHeldItemOffhand();
		ItemStack itemStackInC = player.getHeldItemMainhand();
		int totalcount = 0;
		if(!worldIn.isRemote)
		{
			if(!player.inventory.hasItemStack(itemStackIn))
			{
				if(player.inventory.hasItemStack(itemStackInC))
				{
					for(int i = 0; i < player.inventory.getSizeInventory(); i++)
					{
						if(player.inventory.getStackInSlot(i) != null)
						{
							if(player.inventory.getStackInSlot(i).getItem() instanceof ItemCreditCard)
							{
									totalcount++;
									ItemStack hasCardIS = player.inventory.getStackInSlot(i);
									if(!(totalcount > 1))
									{
											if(hasCardIS.hasTag() && hasCardIS.getTag().hasKey("Owner"))
											{
												
												String nameCard = hasCardIS.getTag().getString("OwnerUUID");
												String nameGame = player.getUniqueID().toString();

												if(nameCard.equals(nameGame))
												{
													boolean linked = hasCardIS.getTag().getBoolean("Linked");
													if(linked == false)
													{
														player.sendMessage(new TextComponentString("Card updated !"));
														hasCardIS.getTag().setBoolean("Linked", true);
														player.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
															data.setLinked(true);
														});
													}
													else
													{
														player.sendMessage(new TextComponentString("Card is already linked"));
														player.addItemStackToInventory(itemStackInC);
													}
													
												}
											}
											else
											{
												player.addItemStackToInventory(itemStackInC);	
											}

								}
								return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
							}
						}
						else
						{
								player.sendMessage(new TextComponentString("You can only linked one card, please remove the uncessary cards"));
								return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
						}
					}
				}
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TextComponentString(I18n.format("title.wireless")));
	}
}
