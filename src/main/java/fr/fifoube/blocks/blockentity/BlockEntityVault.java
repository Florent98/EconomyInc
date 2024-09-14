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

package fr.fifoube.blocks.blockentity;

import fr.fifoube.gui.container.MenuVault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import java.util.UUID;

public class BlockEntityVault extends BlockEntity implements MenuProvider {

	public static final MutableComponent NAME = Component.translatable("block.economyinc.block_vault");
	ItemStackHandler inventory;
	private UUID owner;
	private byte direction;
	private boolean isOpen;
	
    public BlockEntityVault(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    	
		super(tileEntityTypeIn, pos, state);
		this.inventory = new ItemStackHandler(27) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
		};
	}
    
    public BlockEntityVault(BlockPos pos, BlockState state) 
    {
		this(BlockEntityTypeRegistery.TILE_VAULT.get(), pos, state);
	}
   
	public ItemStackHandler getHandler()
	{
		return inventory;
	}

	
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	@Override
	public CompoundTag getUpdateTag() {
		
		return this.saveWithFullMetadata();
	}
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		
		this.load(pkt.getTag());
	}
    
	public boolean getIsOpen()
	{
		return this.isOpen;
	}
	
	public void setIsOpen(boolean isOpenIn)
	{
		this.isOpen = isOpenIn;
	}
	
	// AUTRES
    public void setOwner(UUID uuid)
    {
        this.owner = uuid;
    }
    
    public UUID getOwner()
    {
        return this.owner;
    }
    
    public Boolean hasItems()
    {
    	for(int i = 0; i < 27; i++)
    	{
    		if(inventory.getStackInSlot(i) != ItemStack.EMPTY)
    		{
    			return true;
    		}
    	}
		return false;	
    }
    
    public byte getDirection()
    {
    	return this.direction;
    }

	public void setDirection(byte direction) 
	{
		this.direction = direction;
	}
	
	@Override
	public void load(CompoundTag tag) {
		
		 super.load(tag);
		 inventory.deserializeNBT(tag.getCompound("inventory"));
         this.owner = tag.getUUID("ownerUUID");
         this.direction = tag.getByte("direction");
	}
	
	@Override
	protected void saveAdditional(CompoundTag tag) {
		
		super.saveAdditional(tag);
		tag.put("inventory", inventory.serializeNBT());
		tag.putByte("direction", this.direction);     
	    if(this.owner != null) {
	    	tag.putUUID("ownerUUID", this.owner);
	    }  	
	}
	
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
		return new MenuVault(id, inv, player, this);
	}

	@Override
	public Component getDisplayName() {
		return this.NAME;
	}
	
}
