/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import fr.fifoube.blocks.blockentity.BlockEntityVault;
import fr.fifoube.blocks.blockentity.BlockEntityVault2by2;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketListNBT {

	private String names;
	private int x;
	private int y;
	private int z;
	private boolean isBlock2x2;
	private String addrem;
	private int index;

	
	public PacketListNBT() 
	{
		
	}
	
	public PacketListNBT(String names, int x, int y, int z, boolean isBlock2x2, String addrem, int index)
	{
		this.names = names;
		this.x = x;
		this.y = y;
		this.z = z;
		this.isBlock2x2 = isBlock2x2;
		this.addrem = addrem;
		this.index = index;
	}
	
	public static PacketListNBT decode(FriendlyByteBuf buf) 
	{
		String names = buf.readUtf(32767);
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		boolean isBlock2x2 = buf.readBoolean();
		String addrem = buf.readUtf(32767);
		int index = buf.readInt();
		return new PacketListNBT(names, x, y, z, isBlock2x2, addrem, index);
	}


	public static void encode(PacketListNBT packet, FriendlyByteBuf buf) 
	{
		buf.writeUtf(packet.names);
		buf.writeInt(packet.x);
		buf.writeInt(packet.y);
		buf.writeInt(packet.z);
		buf.writeBoolean(packet.isBlock2x2);
		buf.writeUtf(packet.addrem);
		buf.writeInt(packet.index);
	}
	
    
	public static void handle(PacketListNBT packet, Supplier<NetworkEvent.Context> ctx)
	{

		ctx.get().enqueueWork(() -> {
			
			Player player = ctx.get().getSender(); // GET PLAYER
			Level worldIn = player.level; // GET WORLD
				if(packet.addrem.equals("add"))
				{
					if(packet.isBlock2x2)
					{
						BlockEntityVault2by2 te = (BlockEntityVault2by2)worldIn.getBlockEntity(new BlockPos(packet.x, packet.y, packet.z));
						if(te != null)
						{
							te.addAllowedPlayers(packet.names);
							te.addToMax();
							te.setChanged();
							
						}
					}
					else
					{
						BlockEntityVault te = (BlockEntityVault)worldIn.getBlockEntity(new BlockPos(packet.x, packet.y, packet.z));
						if(te != null)
						{
							te.setChanged();
						}
					}
				}
				else if(packet.addrem.equals("remove"))
				{
					if(packet.isBlock2x2)
					{
						BlockEntityVault2by2 te = (BlockEntityVault2by2)worldIn.getBlockEntity(new BlockPos(packet.x, packet.y, packet.z));
						if(te != null)
						{
							te.getAllowedPlayers().remove(packet.index);
							te.removeToMax();
							te.setChanged();
						}
					}
					else
					{
						BlockEntityVault te = (BlockEntityVault)worldIn.getBlockEntity(new BlockPos(packet.x, packet.y, packet.z));
						if(te != null)
						{
							te.setChanged();
						}
					}	
				}		
		});
		ctx.get().setPacketHandled(true);
	}
}
