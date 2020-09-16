/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import java.util.List;
import java.util.UUID;

import fr.fifoube.gui.ClientGuiScreen;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.config.ConfigFile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemCreditCard extends Item{
	
	public ItemCreditCard(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

		ItemStack itemStackIn = playerIn.getHeldItemMainhand();
	 	if(playerIn.getHeldItemOffhand().isItemEqual(new ItemStack(ItemsRegistery.ITEM_CREDITCARD)))
	 	{
	 	}
	 	else
	 	{
	 		if(!playerIn.isCrouching())
	        {	
			        if(itemStackIn.hasTag()) 
				    {
			        	String nameCard = playerIn.getHeldItemMainhand().getTag().getString("OwnerUUID");
						String nameGame = playerIn.getUniqueID().toString();
						if(nameCard.equals(nameGame))
						{
							if(worldIn.isRemote)
							{
								if(ConfigFile.canAccessCardWithoutWT)
								{
									if(itemStackIn.getTag().getBoolean("Linked"))
										{
											ClientGuiScreen.openGui(0, null);											
										}
										else
										{
											playerIn.sendMessage(new StringTextComponent(I18n.format("title.notLinked")), playerIn.getUniqueID());
										}
								}
							}
						}
			        }
	        		
	            return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemStackIn);
	        }
	        	if(!worldIn.isRemote)
	        	{
	        		if(!itemStackIn.hasTag())
	        		{
	        			itemStackIn.setTag(new CompoundNBT());
	        		}
	        		
	        		if(!itemStackIn.getTag().contains("Owner"))
	        		{
	        			playerIn.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
	        			UUID ownerUUID = playerIn.getUniqueID();
	        			itemStackIn.getTag().putString("OwnerUUID", ownerUUID.toString());
	        			itemStackIn.getTag().putString("Owner", playerIn.getDisplayName().getString());
	        			itemStackIn.getTag().putBoolean("Owned", true);
	        			itemStackIn.getTag().putBoolean("Linked", false);
	        			worldIn.playSound(null, playerIn.getPosition(),  SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5f, 0.0f);
	        			});
	        		}
	        		
	        	}

	 	}
	       
        return new ActionResult<ItemStack>(ActionResultType.FAIL, itemStackIn);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
	{
		PlayerEntity playerIn = Minecraft.getInstance().player;
 		
		 if(!stack.hasTag())
	        {
	            return;
	        }
	 	 playerIn.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {

	 		 double funds = data.getMoney();
	 		 boolean linked = stack.getTag().getBoolean("Linked");
		     String linkedValue = "";
			 if(linked == true)
			 {
				 linkedValue = I18n.format("title.yes");
			 }
			 else
			 {
				linkedValue = I18n.format("title.no");
			 }
			 
		        String ownerName = stack.getTag().getString("Owner");		      
		        tooltip.add(new StringTextComponent(I18n.format("title.ownerCard") + " : " + ownerName));
		        tooltip.add(new StringTextComponent(I18n.format("title.fundsCard") + " : " + String.valueOf(funds)));
		        tooltip.add(new StringTextComponent(I18n.format("title.linkdCard") + " : " + linkedValue));
	 		 
	 	 });
	    
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		if(stack.hasTag())
			return true;
		return false;
	}
}
