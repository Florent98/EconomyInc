/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import java.util.function.Supplier;

import fr.fifoube.items.ItemsRegistery;
import fr.fifoube.main.capabilities.CapabilityMoney;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketCardChange {


	private double funds;

	
	public PacketCardChange() 
	{
		
	}
	
	public PacketCardChange(double funds)
	{
		this.funds = funds;
	}	
	
	public static PacketCardChange decode(PacketBuffer buf) 
	{
		double funds = buf.readDouble();
		return new PacketCardChange(funds);
	}


	public static void encode(PacketCardChange packet, PacketBuffer buf) 
	{
		buf.writeDouble(packet.funds);
	}
	
	public static void handle(PacketCardChange packet, Supplier<NetworkEvent.Context> ctx)
	{

		ctx.get().enqueueWork(() -> {
			PlayerEntity player = ctx.get().getSender(); // GET PLAYER
			player.getCapability(CapabilityMoney.MONEY_CAPABILITY, null)
				.ifPresent(data -> {
					
					double funds = packet.funds;
					if(funds == 1)
					{
						if(data.getMoney() >= 1)
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money - 1);
							player.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_ONEB));
						}
						
					}
					else if(funds == 5)
					{
						if(data.getMoney() >= 5)
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money - 5);
							player.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_FIVEB));
						}
						
					}
					else if(funds == 10)
					{
						if(data.getMoney() >= 10)
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money - 10);
							player.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_TENB));

						}
					}
					else if(funds == 20)
					{
						if(data.getMoney() >= 20)
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money - 20);
							player.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_TWENTYB));

						}
					}
					else if(funds == 50)
					{
						if(data.getMoney() >= 50)
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money - 50);
							player.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_FIFTYB));
						}
					}
					else if(funds == 100)
					{
						if(data.getMoney() >= 100)
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money - 100);
							player.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_HUNDREEDB));

						}
					}
					else if(funds == 200)
					{
						if(data.getMoney() >= 200)
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money - 200);
							player.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_TWOHUNDREEDB));

						}
					}
					else if(funds == 500)
					{
						if(data.getMoney() >= 500)
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money - 500);
							player.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_FIVEHUNDREEDB));
						}
					}
					else if(funds == -1)
					{
						
						if(player.inventory.hasItemStack(new ItemStack(ItemsRegistery.ITEM_ONEB)))
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money + 1);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.ITEM_ONEB), 1);
						}
					}
					else if(funds == -5)
					{
						if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_FIVEB))))
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money + 5);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.ITEM_FIVEB), 1);

						}
					}
					else if(funds == -10)
					{
						if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_TENB))))
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money + 10);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.ITEM_TENB), 1);

						}
					}
					else if(funds == -20)
					{
						if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_TWENTYB))))
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money + 20);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.ITEM_TWENTYB), 1);
						}
					}
					else if(funds == -50)
					{
						if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_FIFTYB))))
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money + 50);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.ITEM_FIFTYB), 1);

						}
					}
					else if(funds == -100)
					{
						if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_HUNDREEDB))))
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money + 100);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.ITEM_HUNDREEDB), 1);

						}
					}
					else if(funds == -200)
					{
						if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_TWOHUNDREEDB))))
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money + 200);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.ITEM_TWOHUNDREEDB), 1);

						}
					}
					else if(funds == -500)
					{
						if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_FIVEHUNDREEDB))))
						{
							double previous_money = data.getMoney();
							data.setMoney(previous_money + 500);
							clearMatchingItems(player, new ItemStack(ItemsRegistery.ITEM_FIVEHUNDREEDB), 1);

						}
					}
				});
		});
		ctx.get().setPacketHandled(true);
	}
	
	
	public static void clearMatchingItems(PlayerEntity player, ItemStack stack, int count) {

	      for(int j = 0; j < player.inventory.getSizeInventory(); ++j) 
	      {
	         ItemStack itemstack = player.inventory.getStackInSlot(j);
	         if(itemstack.isItemEqual(stack))
	         {
	        	 itemstack.setCount(itemstack.getCount() - count);
	         }
	      }
	}
	      
}
