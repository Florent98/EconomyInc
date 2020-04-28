/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import java.util.function.Supplier;

import fr.fifoube.blocks.BlockSeller;
import fr.fifoube.blocks.BlocksRegistry;
import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import fr.fifoube.main.capabilities.CapabilityMoney;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketCardChangeSeller {

	
	private double cost;
	private BlockPos pos;

	
	public PacketCardChangeSeller() 
	{
		
	}
	
	public PacketCardChangeSeller(double cost, BlockPos pos)
	{
		this.cost = cost;
		this.pos = pos;
	}	
	
	public static PacketCardChangeSeller decode(PacketBuffer buf) 
	{
		double cost = buf.readDouble();
		BlockPos pos = buf.readBlockPos();
		return new PacketCardChangeSeller(cost, pos);
	}


	public static void encode(PacketCardChangeSeller packet, PacketBuffer buf) 
	{
		buf.writeDouble(packet.cost);
		buf.writeBlockPos(packet.pos);
	}
	
	public static void handle(PacketCardChangeSeller packet, Supplier<NetworkEvent.Context> ctx)
	{

		ctx.get().enqueueWork(() -> {
			
			PlayerEntity player = ctx.get().getSender(); // GET PLAYER
			World world = player.world;
			player.getCapability(CapabilityMoney.MONEY_CAPABILITY, null)
				.ifPresent(data -> {
					data.setMoney(data.getMoney() - packet.cost);
				});
			BlockState state = world.getBlockState(packet.pos);
			if(state.getBlock() instanceof BlockSeller)
			{
				BlockSeller seller = (BlockSeller) state.getBlock();
				seller.scheduleTick(state, world, packet.pos);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
