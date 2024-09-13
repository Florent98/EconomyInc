/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.world.item.Item;

public class ItemOneB extends Item implements IValue{

	public ItemOneB(Properties properties) {
		super(properties);
	}
	
	@Override
	public int getValue() {
		return 1;
	}

}
