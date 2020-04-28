/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui.container;

import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import fr.fifoube.gui.container.type.ContainerTypeRegistery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSeller extends Container {


	private TileEntityBlockSeller tile;
	public ContainerSeller(int windowId, PlayerInventory playerInv, PacketBuffer extraData) {
		this(windowId, playerInv, extraData.readBlockPos());
	}
	
	public ContainerSeller(int windowId, PlayerInventory playerInv, BlockPos pos)
	{
		super(ContainerTypeRegistery.SELLER_TYPE, windowId);
		TileEntity entity = playerInv.player.world.getTileEntity(pos);
		if(entity instanceof TileEntityBlockSeller)
		{
			TileEntityBlockSeller te = (TileEntityBlockSeller)entity;
			this.tile = te;
			IItemHandler inventory = te.getHandler();
			this.addSlot(new SlotItemHandler(inventory, 0, 80, 35));
			this.bindPlayerInventory(playerInv);
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

	public TileEntityBlockSeller getTile() {
		return this.tile;
	}

}
