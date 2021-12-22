/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.item.Item;

public class ItemPacketTenB extends Item implements IValue {

    public ItemPacketTenB(Properties properties) {
        super(properties);
    }

    @Override
    public int getValue() {
        return 90;
    }
}
