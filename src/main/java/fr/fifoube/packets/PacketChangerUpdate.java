/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import fr.fifoube.blocks.tileentity.TileEntityBlockChanger;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketChangerUpdate {

	private BlockPos pos;

	public PacketChangerUpdate() 
	{
		
	}
	
	public PacketChangerUpdate(BlockPos pos)
	{
		this.pos = pos;
	}	
	
	public static PacketChangerUpdate decode(PacketBuffer buf) 
	{
		BlockPos pos = buf.readBlockPos();
		return new PacketChangerUpdate(pos);
	}


	public static void encode(PacketChangerUpdate packet, PacketBuffer buf) 
	{
		buf.writeBlockPos(packet.pos);
		
	}
	
	public static void handle(PacketChangerUpdate packet, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			PlayerEntity player = ctx.get().getSender();
			World world = player.world;
			TileEntity tile = world.getTileEntity(packet.pos);
			if(tile instanceof TileEntityBlockChanger)
			{
				TileEntityBlockChanger te = (TileEntityBlockChanger)tile;
				te.setNumbUse(0);
			}
		ctx.get().setPacketHandled(true);
		});
	}
}
