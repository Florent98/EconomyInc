/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import fr.fifoube.blocks.BlockSeller;
import fr.fifoube.blocks.blockentity.BlockEntitySeller;
import fr.fifoube.items.ItemCreditCard;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.config.ConfigFile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSellerFundsTotal {

	private BlockPos pos;
	private boolean recovery;
	
	public PacketSellerFundsTotal() 
	{
		
	}
	
	public PacketSellerFundsTotal(BlockPos pos, boolean recoveryS)
	{
		this.pos = pos;
		this.recovery = recoveryS;

	}
	
	public static void encode(PacketSellerFundsTotal packet, FriendlyByteBuf buf) 
	{
		buf.writeBlockPos(packet.pos);
		buf.writeBoolean(packet.recovery);
	}
	
	public static PacketSellerFundsTotal decode(FriendlyByteBuf buf) 
	{
		BlockPos pos = buf.readBlockPos();
		Boolean recovery = buf.readBoolean();
		return new PacketSellerFundsTotal(pos, recovery);

	}
	
	public static void handle(PacketSellerFundsTotal packet, Supplier<NetworkEvent.Context> ctx)
	{
			ctx.get().enqueueWork(() -> {
				
				ServerPlayer player = ctx.get().getSender(); // GET PLAYER
				Level worldIn = player.level; // GET WORLD
				BlockPos pos = packet.pos;
				BlockEntity tileentity = worldIn.getBlockEntity(pos); // GET THE TILE ENTITY IN WORLD THANKS TO COORDINATES
				BlockSeller seller = (BlockSeller) worldIn.getBlockState(pos).getBlock();
				if(!worldIn.isClientSide)
				if(tileentity instanceof BlockEntitySeller)
				{
					BlockEntitySeller te = (BlockEntitySeller)tileentity;
					if(te != null)
					{
						ItemStack stackInSlot = new ItemStack(te.getInventory().getStackInSlot(0).getItem(), 1);
						player.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> {
						if(!packet.recovery)
						{
							for(int i = 0; i < player.getInventory().getContainerSize(); i++) //CHECKING INVENTORY TO SEE IF CREDIT CARD IS THERE
							{
								if(player.getInventory().getItem(i).getItem() instanceof ItemCreditCard) //IF AN ITEM CREDIT CARD IS FOUND WE ACCEPT THE ACTION PERFORMED
								{
									ItemStack creditCard = player.getInventory().getItem(i); //SET THE SLOT FOUND TO BE THE CREDIT CARD
									if(creditCard.hasTag()) //IF IT HAS TAG 
									if(player.getStringUUID().equals(creditCard.getTag().getString("OwnerUUID"))) //AND IT'S THE SAME OWNER 
									{
										if(creditCard.getTag().getBoolean("Linked")) //IF IT'S A LINKED CREDIT CARD THEN WE ACCEPT 
										{
											if(data.getMoney() >= te.getCost()) //IF THE PLAYER HAS ENOUGH MONEY
											{
												if(te.getAmount() >= 1)
												{
													boolean admin = te.getAdmin();
													boolean flag = player.getInventory().add(stackInSlot);
													if(flag)
													{
														ModEconomyInc.LOGGER_MONEY.info(player.getDisplayName().getString() + " has bought " + te.getItem() + " from : " + te.getOwnerName() + "[UUID : " + te.getOwner() + "]. Balance was at " + data.getMoney() + ", balance is now " + (data.getMoney() + te.getCost()) + "." + "[UUID: " + player.getUUID() + "]");
														data.setMoney(data.getMoney() - te.getCost());
														te.increaseFundsTotal();
														if(!admin)
														{
															te.getInventory().getStackInSlot(0).split(1);
														}
														te.setTime(ConfigFile.cooldownSeller);
														seller.signalPower(worldIn, pos);
														te.setChanged();
													}
												}
											}
										}
									} 
								}
							}
						}
						else if(packet.recovery)
						{
							if(te.getFundsTotal() > 0)
							{
								if(te.getOwner().equals(player.getUUID()))
								{
									data.addMoney(te.getFundsTotal());
									te.setFundsTotal(0);
									te.setChanged();
								}
							}
						}
						});
					}
				}
			});
			ctx.get().setPacketHandled(true);
	}

}
