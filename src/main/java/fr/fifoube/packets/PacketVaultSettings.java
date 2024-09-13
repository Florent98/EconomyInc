/*****************************************************/

package fr.fifoube.packets;

import fr.fifoube.blocks.blockentity.BlockEntityVault2by2;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

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
	
	public static PacketVaultSettings decode(FriendlyByteBuf buf) 
	{
		BlockPos pos = buf.readBlockPos();
		String playerToAdd = buf.readUtf(32767);
		boolean remove = buf.readBoolean();
		int id = buf.readInt();
		return new PacketVaultSettings(pos, playerToAdd, remove, id);
	}


	public static void encode(PacketVaultSettings packet, FriendlyByteBuf buf) 
	{
		buf.writeBlockPos(packet.pos);
		buf.writeUtf(packet.playerToAdd);
		buf.writeBoolean(packet.remove);
		buf.writeInt(packet.id);
	}
	
	public static void handle(PacketVaultSettings packet, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			Player player = ctx.get().getSender(); // GET PLAYER
			BlockEntity tile = player.level.getBlockEntity(packet.pos);
			if(tile instanceof BlockEntityVault2by2)
			{
				BlockEntityVault2by2 te = (BlockEntityVault2by2)tile;
				if(!packet.remove)
				{
					te.addAllowedPlayers(packet.playerToAdd);
					te.addToMax();
					te.setChanged();			
				}
				else
				{
					te.getAllowedPlayers().remove(packet.id);
					te.removeToMax();
					te.setChanged();
				}
			}
		});
	}
}
