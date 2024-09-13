/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import fr.fifoube.blocks.blockentity.BlockEntitySeller;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSellerCreated {

	private boolean created; 
	private double cost;
	private boolean admin;
	private boolean autorefill;
	private BlockPos pos;

	public PacketSellerCreated() {}

	public PacketSellerCreated(double costS, BlockPos pos, boolean adminS, boolean refill)
	{
		this.cost = costS;
		this.admin = adminS;
		this.autorefill = refill;
		this.pos = pos;
	}
	
	public static PacketSellerCreated decode(FriendlyByteBuf buf) 
	{
		double cost = buf.readDouble();
		boolean admin = buf.readBoolean();
		boolean refill = buf.readBoolean();
		BlockPos pos = buf.readBlockPos();
		return new PacketSellerCreated(cost, pos, admin, refill);
	}


	public static void encode(PacketSellerCreated packet, FriendlyByteBuf buf) 
	{
		buf.writeDouble(packet.cost);
		buf.writeBoolean(packet.admin);
		buf.writeBoolean(packet.autorefill);
		buf.writeBlockPos(packet.pos);
	}
	
    
	public static void handle(PacketSellerCreated packet, Supplier<NetworkEvent.Context> ctx)
	{

		ctx.get().enqueueWork(() -> {

				Player player = ctx.get().getSender(); // GET PLAYER
	  			Level world = player.level;
				BlockPos pos = packet.pos;
				BlockEntitySeller te = (BlockEntitySeller)world.getBlockEntity(pos); //WE TAKE THE POSITION OF THE TILE ENTITY TO ADD INFO
				if(te != null) // CHECK IF PLAYER HAS NOT DESTROYED TILE ENTITY IN THE SHORT TIME OF SENDING PACKET
				{
					if(packet.cost > 0 ) {
						if (te.getStackInSlot(0).getItem() != Items.AIR) // IF SLOT 0 IS NOT BLOCKS.AIR, WE PASS
						{
							String name = te.getStackInSlot(0).getDisplayName().getString(); // GET ITEM NAME IN TILE THANKS TO STACK IN SLOT
							te.setItem(name); //  SET ITEM NAME
							te.setCreated(true);
							te.setCost(packet.cost);
							te.setAdmin(packet.admin);
							te.setAutoRefill(packet.autorefill);
							te.setAdmin(packet.admin);
							te.setChanged();

						} else // PROVIDE PLAYER TO SELL AIR
						{
							player.sendMessage(new TranslatableComponent("title.sellAir"), player.getUUID());
						}
					}
					else {
						player.sendMessage(new TranslatableComponent("title.noValidCost"), player.getUUID());
					}
				}
		});
		ctx.get().setPacketHandled(true);
	}
}