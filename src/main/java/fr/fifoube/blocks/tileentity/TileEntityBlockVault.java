/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks.tileentity;

import fr.fifoube.gui.container.ContainerVault;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

import java.util.UUID;

public class TileEntityBlockVault extends TileEntity implements INamedContainerProvider {

	private static final TranslationTextComponent NAME = new TranslationTextComponent("container.vault");
	ItemStackHandler inventory = new ItemStackHandler(27);
	private UUID owner;
	private byte direction;
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
    	read(null, pkt.getNbtCompound());
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
    public CompoundNBT write(CompoundNBT compound) 
    {
        compound.put("inventory", inventory.serializeNBT());
        compound.putByte("direction", this.direction);     
        if(this.owner != null) {
        	compound.putUniqueId("ownerUUID", this.owner);
        }  	
        if (this.getDisplayName() != null) {
        	compound.putString("CustomName", ITextComponent.Serializer.toJson(this.getDisplayName()));
        }
        return super.write(compound);
    }
    
    @Override
    public void read(BlockState state, CompoundNBT compound) {

    	super.read(state, compound);
    	 inventory.deserializeNBT(compound.getCompound("inventory"));
         this.owner = compound.getUniqueId("ownerUUID");
         this.direction = compound.getByte("direction");
         if (compound.contains("CustomName", Constants.NBT.TAG_STRING)) {
             this.customName = ITextComponent.Serializer.getComponentFromJson(compound.getString("CustomName"));
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
