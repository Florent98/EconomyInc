/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks;

import fr.fifoube.blocks.blockentity.BlockEntityTypeRegistery;
import fr.fifoube.blocks.blockentity.BlockEntityVault;
import fr.fifoube.blocks.blockentity.BlockEntityVault2by2;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;

import java.util.UUID;

public class BlockVault extends Block implements EntityBlock {

 	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public BlockVault(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		
		return BlockEntityTypeRegistery.TILE_VAULT.get().create(pos, state);
	}

	public void setBlockEntityVault2by2(Level level, BlockEntityVault prevTe, int xPos, int yPos, int zPos, Direction dir)
	{
		level.setBlock(new BlockPos(xPos, yPos, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.get().defaultBlockState().setValue(FACING, dir), 2);
		BlockEntity te = level.getBlockEntity(new BlockPos(xPos, yPos, zPos));
		if(te instanceof  BlockEntityVault2by2 te2by2)
		{
			te2by2.setDirection(dir);
			te2by2.setOwner(prevTe.getOwner());
		}
	}
	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {

		level.setBlock(pos, state.setValue(FACING, placer.getDirection().getOpposite()), 2);
		BlockEntity tileentity = level.getBlockEntity(pos);
		if(tileentity instanceof BlockEntityVault)
		{
			BlockEntityVault te = (BlockEntityVault)tileentity;

			BlockEntityVault te1;
			BlockEntityVault te2;
			BlockEntityVault te3;

			int xPos = te.getBlockPos().getX();
			int yPos = te.getBlockPos().getY();
			int zPos = te.getBlockPos().getZ();

			te.setOwner(placer.getUUID());
			if(state.getValue(FACING).equals(Direction.SOUTH))
			{
				if(level.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos + 1, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos + 1, yPos, zPos));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos + 1, yPos + 1, zPos));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos + 1, zPos));

					//EN BAS A GAUCHE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()){
						for(int i = 0; i <= 1; i++)
						{
							for(int j = 0; j <= 1; j++)
							{
								setBlockToAir(level, new BlockPos(xPos + i, yPos + j, zPos));
							}
						}
						setBlockEntityVault2by2(level, te,xPos + 1, yPos, zPos, Direction.SOUTH);
					}
				}
				else if(level.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos - 1, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos - 1, yPos, zPos));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos - 1, yPos + 1, zPos));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos + 1, zPos));
					//EN BAS A DROITE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()){
						for(int i = 0; i <= 1; i++)
						{
							for(int j = 0; j <= 1; j++)
							{
								setBlockToAir(level, new BlockPos(xPos - i, yPos + j, zPos));
							}
						}
						setBlockEntityVault2by2(level, te,xPos, yPos, zPos, Direction.SOUTH);
					}
					

				}
				else if(level.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos - 1, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos - 1, yPos, zPos));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos - 1, yPos - 1, zPos));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos - 1, zPos));
					//EN HAUT A DROITE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos - i, yPos - j, zPos));
							}
						}
						setBlockEntityVault2by2(level, te, xPos, yPos - 1, zPos, Direction.SOUTH);
					}

				}
				else if(level.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos + 1, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos + 1, yPos, zPos));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos + 1, yPos - 1, zPos));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos - 1, zPos));
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
					//EN HAUT A GAUCHE
						for(int i = 0; i <= 1; i++)
						{
							for(int j = 0; j <= 1; j++)
							{
								setBlockToAir(level, new BlockPos(xPos + i, yPos - j, zPos));
							}
						}
						setBlockEntityVault2by2(level, te, xPos + 1, yPos -1 , zPos, Direction.SOUTH);
					}

				}
				
			}
			else if(state.getValue(FACING).equals(Direction.NORTH))
			{
				if(level.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos - 1, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos - 1, yPos, zPos));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos - 1, yPos + 1, zPos));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos + 1, zPos));

					//EN BAS A GAUCHE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos - i, yPos + j, zPos));
							}
						}
						setBlockEntityVault2by2(level, te, xPos - 1, yPos, zPos, Direction.NORTH);
					}

				}
				else if(level.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos + 1, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos + 1, yPos, zPos));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos + 1, yPos + 1, zPos));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos + 1, zPos));

					//EN BAS A DROITE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos + i, yPos + j, zPos));
							}
						}
						setBlockEntityVault2by2(level, te, xPos, yPos, zPos, Direction.NORTH);
					}


				}
				else if(level.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos + 1, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos + 1, yPos, zPos));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos + 1, yPos - 1, zPos));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos - 1, zPos));

					//EN HAUT A DROITE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos + i, yPos - j, zPos));
							}
						}
						setBlockEntityVault2by2(level, te, xPos, yPos - 1, zPos, Direction.NORTH);
					}
				}
				else if(level.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos - 1, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos - 1, yPos, zPos));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos - 1, yPos - 1, zPos));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos - 1, zPos));

					//EN HAUT A GAUCHE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos - i, yPos - j, zPos));
							}
						}
						setBlockEntityVault2by2(level, te, xPos - 1, yPos - 1, zPos, Direction.NORTH);
					}

				}
			}
			else if(state.getValue(FACING).equals(Direction.WEST))
			{
				if(level.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos + 1, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos, zPos + 1));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos + 1, zPos + 1));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos + 1, zPos));

					//EN BAS A GAUCHE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos, yPos + i, zPos + j));
							}
						}
						setBlockEntityVault2by2(level, te, xPos, yPos, zPos + 1, Direction.WEST);
					}

				}
				else if(level.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos , yPos + 1, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos, zPos - 1));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos + 1, zPos - 1));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos + 1, zPos));

					//EN BAS A DROITE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos, yPos + i, zPos - j));
							}
						}
						setBlockEntityVault2by2(level, te, xPos, yPos, zPos, Direction.WEST);
					}

				}
				else if(level.getBlockState(new BlockPos(xPos , yPos, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos - 1, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos, zPos - 1));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos - 1, zPos - 1));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos - 1, zPos));

					//EN HAUT A DROITE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos, yPos - i, zPos - j));
							}
						}
						setBlockEntityVault2by2(level, te, xPos, yPos - 1, zPos, Direction.WEST);
					}
				}
				else if(level.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos - 1, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos, zPos + 1));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos - 1, zPos + 1));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos - 1, zPos));

					//EN HAUT A GAUCHE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos, yPos - i, zPos + j));
							}
						}
						setBlockEntityVault2by2(level, te, xPos, yPos - 1, zPos + 1, Direction.WEST);
					}
				}
			}
			else if(state.getValue(FACING).equals(Direction.EAST))
			{
				if(level.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos + 1, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos, zPos - 1));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos + 1, zPos - 1));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos + 1, zPos));

					//EN BAS A GAUCHE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos, yPos + i, zPos - j));
							}
						}
						setBlockEntityVault2by2(level, te, xPos, yPos, zPos - 1, Direction.EAST);
					}

				}
				else if(level.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos , yPos + 1, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos, zPos + 1));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos + 1, zPos + 1));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos + 1, zPos));

					//EN BAS A DROITE
					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos, yPos + i, zPos + j));
							}
						}
						setBlockEntityVault2by2(level, te, xPos, yPos, zPos, Direction.EAST);
					}
				}
				else if(level.getBlockState(new BlockPos(xPos , yPos, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos - 1, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{
					//EN HAUT A DROITE
					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos, zPos + 1));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos - 1, zPos + 1));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos - 1, zPos));

					if(te1 != null && te2 != null && te3 != null)
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos, yPos - i, zPos + j));
							}
						}
						setBlockEntityVault2by2(level, te, xPos, yPos - 1, zPos, Direction.EAST);
					}

				}
				else if(level.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos - 1, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT.get() && level.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT.get())
				{

					te1 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos, zPos - 1));
					te2 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos - 1, zPos - 1));
					te3 = (BlockEntityVault) level.getBlockEntity(new BlockPos(xPos, yPos - 1, zPos));

					//EN HAUT A GAUCHE
					if(te1 != null && te2 != null && te3 != null) 
					if(!te1.hasItems() && !te2.hasItems() && !te3.hasItems()) {
						for (int i = 0; i <= 1; i++) {
							for (int j = 0; j <= 1; j++) {
								setBlockToAir(level, new BlockPos(xPos, yPos - i, zPos - j));
							}
						}
						setBlockEntityVault2by2(level, te, xPos, yPos - 1, zPos - 1, Direction.EAST);
					}

				}
			}
		}
		super.setPlacedBy(level, pos, state, placer, stack);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		
		if(!level.isClientSide)
		{
			BlockEntity tileentity = level.getBlockEntity(pos);		
			if(tileentity instanceof BlockEntityVault)
			{
				BlockEntityVault te = (BlockEntityVault)tileentity;
				if(te.getOwner() != null)
				{
					UUID checkONBT = te.getOwner();
					UUID checkOBA = player.getUUID();
					if(checkONBT.equals(checkOBA))
					{
						NetworkHooks.openScreen((ServerPlayer)player, te, pos);
						return InteractionResult.SUCCESS;
					}
					else if(player.hasPermissions(4))
					{
						NetworkHooks.openScreen((ServerPlayer)player, te, pos);
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
		if(entity instanceof BlockEntityVault)
		{
			BlockEntityVault te = (BlockEntityVault)entity;
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

	public void dropBlocks(BlockEntityVault te, Level level, BlockPos pos) {
		
		
			IItemHandler inventory = te.getHandler();
			ItemEntity itemBase = new ItemEntity(level, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(BlocksRegistry.BLOCK_VAULT.get()));
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
	
	@Override
	public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
		
		BlockEntity tileentity = level.getBlockEntity(pos);
	    return tileentity == null ? false : tileentity.triggerEvent(id, param);
	}
	
		
	public void setBlockToAir(Level level, BlockPos pos)
	{
		level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
	}
	
	
}
