/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main;


import fr.fifoube.blocks.BlocksRegistry;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EconomyIncModTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModEconomyInc.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ECONOMYINC_TAB = CREATIVE_MODE_TABS.register("eic_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.EIC"))
                    .icon(() -> new ItemStack(ItemsRegistery.CREDITCARD.get()))
                    .displayItems((params, output) -> {
                        ItemsRegistery.REGISTER.getEntries().forEach(data -> {
                            if (data.get() != BlocksRegistry.BLOCK_VAULT_2BY2.get().asItem()) {
                                output.accept(data.get());
                            }
                        });
                    })
                    .build());
}
