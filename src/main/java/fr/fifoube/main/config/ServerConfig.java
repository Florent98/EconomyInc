/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {

	public ForgeConfigSpec.IntValue multiplierGoldNuggetWeight;
	public ForgeConfigSpec.BooleanValue canAccessCardWithoutWT;
	public ForgeConfigSpec.ConfigValue<String> plotBorderBlock;
	public ForgeConfigSpec.IntValue goldChangerDuration;
	public ForgeConfigSpec.IntValue cooldownSeller;
	public ForgeConfigSpec.DoubleValue rangeGoldNugget;

	public ForgeConfigSpec.BooleanValue doMobsGiveMoney;
	public ForgeConfigSpec.DoubleValue mobsMoney;

	public ForgeConfigSpec.BooleanValue doSpecificMobsGiveMoney;
	public ForgeConfigSpec.DoubleValue zombieMoney;
	public ForgeConfigSpec.DoubleValue creeperMoney;
	public ForgeConfigSpec.DoubleValue skeletonMoney;
	public ForgeConfigSpec.DoubleValue spiderMoney;
	public ForgeConfigSpec.DoubleValue witchMoney;

	public ForgeConfigSpec.DoubleValue payMinAmount;
	public ForgeConfigSpec.DoubleValue payMaxAmount;
	public ForgeConfigSpec.IntValue payCooldownSeconds;
	public ForgeConfigSpec.DoubleValue atmWithdrawFeePercent;
	public ForgeConfigSpec.DoubleValue goldConvertFeePercent;
	public ForgeConfigSpec.BooleanValue enableAtmWithdrawFee;
	public ForgeConfigSpec.BooleanValue enableGoldConvertFee;
	public ForgeConfigSpec.DoubleValue atmConfirmThreshold;
	public ForgeConfigSpec.IntValue baltopSize;
	public ForgeConfigSpec.IntValue maxTransactionHistory;

	ServerConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("general");
		multiplierGoldNuggetWeight = builder
                .comment("It will multiply the weight of the nugget with this number to create a funds to add to the credit card.")
                .translation("text.economyinc.config.nuggetweight")
                .defineInRange("multiplierGoldNuggetWeight", 2, 1, 9999);
		rangeGoldNugget = builder
				.comment("Define the max weight the nugget can go to. By default it's at 1.2 meaning a nugget could go from 0.01 to 1.20 grams.")
				.translation("text.economyinc.config.nuggetRange")
				.defineInRange("rangeGoldNugget", 1.20, 0.01, 99.99);
		canAccessCardWithoutWT = builder
				.comment("Allow player that have the wireless technology to access their account without an ATM nearby.")
		        .translation("text.economyinc.config.access")
				.define("canAccessCardWithoutWT", true);
		plotBorderBlock = builder
				.comment("Define the block use to create the edges of the plot.")
                .translation("text.economyinc.config.edgeplot")
				.define("plotBorderBlock", "minecraft:smooth_stone_slab");
		goldChangerDuration = builder
				.comment("Change the time it takes for the gold changed to process.")
                .translation("text.economyinc.config.goldchangerduration")
                .defineInRange("goldChangerDuration", 356, 0, 5000);
		cooldownSeller = builder
				.comment("Cooldown applied for the seller, prevent fast buy.")
				.translation("text.economyinc.config.sellercooldown")
				.defineInRange("cooldownSeller", 20, 20, 500);

		doMobsGiveMoney = builder
				.comment("Do mobs will give money when a player kill them. No matter the type of the mob.")
				.translation("text.economyinc.config.mobsgivemoney")
				.define("doMobsGiveMoney", false);
		mobsMoney = builder
				.comment("Define the money given to the player when he kills a monster mob.")
				.translation("text.economyinc.config.mobsMoney")
				.defineInRange("mobsMoney", 0.25, 0.01, 99999);

		doSpecificMobsGiveMoney = builder
				.comment("Do specific mobs will give money when a player kill them. Values can be changed.")
				.translation("text.economyinc.config.specificmobsgivemoney")
				.define("doSpecificMobsGiveMoney", false);
		zombieMoney = builder
				.comment("Define the money given to the player when he kills a zombie")
				.translation("text.economyinc.config.moneyZombie")
				.defineInRange("zombieMoney", 0.25, 0.01, 99999);
		creeperMoney = builder
				.comment("Define the money given to the player when he kills a creeper")
				.translation("text.economyinc.config.moneyCreeper")
				.defineInRange("creeperMoney", 1.25, 0.01, 99999);
		skeletonMoney = builder
				.comment("Define the money given to the player when he kills a skeleton")
				.translation("text.economyinc.config.moneySkeleton")
				.defineInRange("skeletonMoney", 0.75, 0.01, 99999);
		spiderMoney = builder
				.comment("Define the money given to the player when he kills a spider")
				.translation("text.economyinc.config.moneySpider")
				.defineInRange("spiderMoney", 0.25, 0.01, 99999);
		witchMoney = builder
				.comment("Define the money given to the player when he kills a witch")
				.translation("text.economyinc.config.moneyWitch")
				.defineInRange("witchMoney", 2.0, 0.01, 99999);
		builder.pop();

		builder.push("economy");
		payMinAmount = builder
				.comment("Minimum amount for /pay transfers.")
				.defineInRange("payMinAmount", 1.0, 0.01, 999999999.0);
		payMaxAmount = builder
				.comment("Maximum amount for /pay transfers.")
				.defineInRange("payMaxAmount", 1000000.0, 1.0, 999999999.0);
		payCooldownSeconds = builder
				.comment("Cooldown between /pay commands in seconds.")
				.defineInRange("payCooldownSeconds", 3, 0, 300);
		atmWithdrawFeePercent = builder
				.comment("Fee percentage applied on ATM withdrawals when enableAtmWithdrawFee is true.")
				.defineInRange("atmWithdrawFeePercent", 2.0, 0.0, 50.0);
		enableAtmWithdrawFee = builder
				.comment("Enable or disable ATM withdrawal fees.")
				.define("enableAtmWithdrawFee", true);
		goldConvertFeePercent = builder
				.comment("Fee percentage applied on gold to account conversion when enableGoldConvertFee is true.")
				.defineInRange("goldConvertFeePercent", 2.0, 0.0, 50.0);
		enableGoldConvertFee = builder
				.comment("Enable or disable gold conversion fees.")
				.define("enableGoldConvertFee", true);
		atmConfirmThreshold = builder
				.comment("Withdrawals above this amount require confirmation in the ATM GUI.")
				.defineInRange("atmConfirmThreshold", 10000.0, 0.0, 999999999.0);
		baltopSize = builder
				.comment("Number of players shown in /baltop.")
				.defineInRange("baltopSize", 10, 1, 50);
		maxTransactionHistory = builder
				.comment("Maximum transaction history entries stored per player.")
				.defineInRange("maxTransactionHistory", 15, 5, 50);
		builder.pop();
	}
}
