
package fr.fifoube.gui.container;

import fr.fifoube.blocks.blockentity.BlockEntitySeller;
import fr.fifoube.gui.container.type.MenuTypeRegistery;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MenuSellerBuy extends AbstractContainerMenu {

	private BlockEntitySeller tile;
	
	public MenuSellerBuy(int windowId, Inventory inv, BlockEntitySeller entity)
	{
		super(MenuTypeRegistery.SELLERBUY_TYPE.get(), windowId);
		Player player = inv.player;
		this.tile = entity;
	}

	private void trackCooldown() {

		addDataSlot(new DataSlot() {

			@Override
			public void set(int value) {
				tile.setTime(value);
			}

			@Override
			public int get() {
				return getCooldown();
			}
		});
	}

	public int getCooldown() {
		return tile.getTime();
	}


	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		
		ItemStack itemstack = ItemStack.EMPTY;
	      Slot slot = this.slots.get(index);
	      if (slot != null && slot.hasItem()) {
	         ItemStack itemstack1 = slot.getItem();
	         itemstack = itemstack1.copy();
	         if (index < 1) {
	            if (!this.moveItemStackTo(itemstack1, 1, this.slots.size(), true)) {
	               return ItemStack.EMPTY;
	            }
	         } else if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
	            return ItemStack.EMPTY;
	         }

	         if (itemstack1.isEmpty()) {
	            slot.set(ItemStack.EMPTY);
	         } else {
	            slot.setChanged();
	         }
	      }

	      return itemstack;
	}



	public BlockEntitySeller getTile() {
		return this.tile;
	}

	@Override
	public boolean stillValid(Player player) {

		return true;
	}
	
}
