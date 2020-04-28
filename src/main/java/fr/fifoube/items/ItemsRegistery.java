/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import fr.fifoube.main.ModEconomyInc;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemsRegistery {
	
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_remover")
	public static final Item ITEM_REMOVER = null;
	
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_creditcard")
	public static final Item ITEM_CREDITCARD = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_wireless")
	public static final Item ITEM_WIRELESS = null;	
	
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_oneb")
	public static final Item ITEM_ONEB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_fiveb")
	public static final Item ITEM_FIVEB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_tenb")
	public static final Item ITEM_TENB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_twentyb")
	public static final Item ITEM_TWENTYB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_fiftybe")
	public static final Item ITEM_FIFTYB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_hundreedb")
	public static final Item ITEM_HUNDREEDB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_twohundreedb")
	public static final Item ITEM_TWOHUNDREEDB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_fivehundreedb")
	public static final Item ITEM_FIVEHUNDREEDB = null;
	
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_packetone")
	public static final Item ITEM_PACKETONEB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_packetfive")
	public static final Item ITEM_PACKETFIVEB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_packetten")
	public static final Item ITEM_PACKETTENB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_packettwenty")
	public static final Item ITEM_PACKETTWENTYB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_packetfifty")
	public static final Item ITEM_PACKETFIFTYB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_packethundreed")
	public static final Item ITEM_PACKETHUNDREEDB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_packettwohundreed")
	public static final Item ITEM_PACKETTWOHUNDREEDB = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_packetfivehundreed")
	public static final Item ITEM_PACKETFIVEHUNDREEDB = null;
	
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_gear")
	public static final Item ITEM_GEAR = null;
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_gearmechanism")
	public static final Item ITEM_GEARSMECHANISM = null;
	
	@ObjectHolder(ModEconomyInc.MOD_ID + ":item_goldnuggetsubs")
	public static final Item ITEM_GOLDNUGGET = null;

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		
		event.getRegistry().register(new ItemRemover(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(1)).setRegistryName("item_remover"));
		
		event.getRegistry().register(new ItemCreditCard(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(1)).setRegistryName("item_creditcard"));
		event.getRegistry().register(new ItemWireless(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(1)).setRegistryName("item_wireless"));	
		
		event.getRegistry().register(new ItemOneB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_oneb"));
		event.getRegistry().register(new ItemFiveB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_fiveb"));
		event.getRegistry().register(new ItemTenB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_tenb"));
		event.getRegistry().register(new ItemTwentyB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_twentyb"));
		event.getRegistry().register(new ItemFiftyB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_fiftybe"));
		event.getRegistry().register(new ItemHundreedB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_hundreedb"));
		event.getRegistry().register(new ItemTwoHundreedB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_twohundreedb"));
		event.getRegistry().register(new ItemFiveHundreedB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_fivehundreedb"));

		event.getRegistry().register(new ItemPacketOneB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_packetone"));
		event.getRegistry().register(new ItemPacketFiveB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_packetfive"));
		event.getRegistry().register(new ItemPacketTenB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_packetten"));
		event.getRegistry().register(new ItemPacketTwentyB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_packettwenty"));
		event.getRegistry().register(new ItemPacketFiftyB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_packetfifty"));
		event.getRegistry().register(new ItemPacketHundreedB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_packethundreed"));
		event.getRegistry().register(new ItemPacketTwoHundreedB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_packettwohundreed"));
		event.getRegistry().register(new ItemPacketFiveHundreedB(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_packetfivehundreed"));
		
		event.getRegistry().register(new ItemGear(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_gear"));
		event.getRegistry().register(new ItemGearMechanism(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(64)).setRegistryName("item_gearmechanism"));

		event.getRegistry().register(new ItemGoldNuggetSubs(new Item.Properties().group(ModEconomyInc.EIC).maxStackSize(1)).setRegistryName("item_goldnuggetsubs"));



	}
}
