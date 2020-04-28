/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks.tileentity;

import java.util.ArrayList;
import java.util.List;

import fr.fifoube.gui.container.ContainerVault;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBlockVault extends TileEntity implements INamedContainerProvider {

	private static final TranslationTextComponent NAME = new TranslationTextComponent("container.vault");
	ItemStackHandler inventory = new ItemStackHandler(27);
	private String ownerS = "";
	private byte direction;
	private List<String> allowedPlayers = new ArrayList<String>();
	private int maxAllowedPlayers = 0;
	private boolean isOpen;
    private ITextComponent customName;
	
    public TileEntityBlockVault() 
    {
		this(TileEntityRegistery.TILE_BLOCKVAULT);
	}
    
    public TileEntityBlockVault(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	public ItemStackHandler getHandler()
	{
		return inventory;
	}
	
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(this.pos, 1, this.getUpdateTag());
    }

    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) 
    {
    	this.allowedPlayers.clear();
    	read(pkt.getNbtCompound());
    }
    // ANIMATION
	
	public boolean getIsOpen()
	{
		return this.isOpen;
	}
	
	public void setIsOpen(boolean isOpenIn)
	{
		this.isOpen = isOpenIn;
	}
	
	// AUTRES
    public void setOwner(String string)
    {
        this.ownerS = string;
    }
    
    public String getOwnerS()
    {
        return this.ownerS;
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
    
    public void setAllowedPlayers(String allowed)
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
    	this.maxAllowedPlayers = this.maxAllowedPlayers - 1;
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
    public CompoundNBT write(CompoundNBT compound) 
    {
        compound.put("inventory", inventory.serializeNBT());
        compound.putString("ownerS", this.ownerS);
        compound.putByte("direction", this.direction);
        compound.putInt("maxallowed", this.maxAllowedPlayers);
        ListNBT tagList = new ListNBT();
         for(int i = 0; i < this.allowedPlayers.size(); i++)
         {
          String s = allowedPlayers.get(i);
          if(s != null)
          {
              tagList.add(StringNBT.valueOf(s));
          }
         }
         compound.put("allowedList", tagList);
         compound.putBoolean("isOpen", this.isOpen);
         
         if (this.getDisplayName() != null) {
             compound.putString("CustomName", ITextComponent.Serializer.toJson(this.getDisplayName()));
          }
        return super.write(compound);
    }
    
    
    @Override
    public void read(CompoundNBT compound) 
    {
        super.read(compound);
        inventory.deserializeNBT(compound.getCompound("inventory"));
        this.ownerS = compound.getString("ownerS");
        this.direction = compound.getByte("direction");
        this.maxAllowedPlayers = compound.getInt("maxallowed");
		this.isOpen = compound.getBoolean("isOpen");
        ListNBT tagList = compound.getList("allowedList", NBT.TAG_STRING);
        for(int i = 0; i < tagList.size(); i++)
        {    
            this.allowedPlayers.add(i, tagList.getString(i));
        }
        if (compound.contains("CustomName", Constants.NBT.TAG_STRING)) {
            this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
         }
    }
    
	@Override
	public void markDirty() 
	{
		 BlockState state = this.world.getBlockState(getPos());
	     this.world.notifyBlockUpdate(getPos(), state, state, 3);
	}

	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new ContainerVault(id, playerInventory, getPos());
	}

	@Override
	public ITextComponent getDisplayName() {
		return NAME;
	}


}
