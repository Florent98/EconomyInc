/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.item.Item;

public class ItemFiveB extends Item implements IValue{

	public ItemFiveB(Properties properties) {
		super(properties);
	}
	
	@Override
	public int getValue() {
		return 5;
	}

}
