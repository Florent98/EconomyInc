/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.item.Item;

public class ItemPacketFiftyB extends Item implements IValue {

    public ItemPacketFiftyB(Properties properties) {
        super(properties);
    }

    @Override
    public int getValue() {

        return 450;
    }
}
