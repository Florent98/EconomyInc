package fr.fifoube.packets;

import java.util.function.Supplier;

import fr.fifoube.main.capabilities.CapabilityMoney;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketCardChangeSeller {

	
	private double cost;

	
	public PacketCardChangeSeller() 
	{
		
	}
	
	public PacketCardChangeSeller(double cost)
	{
		this.cost = cost;
	}	
	
	public static PacketCardChangeSeller decode(PacketBuffer buf) 
	{
		double cost = buf.readDouble();
		return new PacketCardChangeSeller(cost);
	}


	public static void encode(PacketCardChangeSeller packet, PacketBuffer buf) 
	{
		buf.writeDouble(packet.cost);
	}
	
	public static void handle(PacketCardChangeSeller packet, Supplier<NetworkEvent.Context> ctx)
	{

		ctx.get().enqueueWork(() -> {
			
			EntityPlayer player = ctx.get().getSender(); // GET PLAYER
			player.getCapability(CapabilityMoney.MONEY_CAPABILITY, null)
				.ifPresent(data -> {
					data.setMoney(data.getMoney() - packet.cost);
				});
		});
		ctx.get().setPacketHandled(true);
	}
}
