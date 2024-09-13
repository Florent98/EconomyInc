/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui.container;

import fr.fifoube.blocks.blockentity.BlockEntityVault;
import fr.fifoube.gui.container.type.MenuTypeRegistery;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MenuVault extends AbstractContainerMenu
{
	public int X;
	public int Y;
	public int Z;
	private BlockEntityVault tile;

	public MenuVault(int windowId, Inventory playerInv, Player player, BlockEntityVault te)
	{
		super(MenuTypeRegistery.VAULT_TYPE.get(), windowId);
		this.tile = te;
		IItemHandler inventory = te.getHandler();
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlot(new SlotItemHandler(inventory, j + i * 9, 8 + j * 18, 17 + i * 18));
			}
		}
		this.bindPlayerInventory(playerInv);	
	}
	
	private void bindPlayerInventory(Inventory inventoryPlayer)
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
	public ItemStack quickMoveStack(Player player, int index) {
		
		 ItemStack stack = ItemStack.EMPTY;
	        Slot slot = this.slots.get(index);

	        if (slot != null && slot.hasItem()) {
	            ItemStack stackInSlot = slot.getItem();
	            stack = stackInSlot.copy();

	            int containerSlots = this.slots.size() - player.getInventory().getContainerSize();

	            if (index < containerSlots) {
	                if (!this.moveItemStackTo(stackInSlot, containerSlots, this.slots.size(), true)) {
	                    return ItemStack.EMPTY;
	                }
	            } else if (!this.moveItemStackTo(stackInSlot, 0, containerSlots, false)) {
	                return ItemStack.EMPTY;
	            }

	            if (stackInSlot.getCount() == 0) {
	                slot.set(ItemStack.EMPTY);
	            } else {
	                slot.setChanged();
	            }

	            slot.onTake(player, stackInSlot);

	        }
	        return stack;
	}

	public BlockEntityVault getTile() {
		return tile;
	}


	@Override
	public boolean stillValid(Player player) {
		
		return true;
	}

}
