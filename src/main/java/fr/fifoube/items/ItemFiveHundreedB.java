/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.item.Item;

public class ItemFiveHundreedB extends Item implements IValue{

	public ItemFiveHundreedB(Properties properties) {
		super(properties);
	}
	
	@Override
	public int getValue() {
		return 500;
	}

}
