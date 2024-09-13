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

import fr.fifoube.blocks.blockentity.BlockEntityBuyer;
import fr.fifoube.gui.container.type.MenuTypeRegistery;
import fr.fifoube.items.IValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MenuBuyerCreation extends AbstractContainerMenu {

	private BlockEntityBuyer tile;
	private ItemStackHandler inventorySlot = new ItemStackHandler(1);

	public MenuBuyerCreation(int windowId, Inventory inv, BlockEntityBuyer be) {
		super(MenuTypeRegistery.BUYER_CREA_TYPE.get(), windowId);
		Player player = inv.player;
		this.tile = be;
		this.bindBuyerInventory(be);
		this.bindPlayerInventory(player.getInventory());
	}

	@Override
	public void broadcastChanges() {
		super.broadcastChanges();
		ItemStack stack = this.tile.getMoneyHandler().getStackInSlot(0);
		if(stack != ItemStack.EMPTY && stack.getItem() instanceof IValue)
		{
			IValue value = (IValue)stack.getItem();
			this.tile.addToAccount(value.getValue() * stack.getCount());
			this.tile.addToStackList(stack.copy());
			stack.split(stack.getCount());
			this.tile.setChanged();
		}
	}

	private void bindPlayerInventory(Inventory inventoryPlayer) {
		int i;
		for (i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 48 + i * 18 + 37));
			}
		}

		for (i = 0; i < 9; i++) {
			this.addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 106 + 37));
		}
	}

	private void bindBuyerInventory(BlockEntityBuyer te) {
		this.addSlot(new SlotItemHandler(inventorySlot, 0, 8, 20) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			@Override
			public boolean mayPlace(@NotNull ItemStack stack) {
					return true;
			}

		});

		this.addSlot(new SlotItemHandler(tile.getMoneyHandler(), 0, 8, 40) {


			@Override
			public boolean mayPlace(@NotNull ItemStack stack) {
				return stack.getItem() instanceof IValue;
			}
		});
	}

	@Override
	public void clicked(int slotId, int dragType, ClickType clickType, Player player) {

		Slot slot = this.slots.get(0);

		if (slot != null && slotId == 0) { // Assuming slot 0 is the special slot
			ItemStack carriedStack = this.getCarried(); // Get the item stack currently being carried by the player

			if(carriedStack.getItem() instanceof IValue) {}
			else {
				if (!carriedStack.isEmpty()) {
					// Create a copy of the carried stack with a count of 1
					ItemStack singleItemStack = carriedStack.copy();
					singleItemStack.setCount(1);

					// Set this stack in the special slot
					slot.set(singleItemStack);
				} else if (slot.hasItem()) {
					// If the player clicks an empty carried stack on the special slot, clear the slot
					slot.set(ItemStack.EMPTY);
				}
			}
			// Do not call the super method to avoid normal click behavior
		} else {
			// For all other slots, use the default behavior
			super.clicked(slotId, dragType, clickType, player);
		}
	}


	public BlockEntityBuyer getBlockEntity() {
		return this.tile;
	}

	@Override
	public boolean stillValid(Player player) {

		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {

		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack originalStack = slot.getItem();
			ItemStack newStack = originalStack.copy();

			// Special handling for the special slot (assuming slot 0 is the special slot)
			if (index == 0) {
				// If the player is quick-moving from the special slot, just clear the slot
				slot.set(ItemStack.EMPTY);
				return ItemStack.EMPTY;
			}
			else {
				// If the player is quick-moving into the special slot
				Slot specialSlot = this.slots.get(0);
				if(originalStack.getItem() instanceof IValue) {

					if(!this.slots.get(1).hasItem()) {
						ItemStack singleItemStack = originalStack.copy();
						this.slots.get(1).set(singleItemStack);

						originalStack.shrink(originalStack.getCount());
					}
				}
				else {
					if(specialSlot != null && !specialSlot.hasItem()) {

						ItemStack singleItemStack = originalStack.copy();
						singleItemStack.setCount(1);
						specialSlot.set(singleItemStack);

						// If the stack is empty, clear the slot
						if (originalStack.isEmpty()) {
							slot.set(ItemStack.EMPTY);
						} else {
							slot.set(originalStack);
						}

						// Return the modified stack
						return originalStack;
					}
				}
			}

			// Normal behavior for quick-moving between slots
			if (index < this.slots.size()) {
				if (!this.moveItemStackTo(originalStack, this.slots.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(originalStack, 2, this.slots.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (originalStack.getCount() == newStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, originalStack);
		}

		return ItemStack.EMPTY;
	}


}