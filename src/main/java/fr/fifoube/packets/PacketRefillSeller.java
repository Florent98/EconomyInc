/** 
 *  Copyright 2020, Turrioni Florent, All rights reserved.
 *  
 * 	This program is copyrighted for all the files and code 
 * 	included in this program. No reuse, modification or 
 * 	reselling is authorized without any legal document 
 *  approved by the owner*.
 * 
 * 	*Owner : Turrioni Florent resident in Belgium and 
 *  contactable at florent_turrioni@hotmail.com
 *  
 * */

package fr.fifoube.packets;

import fr.fifoube.blocks.blockentity.BlockEntitySeller;
import fr.fifoube.blocks.blockentity.BlockEntityVault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class PacketRefillSeller {

	private BlockPos posSeller;
	private BlockPos posVault;
	private ItemStack stackToMatch;

	public PacketRefillSeller() 
	{
		
	}
	
	public PacketRefillSeller(BlockPos posSeller, BlockPos posVault, ItemStack stack)
	{
		this.posSeller = posSeller;
		this.posVault = posVault;
		this.stackToMatch = stack;
	}	
	
	public static PacketRefillSeller decode(FriendlyByteBuf buf)
	{
		BlockPos posSeller = buf.readBlockPos();
		BlockPos posVault = buf.readBlockPos();
		ItemStack stackToMatch = buf.readItem();
		return new PacketRefillSeller(posSeller, posVault, stackToMatch);
	}


	public static void encode(PacketRefillSeller packet, FriendlyByteBuf buf)
	{
		buf.writeBlockPos(packet.posSeller);
		buf.writeBlockPos(packet.posVault);
		buf.writeItem(packet.stackToMatch);
	}
	
	public static void handle(PacketRefillSeller packet, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			
			Player player = ctx.get().getSender();
			Level world = player.level;
			BlockEntity tileS = world.getBlockEntity(packet.posSeller);
			BlockEntity tileV = world.getBlockEntity(packet.posVault);
			
			if(tileS instanceof BlockEntitySeller && tileV instanceof BlockEntityVault)
			{
				BlockEntitySeller teS = (BlockEntitySeller) tileS;
				BlockEntityVault teV = (BlockEntityVault) tileV;
				
				int maxToTake = teS.getStackInSlot(0).getMaxStackSize() - teS.getStackInSlot(0).getCount();
				boolean air = packet.stackToMatch.getItem().equals(Items.AIR);
				boolean found = false;
				
				for (int i = 0; i < teV.getHandler().getSlots(); i++) { //CHECKING FULL INVENTORY OF THE VAULT UNDER
					
					if(maxToTake != 0)
					{
						if(!air)
						{
							if(packet.stackToMatch.equals(teV.getHandler().getStackInSlot(i))) //IF STACKTOMATCH IS EQUAL TO ANY STACK IN THE INVENTORY
							{
								if(teV.getHandler().getStackInSlot(i).getCount() >= maxToTake) //IF THE STACK IN THE INVENTORY IS GREATER OR EQUAL TO THE MAX TO TAKE TO FILL THE SELLER
								{	
									teS.getHandler().getStackInSlot(0).setCount(teS.getHandler().getStackInSlot(0).getCount() + maxToTake); //WE PUT THE VALUE OF THE SELLER TO (MAX TO TAKE + ACTUAL COUNT) = MAXSTACKSIZE OF OBJECT
									teV.getHandler().getStackInSlot(i).setCount(teS.getHandler().getStackInSlot(i).getCount() - maxToTake); //WE TAKE THE MAX TO TAKE FROM THE INVENTORY FOUND SLOT
									maxToTake = 0; //WE SAY THAT MAX TO TAKE IS NOW 0 SINCE IT'S FILLED
									teV.setChanged(); //WE FORCE UPDATE VAULT
									teS.setChanged(); //WE FORCE UPDATE SELLER
								}
								else if(teV.getHandler().getStackInSlot(i).getCount() < maxToTake) // IF THE STACK IN THE INVENTORY IS LESS THAN THE MAX TO TAKE TO FILL THE SELLER
								{
									if(teS.getHandler().getStackInSlot(0).getCount() != teS.getHandler().getStackInSlot(0).getMaxStackSize()) { // WE FIRST CHECK TO SEE IF THE COUNT IS NOT THE SAME AS THE MAX STACK SIZE TO PREVENT MAXSTACKSIZE OVERFLOW (ex: enderpearl are maxstacksized 16)
										
										teS.getHandler().getStackInSlot(0).setCount(teS.getHandler().getStackInSlot(0).getCount() + teV.getHandler().getStackInSlot(i).getCount()); // WE SET THE NEW COUNT TO BE THE PREVIOUS COUNT + THE COUNT OF THE STACK FOUND
										teV.getHandler().getStackInSlot(i).setCount(0); // WE SET TO 0 THE COUNT OF THE STACK TAKEN
										maxToTake = maxToTake - teV.getHandler().getStackInSlot(i).getCount(); // WE UPDATE THE NEW MAX TO TAKE TO CONTINUE LOOPING TROUGH INVENTORY
										teV.setChanged(); //WE FORCE UPDATE VAULT
										teS.setChanged(); //WE FORCE UPDATE SELLER
										
									}
								}
								
							}	
						}
						else
						{
							if(!teV.getHandler().getStackInSlot(i).isEmpty() && !found)
							{
								found = true;
								if(teS.getOwner().equals(teV.getOwner()))
								{
									teS.getHandler().setStackInSlot(0, teV.getHandler().getStackInSlot(i));
									teS.setChanged();
									teV.getHandler().setStackInSlot(i, ItemStack.EMPTY);
									teV.setChanged();
								}
							}
						}
					}

				}
				

			
			}

		});
	}
	
	
	

}
