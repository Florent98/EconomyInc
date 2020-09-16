/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.config;

import net.minecraftforge.fml.config.ModConfig;

public class ConfigHelper {

	public static void bakeClient(final ModConfig config) {
		
		ConfigFile.canPreviewItemInBlock = ConfigHolder.CLIENT.canPreviewItemInBlock.get();

	}

	public static void bakeServer(final ModConfig config) {

		ConfigFile.multiplierGoldNuggetWeight = ConfigHolder.SERVER.multiplierGoldNuggetWeight.get();
		ConfigFile.canAccessCardWithoutWT = ConfigHolder.SERVER.canAccessCardWithoutWT.get();
		ConfigFile.goldNuggetRecipe = ConfigHolder.SERVER.goldNuggetRecipe.get();
		ConfigFile.plotBorderBlock = ConfigHolder.SERVER.plotBorderBlock.get();
		ConfigFile.goldChangerDuration = ConfigHolder.SERVER.goldChangerDuration.get();
		ConfigFile.cooldownSeller = ConfigHolder.SERVER.cooldownSeller.get();
	}
}
