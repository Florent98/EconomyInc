/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main;


import fr.fifoube.blocks.BlocksRegistry;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EconomyIncModTab {

    public static CreativeModeTab ECONOMYINC_TAB;

    public static void addCreativeModTab(CreativeModeTabEvent.Register event){
        ECONOMYINC_TAB = event.registerCreativeModeTab(new ResourceLocation(ModEconomyInc.MOD_ID, "eic_tab"),
                builder -> builder.icon(() -> new ItemStack(ItemsRegistery.CREDITCARD.get()))
                        .title(Component.translatable("itemGroup.EIC")));

    }

    public static void addToTab(CreativeModeTabEvent.BuildContents event){
        if(event.getTab() == EconomyIncModTab.ECONOMYINC_TAB)
        {
            ItemsRegistery.REGISTER.getEntries().forEach(data -> {
                if(data.get() != BlocksRegistry.BLOCK_VAULT_2BY2.get().asItem())
                {
                    event.accept(data.get());
                }
            });
        }
    }

}