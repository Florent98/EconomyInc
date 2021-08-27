/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.item.Item;

public class ItemPacketFiveHundreedB extends Item implements IValue{

	public ItemPacketFiveHundreedB(Properties properties) {
		super(properties);
	}
	
	@Override
	public int getValue() {
		return 4500;
	}
}
