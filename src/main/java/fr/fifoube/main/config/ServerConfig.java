package fr.fifoube.main.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {

	public ForgeConfigSpec.IntValue multiplierGoldNuggetWeight;
	public ForgeConfigSpec.BooleanValue canAccessCardWithoutWT;
	public ForgeConfigSpec.BooleanValue doesBankGenerateInVillages; 
	public ForgeConfigSpec.BooleanValue goldNuggetRecipe;
	public ForgeConfigSpec.ConfigValue<String> plotBorderBlock;

	ServerConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("general");
		multiplierGoldNuggetWeight = builder
                .comment("It will multiply the weight of the nugget with this number to create a funds to add to the credit card.")
                .translation("text.economyinc.config.nuggetweight")
                .defineInRange("multiplierGoldNuggetWeight", 2, 1, 9999);
		goldNuggetRecipe = builder
				.comment("Allow the mod to replace the furnace recipe for the gold ore and give instead the EconomyInc's nugget.")
                .translation("text.economyinc.config.nuggetrecipe")
				.define("goldNuggetRecipe", true);
		canAccessCardWithoutWT = builder
		.comment("Allow player that have the wireless technology to access their account without an ATM nearby.")
        .translation("text.economyinc.config.access")
		.define("canAccessCardWithoutWT", true);
		doesBankGenerateInVillages = builder
				.comment("Allow or not the bank to generate in villages, by default it generates turn it to false to disable its generation.")
                .translation("text.economyinc.config.bankvillage")
				.define("doesBankGenerateInVillages", true);
		plotBorderBlock = builder
				.comment("Define the block use to create the edges of the plot.")
                .translation("text.economyinc.config.edgeplot")
				.define("plotBorderBlock", "minecraft:smooth_stone_slab");
		builder.pop();
	}
}
