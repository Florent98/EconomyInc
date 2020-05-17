/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import java.util.function.Supplier;

import fr.fifoube.blocks.BlockSeller;
import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import fr.fifoube.main.capabilities.CapabilityMoney;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketSellerFundsTotal {

	private double funds;
	private double cost;
	private double fundstotal;
	private int x;
	private int y;
	private int z;
	private int amount;
	private boolean recovery;
	
	public PacketSellerFundsTotal() 
	{
		
	}
	
	public PacketSellerFundsTotal(double funds, double cost, int xS, int yS, int zS, int amountS, boolean recoveryS)
	{
		this.funds= funds;
		this.cost = cost;
		this.x = xS;
		this.y = yS;
		this.z = zS;
		this.amount = amountS;
		this.recovery = recoveryS;
		
		this.fundstotal = funds + cost;

	}
	
	public static PacketSellerFundsTotal decode(PacketBuffer buf) 
	{
		double funds = buf.readDouble();
		double cost = buf.readDouble();
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		int amount = buf.readInt();
		boolean recovery = buf.readBoolean();
		return new PacketSellerFundsTotal(funds, cost, x, y, z, amount, recovery);

	}
	
	public static void encode(PacketSellerFundsTotal packet, PacketBuffer buf) 
	{
		buf.writeDouble(packet.funds);
		buf.writeDouble(packet.cost);
		buf.writeInt(packet.x);
		buf.writeInt(packet.y);
		buf.writeInt(packet.z);
		buf.writeInt(packet.amount);
		buf.writeBoolean(packet.recovery);
	}
	
	public static void handle(PacketSellerFundsTotal packet, Supplier<NetworkEvent.Context> ctx)
	{
			ctx.get().enqueueWork(() -> {
				
				ServerPlayerEntity player = ctx.get().getSender(); // GET PLAYER
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
								if(admin == false) // NOT UNLIMITED STACK
								{
									boolean flag = player.inventory.addItemStackToInventory(new ItemStack(te.getStackInSlot(0).getItem(), 1));
									if(flag)
									{
										te.getStackInSlot(0).split(1);
										te.setFundsTotal(packet.fundstotal); // SERVER SET THE FUNDS TOTAL FROM WHAT WE SENT
										te.markDirty();
										//
										player.getCapability(CapabilityMoney.MONEY_CAPABILITY, null)
										.ifPresent(data -> {
											data.setMoney(data.getMoney() - packet.cost);
										});
										BlockState state = worldIn.getBlockState(pos);
										if(state.getBlock() instanceof BlockSeller)
										{
											BlockSeller seller = (BlockSeller) state.getBlock();
											seller.scheduleTick(state, worldIn, pos);
										}
									}
									else
									{
										player.sendMessage(new StringTextComponent(I18n.format("title.noInventoryPlace")));
									}
								}
								else if(admin == true) // UNLIMITED STACK
								{
									boolean flag = player.inventory.addItemStackToInventory(new ItemStack(te.getStackInSlot(0).getItem(), 1));
									if(flag)
									{
										te.setFundsTotal(packet.fundstotal); // SERVER SET THE FUNDS TOTAL FROM WHAT WE SENT
										te.markDirty();	
										//
										player.getCapability(CapabilityMoney.MONEY_CAPABILITY, null)
										.ifPresent(data -> {
											data.setMoney(data.getMoney() - packet.cost);
										});
										BlockState state = worldIn.getBlockState(pos);
										if(state.getBlock() instanceof BlockSeller)
										{
											BlockSeller seller = (BlockSeller) state.getBlock();
											seller.scheduleTick(state, worldIn, pos);
										}
									}
									else
									{
										player.sendMessage(new StringTextComponent(I18n.format("title.noInventoryPlace")));
									}
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
