/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.world.item.Item;

public class ItemHundreedB extends Item implements IValue{

	public ItemHundreedB(Properties properties) {
		super(properties);
	}

	@Override
	public int getValue() {
		return 100;
	}
}
