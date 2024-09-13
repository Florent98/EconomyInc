/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main;

import fr.fifoube.items.ItemsRegistery;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EconomyIncModTab {

    public static final CreativeModeTab ECONOMYINC_TAB = new CreativeModeTab("EIC") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemsRegistery.CREDITCARD.get());
        }
    };

}