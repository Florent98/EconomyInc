package fr.fifoube.gui;

import fr.fifoube.gui.container.type.ContainerTypeRegistery;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GuiRegistery {

	public static void register() {

		ScreenManager.registerFactory(ContainerTypeRegistery.SELLER_TYPE, GuiSeller::new);
		ScreenManager.registerFactory(ContainerTypeRegistery.SELLERBUY_TYPE, GuiSellerBuy::new);
		ScreenManager.registerFactory(ContainerTypeRegistery.VAULT_TYPE, GuiVault::new);
		ScreenManager.registerFactory(ContainerTypeRegistery.VAULT2BY2_TYPE, GuiVault2by2::new);

	}
}
