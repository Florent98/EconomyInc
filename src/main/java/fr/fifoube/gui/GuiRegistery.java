/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import fr.fifoube.gui.container.type.MenuTypeRegistery;
import net.minecraft.client.gui.screens.MenuScreens;

public class GuiRegistery {

	public static void register() {

		MenuScreens.register(MenuTypeRegistery.SELLER_TYPE.get(), GuiSeller::new);
		MenuScreens.register(MenuTypeRegistery.SELLERBUY_TYPE.get(), GuiSellerBuy::new);
		MenuScreens.register(MenuTypeRegistery.VAULT_TYPE.get(), GuiVault::new);
		MenuScreens.register(MenuTypeRegistery.VAULT2BY2_TYPE.get(), GuiVault2by2::new);
		MenuScreens.register(MenuTypeRegistery.CHANGER_TYPE.get(), GuiChanger::new);
		MenuScreens.register(MenuTypeRegistery.BUYER_TYPE.get(), GuiBuyerContainer::new);
		MenuScreens.register(MenuTypeRegistery.BUYER_CREA_TYPE.get(), GuiBuyerCreation::new);

	}
}
