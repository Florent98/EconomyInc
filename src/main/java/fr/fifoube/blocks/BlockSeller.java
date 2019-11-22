package fr.fifoube.blocks;

import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import fr.fifoube.gui.ClientGuiScreen;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockSeller extends ContainerBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	private static final TranslationTextComponent NAME = new TranslationTextComponent("container.seller_buy");

	public BlockSeller(Properties properties) {
		super(properties);
	    this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TileEntityBlockSeller();
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);		
			if(tileentity instanceof TileEntityBlockSeller)
			{
				TileEntityBlockSeller te = (TileEntityBlockSeller)tileentity;
				if(te.getOwner() != null)
				{
					String checkONBT = te.getOwner();
					String checkOBA = player.getUniqueID().toString();
						
						if(checkONBT.equals(checkOBA))
						{
							if(!te.getCreated())
							{
								NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)te, buf -> buf.writeBlockPos(pos));				
							}
						}
				}
			}
		}
		else if(worldIn.isRemote)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);		
			if(tileentity instanceof TileEntityBlockSeller)
			{
				TileEntityBlockSeller te = (TileEntityBlockSeller)tileentity;
				if(te.getOwner() != null)
				{
					String checkONBT = te.getOwner();
					String checkOBA = player.getUniqueID().toString();
						
						if(checkONBT.equals(checkOBA))
						{
							if(te.getCreated())
							{
								ClientGuiScreen.openGui(1, te);	
							}
						}
				}
			}		
		}
		return true;
	}
	
	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if(tileentity instanceof TileEntityBlockSeller)
		{
			TileEntityBlockSeller te = (TileEntityBlockSeller)tileentity;
			ItemStack stack = player.getHeldItemMainhand();
			state = worldIn.getBlockState(pos);
			
			if(te != null)
			{
				if(stack.isItemEqual(new ItemStack(ItemsRegistery.ITEM_REMOVER)))
				{
					String checkONBT = te.getOwner();
					String checkOBA = player.getUniqueID().toString();
					
					if(checkONBT.equals(checkOBA))
					{
						worldIn.destroyBlock(pos, true);
						worldIn.removeTileEntity(pos);
					}
				}
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    	worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing()), 2);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if(tileentity instanceof TileEntityBlockSeller)
		{
			TileEntityBlockSeller te = (TileEntityBlockSeller)tileentity;
			te.setOwner(placer.getUniqueID().toString());
			te.setFacing(state.toString().substring(28, 33));
			te.setOwnerName(placer.getName().getString());   
		}
	}
	
	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
	   	 this.setDefaultFacing(worldIn, pos, state);	 
	}
	
	 private void setDefaultFacing(World worldIn, BlockPos pos, BlockState state)
	    {
	        if (!worldIn.isRemote)
	        {
	            BlockState blockstate = worldIn.getBlockState(pos.north());
	            BlockState blockstate1 = worldIn.getBlockState(pos.south());
	            BlockState blockstate2 = worldIn.getBlockState(pos.west());
	            BlockState blockstate3 = worldIn.getBlockState(pos.east());
	            Direction dir = (Direction)state.get(FACING);

	            if (dir == Direction.NORTH && blockstate.isSolid() && !blockstate1.isSolid())
	            {
	            	dir = Direction.SOUTH;
	            }
	            else if (dir == Direction.SOUTH && blockstate1.isSolid() && !blockstate.isSolid())
	            {
	            	dir = Direction.NORTH;
	            }
	            else if (dir == Direction.WEST && blockstate2.isSolid() && !blockstate3.isSolid())
	            {
	            	dir = Direction.EAST;
	            }
	            else if (dir == Direction.EAST && blockstate3.isSolid() && !blockstate2.isSolid())
	            {
	            	dir = Direction.WEST;
	            }

	            worldIn.setBlockState(pos, state.with(FACING, dir), 2);
	        }
	    }
	
	/**
	* Convert the given metadata into a BlockState for this Block
	*/
	public BlockState getStateFromMeta(int meta)
	{
		Direction dir = Direction.byIndex(meta);
	 
	    if (dir.getAxis() == Direction.Axis.Y)
	    {
	        dir = Direction.NORTH;
	    }
	 
	    return this.getDefaultState().with(FACING, dir);
	}
	 
	/**
	* Convert the BlockState into the correct metadata value
	*/
	public int getMetaFromState(BlockState state)
	{
	    return ((Direction)state.get(FACING)).getIndex();
	}
	
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}
	
	@Override
	public BlockRenderLayer getRenderLayer() {

		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	 
}
