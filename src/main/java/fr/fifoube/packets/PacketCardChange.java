/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import fr.fifoube.items.ItemsRegistery;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketCardChange {
	
	private double funds;
	
	public PacketCardChange() 
	{
		
	}
	
	public PacketCardChange(double funds)
	{
		this.funds = funds;
	}	
	
	public static PacketCardChange decode(FriendlyByteBuf buf) 
	{
		double funds = buf.readDouble();
		return new PacketCardChange(funds);
	}


	public static void encode(PacketCardChange packet, FriendlyByteBuf buf) 
	{
		buf.writeDouble(packet.funds);
	}
	
	public static void handle(PacketCardChange packet, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			Player player = ctx.get().getSender(); // GET PLAYER
			player.getCapability(CapabilityMoney.MONEY_CAPABILITY, null)
				.ifPresent(data -> {
					 
					double funds = packet.funds;
					if(funds == 1)
					{
						if(data.getMoney() >= 1)
						{
							boolean flag = player.addItem(new ItemStack(ItemsRegistery.ONEB.get()));
							if(flag)
							{
								double previous_money = data.getMoney();
								ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has withdrawn " + 1 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() - 1) + "." + "[UUID: " + player.getUUID() + "]");
								data.setMoney(previous_money - 1);								
							}
							else
							{
								player.sendMessage(new TranslatableComponent("title.noInventoryPlace"), player.getUUID());
							}
							
						}
						
					}
					else if(funds == 5)
					{
						if(data.getMoney() >= 5)
						{
							boolean flag = player.addItem(new ItemStack(ItemsRegistery.FIVEB.get()));
							if(flag)
							{
								double previous_money = data.getMoney();
								ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has withdrawn " + 5 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() - 5) + "." + "[UUID: " + player.getUUID() + "]");
								data.setMoney(previous_money - 5);								
							}
							else
							{
								player.sendMessage(new TranslatableComponent("title.noInventoryPlace"), player.getUUID());
							}
						}
						
					}
					else if(funds == 10)
					{
						if(data.getMoney() >= 10)
						{
							boolean flag = player.addItem(new ItemStack(ItemsRegistery.TENB.get()));
							if(flag)
							{
								double previous_money = data.getMoney();
								ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has withdrawn " + 10 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() - 10) + "." + "[UUID: " + player.getUUID() + "]");
								data.setMoney(previous_money - 10);								
							}
							else
							{
								player.sendMessage(new TranslatableComponent("title.noInventoryPlace"), player.getUUID());
							}

						}
					}
					else if(funds == 20)
					{
						if(data.getMoney() >= 20)
						{
							boolean flag = player.addItem(new ItemStack(ItemsRegistery.TWENTYB.get()));
							if(flag)
							{
								double previous_money = data.getMoney();
								ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has withdrawn " + 20 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() - 20) + "." + "[UUID: " + player.getUUID() + "]");
								data.setMoney(previous_money - 20);								
							}
							else
							{
								player.sendMessage(new TranslatableComponent("title.noInventoryPlace"), player.getUUID());
							}

						}
					}
					else if(funds == 50)
					{
						if(data.getMoney() >= 50)
						{
							boolean flag = player.addItem(new ItemStack(ItemsRegistery.FIFTYB.get()));
							if(flag)
							{
								double previous_money = data.getMoney();
								ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has withdrawn " + 50 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() - 50) + "." + "[UUID: " + player.getUUID() + "]");
								data.setMoney(previous_money - 50);								
							}
							else
							{
								player.sendMessage(new TranslatableComponent("title.noInventoryPlace"), player.getUUID());
							}
						}
					}
					else if(funds == 100)
					{
						if(data.getMoney() >= 100)
						{
							boolean flag = player.addItem(new ItemStack(ItemsRegistery.HUNDREDB.get()));
							if(flag)
							{
								double previous_money = data.getMoney();
								ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has withdrawn " + 100 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() - 100) + "." + "[UUID: " + player.getUUID() + "]");
								data.setMoney(previous_money - 100);								
							}
							else
							{
								player.sendMessage(new TranslatableComponent("title.noInventoryPlace"), player.getUUID());
							}

						}
					}
					else if(funds == 200)
					{
						if(data.getMoney() >= 200)
						{
							boolean flag = player.addItem(new ItemStack(ItemsRegistery.TWOHUNDREDB.get()));
							if(flag)
							{
								double previous_money = data.getMoney();
								ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has withdrawn " + 200 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() - 200) + "." + "[UUID: " + player.getUUID() + "]");
								data.setMoney(previous_money - 200);								
							}
							else
							{
								player.sendMessage(new TranslatableComponent("title.noInventoryPlace"), player.getUUID());
							}

						}
					}
					else if(funds == 500)
					{
						if(data.getMoney() >= 500)
						{
							boolean flag = player.addItem(new ItemStack(ItemsRegistery.FIVEHUNDREDB.get()));
							if(flag)
							{
								double previous_money = data.getMoney();
								ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has withdrawn " + 500 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() - 500) + "." + "[UUID: " + player.getUUID() + "]");
								data.setMoney(previous_money - 500);								
							}
							else
							{
								player.sendMessage(new TranslatableComponent("title.noInventoryPlace"), player.getUUID());
							}
						}
					}
					else if(funds == -1)
					{
						
						if(player.getInventory().contains(new ItemStack(ItemsRegistery.ONEB.get())))
						{
							double previous_money = data.getMoney();
							ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has added " + 1 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() + 1) + "." + "[UUID: " + player.getUUID() + "]");
							data.setMoney(previous_money + 1);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.ONEB.get()), 1);
						}
					}
					else if(funds == -5)
					{
						if(player.getInventory().contains(new ItemStack((ItemsRegistery.FIVEB.get()))))
						{
							double previous_money = data.getMoney();
							ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has added " + 5 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() + 5) + "." + "[UUID: " + player.getUUID() + "]");
							data.setMoney(previous_money + 5);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.FIVEB.get()), 1);

						}
					}
					else if(funds == -10)
					{
						if(player.getInventory().contains(new ItemStack((ItemsRegistery.TENB.get()))))
						{
							double previous_money = data.getMoney();
							ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has added " + 10 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() + 10) + "." + "[UUID: " + player.getUUID() + "]");
							data.setMoney(previous_money + 10);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.TENB.get()), 1);

						}
					}
					else if(funds == -20)
					{
						if(player.getInventory().contains(new ItemStack((ItemsRegistery.TWENTYB.get()))))
						{
							double previous_money = data.getMoney();
							ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has added " + 20 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() + 20) + "." + "[UUID: " + player.getUUID() + "]");
							data.setMoney(previous_money + 20);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.TWENTYB.get()), 1);
						}
					}
					else if(funds == -50)
					{
						if(player.getInventory().contains(new ItemStack((ItemsRegistery.FIFTYB.get()))))
						{
							double previous_money = data.getMoney();
							ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has added " + 50 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() + 50) + "." + "[UUID: " + player.getUUID() + "]");
							data.setMoney(previous_money + 50);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.FIFTYB.get()), 1);

						}
					}
					else if(funds == -100)
					{
						if(player.getInventory().contains(new ItemStack((ItemsRegistery.HUNDREDB.get()))))
						{
							double previous_money = data.getMoney();
							ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has added " + 100 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() + 100) + "." + "[UUID: " + player.getUUID() + "]");
							data.setMoney(previous_money + 100);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.HUNDREDB.get()), 1);

						}
					}
					else if(funds == -200)
					{
						if(player.getInventory().contains(new ItemStack((ItemsRegistery.TWOHUNDREDB.get()))))
						{
							double previous_money = data.getMoney();
							ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has added " + 200 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() + 200) + "." + "[UUID: " + player.getUUID() + "]");
							data.setMoney(previous_money + 200);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.TWOHUNDREDB.get()), 1);

						}
					}
					else if(funds == -500)
					{
						if(player.getInventory().contains(new ItemStack((ItemsRegistery.FIVEHUNDREDB.get()))))
						{
							double previous_money = data.getMoney();
							ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has added " + 500 + "." + " Balance was " + previous_money + ", balance is now " + (data.getMoney() + 500) + "." + "[UUID: " + player.getUUID() + "]");
							data.setMoney(previous_money + 500);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.FIVEHUNDREDB.get()), 1);

						}
					}
				});
		});
		ctx.get().setPacketHandled(true);
	}
	
	
	public static void clearMatchingItems(Player player, ItemStack stack, int count) {

		boolean found = false;
	      for(int j = 0; j < player.getInventory().getContainerSize(); ++j) 
	      {
	         ItemStack itemstack = player.getInventory().getItem(j);
	         if(itemstack.getItem().equals(stack.getItem()) && !found)
	         {
	        	 found = true;
	        	 itemstack.setCount(itemstack.getCount() - count);
	         }
	      }
	}
	      
}
