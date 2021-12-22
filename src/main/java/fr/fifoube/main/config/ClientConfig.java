/*******************************************************************************
 *******************************************************************************/
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
