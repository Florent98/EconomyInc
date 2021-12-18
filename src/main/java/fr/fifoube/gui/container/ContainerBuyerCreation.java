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

import fr.fifoube.blocks.tileentity.TileEntityBlockBuyer;
import fr.fifoube.gui.container.type.ContainerTypeRegistery;
import fr.fifoube.items.IValue;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBuyerCreation extends Container {

	private TileEntityBlockBuyer tile;
	private Inventory inventorySlot = new Inventory(1);
	private final Item[] bills = {ItemsRegistery.ITEM_ONEB, ItemsRegistery.ITEM_FIVEB, ItemsRegistery.ITEM_TENB, ItemsRegistery.ITEM_TWENTYB, ItemsRegistery.ITEM_FIFTYB, ItemsRegistery.ITEM_HUNDREEDB, ItemsRegistery.ITEM_TWOHUNDREEDB, ItemsRegistery.ITEM_FIVEHUNDREEDB};
	public ContainerBuyerCreation(int windowId, PlayerInventory playerInv, PacketBuffer extraData) {
		this(windowId, playerInv, extraData.readBlockPos());
	}
	
	public ContainerBuyerCreation(int windowId, PlayerInventory playerInv, BlockPos pos)
	{
		super(ContainerTypeRegistery.BUYER_CREATION_TYPE, windowId);
		TileEntity entity = playerInv.player.world.getTileEntity(pos);
		if(entity instanceof TileEntityBlockBuyer)
		{
			TileEntityBlockBuyer te = (TileEntityBlockBuyer)entity;
			this.tile = te;
			this.bindBuyerInventory(te);
			this.bindPlayerInventory(playerInv);
		}

	}
	
	private void bindBuyerInventory(TileEntityBlockBuyer te)
	{
		this.addSlot(new Slot(inventorySlot, 0, 8, 20) {
	         /**
	          * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	          */
	         public boolean isItemValid(ItemStack stack) {
	            return true;
	         }

	         /**
	          * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
	          * case of armor slots)
	          */
	         public int getSlotStackLimit() {
	            return 1;
	         }
	         
	         
	      });

		this.addSlot(new SlotItemHandler(tile.getMoneyHandler(), 0, 8, 40) {
			
			@Override
			public boolean isItemValid(ItemStack stack) {
				
				return stack.getItem() instanceof IValue;
			}
		
		});

	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
		
		ItemStack stack = player.inventory.getItemStack().copy();
		if(clickTypeIn != ClickType.PICKUP_ALL)
		{
			if(slotId == 0)
			{
				ItemStack stackCopy = stack.copy();
				stackCopy.setCount(1);
				getSlot(0).putStack(stackCopy);
				stack.setCount(stack.getCount());
				player.inventory.setItemStack(stack);
			}
		}
		else
		{
			getSlot(0).putStack(ItemStack.EMPTY);
		}
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
		return false;
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
	        	if(stackInSlot.getItem() instanceof IValue)
	        	{
	        		
	        		Slot slotContainer = getSlot(1); //SLOT OF THE MONEY
	        		slotContainer.putStack(stackInSlot);
	        		
	        	}
	        	else
	        	{
	        		stack = stackInSlot.copy();
	        		stack.setCount(1);
	        		
	        		Slot slotContainer = getSlot(0); //SLOT FOR THE OBJECT
	        		slotContainer.putStack(stack);	
	        	}
	        }
	        return ItemStack.EMPTY;
	}


	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}

	public TileEntityBlockBuyer getTile() {
		return this.tile;
	}
	
	public void closeContainer(PlayerEntity playerIn, boolean retrieveMoney) {
		
		if(retrieveMoney)
		{
			boolean found = false;
			int index = -1;
			
			for (int i = 0; i < playerIn.inventory.mainInventory.size(); i++) {
				
				if(!found)
				if(playerIn.inventory.getStackInSlot(i).isEmpty())
				{
					found = true;
					index = i;
				}
				
			}
	
			if(found && index != -1)
			{
				if(!tile.getStackList().isEmpty() && !tile.isCreated())
				{
					tile.setAccount(0);
					tile.setCost(0);
					tile.setItemStackToBuy(ItemStack.EMPTY);
					for(int i = 0; i < tile.getStackList().size(); i++)
					{
						playerIn.inventory.addItemStackToInventory(tile.getStackList().get(i));
					}
					tile.getStackList().clear();
				}
			}
			else
			{
				playerIn.sendMessage(new TranslationTextComponent("title.buyer.recoverFundError"), playerIn.getUniqueID());
			}
		}
	}
	
}