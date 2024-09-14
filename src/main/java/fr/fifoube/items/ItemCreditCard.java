/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import fr.fifoube.gui.ClientGuiScreen;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.config.ConfigFile;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemCreditCard extends Item {
	
	public ItemCreditCard(Properties properties) {
		super(properties);
	}

	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand hand) {

			ItemStack stack = playerIn.getItemInHand(hand);
			if(stack != ItemStack.EMPTY && stack.getItem() instanceof ItemCreditCard)
			if(!playerIn.isCrouching())
	        {	
			        if(stack.hasTag()) 
				    {
			        	String nameCard = playerIn.getItemInHand(hand).getTag().getString("OwnerUUID");
						String nameGame = playerIn.getStringUUID();
						if(nameCard.equals(nameGame))
						{
							if(level.isClientSide)
							{
								if(ConfigFile.canAccessCardWithoutWT)
								{
									if(stack.getTag().getBoolean("Linked"))
										{
											ClientGuiScreen.openGui(0, null);											
										}
										else
										{
											playerIn.sendSystemMessage(Component.translatable("title.notLinked"));
										}
								}
							}
						}
			        }
	        		
	            return InteractionResultHolder.success(stack);
	        }
	        	
	 			if(!level.isClientSide)
	        	{
	        		if(!stack.hasTag())
	        		{
	        			stack.setTag(new CompoundTag());
	        		}
	        		
	        		if(!stack.getTag().contains("Owner"))
	        		{
	        			playerIn.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> {
	        			stack.getTag().putString("OwnerUUID", playerIn.getStringUUID());
	        			stack.getTag().putString("Owner", playerIn.getDisplayName().getString());
	        			stack.getTag().putBoolean("Owned", true);
	        			stack.getTag().putBoolean("Linked", false);
	        			level.playSound(null, playerIn.blockPosition(),  SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5f, 0.0f);
	        			});
	        		}
	        		
	        	}

	 			return InteractionResultHolder.fail(stack);
	 	}

	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
		
		Player playerIn = Minecraft.getInstance().player;
 		
		 if(!stack.hasTag())
	        {
	            return;
	        }
	 	 playerIn.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {

	 		 double funds = data.getMoney();
	 		 boolean linked = stack.getTag().getBoolean("Linked");
		     MutableComponent linkedValue = null;
			 if(linked == true)
			 {
				 linkedValue = Component.translatable("title.yes");
			 }
			 else
			 {
				linkedValue = Component.translatable("title.no");
			 }
			 
		        String ownerName = stack.getTag().getString("Owner");	
		        tooltip.add(Component.translatable("title.ownerCard", ownerName));
		        tooltip.add(Component.translatable("title.fundsCard", funds));
		        tooltip.add(Component.translatable("title.linkedCard", linkedValue));
	 		 
	 	 });

	}

	@Override
	public boolean isFoil(ItemStack stack) {
		if(stack.hasTag())
			return true;
		return false;
	}
	
}
