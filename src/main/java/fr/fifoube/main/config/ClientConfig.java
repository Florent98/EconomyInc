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

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

	final ForgeConfigSpec.BooleanValue canPreviewItemInBlock;

	ClientConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("Block Seller");
		canPreviewItemInBlock = builder
				.comment("Allow you to disable the item preview in block seller when you hover it.")
                .translation("text.economyinc.config.preview")
				.define("canPreviewItemInBlock", true);
		builder.pop();
	}
}
