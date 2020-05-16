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

import java.util.function.Supplier;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketVaultSettings {

	private BlockPos pos;
	private String playerToAdd;
	private boolean remove;
	private int id;
	
	public PacketVaultSettings() 
	{
		
	}
	
	public PacketVaultSettings(BlockPos pos, String playerToAddOrRemove, boolean remove, int id)
	{
		this.pos = pos;
		this.playerToAdd = playerToAddOrRemove;
		this.remove = remove;
		this.id = id;
	}	
	
	public static PacketVaultSettings decode(PacketBuffer buf) 
	{
		BlockPos pos = buf.readBlockPos();
		String playerToAdd = buf.readString(32767);
		boolean remove = buf.readBoolean();
		int id = buf.readInt();
		return new PacketVaultSettings(pos, playerToAdd, remove, id);
	}


	public static void encode(PacketVaultSettings packet, PacketBuffer buf) 
	{
		buf.writeBlockPos(packet.pos);
		buf.writeString(packet.playerToAdd);
		buf.writeBoolean(packet.remove);
		buf.writeInt(packet.id);
	}
	
	public static void handle(PacketVaultSettings packet, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			PlayerEntity player = ctx.get().getSender(); // GET PLAYER
			TileEntity tile = player.world.getTileEntity(packet.pos);
			if(tile instanceof TileEntityBlockVault2by2)
			{
				TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2)tile;
				if(!packet.remove)
				{
					te.addAllowedPlayers(packet.playerToAdd);
					te.addToMax();
					te.markDirty();			
				}
				else
				{
					te.getAllowedPlayers().remove(packet.id);
					te.removeToMax();
					te.markDirty();
				}
			}
		});
	}
}
