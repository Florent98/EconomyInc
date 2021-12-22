/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.item.Item;

public class ItemPacketTwentyB extends Item implements IValue {

    public ItemPacketTwentyB(Properties properties) {
        super(properties);
    }

    @Override
    public int getValue() {
        return 180;
    }

}
