/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import net.minecraft.item.Item;

public class ItemTwentyB extends Item implements IValue {

    public ItemTwentyB(Properties properties) {
        super(properties);
    }

    @Override
    public int getValue() {
        return 20;
    }

}
