package fr.fifoube.blocks;

import fr.fifoube.blocks.tileentity.TileEntityBlockBills;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockBills extends Block {

	public ItemEntity item;

	public BlockBills(Properties properties) {
		super(properties);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if(tileentity instanceof TileEntityBlockBills)
		{
			TileEntityBlockBills te = (TileEntityBlockBills)tileentity;
	        if(!worldIn.isRemote)
	        {
	        	int direction = MathHelper.floor((double) (placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
	        	te.setDirection((byte) direction);
	        	te.markDirty();
	        }
		}
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if(!worldIn.isRemote)
		{
	    	TileEntity tileentity = worldIn.getTileEntity(pos);
	    	if(tileentity instanceof TileEntityBlockBills)
	    	{
	    		TileEntityBlockBills te = (TileEntityBlockBills)tileentity;
		    	if(te.getNumbBills() != 64)
		    	{
	    			String unNa = player.getHeldItem(hand).getTranslationKey();
		    		if(te.getNumbBills() == 0)
		    		{
			    		if(unNa.equals("item.economyinc.item_oneb") || unNa.equals("item.economyinc.item_fiveb") || unNa.equals("item.economyinc.item_tenb") || unNa.equals("item.economyinc.item_twentyb") || unNa.equals("item.economyinc.item_fiftybe") || unNa.equals("item.economyinc.item_hundreedb") || unNa.equals("item.economyinc.item_twohundreedb") || unNa.equals("item.economyinc.item_fivehundreedb"))
			    		{
			    			checkBillRef(te, worldIn, player, hand);
					    	te.addBill();
					    	player.getHeldItem(hand).setCount(player.getHeldItemMainhand().getCount() - 1);
					    	te.markDirty();
			    		}
		    		}
		    		else
		    		{
		    			if(te.getBillRef().equals(unNa))
		    			{
		    				te.addBill();
		    				player.getHeldItem(hand).setCount(player.getHeldItemMainhand().getCount() - 1);
					    	te.markDirty();
		    			}
		    		}
	
		    	}
	    	}
		}
		return true;
		
	}

	public void checkBillRef(TileEntityBlockBills te, IWorld worldIn, PlayerEntity playerIn, Hand hand)
	{
		if(!worldIn.isRemote())
		{
			switch (playerIn.getHeldItem(hand).getTranslationKey()) {
				case "item.economyinc.item_oneb":
					te.setBillRef("item.economyinc.item_oneb");
					te.markDirty();
					break;
				case "item.economyinc.item_fiveb":
					te.setBillRef("item.economyinc.item_fiveb");
					te.markDirty();
					break;
				case "item.economyinc.item_tenb":
					te.setBillRef("item.economyinc.item_tenb");
					te.markDirty();
					break;
				case "item.economyinc.item_twentyb":
					te.setBillRef("item.economyinc.item_twentyb");
					te.markDirty();
					break;
				case "item.economyinc.item_fiftybe":
					te.setBillRef("item.economyinc.item_fiftybe");
					te.markDirty();
					break;
				case "item.economyinc.item_hundreedb":
					te.setBillRef("item.economyinc.item_hundreedb");
					te.markDirty();
					break;
				case "item.economyinc.item_twohundreedb":
					te.setBillRef("item.economyinc.item_twohundreedb");
					te.markDirty();
					break;
				case "item.economyinc.item_fivehundreedb":
					te.setBillRef("item.economyinc.item_fivehundreedb");
					te.markDirty();
					break;
				default:
					te.setBillRef("item.economyinc.item_zerob");
					te.markDirty();
					break;
			}
		}
	}
	
	public void checkBillRefForDrop(TileEntityBlockBills te, IWorld worldIn, BlockPos pos)
	{
		if(!worldIn.isRemote())
		{
			switch (te.getBillRef()) {
				case "item.economyinc.item_oneb":
					item = new ItemEntity(worldIn.getWorld(), pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_ONEB));
					break;
				case "item.economyinc.item_fiveb":
					item = new ItemEntity(worldIn.getWorld(), pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_FIVEB));
					break;
				case "item.economyinc.item_tenb":
					item = new ItemEntity(worldIn.getWorld(), pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_TENB));
					break;
				case "item.economyinc.item_twentyb":
					item = new ItemEntity(worldIn.getWorld(), pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_TWENTYB));
					break;
				case "item.economyinc.item_fiftybe":
					item = new ItemEntity(worldIn.getWorld(), pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_FIFTYB));
					break;
				case "item.economyinc.item_hundreedb":
					item = new ItemEntity(worldIn.getWorld(), pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_HUNDREEDB));
					break;
				case "item.economyinc.item_twohundreedb":
					item = new ItemEntity(worldIn.getWorld(), pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_TWOHUNDREEDB));
					break;
				case "item.economyinc.item_fivehundreedb":
					item = new ItemEntity(worldIn.getWorld(), pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_FIVEHUNDREEDB));
					break;
				default:
					break;
			}
		}
	}
	//TILE ENTITY
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityBlockBills();
	}
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
	     return tileentity == null ? false : tileentity.receiveClientEvent(id, param);	     
	}

	
	//RENDER
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
}

