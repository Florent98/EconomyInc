/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui.container;

import fr.fifoube.blocks.blockentity.BlockEntitySeller;
import fr.fifoube.gui.container.type.MenuTypeRegistery;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MenuSeller extends AbstractContainerMenu {

	private BlockEntitySeller tile;
	
	public MenuSeller(int windowId, Inventory inv, BlockEntitySeller entity)
	{
		super(MenuTypeRegistery.SELLER_TYPE.get(), windowId);
		Player player = inv.player;
		this.tile = entity;
		IItemHandler inventory = entity.getInventory();
		this.addSlot(new SlotItemHandler(inventory, 0, 80, 35));
		this.bindPlayerInventory(player.getInventory());
		trackCooldown();
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
	public boolean stillValid(Player p_38874_) {

		return true;
	}
}
