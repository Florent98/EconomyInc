/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ItemGoldNuggetSubs extends Item{
	
	public ItemGoldNuggetSubs(Properties properties) {
		super(properties);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		
		if(stack.hasTag())
		{
	        String weight = stack.getTag().getString("weight");
			tooltip.add(new StringTextComponent(I18n.format("title.weight") + weight));
		}
	}

}
