/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.main.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigFile {

	//CLIENT 
	public static boolean canPreviewItemInBlock;
	//SERVER
	public static int multiplierGoldNuggetWeight = 2; //SERVER
	public static boolean canAccessCardWithoutWT; //SERVER
	public static String plotBorderBlock; //SERVER
	public static int goldChangerDuration = 356; //SERVER
	public static int cooldownSeller = 20; //SERVER
	public static double rangeGoldNugget = 1.2; //SERVER
	public static boolean doMobsGiveMoney = false; //SERVER
	public static double mobsMoney = 0.25;

	public static boolean doSpecificMobsGiveMoney = false; //SERVER
	public static double zombieMoney = 0.25;
	public static double creeperMoney = 1.25;
	public static double skeletonMoney = 0.75;
	public static double spiderMoney = 0.25;
	public static double witchMoney = 2.0;

}
