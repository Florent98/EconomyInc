package fr.fifoube.blocks.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityBlockBills extends TileEntity {

	private byte direction;
	public int numbBills = 0;
	public String billRef = "";
	
	public TileEntityBlockBills(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
    public TileEntityBlockBills() 
    {
		this(TileEntityRegistery.TILE_BILLS);
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
    
    public String getBillRef()
    {
    	return this.billRef;
    }
    
    public void setBillRef(String billRefIn)
    {
    	this.billRef = billRefIn;
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
	
	
	@Override
	public CompoundNBT write(CompoundNBT compound) 
	{
		compound.putInt("numbBills", this.numbBills);
		compound.putByte("direction", this.direction);
		compound.putString("billRef", this.billRef);
		return super.write(compound);
	}
	
	
	@Override
	public void read(CompoundNBT compound) 
	{
		super.read(compound);
		this.numbBills = compound.getInt("numbBills");
		this.direction = compound.getByte("direction");
		this.billRef = compound.getString("billRef");
	}
	
	@Override
	public void markDirty() 
	{
		BlockState state = this.world.getBlockState(getPos());
		this.world.notifyBlockUpdate(getPos(), state, state, 3);
	}
}
