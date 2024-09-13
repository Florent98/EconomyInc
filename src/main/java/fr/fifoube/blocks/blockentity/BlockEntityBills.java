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

import fr.fifoube.items.ItemsRegistery;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityBills extends BlockEntity {

	private byte direction;
	public int numbBills = 0;
	public int billRef = 0;

	protected BlockEntityBills(BlockPos pos, BlockState state)
	{
		super(BlockEntityTypeRegistery.TILE_BILLS.get(), pos, state);
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	@Override
	public CompoundTag getUpdateTag() {

		CompoundTag tag = new CompoundTag();
		this.saveAdditional(tag);
		return tag;
	}
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {

		this.load(pkt.getTag());
	}
	
	public int getBillValue()
    {
    	return this.billRef;
    }
    
    public void setBillValue(int billValueIn)
    {
    	this.billRef = billValueIn;
    }
    
    public int getNumbBills()
	{
		return this.numbBills;
	}
	
	public void setNumbUse(int numbBillsIn)
	{
		this.numbBills = numbBillsIn;
	}
	
	public void addBill()
	{
		this.numbBills = this.numbBills + 1;
	}
	
	public byte getDirection()
    {
    	return this.direction;
    }

	public void setDirection(byte direction) 
	{
		this.direction = direction;
	}

	public ItemStack getItemBill(int value)
	{
		ItemStack stack;
		switch (value)
		{
			case 1:
				stack = new ItemStack(ItemsRegistery.ONEB.get());
				break;
			case 5:
				stack = new ItemStack(ItemsRegistery.FIVEB.get());
				break;
			case 10:
				stack = new ItemStack(ItemsRegistery.TENB.get());
				break;
			case 20:
				stack = new ItemStack(ItemsRegistery.TWENTYB.get());
				break;
			case 50:
				stack = new ItemStack(ItemsRegistery.FIFTYB.get());
				break;
			case 100:
				stack = new ItemStack(ItemsRegistery.HUNDREDB.get());
				break;
			case 200:
				stack = new ItemStack(ItemsRegistery.TWOHUNDREDB.get());
				break;
			case 500:
				stack = new ItemStack(ItemsRegistery.FIVEHUNDREDB.get());
				break;
			default:
				stack = ItemStack.EMPTY;
		}

		return stack;
	}

	@Override
	public void setChanged() {
		super.setChanged();
		level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
	}

	@Override
	public void load(CompoundTag compound) {
		
		super.load(compound);
		this.numbBills = compound.getInt("numbBills");
		this.direction = compound.getByte("direction");
		this.billRef = compound.getInt("billValue");
		
	}
	
	@Override
	protected void saveAdditional(CompoundTag compound) {
		
		super.saveAdditional(compound);
		compound.putInt("numbBills", this.numbBills);
		compound.putByte("direction", this.direction);
		compound.putInt("billValue", this.billRef);
	}

	
}
