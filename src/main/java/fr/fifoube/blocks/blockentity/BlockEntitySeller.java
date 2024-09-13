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

import fr.fifoube.gui.container.MenuSeller;
import fr.fifoube.gui.container.MenuSellerBuy;
import fr.fifoube.items.ItemsRegistery;
import fr.fifoube.packets.PacketRefillSeller;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import java.util.UUID;

public class BlockEntitySeller extends BlockEntity implements MenuProvider {

	public static final TranslatableComponent NAME = new TranslatableComponent("block.economyinc.block_seller");
	private UUID owner;
	private String ownerName = "";
	private String item = "";
	private String facing = "";
	private double funds_total;
	private double cost;
	private int amount;
	private int timer;
	private boolean autorefill;
	private boolean admin;
	private boolean created;
    protected final ItemStackHandler inventory;


    protected BlockEntitySeller(BlockPos pos, BlockState state)
    {
        super(BlockEntityTypeRegistery.TILE_SELLER.get(), pos, state);
        this.inventory = new ItemStackHandler(1) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
		};
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
		
		this.load(pkt.getTag());
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
	
    public void setOwner(UUID uuid)
    {
        this.owner = uuid;
    }
    
    public UUID getOwner()
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
    
    public void increaseFundsTotal()
    {
    	this.funds_total = getFundsTotal() + getCost();
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
    	return this.inventory.getStackInSlot(0).getHoverName().getString();
    }
    
    public int getAmount()
    {
    	return this.inventory.getStackInSlot(0).getCount();
    }
    
    public void setTime(int time)
    {
    	this.timer = time;
    }
    
    public int getTime()
    {
    	return this.timer;
    }
    
    public void deIncTime()
    {
    	--this.timer;
    }

	public void setAutoRefill(boolean restock) { this.autorefill = restock; }

	public boolean getAutoRefill() { return this.autorefill; }

	public ItemStack getStackInSlot(int index) {

		return this.inventory.getStackInSlot(index);
	}
    

    @Override
    protected void saveAdditional(CompoundTag compound) {
    	
    	super.saveAdditional(compound);
    	compound.put("inventory", inventory.serializeNBT());
    	if(this.owner != null)
		{
			compound.putUUID("ownerUUID", this.owner);	
		}
		compound.putString("ownerName", this.ownerName);
		compound.putDouble("cost", this.cost);
		compound.putInt("amount", this.amount);
		compound.putString("item", this.item);
		compound.putDouble("funds_total", this.funds_total);
		compound.putBoolean("created", this.created);
		compound.putBoolean("admin", this.admin);
		compound.putString("facing", this.facing);
		compound.putInt("timer", this.timer);
		compound.putBoolean("restock", this.autorefill);
    }
    
    @Override
    public void load(CompoundTag compound) {
    	
    	super.load(compound);
    	inventory.deserializeNBT(compound.getCompound("inventory"));
		this.owner = compound.getUUID("ownerUUID");
		this.ownerName = compound.getString("ownerName");
		this.cost = compound.getDouble("cost");
		this.amount = compound.getInt("amount");
		this.item = compound.getString("item");
		this.funds_total = compound.getDouble("funds_total");
		this.created = compound.getBoolean("created");
		this.admin = compound.getBoolean("admin");
		this.facing = compound.getString("facing");
		this.timer = compound.getInt("timer");
		this.autorefill = compound.getBoolean("restock");

	}
    
	public static void serverTicker(Level level, BlockPos pos, BlockState state, BlockEntitySeller seller) {
		
		if(seller.timer != 0)
		{
			--seller.timer;
		}	
	}
	
	@Override
	public void setChanged() {
		super.setChanged();
		level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
	}
	
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
		
		if(getOwner().equals(inv.player.getUUID()))
		{
			if(!getCreated() || inv.player.getMainHandItem().getItem() == ItemsRegistery.WRENCH.get())
			{
				return new MenuSeller(id, inv, this);
			}
			else
			{
				return new MenuSellerBuy(id, inv, this);
			}
		}
		return new MenuSellerBuy(id, inv, this);
	}

	@Override
	public Component getDisplayName() {

		return this.NAME;
	}

	public IItemHandlerModifiable getHandler() {
		return this.inventory;
	}

	public void refill()
	{
		BlockPos pos = getBlockPos().below();
		if(getLevel().getBlockEntity(pos) instanceof BlockEntityVault te)
		{
			if(te.getOwner().equals(this.getOwner()))
			{
				ItemStack stack = getStackInSlot(0);
				if(stack.getCount() != stack.getMaxStackSize())
				{
					PacketsRegistery.CHANNEL.sendToServer(new PacketRefillSeller(getBlockPos(), getBlockPos().below(), stack));
				}
			}
		}
	}
}
