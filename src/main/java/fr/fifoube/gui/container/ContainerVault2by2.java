package fr.fifoube.gui.container;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
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

public class ContainerVault2by2 extends Container
{
	protected TileEntityBlockVault2by2 tile;
	
	public ContainerVault2by2(int windowId, PlayerInventory playerInv, PacketBuffer extraData) {
		this(windowId, playerInv, extraData.readBlockPos());
	}
	
	public ContainerVault2by2(int windowId, PlayerInventory playerInv, BlockPos pos)
	{
		super(ContainerTypeRegistery.VAULT2BY2_TYPE, windowId);
		TileEntity entity = playerInv.player.world.getTileEntity(pos);
		if(entity instanceof TileEntityBlockVault2by2)
		{
			TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2)entity;
			this.tile = te;
			IItemHandler inventory = te.getHandler();
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 9; j++)
				{
					this.addSlot(new SlotItemHandler(inventory, j + i * 9, 8 + j * 18, 17 + i * 18));
				}
			}
		}
		this.bindPlayerInventory(playerInv);
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

	public TileEntityBlockVault2by2 getTile() {
		return this.tile;
	}
	

}