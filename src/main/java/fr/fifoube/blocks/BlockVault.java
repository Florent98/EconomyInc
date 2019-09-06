package fr.fifoube.blocks;

import java.util.List;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault;
import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext.Builder;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;

public class BlockVault extends ContainerBlock {

	public BlockVault(Properties properties) {
		super(properties);
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
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if(tileentity instanceof TileEntityBlockVault)
		{
			TileEntityBlockVault te = (TileEntityBlockVault)tileentity;
		    te.setOwner(placer.getUniqueID().toString());
		    int direction = MathHelper.floor((double) (placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
			te.setDirection((byte) direction);
			byte direction_te = te.getDirection();
			if(direction_te == 0)
			{
				int xPos = te.getPos().getX();
				int yPos = te.getPos().getY();
				int zPos = te.getPos().getZ();
				if(worldIn.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos + 1, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos + i, yPos + j, zPos));
						}
					}
					worldIn.setBlockState(new BlockPos(xPos + 1, yPos, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos + 1, yPos, zPos));
					te2by2.setDirection((byte)0);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos - 1, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos - i, yPos + j, zPos));
						}
					}
					
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos));
					te2by2.setDirection((byte)0);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos - 1, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos - i, yPos - j, zPos));
						}
					}
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos));
					te2by2.setDirection((byte)0);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos + 1, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos + i, yPos - j, zPos));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos + 1, yPos - 1, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos + 1, yPos - 1, zPos));
					te2by2.setDirection((byte)0);
					te2by2.setString(te.getOwnerS());
				}
				
			}
			else if(direction_te ==  2)
			{
				int xPos = te.getPos().getX();
				int yPos = te.getPos().getY();
				int zPos = te.getPos().getZ();
				if(worldIn.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos - 1, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos - i, yPos + j, zPos));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos - 1, yPos, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos - 1, yPos, zPos));
					te2by2.setDirection((byte)2);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos + 1, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos + i, yPos + j, zPos));
						}
					}				
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos));
					te2by2.setDirection((byte)2);
					te2by2.setString(te.getOwnerS());

				}
				else if(worldIn.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos + 1, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos + i, yPos - j, zPos));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos));
					te2by2.setDirection((byte)2);
					te2by2.setString(te.getOwnerS());

				}
				else if(worldIn.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos - 1, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos - i, yPos - j, zPos));
						}
					}			
					worldIn.setBlockState(new BlockPos(xPos - 1, yPos - 1, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos - 1, yPos - 1, zPos));
					te2by2.setDirection((byte)2);
					te2by2.setString(te.getOwnerS());

				}
				
			}
			else if(direction_te == 1)
			{
				int xPos = te.getPos().getX();
				int yPos = te.getPos().getY();
				int zPos = te.getPos().getZ();

				if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos + i, zPos + j));
						}
					}			
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos + 1), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos + 1));
					te2by2.setDirection((byte)1);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos , yPos + 1, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos + i, zPos - j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos));
					te2by2.setDirection((byte)1);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos , yPos, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos - i, zPos - j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos));
					te2by2.setDirection((byte)1);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos - i, zPos + j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos + 1), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos + 1));
					te2by2.setDirection((byte)1);
					te2by2.setString(te.getOwnerS());
				}
			}
			else if(direction_te == 3)
			{
				int xPos = te.getPos().getX();
				int yPos = te.getPos().getY();
				int zPos = te.getPos().getZ();

				if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos + i, zPos - j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos - 1), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos - 1));
					te2by2.setDirection((byte)3);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos , yPos + 1, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos + i, zPos + j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos));
					te2by2.setDirection((byte)3);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos , yPos, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos - i, zPos + j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos));
					te2by2.setDirection((byte)3);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							setBlockToAir(worldIn, new BlockPos(xPos, yPos - i, zPos - j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos - 1), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos - 1));
					te2by2.setDirection((byte)3);
					te2by2.setString(te.getOwnerS());
				}
			}
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
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
							}
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
						worldIn.destroyBlock(pos, true);
						worldIn.removeTileEntity(pos);
					}
				}
			}
		}
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		
		/*TileEntity tileentity = builder.getWorld().getTileEntity(builder.b);
		if(tileentity instanceof TileEntityBlockVault)
		{
			TileEntityBlockVault te = (TileEntityBlockVault)tileentity;
			IItemHandler inventory = te.getHandler();
			if(inventory != null)
			{
				for(int i=0; i < inventory.getSlots(); i++)
				{
					if(inventory.getStackInSlot(i) != ItemStack.EMPTY)
					{
						EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, inventory.getStackInSlot(i));
						
						float multiplier = 0.1f;
						float motionX = world.rand.nextFloat() - 0.5F;
						float motionY = world.rand.nextFloat() - 0.5F;
						float motionZ = world.rand.nextFloat() - 0.5F;
						
						item.motionX = motionX * multiplier;
						item.motionY = motionY * multiplier;
						item.motionZ = motionZ * multiplier;
						
						world.spawnEntity(item);
					}
				}
			}
		}	*/
		return null;
	}
	

	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
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
