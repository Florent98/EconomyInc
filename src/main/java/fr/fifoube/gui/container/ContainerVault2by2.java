package fr.fifoube.gui.container;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerVault2by2 extends Container
{
	protected TileEntityBlockVault2by2 te;
	
	public ContainerVault2by2(PlayerInventory inventoryPlayer, TileEntityBlockVault2by2 tile)
	{
		this.te = tile;
		IItemHandler inventory = tile.getHandler();
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlot(new SlotItemHandler(inventory, j + i * 9, 8 + j * 18, -10 + i * 18));
			}
		}
		this.bindPlayerInventory(inventoryPlayer);		
	}
	
	private void bindPlayerInventory(PlayerInventory inventoryPlayer)
	{
		int i;
		for(i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, j * 18 + 8, i * 18 + 112));
			}
		}

		for(i = 0; i < 9; i++)
		{
			this.addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 170));
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) 
	{
		return true;
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
	

}