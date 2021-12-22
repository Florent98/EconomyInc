/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.item.Item;

public class ItemPacketFiveB extends Item implements IValue {

    public ItemPacketFiveB(Properties properties) {
        super(properties);
    }

    @Override
    public int getValue() {

        return 45;
    }
}
