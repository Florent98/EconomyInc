/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks;


import fr.fifoube.blocks.blockentity.BlockEntityChanger;
import fr.fifoube.blocks.blockentity.BlockEntityTypeRegistery;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class BlockChanger extends Block implements EntityBlock {

	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public BlockChanger(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		
		return BlockEntityTypeRegistery.TILE_CHANGER.get().create(pos, state);
	}
			
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		
		boolean canOpen = false;
		if(!level.isClientSide)
		{
			BlockEntity tileentity = level.getBlockEntity(pos);
			if(tileentity instanceof BlockEntityChanger)
			{
				BlockEntityChanger te = (BlockEntityChanger)tileentity;
				if(te != null)
				{
					if(te.getNumbUse() < 1)
					{
						canOpen = true;
					}
				}
				openMenuOrThrowError((ServerPlayer)player, te, pos, canOpen, state, level);
			}
			
		}
        return InteractionResult.CONSUME;
	}
	
	public InteractionResult openMenuOrThrowError(ServerPlayer player, BlockEntityChanger te, BlockPos pos, boolean canOpen, BlockState state, Level level)
	{
		if(canOpen)
		{
            NetworkHooks.openScreen(player, te, pos);
			te.setNumbUse(1);
			te.setEntityPlayer(player);
			te.setChanged();
			return InteractionResult.SUCCESS;
		}
		else
		{
			player.displayClientMessage(Component.translatable("title.alreadyUsed"), true);
		}
		return InteractionResult.CONSUME;
	}
	
	@Override
	public void attack(BlockState state, Level level, BlockPos pos, Player player) {
		
		BlockEntity tileentity = level.getBlockEntity(pos);
		if(tileentity instanceof BlockEntityChanger)
		{
			BlockEntityChanger te = (BlockEntityChanger)tileentity;				
				if(te != null)
				{
					if(player.getMainHandItem().is(ItemsRegistery.WRENCH.get()))
					{
						if(te.getNumbUse() < 1)
						{
							level.destroyBlock(pos, true);
							level.removeBlockEntity(pos);
							
							//DROPPING ITEMS
							ItemEntity itemBase = new ItemEntity(level, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(BlocksRegistry.BLOCK_CHANGER.get()));
							level.addFreshEntity(itemBase);
							for(int i=0; i < te.getInventory().getSlots(); i++)
							{
								ItemStack toDrop = te.getInventory().getStackInSlot(i);
								if(toDrop != null && toDrop != ItemStack.EMPTY)
								{
									ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, toDrop);
									
									float multiplier = 0.1f;
									float motionX = level.random.nextFloat() - 0.5F;
									float motionY = level.random.nextFloat() - 0.5F;
									float motionZ = level.random.nextFloat() - 0.5F;
									
									item.xOld = motionX * multiplier;
									item.yOld = motionY * multiplier;
									item.zOld = motionZ * multiplier;
									
									level.addFreshEntity(item);
								}
							}
						}
					}
				}
		}
		
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		
		return level.isClientSide() ? null : create(type, BlockEntityTypeRegistery.TILE_CHANGER.get(), BlockEntityChanger::serverTicker);
	}
	
    @SuppressWarnings("unchecked")
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> create(BlockEntityType<A> type, BlockEntityType<E> typeExpec, BlockEntityTicker<? super E> ticker)
    {
        return typeExpec == type ? (BlockEntityTicker<A>) ticker : null;
    }

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(level, pos, state, placer, stack);
    	level.setBlock(pos, state.setValue(FACING, placer.getDirection().getOpposite()), 2);
	}
	

	/*@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
	   	 this.setDefaultFacing(worldIn, pos, state);	 
	}

	
	 private void setDefaultFacing(World worldIn, BlockPos pos, BlockState state) {
	        if (!worldIn.isRemote)
	        {
	            BlockState blockstate = worldIn.getBlockState(pos.north());
	            BlockState blockstate1 = worldIn.getBlockState(pos.south());
	            BlockState blockstate2 = worldIn.getBlockState(pos.west());
	            BlockState blockstate3 = worldIn.getBlockState(pos.east());
	            Direction dir = (Direction)state.get(FACING);

	            if (dir == Direction.NORTH && blockstate.isCollisionShapeLargerThanFullBlock() && !blockstate1.isCollisionShapeLargerThanFullBlock())
	            {
	                dir = Direction.SOUTH;
	            }
	            else if (dir == Direction.SOUTH && blockstate1.isCollisionShapeLargerThanFullBlock() && !blockstate.isCollisionShapeLargerThanFullBlock())
	            {
	            	dir = Direction.NORTH;
	            }
	            else if (dir == Direction.WEST && blockstate2.isCollisionShapeLargerThanFullBlock() && !blockstate3.isCollisionShapeLargerThanFullBlock())
	            {
	            	dir = Direction.EAST;
	            }
	            else if (dir == Direction.EAST && blockstate3.isCollisionShapeLargerThanFullBlock() && !blockstate2.isCollisionShapeLargerThanFullBlock())
	            {
	            	dir = Direction.WEST;
	            }
	            worldIn.setBlockState(pos, state.with(FACING, dir), 2);
	        }
	    }*/
	
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
			builder.add(FACING);
		}
	 

		@Override
		public RenderShape getRenderShape(BlockState state) {
			
			return RenderShape.MODEL;
		}

}
