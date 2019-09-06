package fr.fifoube.main;

import fr.fifoube.items.ItemsRegistery;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGroupEIC extends ItemGroup {
   
	public ItemGroupEIC(String label) {
		super(label);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack createIcon() {
		return new ItemStack(ItemsRegistery.ITEM_CREDITCARD);
	}

}
