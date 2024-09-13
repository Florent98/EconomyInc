/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import fr.fifoube.blocks.blockentity.BlockEntityChanger;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

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
	
	public static PacketChangerUpdate decode(FriendlyByteBuf buf) 
	{
		BlockPos pos = buf.readBlockPos();
		return new PacketChangerUpdate(pos);
	}


	public static void encode(PacketChangerUpdate packet, FriendlyByteBuf buf) 
	{
		buf.writeBlockPos(packet.pos);
		
	}
	
	public static void handle(PacketChangerUpdate packet, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			Player player = ctx.get().getSender();
			Level world = player.level;
			BlockEntity tile = world.getBlockEntity(packet.pos);
			if(tile instanceof BlockEntityChanger)
			{
				BlockEntityChanger te = (BlockEntityChanger)tile;
				te.setNumbUse(0);
			}
		ctx.get().setPacketHandled(true);
		});
	}
}
