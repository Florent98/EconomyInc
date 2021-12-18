/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import fr.fifoube.gui.container.type.ContainerTypeRegistery;
import net.minecraft.client.gui.ScreenManager;

public class GuiRegistery {

	public static void register() {

		ScreenManager.registerFactory(ContainerTypeRegistery.SELLER_TYPE, GuiSeller::new);
		ScreenManager.registerFactory(ContainerTypeRegistery.VAULT_TYPE, GuiVault::new);
		ScreenManager.registerFactory(ContainerTypeRegistery.VAULT2BY2_TYPE, GuiVault2by2::new);
		ScreenManager.registerFactory(ContainerTypeRegistery.CHANGER_TYPE, GuiChanger::new);
		ScreenManager.registerFactory(ContainerTypeRegistery.BUYER_CREATION_TYPE, GuiBuyerCreation::new);
		ScreenManager.registerFactory(ContainerTypeRegistery.BUYER_TYPE, GuiBuyerContainer::new);
	}
}
