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

import fr.fifoube.gui.container.MenuVault2by2;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlockEntityVault2by2 extends BlockEntity implements MenuProvider {

	public UUID owner;
	private Direction direction = Direction.NORTH;
	private List<String> allowedPlayers = new ArrayList<String>();
	private int maxAllowedPlayers = 0;
    protected final ItemStackHandler inventory;
    public static final TranslatableComponent NAME = new TranslatableComponent("block.economyinc.block_vault");

    public BlockEntityVault2by2(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    	
		super(tileEntityTypeIn, pos, state);
		this.inventory = new ItemStackHandler(54) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
		};
	}
    
    public BlockEntityVault2by2(BlockPos pos, BlockState state) 
    {
		this(BlockEntityTypeRegistery.TILE_VAULT_2BY2.get(), pos, state);
	}
   
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    	
        return super.getCapability(cap, side);
    }
    
    public ItemStackHandler getInventory() {
    	return this.inventory;
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
		
    	this.allowedPlayers.clear();
		this.load(pkt.getTag());
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
	
    public void setOwner(UUID uuid)
    {
        this.owner = uuid;
    }
   
    public UUID getOwner()
    {
        return this.owner;
    }
    
    public void addAllowedPlayers(String allowed)
    {
    	this.allowedPlayers.add(allowed);
    }
    
    public List<String> getAllowedPlayers()
    {
		return this.allowedPlayers; 	
    }
    
    public int getMax()
    {
    	return this.maxAllowedPlayers;
    }
    
    public void addToMax()
    {
    	this.maxAllowedPlayers = this.maxAllowedPlayers + 1;
    }
    
    public void removeToMax() 
    {
    	this.maxAllowedPlayers = this.maxAllowedPlayers -1;
    }
    public Direction getDirection()
    {
    	return this.direction;
    }

	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}
	
	@Override
	protected void saveAdditional(CompoundTag compound) {
		
		super.saveAdditional(compound);
		compound.put("inventory", inventory.serializeNBT());
	    if(this.owner != null) {
	    	compound.putUUID("owner", this.owner);
	    }
	    compound.putString("direction", this.direction.toString());
		compound.putInt("maxallowed", this.maxAllowedPlayers);
		ListTag tagList = new ListTag();
         for(int i = 0; i < this.allowedPlayers.size(); i++)
         {
          String s = allowedPlayers.get(i);
          if(s != null)
          {
              tagList.add(StringTag.valueOf(s));
          }
         }
         compound.put("allowedList", tagList);
	}
	
	@Override
	public void load(CompoundTag compound) {

		super.load(compound);
		inventory.deserializeNBT(compound.getCompound("inventory"));
		this.owner = compound.getUUID("owner");
		this.direction = getDirectionFromString(compound.getString("direction"));
		this.maxAllowedPlayers = compound.getInt("maxallowed");
        ListTag tagList = compound.getList("allowedList", Tag.TAG_STRING);
        for(int i = 0; i < tagList.size(); i++)
        {    
            this.allowedPlayers.add(i, tagList.getString(i));
        }
	}

	public Direction getDirectionFromString(String direction) {

        return switch (direction) {
            case "north" -> Direction.NORTH;
            case "south" -> Direction.SOUTH;
            case "east" -> Direction.EAST;
            case "west" -> Direction.WEST;
            default -> Direction.NORTH;
        };
	}

	@Override
	public Component getDisplayName() {
		return NAME;
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
		return new MenuVault2by2(id, inv, player, this);
	}
}
