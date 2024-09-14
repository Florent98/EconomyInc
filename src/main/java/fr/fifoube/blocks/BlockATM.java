/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks;

import fr.fifoube.gui.ClientGuiScreen;
import fr.fifoube.items.ItemCreditCard;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;


public class BlockATM extends Block {

	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	private static final MutableComponent NAME = Component.translatable("container.atm");

	public BlockATM(Properties properties) {
		super(properties);
	      this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));

	}
	
	
	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
		
		return 10;
	}
	
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
			for(int i = 0; i <= player.getInventory().getContainerSize(); i++)
			{
				if(player.getInventory().getItem(i) != null)
				{
					if(player.getInventory().getItem(i).getItem() instanceof ItemCreditCard)		
					{
						ItemStack stackIn = player.getInventory().getItem(i);
						if(stackIn.hasTag() && stackIn.getTag().getBoolean("Owned"))
						{
					    	if(level.isClientSide)
							{
					    		ClientGuiScreen.openGui(0, null);		
							}
							return InteractionResult.SUCCESS;
						}
					}
				}
			}
        return InteractionResult.CONSUME;
    }
    
    
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
    	
    	return this.defaultBlockState().setValue(FACING,placeContext.getHorizontalDirection().getOpposite());
    }
    
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean isMoving) {
	   	 this.setDefaultFacing(level, pos, state);	 
    }
	
	 private void setDefaultFacing(Level level, BlockPos pos, BlockState state)
	    {
	        if (!level.isClientSide)
	        {
	            BlockState blockstate = level.getBlockState(pos.north());
	            BlockState blockstate1 = level.getBlockState(pos.south());
	            BlockState blockstate2 = level.getBlockState(pos.west());
	            BlockState blockstate3 = level.getBlockState(pos.east());
	            Direction dir = (Direction)state.getValue(FACING);

	            if (dir == Direction.NORTH && blockstate.hasLargeCollisionShape() && !blockstate1.hasLargeCollisionShape())
	            {
	            	dir = Direction.SOUTH;
	            }
	            else if (dir == Direction.SOUTH && blockstate1.hasLargeCollisionShape() && !blockstate.hasLargeCollisionShape())
	            {
	            	dir = Direction.NORTH;
	            }
	            else if (dir == Direction.WEST && blockstate2.hasLargeCollisionShape() && !blockstate3.hasLargeCollisionShape())
	            {
	            	dir = Direction.EAST;
	            }
	            else if (dir == Direction.EAST && blockstate3.hasLargeCollisionShape() && !blockstate2.hasLargeCollisionShape())
	            {
	            	dir = Direction.WEST;
	            }

	            level.setBlock(pos, state.setValue(FACING, dir), 2);
	        }
	    }
	
	/**
	* Convert the given metadata into a BlockState for this Block
	*/
	public BlockState getStateFromMeta(int meta)
	{
	    Direction dir = Direction.from2DDataValue(meta);
	 
	    if (dir.getAxis() == Direction.Axis.Y)
	    {
	    	dir = Direction.NORTH;
	    }
	 
	    return this.defaultBlockState().setValue(FACING, dir);
	}
	 
	/**
	* Convert the BlockState into the correct metadata value
	*/
	public int getMetaFromState(BlockState state)
	{
	    return ((Direction)state.getValue(FACING)).get3DDataValue();
	}
	

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {

		builder.add(FACING);
	}

	@Override
	public MutableComponent getName() {
		return NAME;
	}

}
