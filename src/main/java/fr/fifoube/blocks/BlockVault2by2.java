/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks;

import fr.fifoube.blocks.blockentity.BlockEntityTypeRegistery;
import fr.fifoube.blocks.blockentity.BlockEntityVault2by2;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;

import java.util.UUID;

public class BlockVault2by2 extends Block implements EntityBlock {

 	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
		
	public static final VoxelShape NORTH_AABB = Block.box(0, 0, 0, 32D, 32D, 16D);
	public static final VoxelShape SOUTH_AABB = Block.box(-16D, 0, 0, 16D, 32D, 16D);
	public static final VoxelShape EAST_AABB = Block.box(0, 0, 0, 16D, 32D, 32D);
	public static final VoxelShape WEST_AABB = Block.box(0, 0, -16D, 16D, 32D, 16D);


	public BlockVault2by2(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
	}
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		
		return BlockEntityTypeRegistery.TILE_VAULT_2BY2.get().create(pos, state);
	}
	
	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {

		Direction dir = placer.getDirection().getOpposite();
		level.setBlock(pos, state.setValue(FACING, dir), 2);
		BlockEntity tileentity = level.getBlockEntity(pos);
    	if(tileentity instanceof BlockEntityVault2by2)
    	{
    		BlockEntityVault2by2 te = (BlockEntityVault2by2)tileentity;
    		te.setOwner(placer.getUUID());
			System.out.println(dir);
    		te.setDirection(dir);
    	}
	}
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		
		if(!level.isClientSide)
		{
			BlockEntity tileentity = level.getBlockEntity(pos);		
			if(tileentity instanceof BlockEntityVault2by2)
			{
				BlockEntityVault2by2 te = (BlockEntityVault2by2)tileentity;
				if(te.getOwner() != null)
				{
					UUID checkONBT = te.getOwner();
					UUID checkOBA = player.getUUID();
					if(checkONBT.equals(checkOBA) || player.hasPermissions(4))
					{
						NetworkHooks.openScreen((ServerPlayer)player, te, pos);
						te.setChanged();
						return InteractionResult.SUCCESS;
					}
					else
					{
						for(int i = 0; i < te.getAllowedPlayers().size(); i++)
						{
							String fullString = te.getAllowedPlayers().get(i);
							String listToCheck = fullString.substring(fullString.indexOf(",") + 1);
							if(player.getUUID().equals(UUID.fromString(listToCheck)))
							{
								NetworkHooks.openScreen((ServerPlayer)player, te, pos);
								te.setChanged();
								return InteractionResult.SUCCESS;
							}
						}
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
		if(entity instanceof BlockEntityVault2by2)
		{
			BlockEntityVault2by2 te = (BlockEntityVault2by2)entity;
			state = level.getBlockState(pos);
			
			if(te != null)
			{
				if(player.getMainHandItem().is(ItemsRegistery.WRENCH.get()))
				{
					UUID checkONBT = te.getOwner();
					UUID checkOBA = player.getUUID();
					
					if(checkONBT.equals(checkOBA))
					{
						level.destroyBlock(pos, false);
						dropBlocks(te, level, pos);
						level.removeBlockEntity(pos);
					}
				}
			}
		}
	}

	public void dropBlocks(BlockEntityVault2by2 te, Level level, BlockPos pos) {
		
		
			IItemHandler inventory = te.getInventory();
			ItemEntity itemBase = new ItemEntity(level, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(BlocksRegistry.BLOCK_VAULT.get(), 4));
			level.addFreshEntity(itemBase);
			if(inventory != null)
			{
				for(int i=0; i < inventory.getSlots(); i++) 
				{
					if(inventory.getStackInSlot(i) != ItemStack.EMPTY)
					{
						ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, inventory.getStackInSlot(i));
						
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

	/*@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		
		this.setDefaultFacing(worldIn, pos, state);
	}
	
	 private void setDefaultFacing(Level level, BlockPos pos, BlockState state) {
		 
	        if (!level.isClientSide)
	        {
	            BlockState blockstate = level.getBlockState(pos.north());
	            BlockState blockstate1 = level.getBlockState(pos.south());
	            BlockState blockstate2 = level.getBlockState(pos.west());
	            BlockState blockstate3 = level.getBlockState(pos.east());
	            Direction dir = (Direction)state.getValue(FACING);

	            if (dir == Direction.NORTH && blockstate.hasLargeCollisionShape() && blockstate1.hasLargeCollisionShape())
	            {
	                dir = Direction.SOUTH;
	            }
	            else if (dir == Direction.SOUTH && blockstate1.hasLargeCollisionShape() && blockstate.hasLargeCollisionShape())
	            {
	            	dir = Direction.NORTH;
	            }
	            else if (dir == Direction.WEST && blockstate2.hasLargeCollisionShape() && blockstate3.hasLargeCollisionShape())
	            {
	            	dir = Direction.EAST;
	            }
	            else if (dir == Direction.EAST && blockstate3.hasLargeCollisionShape() && blockstate2.hasLargeCollisionShape())
	            {
	            	dir = Direction.WEST;
	            }
	            level.setBlock(pos, state.setValue(FACING, dir), 2);
	        }
	    }
		*/

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
	
    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
    	
		 BlockEntity tileentity = level.getBlockEntity(pos);
	     return tileentity == null ? false : tileentity.triggerEvent(id, param);
    }

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {

		return getShapeFromDirection(state);

	}

	@Override
	public VoxelShape getInteractionShape(BlockState state, BlockGetter getter, BlockPos pos) {

		return getShapeFromDirection(state);

	}

	@Override
	public VoxelShape getOcclusionShape(BlockState state, BlockGetter getter, BlockPos pos) {

		return getShapeFromDirection(state);

	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext ctx) {

		return getShapeFromDirection(state);

	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext ctx) {
		return getShapeFromDirection(state);
	}

	@Override
	public VoxelShape getBlockSupportShape(BlockState state, BlockGetter getter, BlockPos pos) {

		return getShapeFromDirection(state);
	}

	public VoxelShape getShapeFromDirection(BlockState state){

		return switch (state.getValue(FACING)) {
			case NORTH -> NORTH_AABB;
			case SOUTH -> SOUTH_AABB;
			case EAST -> EAST_AABB;
			case WEST -> WEST_AABB;
			default -> NORTH_AABB;
		};

	}

	@Override
	public boolean collisionExtendsVertically(BlockState state, BlockGetter level, BlockPos pos, Entity collidingEntity) {
		return true;
	}


}
