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

package fr.fifoube.packets;

import fr.fifoube.blocks.blockentity.BlockEntityBuyer;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.config.ConfigFile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class PacketBuyerChange {

	private BlockPos pos;
	private boolean isOneSell;

	
	public PacketBuyerChange() {}
	
	public PacketBuyerChange(BlockPos pos, boolean isOneSell)
	{
		this.pos = pos;
		this.isOneSell = isOneSell;
	}
	
	public static PacketBuyerChange decode(FriendlyByteBuf buf)
	{
		BlockPos pos = buf.readBlockPos();
		boolean isOneSell = buf.readBoolean();
		return new PacketBuyerChange(pos, isOneSell);
	}


	public static void encode(PacketBuyerChange packet, FriendlyByteBuf buf)
	{
		buf.writeBlockPos(packet.pos);
		buf.writeBoolean(packet.isOneSell);
	}
	
    
	public static void handle(PacketBuyerChange packet, Supplier<NetworkEvent.Context> ctx)
	{

		ctx.get().enqueueWork(() -> {
			
				Player player = ctx.get().getSender(); // GET PLAYER
	  			Level world = player.level;
				BlockEntityBuyer te = (BlockEntityBuyer)world.getBlockEntity(packet.pos); //WE TAKE THE POSITION OF THE TILE ENTITY TO ADD INFO
				if(te != null) // CHECK IF PLAYER HAS NOT DESTROYED TILE ENTITY IN THE SHORT TIME OF SENDING PACKET
				{
					int slotFree = te.getFreeSlot();

					int quantityFound = 0;
					ArrayList<Integer> indexes = new ArrayList<>();
					for(int i = 0; i < player.getInventory().getContainerSize(); i++)
					{
						if(player.getInventory().getItem(i).getItem().equals(te.getItemStackToBuy().getItem()))
						{
							quantityFound += player.getInventory().getItem(i).getCount();
							indexes.add(i);
						}
					}

					if(quantityFound != 0)
					{
						int multiplier = packet.isOneSell ? 1 : quantityFound;
							player.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> {
								te.setTime(ConfigFile.cooldownSeller);
								if (packet.isOneSell) {
									if(te.getCost() <= te.getAccountMoney()) {
										ItemStack stackToInsert = player.getInventory().getItem(indexes.get(0));
										ItemStack stackResult = ItemHandlerHelper.insertItem(te.getInventoryHandler(), stackToInsert, true);
										if (stackResult == ItemStack.EMPTY) {
											data.addMoney(te.getCost() * 1);
											te.setAccount(te.getAccountMoney() - (te.getCost() * 1));
											ItemHandlerHelper.insertItem(te.getInventoryHandler(), stackToInsert.split(1), false);
										}
										else {
											player.sendSystemMessage(Component.translatable("title.buyerNoSpaceLeft"));
										}
									}
									else {
										player.sendSystemMessage(Component.translatable("title.buyerNoMoney"));
									}

								} else {
									boolean wasAbleToInsertAny = false;
									boolean costTooHigh = false;
									for (int i = 0; i < indexes.size(); i++) {

										Item item = player.getInventory().getItem(indexes.get(i)).getItem();
										int count = player.getInventory().getItem(indexes.get(i)).getCount();
										ItemStack stackToInsert = new ItemStack(item, count);
										int stackSize = stackToInsert.getCount();
										ItemStack stackResult = ItemHandlerHelper.insertItem(te.getInventoryHandler(), stackToInsert, true);
										if(stackResult.getCount() != stackSize)
										{
											int insertSize = stackSize - stackResult.getCount();
											double cost = te.getCost() * insertSize;
											wasAbleToInsertAny = true;
											if(cost <= te.getAccountMoney())
											{
												ItemHandlerHelper.insertItem(te.getInventoryHandler(), stackToInsert, false);
												data.addMoney(cost);
												te.setAccount(te.getAccountMoney() - cost);
												player.getInventory().getItem(indexes.get(i)).split(insertSize);
											}
											else {
												costTooHigh = true;
											}
										}
									}
									if(!wasAbleToInsertAny)
									{
										player.sendSystemMessage(Component.translatable("title.buyerNoSpaceLeft"));
									}
									if(costTooHigh)
									{
										player.sendSystemMessage(Component.translatable("title.buyerNoMoney"));
									}

								}
								te.setChanged();
							});
						}
						else
						{
							player.sendSystemMessage(Component.translatable("title.buyerNothingSell"));
						}
					}
					else
					{
						player.sendSystemMessage(Component.translatable("title.generalErrorBE"));
					}
		});
		ctx.get().setPacketHandled(true);
	}
}
