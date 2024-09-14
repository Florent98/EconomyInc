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

import fr.fifoube.gui.container.MenuBuyer;
import fr.fifoube.gui.container.MenuBuyerCreation;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.UUID;

public class BlockEntityBuyer extends BlockEntity implements MenuProvider {
	
	public static final MutableComponent NAME = Component.translatable("block.economyinc.block_buyer");
	ItemStackHandler inventory_buyer = new ItemStackHandler(27);
	ItemStackHandler money_handler = new ItemStackHandler(1);
	private Component customName;
	private String ownerName = "";
	private UUID owner; 
	private String facing = "";
	private int timer;
	private double cost;
	private boolean admin;
	private boolean isCreated;
	private double moneyInBlock = 0;
	private ItemStack stackToBuy = ItemStack.EMPTY;
	private ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();

	private int slotFree = 0;

    protected BlockEntityBuyer(BlockPos pos, BlockState state)
    {
        super(BlockEntityTypeRegistery.TILE_BUYER.get(), pos, state);
    }
    
	public ItemStackHandler getInventoryHandler()
	{
		return inventory_buyer;	
	}
	
	public ItemStackHandler getMoneyHandler()
	{
		return money_handler;
	}

	public boolean hasSpace()
	{
		boolean hasEmptySlot = false;
		for (int i = 0; i < getInventoryHandler().getSlots(); i++) {
			if(getInventoryHandler().getStackInSlot(i).isEmpty())
			{
				hasEmptySlot = true;
				this.slotFree += 1;
			}
		}
		return hasEmptySlot;
	}

	public int getFreeSlot()
	{
		return this.slotFree;
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
	
	public void setOwnerName(String name)
	{
		this.ownerName = name;
	}
	
	public String getOwnerName()
	{
		return this.ownerName;
	}
	
	public ItemStack getStackInSlot(int slot)
	{
		return inventory_buyer.getStackInSlot(slot);
	}
	
	public ItemStack removeStackInSlot(int slot)
	{
		return inventory_buyer.getStackInSlot(slot).split(1);
	}

	public UUID getOwner() {
		return owner;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public String getFacing() {
		return this.facing;
	}

	public void setFacing(String facing) {
		this.facing = facing;
	}
	
	public int getTimer() {
		return this.timer;
	}

	public void setTime(int timer) {
		this.timer = timer;
	}
	
	
	public boolean isCreated() {
		return isCreated;
	}

	public void setCreated(boolean isCreated) {
		this.isCreated = isCreated;
	}
	
	public void addToAccount(double value)
	{
		this.moneyInBlock = this.moneyInBlock + value;
	}
	
	public double getAccountMoney() {
		return moneyInBlock;
	}
	
	public void setAccount(double value)
	{
		this.moneyInBlock = value;
	}
	
	public ArrayList<ItemStack> getStackList() {
		return stackList;
	}

	public void addToStackList(ItemStack stack) {
		this.stackList.add(stack);
	}
	    
	public void setItemStackToBuy(ItemStack stack)
	{
		this.stackToBuy = stack;
	}
	
	public ItemStack getItemStackToBuy()
	{
		return this.stackToBuy;
	}
    

    @Override
    protected void saveAdditional(CompoundTag compound) {
    	
    	super.saveAdditional(compound);
    	compound.put("inventory_buyer", this.inventory_buyer.serializeNBT());
		compound.put("money_handler", this.money_handler.serializeNBT());
		compound.putString("ownerName", this.ownerName);
		if(this.owner != null)
		{
			compound.putUUID("ownerUUID", this.owner);
		}
		compound.putString("facing", this.facing);
		compound.putInt("timer", this.timer);
		compound.putDouble("cost", this.cost);
		compound.putBoolean("adminMode", this.admin);
		compound.putBoolean("isCreated", this.isCreated);
		compound.putDouble("moneyInBlock", this.moneyInBlock);
        compound.put("stackToBuy", this.getItemStackToBuy().serializeNBT());
    }
    
    @Override
    public void load(CompoundTag compound) {
    	
    	super.load(compound);
		this.inventory_buyer.deserializeNBT(compound.getCompound("inventory_buyer"));
		this.money_handler.deserializeNBT(compound.getCompound("money_handler"));
		this.ownerName = compound.getString("ownerName");
		this.owner = compound.getUUID("ownerUUID"); 
		this.facing = compound.getString("facing");
		this.timer = compound.getInt("timer");
		this.cost = compound.getDouble("cost");
		this.admin = compound.getBoolean("adminMode");
		this.isCreated = compound.getBoolean("isCreated");
		this.moneyInBlock = compound.getDouble("moneyInBlock");
		this.stackToBuy = ItemStack.of(compound.getCompound("stackToBuy"));
    }
	
	@Override
	public void setChanged() {
		super.setChanged();
		level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
	}
	
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
		
		 return isCreated ? new MenuBuyer(id, inv, this) : new MenuBuyerCreation(id, inv, this);
	}
	@Override
	public Component getDisplayName() {

		return this.NAME;
	}

	public static void serverTicker(Level level, BlockPos pos, BlockState state, BlockEntityBuyer buyer) {

		if(buyer.timer != 0)
		{
			--buyer.timer;
			if(buyer.timer <= 1)
			{
				buyer.setChanged();
			}
		}
	}




}
