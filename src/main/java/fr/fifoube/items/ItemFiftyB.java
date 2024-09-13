/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.world.item.Item;

public class ItemFiftyB extends Item implements IValue {

	public ItemFiftyB(Properties properties) {
		super(properties);
	}

	@Override
	public int getValue() {
		return 50;
	}
}
