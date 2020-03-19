package fr.fifoube.main;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class ConfigFile {

 
	//SERVER AND CONFIG
    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;
    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    public static final ClientConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }
    
    //GLOBAL VARIABLES
    public static boolean canPreviewItemInBlock = true; //CLIENT
    public static int multiplierGoldNuggetWeight = 2; //SERVER & CLIENT
    public static boolean  canAccessCardWithoutWT = true; //SERVER & CLIENT
    public static boolean  doesBankGenerateInVillages = true; //SERVER & CLIENT
    public static boolean goldNuggetRecipe = true; //SERVER & CLIENT
    
    //PROPER FILE
	public static class ServerConfig {
		
		public IntValue multiplierGoldNuggetWeight; //SERVER & CLIENT
		public BooleanValue canAccessCardWithoutWT; //SERVER & CLIENT
		public BooleanValue doesBankGenerateInVillages; //SERVER & CLIENT
		public BooleanValue goldNuggetRecipe; //SERVER & CLIENT
	 
		/*public final String dbName; //SERVER
		public final String urlDB; //SERVER
		public final String userDB; //SERVER
		public final String passwordDB; //SERVER
		public final BooleanValue connectDB; //SERVER*/
	 
		ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.push("general");
			multiplierGoldNuggetWeight = builder
                    .comment("It will multiply the weight of the nugget with this number to create a funds to add to the credit card.")
                    .translation("text.economyinc.config.nuggetweight")
                    .defineInRange("multiplierGoldNuggetWeight", 2, 1, 9999);
			canAccessCardWithoutWT = builder
					.comment("Allow player that have the wireless technology to access their account without an ATM nearby.")
                    .translation("text.economyinc.config.access")
					.define("canAccessCardWithoutWT", true);
			doesBankGenerateInVillages = builder
					.comment("Allow or not the bank to generate in villages, by default it generates turn it to false to disable its generation.")
                    .translation("text.economyinc.config.bankvillage")
					.define("doesBankGenerateInVillages", true);
			goldNuggetRecipe = builder
					.comment("Allow the mod to replace the furnace recipe for the gold ore and give instead the EconomyInc's nugget.")
                    .translation("text.economyinc.config.nuggetrecipe")
					.define("goldNuggetRecipe", true);
            builder.pop();
			
		}
	}
	
	 public static class ClientConfig {
		 
		public BooleanValue canPreviewItemInBlock; //CLIENT
		public  IntValue multiplierGoldNuggetWeight; //SERVER & CLIENT
		public  BooleanValue canAccessCardWithoutWT; //SERVER & CLIENT
		public  BooleanValue doesBankGenerateInVillages; //SERVER & CLIENT
		public  BooleanValue goldNuggetRecipe; //SERVER & CLIENT
		
		ClientConfig(ForgeConfigSpec.Builder builder) {
            builder.push("display");
			multiplierGoldNuggetWeight = builder
                    .comment("It will multiply the weight of the nugget with this number to create a funds to add to the credit card.")
                    .translation("text.economyinc.config.nuggetweight")
                    .defineInRange("multiplierGoldNuggetWeight", 2, 1, 9999);
			canAccessCardWithoutWT = builder
					.comment("Allow player that have the wireless technology to access their account without an ATM nearby.")
                    .translation("text.economyinc.config.access")
					.define("canAccessCardWithoutWT", true);
			doesBankGenerateInVillages = builder
					.comment("Allow or not the bank to generate in villages, by default it generates turn it to false to disable its generation.")
                    .translation("text.economyinc.config.bankvillage")
					.define("doesBankGenerateInVillages", true);
			goldNuggetRecipe = builder
					.comment("Allow the mod to add a recipe from Gold Ingot to gold nugget (EIC)")
                    .translation("text.economyinc.config.nuggetrecipe")
					.define("goldNuggetRecipe", true);
			canPreviewItemInBlock = builder
					.comment("Allow you to disable the item preview in block seller when you hover it.")
                    .translation("text.economyinc.config.preview")
					.define("canPreviewItemInBlock", true);
            builder.pop();
		}

	 }
	 
	 //REFRESH
	 public static void refreshClient()
	 {
		canPreviewItemInBlock = CLIENT.canPreviewItemInBlock.get();
		multiplierGoldNuggetWeight = CLIENT.multiplierGoldNuggetWeight.get();
		canAccessCardWithoutWT = CLIENT.canAccessCardWithoutWT.get();
		doesBankGenerateInVillages = CLIENT.doesBankGenerateInVillages.get();
		goldNuggetRecipe = CLIENT.goldNuggetRecipe.get();
	 }
	 
	 public static void refreshServer()
	 {
		 multiplierGoldNuggetWeight = SERVER.multiplierGoldNuggetWeight.get();
		 canAccessCardWithoutWT = SERVER.canAccessCardWithoutWT.get();
		 doesBankGenerateInVillages = SERVER.doesBankGenerateInVillages.get();
		 goldNuggetRecipe = SERVER.goldNuggetRecipe.get();
	 }
	 
	 //ON CONFIG CHANGE EVENT TO REFRESH
	 @SubscribeEvent
	 public static void onConfigChange(final ModConfig.ModConfigEvent event)
	 {
		 if (event.getConfig().getSpec() == CLIENT_SPEC) 
		 {
			 refreshClient();
		 }
		 else if(event.getConfig().getSpec() == SERVER_SPEC)
		 {
			 refreshServer();
		 }
	 }

	 

}
