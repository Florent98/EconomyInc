package fr.fifoube.blocks;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault;
import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;

public class BlockVault extends ContainerBlock {

 	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	private static final TranslationTextComponent NAME = new TranslationTextComponent("container.vault");

	public BlockVault(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TileEntityBlockVault();
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		
		worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if(tileentity instanceof TileEntityBlockVault)
		{
			TileEntityBlockVault te = (TileEntityBlockVault)tileentity;
		    te.setOwner(placer.getUniqueID().toString());
			if(state.get(FACING).equals(Direction.SOUTH))
			{
				int xPos = te.getPos().getX();
				int yPos = te.getPos().getY();
				int zPos = te.getPos().getZ();
				if(worldIn.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos + 1, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN BAS A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos + i, yPos + j, zPos));
						}
					}
					worldIn.setBlockState(new BlockPos(xPos + 1, yPos, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.SOUTH));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos + 1, yPos, zPos));
					te2by2.setDirection((byte)0);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos - 1, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN BAS A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos - i, yPos + j, zPos));
						}
					}
					
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.SOUTH));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos));
					te2by2.setDirection((byte)0);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos - 1, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN HAUT A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos - i, yPos - j, zPos));
						}
					}
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.SOUTH));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos));
					te2by2.setDirection((byte)0);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos + 1, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN HAUT A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos + i, yPos - j, zPos));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos + 1, yPos - 1, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.SOUTH));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos + 1, yPos - 1, zPos));
					te2by2.setDirection((byte)0);
					te2by2.setString(te.getOwnerS());
				}
				
			}
			else if(state.get(FACING).equals(Direction.NORTH))
			{
				int xPos = te.getPos().getX();
				int yPos = te.getPos().getY();
				int zPos = te.getPos().getZ();
				if(worldIn.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos - 1, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN BAS A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos - i, yPos + j, zPos));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos - 1, yPos, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.NORTH));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos - 1, yPos, zPos));
					te2by2.setDirection((byte)2);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos + 1, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN BAS A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos + i, yPos + j, zPos));
						}
					}				
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.NORTH));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos));
					te2by2.setDirection((byte)2);
					te2by2.setString(te.getOwnerS());

				}
				else if(worldIn.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos + 1, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN HAUT A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos + i, yPos - j, zPos));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.NORTH));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos));
					te2by2.setDirection((byte)2);
					te2by2.setString(te.getOwnerS());

				}
				else if(worldIn.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos - 1, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN HAUT A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos - i, yPos - j, zPos));
						}
					}			
					worldIn.setBlockState(new BlockPos(xPos - 1, yPos - 1, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.NORTH));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos - 1, yPos - 1, zPos));
					te2by2.setDirection((byte)2);
					te2by2.setString(te.getOwnerS());

				}
				
			}
			else if(state.get(FACING).equals(Direction.WEST))
			{
				int xPos = te.getPos().getX();
				int yPos = te.getPos().getY();
				int zPos = te.getPos().getZ();

				if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN BAS A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos + i, zPos + j));
						}
					}			
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos + 1), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.WEST));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos + 1));
					te2by2.setDirection((byte)1);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos , yPos + 1, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN BAS A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos + i, zPos - j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.WEST));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos));
					te2by2.setDirection((byte)1);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos , yPos, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN HAUT A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos - i, zPos - j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.WEST));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos));
					te2by2.setDirection((byte)1);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN HAUT A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos - i, zPos + j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos + 1), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.WEST));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos + 1));
					te2by2.setDirection((byte)1);
					te2by2.setString(te.getOwnerS());
				}
			}
			else if(state.get(FACING).equals(Direction.EAST))
			{
				int xPos = te.getPos().getX();
				int yPos = te.getPos().getY();
				int zPos = te.getPos().getZ();

				if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN BAS A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos + i, zPos - j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos - 1), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.EAST));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos - 1));
					te2by2.setDirection((byte)3);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos , yPos + 1, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN BAS A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos + i, zPos + j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.EAST));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos));
					te2by2.setDirection((byte)3);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos , yPos, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos + 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN HAUT A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos - i, zPos + j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.EAST));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos));
					te2by2.setDirection((byte)3);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos - 1)).getBlock() == BlocksRegistry.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistry.BLOCK_VAULT)
				{
					//EN HAUT A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos - i, zPos - j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos - 1), BlocksRegistry.BLOCK_VAULT_2BY2.getDefaultState().with(FACING, Direction.EAST));
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos - 1));
					te2by2.setString(te.getOwnerS());
				}
			}
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);		
			if(tileentity instanceof TileEntityBlockVault)
			{
				TileEntityBlockVault te = (TileEntityBlockVault)tileentity;
				if(te.getOwnerS() != null)
				{
					String checkONBT = te.getOwnerS();
					String checkOBA = player.getUniqueID().toString();
					
					if(checkONBT.equals(checkOBA))
					{
			            NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)te, buf -> buf.writeBlockPos(pos));
						te.setIsOpen(true);
						te.markDirty();
						return ActionResultType.SUCCESS;
						
					}
					else
					{
						for(int i = 0; i < te.getAllowedPlayers().size(); i++)
						{
							String listToCheck = te.getAllowedPlayers().get(i).toString();
							if(player.getName().equals(listToCheck))
							{
					            NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)te, buf -> buf.writeBlockPos(pos));
								te.markDirty();
								return ActionResultType.SUCCESS;
							}
						}
					}
	
				}
				
			}
		}
         return ActionResultType.FAIL;
	}
	
	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if(tileentity instanceof TileEntityBlockVault)
		{
			TileEntityBlockVault te = (TileEntityBlockVault)tileentity;
			ItemStack stack = player.getHeldItemMainhand();
			state = worldIn.getBlockState(pos);
			
			if(te != null)
			{
				if(stack.isItemEqual(new ItemStack(ItemsRegistery.ITEM_REMOVER)))
				{
					String checkONBT = te.getOwnerS();
					String checkOBA = player.getUniqueID().toString();
					
					if(checkONBT.equals(checkOBA))
					{
						worldIn.destroyBlock(pos, false);
						dropBlocks(te, worldIn, pos);
						worldIn.removeTileEntity(pos);
					}
				}
			}
		}
	}

	public void dropBlocks(TileEntity tileentity, World world, BlockPos pos) {
		
		
		if(tileentity instanceof TileEntityBlockVault)
		{
			TileEntityBlockVault te = (TileEntityBlockVault)tileentity;
			IItemHandler inventory = te.getHandler();
			ItemEntity itemBase = new ItemEntity(world, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(BlocksRegistry.BLOCK_VAULT));
			world.addEntity(itemBase);
			if(inventory != null)
			{
				for(int i=0; i < inventory.getSlots(); i++)
				{
					if(inventory.getStackInSlot(i) != ItemStack.EMPTY)
					{
						ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, inventory.getStackInSlot(i));
						
						float multiplier = 0.1f;
						float motionX = world.rand.nextFloat() - 0.5F;
						float motionY = world.rand.nextFloat() - 0.5F;
						float motionZ = world.rand.nextFloat() - 0.5F;
						
						item.lastTickPosX = motionX * multiplier;
						item.lastTickPosY = motionY * multiplier;
						item.lastTickPosZ = motionZ * multiplier;
						
						world.addEntity(item);
					}
				}
			}
		}	
	}
	
	
	@Override
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
	    }
		
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate((Direction)state.get(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation((Direction)state.get(FACING)));
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
	public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
	     return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}
	
		
	public void setBlockToAir(World worldIn, BlockPos pos)
	{
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
	}
	
	
}
