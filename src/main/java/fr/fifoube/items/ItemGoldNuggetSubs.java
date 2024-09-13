/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemGoldNuggetSubs extends Item {
	
	public ItemGoldNuggetSubs(Properties properties) {
		super(properties);
	}
	
	
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
		
		if(stack.hasTag())
		{
			if(stack.getTag().contains("weight"))
			{
				String weight = stack.getTag().getString("weight");
				tooltip.add(new TranslatableComponent("title.weight", weight));	
			}
		}
	}
}
