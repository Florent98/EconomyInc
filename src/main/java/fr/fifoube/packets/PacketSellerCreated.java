package fr.fifoube.packets;

import java.util.function.Supplier;

import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketSellerCreated {

	private boolean created; 
	private double cost;
	private int x;
	private int y;
	private int z;
	private String name = "";
	private int amount = 0;
	private boolean admin;
	
	public PacketSellerCreated() {}
	
	public PacketSellerCreated(boolean createdS, double costS, String nameS, int amountS, int xS, int yS, int zS, boolean adminS)
	{
		this.created = createdS;
		this.cost = costS;
		this.name = nameS;
		this.amount = amountS;
		this.x = xS;
		this.y = yS;
		this.z = zS;
		this.admin = adminS;
	}
	
	public static PacketSellerCreated decode(PacketBuffer buf) 
	{
		boolean created = buf.readBoolean();
		double cost = buf.readDouble();
		String name = buf.readString(32767);
		int amount = buf.readInt();
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		boolean admin = buf.readBoolean();
		return new PacketSellerCreated(created, cost, name, amount, x, y, z, admin);
	}


	public static void encode(PacketSellerCreated packet, PacketBuffer buf) 
	{
		buf.writeBoolean(packet.created);
		buf.writeDouble(packet.cost);
		buf.writeString(packet.name);
		buf.writeInt(packet.amount);
		buf.writeInt(packet.x);
		buf.writeInt(packet.y);
		buf.writeInt(packet.z);
		buf.writeBoolean(packet.admin);

	}
	
    
	public static void handle(PacketSellerCreated packet, Supplier<NetworkEvent.Context> ctx)
	{

		ctx.get().enqueueWork(() -> {
			
				EntityPlayer player = ctx.get().getSender(); // GET PLAYER
	  			World world = player.world;  
				BlockPos pos = new BlockPos(packet.x, packet.y, packet.z);
				TileEntityBlockSeller te = (TileEntityBlockSeller)world.getTileEntity(pos); //WE TAKE THE POSITION OF THE TILE ENTITY TO ADD INFO
				if(te != null) // CHECK IF PLAYER HAS NOT DESTROYED TILE ENTITY IN THE SHORT TIME OF SENDING PACKET
				{
					te.setCreated(packet.created); // SERVER ADD CREATED TO TILE ENTITY
					te.setCost(packet.cost); // SERVER ADD COST TO TILE ENTITY
					te.setItem(packet.name); // SERVER ADD NAME TO TILE ENTITY
					te.setAmount(packet.amount); // SERVER ADD AMOUNT TO TILE ENTITY
					te.setAdmin(packet.admin); // SERVER ADD ADMIN TO TILE ENTITY
					te.markDirty(); //UPDATE THE TILE ENTITY
				}
		});
		ctx.get().setPacketHandled(true);
	}
}