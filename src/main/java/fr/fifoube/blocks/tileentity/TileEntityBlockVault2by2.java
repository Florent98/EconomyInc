/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks.tileentity;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.fifoube.gui.container.ContainerVault2by2;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
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

public class TileEntityBlockVault2by2 extends TileEntity implements INamedContainerProvider {

	public static final TranslationTextComponent NAME = new TranslationTextComponent("container.vault2by2");
	ItemStackHandler inventory = new ItemStackHandler(54);
	public String ownerS = "";
	private byte direction;
	private List<String> allowedPlayers = new ArrayList<String>();
	private int maxAllowedPlayers = 0;
    private ITextComponent customName;

	
    public TileEntityBlockVault2by2() 
    {
		this(TileEntityRegistery.TILE_BLOCKVAULT_2BY2);
	}
    
    public TileEntityBlockVault2by2(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
  
	public ItemStackHandler getHandler()
	{
		return inventory;
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
	
    public void setString(String string)
    {
        this.ownerS = string;
    }
    
    public String getOwnerS()
    {
        return this.ownerS;
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
         if (this.getDisplayName() != null) {
             compound.putString("CustomName", ITextComponent.Serializer.toJson(this.getDisplayName()));
          }
		return super.write(compound);
	}
	
	@Override
	public void read(BlockState state, CompoundNBT compound) {

		super.read(state, compound);
		inventory.deserializeNBT(compound.getCompound("inventory"));
		this.ownerS = compound.getString("ownerS");
		this.direction = compound.getByte("direction");
		this.maxAllowedPlayers = compound.getInt("maxallowed");
        ListNBT tagList = compound.getList("allowedList", NBT.TAG_STRING);
        for(int i = 0; i < tagList.size(); i++)
        {    
            this.allowedPlayers.add(i, tagList.getString(i));
        }
        if (compound.contains("CustomName", Constants.NBT.TAG_STRING)) {
            this.customName = ITextComponent.Serializer.func_240643_a_(compound.getString("CustomName"));
         }

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
    	read(null, pkt.getNbtCompound());
    }

	@Override
	public void markDirty() 
	{
		 BlockState state = this.world.getBlockState(getPos());
	     this.world.notifyBlockUpdate(getPos(), state, state, 3);
	}

	

	@Override
	public Container createMenu(int id, PlayerInventory inventoryPlayer, PlayerEntity player) {
		return new ContainerVault2by2(id, inventoryPlayer, getPos());
	}

	@Override
	public ITextComponent getDisplayName() {
		return NAME;
	}



}
