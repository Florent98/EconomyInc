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
		ConfigFile.rangeGoldNugget = ConfigHolder.SERVER.rangeGoldNugget.get();
		ConfigFile.plotBorderBlock = ConfigHolder.SERVER.plotBorderBlock.get();
		ConfigFile.goldChangerDuration = ConfigHolder.SERVER.goldChangerDuration.get();
		ConfigFile.cooldownSeller = ConfigHolder.SERVER.cooldownSeller.get();

		ConfigFile.doMobsGiveMoney = ConfigHolder.SERVER.doMobsGiveMoney.get();
		ConfigFile.mobsMoney = ConfigHolder.SERVER.mobsMoney.get();

		ConfigFile.doSpecificMobsGiveMoney = ConfigHolder.SERVER.doSpecificMobsGiveMoney.get();
		ConfigFile.zombieMoney = ConfigHolder.SERVER.zombieMoney.get();
		ConfigFile.creeperMoney = ConfigHolder.SERVER.creeperMoney.get();
		ConfigFile.skeletonMoney = ConfigHolder.SERVER.skeletonMoney.get();
		ConfigFile.spiderMoney = ConfigHolder.SERVER.spiderMoney.get();
		ConfigFile.witchMoney = ConfigHolder.SERVER.witchMoney.get();

	}
}
