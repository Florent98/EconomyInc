package fr.fifoube.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockChanger extends Block {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	public BlockChanger(Properties properties) {
		super(properties);
	    this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));

	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    	worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()), 2);
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
	 


}
