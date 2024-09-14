/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

public class ItemWireless extends Item {

	public ItemWireless(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		
		ItemStack stack = player.getItemInHand(hand);
		ItemStack stackFound = ItemStack.EMPTY;
		
		if(!level.isClientSide)
		{
			boolean found = false;
			for (int i = 0; i < player.getInventory().getContainerSize(); i++) {		
			if(!found)
				if(player.getInventory().getItem(i).getItem().equals(ItemsRegistery.CREDITCARD.get()))
				{
					if(player.getInventory().getItem(i).hasTag())
					{
							stackFound = player.getInventory().getItem(i);
							found = true;
					}
				}
			}
			if(found && stackFound != ItemStack.EMPTY)
			{
				if(!stackFound.getTag().getBoolean("Linked"))
				{
					stackFound.getTag().putBoolean("Linked", true);
					player.getInventory().removeItem(stack);
					player.sendSystemMessage(Component.translatable("title.cardUpdated"));
					InteractionResultHolder.success(stackFound);
	
				}
				else
				{
					player.sendSystemMessage(Component.translatable("title.cardAlreadyLinked"));
					InteractionResultHolder.fail(stackFound);
				}
			}
			else
			{
				player.sendSystemMessage(Component.translatable("title.cardTooMuch"));
				InteractionResultHolder.fail(stackFound);
			}
		}
		return InteractionResultHolder.fail(stack);
		
	}
	
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
		
		tooltip.add(Component.translatable("title.wireless").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD, ChatFormatting.ITALIC));

	}

}
