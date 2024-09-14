/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks;

import fr.fifoube.blocks.blockentity.BlockEntitySeller;
import fr.fifoube.blocks.blockentity.BlockEntityTypeRegistery;
import fr.fifoube.gui.ClientGuiScreen;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import java.util.Random;
import java.util.UUID;

public class BlockSeller extends Block implements EntityBlock {

	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
 	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public BlockSeller(Properties properties) {
		super(properties);
	    this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		
		return BlockEntityTypeRegistery.TILE_SELLER.get().create(pos, state);
	}
			

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		if(!level.isClientSide)
		{
			BlockEntity tileentity = level.getBlockEntity(pos);		
			if(tileentity instanceof BlockEntitySeller)
			{
				BlockEntitySeller te = (BlockEntitySeller)tileentity;
				if(te.getOwner() != null)
				{
					UUID checkONBT = te.getOwner();
					UUID checkOBA = player.getUUID();
						
					if(checkONBT.equals(checkOBA))
					{
						if(!te.getCreated())
						{
							NetworkHooks.openScreen((ServerPlayer)player, te, pos);
							return InteractionResult.SUCCESS;
						}
						else if (te.getCreated())
						{
							if(player.getItemInHand(InteractionHand.MAIN_HAND).equals(new ItemStack(ItemsRegistery.WRENCH.get()), false));
							{
								NetworkHooks.openScreen((ServerPlayer)player, te, pos);
							}
						}
					}
				}
			}
		}
		else
		{
			BlockEntity tileentity = level.getBlockEntity(pos);		
			if(tileentity instanceof BlockEntitySeller)
			{

				BlockEntitySeller te = (BlockEntitySeller)tileentity;
				if(te.getOwner() != null)
				{					
					if(te.getCreated())
					{			
						ClientGuiScreen.openGui(1, te);	
						return InteractionResult.SUCCESS;
					}
				}
			}		
		}
		return InteractionResult.CONSUME;
	}


	@Override
	public void attack(BlockState state, Level level, BlockPos pos, Player player) {
		
		BlockEntity entity = level.getBlockEntity(pos);
		if(entity != null)
		if(entity instanceof BlockEntitySeller)
		{
			BlockEntitySeller te = (BlockEntitySeller)entity;
			state = level.getBlockState(pos);
			
			if(te != null)
			{
				if(player.getMainHandItem().is(ItemsRegistery.WRENCH.get()))
				{
					UUID checkONBT = te.getOwner();
					UUID checkOBA = player.getUUID();
					
					if(checkONBT.equals(checkOBA))
					{
						level.destroyBlock(pos, true);
						level.removeBlockEntity(pos);
						dropBlocks(te, level, pos);
					}
				}
			}
		}
	}


	public void dropBlocks(BlockEntitySeller tileentity, Level level, BlockPos pos) {
		
			ItemEntity itemBase = new ItemEntity(level, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(BlocksRegistry.BLOCK_SELLER.get()));
			level.addFreshEntity(itemBase);
			if(tileentity.getInventory().getStackInSlot(0) != null)
			{
				ItemEntity itemContainer = new ItemEntity(level, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, tileentity.getInventory().getStackInSlot(0));
				level.addFreshEntity(itemContainer);
			}
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		
		return level.isClientSide() ? null : create(type, BlockEntityTypeRegistery.TILE_SELLER.get(), BlockEntitySeller::serverTicker);
	}

    @SuppressWarnings("unchecked")
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> create(BlockEntityType<A> type, BlockEntityType<E> typeExpec, BlockEntityTicker<? super E> ticker)
    {
        return typeExpec == type ? (BlockEntityTicker<A>) ticker : null;
    }
	
	
	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		
		super.setPlacedBy(level, pos, state, placer, stack);
		level.setBlock(pos, state.setValue(FACING, placer.getDirection()), 2);
		BlockEntity tileentity = level.getBlockEntity(pos);
		if(tileentity instanceof BlockEntitySeller)
		{
			BlockEntitySeller te = (BlockEntitySeller)tileentity;
			te.setOwner(placer.getUUID());
			te.setFacing(state.toString().substring(38, 43));
			te.setOwnerName(placer.getName().getString());   
		}
	}

	
	
/*	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		
		this.setDefaultFacing(worldIn, pos, state);
	    for(Direction direction : Direction.values()) {
	    	worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
	    }
	}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		
		 if (!isMoving) {
	         for(Direction direction : Direction.values()) {
	            worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
	         }

	      }
	}*/
	
	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean isMoving) {
		this.setDefaultFacing(level, pos, state);
	}
	
	private void setDefaultFacing(Level level, BlockPos pos, BlockState state) {
	        
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
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		
		return state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		
		return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		
		builder.add(FACING, POWERED);
	}

	@Override
	public RenderShape getRenderShape(BlockState p_60550_) {
		
		return RenderShape.MODEL;
	}

	
	//REDSTONE HANDLING
	
	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}
	
	@Override
	public int getSignal(BlockState state, BlockGetter get, BlockPos pos, Direction dir) {
		
			return state.getValue(POWERED) && state.getValue(FACING).getOpposite() == dir ? 15 : 0;
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		return state.getValue(POWERED) ? 15 : 0;
	}
	
    public void signalPower(Level level, BlockPos pos) {
        if (!level.isClientSide() && !level.getBlockTicks().hasScheduledTick(pos, this)) {
            level.scheduleTick(pos, this, 2);
         }
	}
    
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (state.getValue(POWERED)) {
           level.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(false)), 2);
        } else {
           level.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(true)), 2);
           level.scheduleTick(pos, this, 20);
        }
        this.updateNeighborsInFront(level, pos, state);
     }
    
    protected void updateNeighborsInFront(Level level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction);
        level.neighborChanged(blockpos, this, pos);
        level.updateNeighborsAtExceptFromFacing(blockpos, this, direction);
     }
}

