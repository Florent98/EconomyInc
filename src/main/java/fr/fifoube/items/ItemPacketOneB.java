/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.item.Item;

public class ItemPacketOneB extends Item implements IValue {

    public ItemPacketOneB(Properties properties) {
        super(properties);
    }

    @Override
    public int getValue() {
        return 9;
    }
}
