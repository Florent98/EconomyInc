/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui.container;

import fr.fifoube.blocks.blockentity.BlockEntityChanger;
import fr.fifoube.gui.container.type.MenuTypeRegistery;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MenuChanger extends AbstractContainerMenu {
	
	public BlockEntityChanger te;

	public MenuChanger(int windowId, Inventory playerInv, BlockEntityChanger tile)
	{
		super(MenuTypeRegistery.CHANGER_TYPE.get(), windowId);
		
		IItemHandler inventory = tile.getInventory();
		this.addSlot(new SlotItemHandler(inventory, 0, 56, 16));
		this.addSlot(new SlotItemHandler(inventory, 1, 56, 52));
		this.addSlot(new SlotItemHandler(inventory, 2, 116, 34));
		this.bindPlayerInventory(playerInv);
		this.te = tile;
		trackProgress();
	}
	

	private void bindPlayerInventory(Inventory inventoryPlayer)
	{
		int i;
		for(i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 48 + i * 18 + 35));
			}
		}

		for(i = 0; i < 9; i++)
		{
			this.addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 106 + 35));
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
	
	@Override
	public void removed(Player playerIn) {

		Level level = playerIn.level;
		if(!level.isClientSide)
		{
			BlockEntityChanger te = this.te;
			if(te.getNumbUse() > 0)
			{
				te.setNumbUse(0);
				te.setEntityPlayer((Player)null);
				te.setChanged();
			}
			
			ItemStack itemstack = this.getCarried();
	        if (!itemstack.isEmpty()) {
	            if (playerIn.isAlive() && !((ServerPlayer)playerIn).hasDisconnected()) {
	            	playerIn.getInventory().placeItemBackInInventory(itemstack);
	            } else {
	            	playerIn.drop(itemstack, false);
	            }

	            this.setCarried(ItemStack.EMPTY);
	         }
		}
	}
	
	private void trackProgress() {
		
		addDataSlot(new DataSlot() {
			
			@Override
			public void set(int value) {
				te.setTimePassed(value);
			}
			
			@Override
			public int get() {
				return getTimePassed();
			}
		});
		
		addDataSlot(new DataSlot() {
			
			@Override
			public void set(int value) {
				te.setProcessTime(value);
			}
			
			@Override
			public int get() {
				
				return te.getProcessTime();
			}
		});
		
		addDataSlot(new DataSlot() {
			
			@Override
			public void set(int value) {	
				if(value == 1)
				{
					te.setIsProcessing(true);
				}
				else
				{
					te.setIsProcessing(false);
				}
			}
			
			@Override
			public int get() {
				return te.isProcessing ? 1 : 0;
			}
		});
	}
	
	public int getTimePassed() {
		return te.getTimePassed();
	}
	
	public int getProcessTime() {
		return te.getProcessTime();
	}
	
	public int isProcessing() {
		return te.isProcessing ? 1 : 0;
	}

	
	public BlockEntityChanger getBlockEntity() {
		return this.te;
	}


	@Override
	public boolean stillValid(Player player) {
		
		return true;
	}

	public static MenuConstructor getServerMenu(BlockEntityChanger te, BlockPos pos)
	{
		return (id, playerInv, player) -> new MenuChanger(id, playerInv, te); 
	}
}
