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
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketBuyerCreation {

	private double cost;
	private BlockPos pos;
	private ItemStack stack;

	public PacketBuyerCreation() {}
	
	public PacketBuyerCreation(double cost, BlockPos pos, ItemStack stack)
	{
		this.cost = cost;
		this.pos = pos;
		this.stack = stack;
	}
	
	public static PacketBuyerCreation decode(FriendlyByteBuf buf)
	{
		double cost = buf.readDouble();
		BlockPos pos = buf.readBlockPos();
		ItemStack stack = buf.readItem();
		return new PacketBuyerCreation(cost, pos, stack);
	}


	public static void encode(PacketBuyerCreation packet, FriendlyByteBuf buf)
	{
		buf.writeDouble(packet.cost);
		buf.writeBlockPos(packet.pos);
		buf.writeItem(packet.stack);
	}
	
    
	public static void handle(PacketBuyerCreation packet, Supplier<NetworkEvent.Context> ctx)
	{

		ctx.get().enqueueWork(() -> {
			
				Player player = ctx.get().getSender(); // GET PLAYER
	  			Level world = player.level;
				BlockEntityBuyer te = (BlockEntityBuyer)world.getBlockEntity(packet.pos); //WE TAKE THE POSITION OF THE TILE ENTITY TO ADD INFO
				if(te != null) // CHECK IF PLAYER HAS NOT DESTROYED TILE ENTITY IN THE SHORT TIME OF SENDING PACKET
				{
					if(packet.cost != 0 && packet.stack != ItemStack.EMPTY)
					{
						te.setCreated(true);
						te.setCost(packet.cost);
						te.setItemStackToBuy(packet.stack);
						te.setChanged();
					}
				}
		});
		ctx.get().setPacketHandled(true);
	}
}
