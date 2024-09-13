/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui.container;

import fr.fifoube.blocks.blockentity.BlockEntityVault2by2;
import fr.fifoube.gui.container.type.MenuTypeRegistery;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MenuVault2by2 extends AbstractContainerMenu
{
	protected BlockEntityVault2by2 tile;

	public MenuVault2by2(int windowId, Inventory playerInv, Player player, BlockEntityVault2by2 te)
	{
		super(MenuTypeRegistery.VAULT2BY2_TYPE.get(), windowId);
		this.tile = te;
		IItemHandler inventory = te.getInventory();
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlot(new SlotItemHandler(inventory, j + i * 9, 8 + j * 18, i * 18 - 10));
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
				this.addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, j * 18 + 8, i * 18 + 112));
			}
		}

		for(i = 0; i < 9; i++)
		{
			this.addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 170));
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

	public BlockEntityVault2by2 getTile() {
		return this.tile;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

}