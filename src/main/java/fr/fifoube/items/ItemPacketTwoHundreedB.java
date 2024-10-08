/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemPacketTwoHundreedB extends Item implements IValue{

	public ItemPacketTwoHundreedB(Properties properties) {
		super(properties);
	}
	
	@Override
	public int getValue() {
		return 1800;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {

		tooltip.add(Component.translatable("money.value", getValue()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GREEN));

	}
}
