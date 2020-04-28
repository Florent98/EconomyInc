/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks.tileentity;

import fr.fifoube.blocks.BlockSeller;
import fr.fifoube.gui.container.ContainerSeller;
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

public class TileEntityBlockSeller extends TileEntity implements INamedContainerProvider {

	private static final TranslationTextComponent NAME = new TranslationTextComponent("container.seller");
	ItemStackHandler inventory_seller = new ItemStackHandler(1); //STACK HANDLER FOR ONE SLOT = 0 
	private String owner = ""; 
	private String ownerName = "";
	private double funds_total;
	private double cost;
	private boolean created;
	private int amount;
	private String item = "";
	private boolean admin;
	private String facing = "";
    private ITextComponent customName;
	
	public TileEntityBlockSeller()
	{
		this(TileEntityRegistery.TILE_SELLER);
	}
	
	public TileEntityBlockSeller(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
	public ItemStackHandler getHandler()
	{
		return inventory_seller;	
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
	    	read(pkt.getNbtCompound());
	    }
	    

		public ItemStack getStackInSlot(int slot)
		{
			return inventory_seller.getStackInSlot(slot);
		}
		
		public ItemStack removeStackInSlot(int slot)
		{
			return inventory_seller.getStackInSlot(slot).split(1);
		}
		
		public void setFacing(String face)
		{
			this.facing = face;
		}
		
		public String getFacing()
		{
			return this.facing;
		}

		public void setAdmin(Boolean adminS)
		{
			this.admin = adminS;
		}
		
		public boolean getAdmin()
		{
			return this.admin;
		}
		
	    public void setOwner(String string)
	    {
	        this.owner = string;
	    }
	    
	    public String getOwner()
	    {
	        return this.owner;
	    }
	    
	    public void setOwnerName(String stringName)
	    {
	    	this.ownerName = stringName;
	    }
	    
	    public String getOwnerName()
	    {
	    	return this.ownerName;
	    }
	    
	    public void setCost(double costS)
	    {
	    	this.cost = costS;
	    }
	    
	    public double getCost()
	    {
	        return this.cost;
	    }
	    
	    public void setFundsTotal(double fundsS)
	    {
	    	this.funds_total = fundsS;
	    }
	    
	    public double getFundsTotal()
	    {
	    	return this.funds_total;
	    }
	    public void setCreated(boolean createdS)
	    {
	    	this.created = createdS;
	    }
	    
	    public boolean getCreated()
	    {
	        return this.created;
	    }
	    
	    public void setItem(String itemS)
	    {
	    	this.item = itemS;
	    }
	    
	    public String getItem()
	    {
	    	return this.inventory_seller.getStackInSlot(0).getDisplayName().getFormattedText().toString();
	    }
	    
	    public void setAmount(int amountS)
	    {
	    	this.amount = amountS;
	    }
	    
	    public int getAmount()
	    {
	    	return this.inventory_seller.getStackInSlot(0).getCount();
	    }
	   
		@Override
		public CompoundNBT write(CompoundNBT compound) 
		{
			compound.put("inventory", inventory_seller.serializeNBT());
			compound.putString("ownerS", this.owner);
			compound.putString("ownerName", this.ownerName);
			compound.putDouble("cost", this.cost);
			compound.putInt("amount", this.amount);
			compound.putString("item", this.item);
			compound.putDouble("funds_total", this.funds_total);
			compound.putBoolean("created", this.created);
			compound.putBoolean("admin", this.admin);
			compound.putString("facing", this.facing);
			if (this.getDisplayName() != null) {
	             compound.putString("CustomName", ITextComponent.Serializer.toJson(this.getDisplayName()));
	        }
			return super.write(compound);
		}
		
		
		@Override
		public void read(CompoundNBT compound) 
		{
			super.read(compound);
			inventory_seller.deserializeNBT(compound.getCompound("inventory"));
			this.owner = compound.getString("ownerS");
			this.ownerName = compound.getString("ownerName");
			this.cost = compound.getDouble("cost");
			this.amount = compound.getInt("amount");
			this.item = compound.getString("item");
			this.funds_total = compound.getDouble("funds_total");
			this.created = compound.getBoolean("created");
			this.admin = compound.getBoolean("admin");
			this.facing = compound.getString("facing");
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
		public Container createMenu(int id, PlayerInventory inventoryPlayer, PlayerEntity playerEntity) {
			return new ContainerSeller(id, inventoryPlayer, getPos());
		}
		 
		@Override
		public ITextComponent getDisplayName() {
			return NAME;
		}
		


}
