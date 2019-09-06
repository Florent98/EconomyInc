package fr.fifoube.gui;

import fr.fifoube.gui.container.type.ContainerTypeRegistery;
import net.minecraft.client.gui.ScreenManager;

public class GuiRegistery {

	private void register() {

		ScreenManager.registerFactory(ContainerTypeRegistery.SELLER_TYPE, GuiSeller::new);
	}
}
