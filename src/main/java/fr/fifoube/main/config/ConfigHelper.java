/** 
 *  Copyright 2020, Turrioni Florent, All rights reserved.
 *  
 * 	This program is copyrighted for all the files and code 
 * 	included in this program. No reuse, modification or 
 * 	reselling is authorized without any legal document 
 *  approved by the owner*.
 * 
 * 	*Owner : Turrioni Florent resident in Belgium and 
 *  contactable at florent_turrioni@hotmail.com
 *  
 * */

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
