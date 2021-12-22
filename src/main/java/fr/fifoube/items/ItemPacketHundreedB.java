/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.item.Item;

public class ItemPacketHundreedB extends Item implements IValue {

    public ItemPacketHundreedB(Properties properties) {
        super(properties);
    }

    @Override
    public int getValue() {
        return 900;
    }
}
