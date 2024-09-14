/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.items;

import fr.fifoube.main.EconomyIncModTab;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemsRegistery {
	
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, ModEconomyInc.MOD_ID);

    public static final RegistryObject<Item> WRENCH = register("item_wrench", () -> new ItemRemover(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CREDITCARD = register("item_creditcard", () -> new ItemCreditCard(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WIRELESS = register("item_wireless", () -> new ItemWireless(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ONEB = register("item_oneb", () -> new ItemOneB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> FIVEB = register("item_fiveb", () -> new ItemFiveB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> TENB = register("item_tenb", () -> new ItemTenB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> TWENTYB = register("item_twentyb", () -> new ItemTwentyB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> FIFTYB = register("item_fiftyb", () -> new ItemFiftyB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> HUNDREDB = register("item_hundredb", () -> new ItemHundreedB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> TWOHUNDREDB = register("item_twohundredb", () -> new ItemTwoHundreedB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> FIVEHUNDREDB = register("item_fivehundredb", () -> new ItemFiveHundreedB(new Item.Properties().stacksTo(64)));
    
    public static final RegistryObject<Item> PACKETONEB = register("item_packetone", () -> new ItemPacketOneB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> PACKETFIVEB = register("item_packetfive", () -> new ItemPacketFiveB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> PACKETTENB = register("item_packetten", () -> new ItemPacketTenB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> PACKETTWENTYB = register("item_packettwenty", () -> new ItemPacketTwentyB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> PACKETFIFTYB = register("item_packetfifty", () -> new ItemPacketFiftyB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> PACKETHUNDREDB = register("item_packethundred", () -> new ItemPacketHundreedB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> PACKETTWOHUNDREDB = register("item_packettwohundred", () -> new ItemPacketTwoHundreedB(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> PACKETFIVEHUNDREDB = register("item_packetfivehundred", () -> new ItemPacketFiveHundreedB(new Item.Properties().stacksTo(64)));
    
    public static final RegistryObject<Item> GEAR = register("item_gear", () -> new ItemGear(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> GEARMECHANISM = register("item_gearmechanism", () -> new ItemGearMechanism(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> GOLDNUGGETSUB = register("item_goldnuggetsubs", () -> new ItemGoldNuggetSubs(new Item.Properties().stacksTo(1)));


    private static RegistryObject<Item> register(String name, Supplier<Item> item)
    {
        return REGISTER.register(name, item);
    }
    
    public static List<Item> availableBills()
    {
    	List<Item> list = new ArrayList<Item>();
    	list.add(ONEB.get());
    	list.add(FIVEB.get());
    	list.add(TENB.get());
    	list.add(TWENTYB.get());
    	list.add(FIFTYB.get());
    	list.add(HUNDREDB.get());
    	list.add(TWOHUNDREDB.get());
    	list.add(FIVEHUNDREDB.get());
    	return list;
    }
}
