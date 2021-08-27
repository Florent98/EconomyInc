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

package fr.fifoube.gui.container;

import fr.fifoube.blocks.tileentity.TileEntityBlockBuyer;
import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import fr.fifoube.gui.container.type.ContainerTypeRegistery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBuyer extends Container {

	private TileEntityBlockBuyer tile;
	public ContainerBuyer(int windowId, PlayerInventory playerInv, PacketBuffer extraData) {
		this(windowId, playerInv, extraData.readBlockPos());
	}
	
	public ContainerBuyer(int windowId, PlayerInventory playerInv, BlockPos pos)
	{
		super(ContainerTypeRegistery.BUYER_TYPE, windowId);
		TileEntity entity = playerInv.player.world.getTileEntity(pos);
		if(entity instanceof TileEntityBlockBuyer)
		{
			TileEntityBlockBuyer te = (TileEntityBlockBuyer)entity;
			this.bindBuyerInventory(te);
			this.bindPlayerInventory(playerInv);
		}

	}
	
	private void bindBuyerInventory(TileEntityBlockBuyer te)
	{
		IItemHandler inventory = te.getInventoryHandler();
	      for(int j = 0; j < 3; ++j) {
	          for(int k = 0; k < 9; ++k) {
	             this.addSlot(new SlotItemHandler(inventory, k + j * 9, 8 + k * 18, 17 + j * 18));
	          }
	      }
	}

	private void bindPlayerInventory(PlayerInventory inventoryPlayer)
	{
		int i;
		for(i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 48 + i * 18 + 37));
			}
		}

		for(i = 0; i < 9; i++)
		{
			this.addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 106 + 37));
		}
	}

	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) 
	{
		  ItemStack stack = ItemStack.EMPTY;
	        Slot slot = inventorySlots.get(index);

	        if (slot != null && slot.getHasStack()) {
	            ItemStack stackInSlot = slot.getStack();
	            stack = stackInSlot.copy();

	            int containerSlots = inventorySlots.size() - playerIn.inventory.mainInventory.size();

	            if (index < containerSlots) {
	                if (!this.mergeItemStack(stackInSlot, containerSlots, inventorySlots.size(), true)) {
	                    return ItemStack.EMPTY;
	                }
	            } else if (!this.mergeItemStack(stackInSlot, 0, containerSlots, false)) {
	                return ItemStack.EMPTY;
	            }

	            if (stackInSlot.getCount() == 0) {
	                slot.putStack(ItemStack.EMPTY);
	            } else {
	                slot.onSlotChanged();
	            }

	            slot.onTake(playerIn, stackInSlot);

	        }
	        return stack;
	}


	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}

	public TileEntityBlockBuyer getTile() {
		return this.tile;
	}
}
