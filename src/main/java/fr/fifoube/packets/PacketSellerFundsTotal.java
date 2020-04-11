package fr.fifoube.packets;

import java.util.function.Supplier;

import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import fr.fifoube.main.capabilities.CapabilityMoney;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketSellerFundsTotal {

	private double fundstotal;
	private int x;
	private int y;
	private int z;
	private int amount;
	private boolean recovery;
	
	public PacketSellerFundsTotal() 
	{
		
	}
	
	public PacketSellerFundsTotal(double fundstotalS, int xS, int yS, int zS, int amountS, boolean recoveryS)
	{
		this.fundstotal = fundstotalS;
		this.x = xS;
		this.y = yS;
		this.z = zS;
		this.amount = amountS;
		this.recovery = recoveryS;
		
	}
	
	public static PacketSellerFundsTotal decode(PacketBuffer buf) 
	{
		double fundstotal = buf.readDouble();
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		int amount = buf.readInt();
		boolean recovery = buf.readBoolean();
		return new PacketSellerFundsTotal(fundstotal, x, y, z, amount, recovery);

	}
	
	public static void encode(PacketSellerFundsTotal packet, PacketBuffer buf) 
	{
		buf.writeDouble(packet.fundstotal);
		buf.writeInt(packet.x);
		buf.writeInt(packet.y);
		buf.writeInt(packet.z);
		buf.writeInt(packet.amount);
		buf.writeBoolean(packet.recovery);


	}
	
	public static void handle(PacketSellerFundsTotal packet, Supplier<NetworkEvent.Context> ctx)
	{
			ctx.get().enqueueWork(() -> {
				
				PlayerEntity player = ctx.get().getSender(); // GET PLAYER
				World worldIn = player.world; // GET WORLD
				BlockPos pos = new BlockPos(packet.x, packet.y, packet.z); // NEW BLOCK POS FOR TILE ENTITY COORDINATES
				TileEntity tileentity = worldIn.getTileEntity(pos); // GET THE TILE ENTITY IN WORLD THANKS TO COORDINATES
				if(!worldIn.isRemote)
				if(tileentity instanceof TileEntityBlockSeller)
				{
					TileEntityBlockSeller te = (TileEntityBlockSeller)tileentity;
					if(te != null) // IF TILE ENTITY EXIST
					{
						if(packet.recovery == false)
						{
							if(!te.getStackInSlot(0).isEmpty()) // IF THE SLOT IS NOT EMPTY
							{
								boolean admin = te.getAdmin();
								//worldIn.getBlockState(pos);
								if(admin == false) // NOT UNLIMITED STACK
								{
									te.setFundsTotal(packet.fundstotal); // SERVER SET THE FUNDS TOTAL FROM WHAT WE SENT
									te.getStackInSlot(0);
									player.addItemStackToInventory(te.getStackInSlot(0).split(1)); // SERVER ADD STACK IN SLOT BY 1
									int newAmount = packet.amount - 1; // INT THE NEW AMOUNT OF STACK IN SLOT
									te.setAmount(newAmount); // SERVER SET THE NEW AMOUNT OF STACK IN SLOT
									te.markDirty();
								}
								else if(admin == true) // UNLIMITED STACK
								{
									te.setFundsTotal(packet.fundstotal); // SERVER SET THE FUNDS TOTAL FROM WHAT WE SENT
									te.setAmount(packet.amount); // SERVER SET THE NEW AMOUNT OF STACK IN SLOT
									ItemStack stackToGive = te.getStackInSlot(0).copy().split(1);
									player.addItemStackToInventory(stackToGive); // SERVER ADD STACK IN SLOT BY 1
									te.markDirty();	
								}
							}
						}
						else if(packet.recovery == true)
						{
							player.getCapability(CapabilityMoney.MONEY_CAPABILITY, null)
								.ifPresent(data -> { 					
										   data.setMoney(data.getMoney() + packet.fundstotal);	
										   te.setFundsTotal(0);
										   te.markDirty();		
								
								});
			
						}
					}
				}
			});
			ctx.get().setPacketHandled(true);
	}
}
