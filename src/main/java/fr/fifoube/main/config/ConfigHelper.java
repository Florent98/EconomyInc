package fr.fifoube.main.config;

import net.minecraftforge.fml.config.ModConfig;

public class ConfigHelper {

	public static void bakeClient(final ModConfig config) {
		
		ConfigFile.canPreviewItemInBlock = ConfigHolder.CLIENT.canPreviewItemInBlock.get();

	}

	public static void bakeServer(final ModConfig config) {

		ConfigFile.multiplierGoldNuggetWeight = ConfigHolder.SERVER.multiplierGoldNuggetWeight.get();
		ConfigFile.canAccessCardWithoutWT = ConfigHolder.SERVER.canAccessCardWithoutWT.get();
		ConfigFile.doesBankGenerateInVillages = ConfigHolder.SERVER.doesBankGenerateInVillages.get(); //SERVER
		ConfigFile.goldNuggetRecipe = ConfigHolder.SERVER.goldNuggetRecipe.get();
	}
}
