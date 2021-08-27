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

import java.util.function.Supplier;

import fr.fifoube.blocks.tileentity.TileEntityBlockChanger;
import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import fr.fifoube.blocks.tileentity.TileEntityBlockVault;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

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
	
	public static PacketRefillSeller decode(PacketBuffer buf) 
	{
		BlockPos posSeller = buf.readBlockPos();
		BlockPos posVault = buf.readBlockPos();
		ItemStack stackToMatch = buf.readItemStack();
		return new PacketRefillSeller(posSeller, posVault, stackToMatch);
	}


	public static void encode(PacketRefillSeller packet, PacketBuffer buf) 
	{
		buf.writeBlockPos(packet.posSeller);
		buf.writeBlockPos(packet.posVault);
		buf.writeItemStack(packet.stackToMatch);
	}
	
	public static void handle(PacketRefillSeller packet, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			
			PlayerEntity player = ctx.get().getSender();
			World world = player.world;
			TileEntity tileS = world.getTileEntity(packet.posSeller);
			TileEntity tileV = world.getTileEntity(packet.posVault);
			
			if(tileS instanceof TileEntityBlockSeller && tileV instanceof TileEntityBlockVault)
			{
				TileEntityBlockSeller teS = (TileEntityBlockSeller) tileS;
				TileEntityBlockVault teV = (TileEntityBlockVault) tileV;
				
				int maxToTake = teS.getStackInSlot(0).getMaxStackSize() - teS.getStackInSlot(0).getCount();
				boolean air = packet.stackToMatch.getItem().equals(Items.AIR);
				boolean found = false;
				
				for (int i = 0; i < teV.getHandler().getSlots(); i++) { //CHECKING FULL INVENTORY OF THE VAULT UNDER
					
					if(maxToTake != 0)
					{
						if(!air)
						{
							if(packet.stackToMatch.isItemEqual(teV.getHandler().getStackInSlot(i))) //IF STACKTOMATCH IS EQUAL TO ANY STACK IN THE INVENTORY
							{
								if(teV.getHandler().getStackInSlot(i).getCount() >= maxToTake) //IF THE STACK IN THE INVENTORY IS GREATER OR EQUAL TO THE MAX TO TAKE TO FILL THE SELLER
								{	
									teS.getHandler().getStackInSlot(0).setCount(teS.getHandler().getStackInSlot(0).getCount() + maxToTake); //WE PUT THE VALUE OF THE SELLER TO (MAX TO TAKE + ACTUAL COUNT) = MAXSTACKSIZE OF OBJECT
									teV.getHandler().getStackInSlot(i).setCount(teS.getHandler().getStackInSlot(i).getCount() - maxToTake); //WE TAKE THE MAX TO TAKE FROM THE INVENTORY FOUND SLOT
									maxToTake = 0; //WE SAY THAT MAX TO TAKE IS NOW 0 SINCE IT'S FILLED
									teV.markDirty(); //WE FORCE UPDATE VAULT
									teS.markDirty(); //WE FORCE UPDATE SELLER
								}
								else if(teV.getHandler().getStackInSlot(i).getCount() < maxToTake) // IF THE STACK IN THE INVENTORY IS LESS THAN THE MAX TO TAKE TO FILL THE SELLER
								{
									if(teS.getHandler().getStackInSlot(0).getCount() != teS.getHandler().getStackInSlot(0).getMaxStackSize()) { // WE FIRST CHECK TO SEE IF THE COUNT IS NOT THE SAME AS THE MAX STACK SIZE TO PREVENT MAXSTACKSIZE OVERFLOW (ex: enderpearl are maxstacksized 16)
										
										teS.getHandler().getStackInSlot(0).setCount(teS.getHandler().getStackInSlot(0).getCount() + teV.getHandler().getStackInSlot(i).getCount()); // WE SET THE NEW COUNT TO BE THE PREVIOUS COUNT + THE COUNT OF THE STACK FOUND
										teV.getHandler().getStackInSlot(i).setCount(0); // WE SET TO 0 THE COUNT OF THE STACK TAKEN
										maxToTake = maxToTake - teV.getHandler().getStackInSlot(i).getCount(); // WE UPDATE THE NEW MAX TO TAKE TO CONTINUE LOOPING TROUGH INVENTORY
										teV.markDirty(); //WE FORCE UPDATE VAULT
										teS.markDirty(); //WE FORCE UPDATE SELLER
										
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
									teS.markDirty();			
									teV.getHandler().setStackInSlot(i, ItemStack.EMPTY);
									teV.markDirty();
								}
							}
						}
					}

				}
				

			
			}

		});
	}
	
	
	

}
