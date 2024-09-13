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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MenuBuyer extends AbstractContainerMenu {

	private BlockEntityBuyer tile;
	
	public MenuBuyer(int windowId, Inventory inv, BlockEntityBuyer be)
	{
		super(MenuTypeRegistery.BUYER_TYPE.get(), windowId);
		Player player = inv.player;
		this.tile = be;
		this.bindBuyerInventory(be);
		this.bindPlayerInventory(player.getInventory());
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
	
	private void bindBuyerInventory(BlockEntityBuyer te)
	{
		IItemHandler inventory = te.getInventoryHandler();
	      for(int j = 0; j < 3; ++j) {
	          for(int k = 0; k < 9; ++k) {
	             this.addSlot(new SlotItemHandler(inventory, k + j * 9, 8 + k * 18, 17 + j * 18));
	          }
	      }
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


	public BlockEntityBuyer getTile() {
		return this.tile;
	}

	@Override
	public boolean stillValid(Player player) {

		return true;
	}
}