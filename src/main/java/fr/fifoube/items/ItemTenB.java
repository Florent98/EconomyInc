/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.world.item.Item;

public class ItemTenB extends Item implements IValue{

	public ItemTenB(Properties properties) {
		super(properties);
	}

	@Override
	public int getValue() {
		return 10;
	}
}
