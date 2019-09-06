package fr.fifoube.blocks;

import fr.fifoube.items.ItemCreditCard;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockATM extends Block implements INamedContainerProvider {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	private static final TranslationTextComponent NAME = new TranslationTextComponent("container.atm");

	public BlockATM(Properties properties) {
		super(properties);
	    this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));

	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote)
		{
			for(int i = 0; i <= player.inventory.getSizeInventory(); i++)
			{
				if(player.inventory.getStackInSlot(i) != null)
				{
					if(player.inventory.getStackInSlot(i).getItem() instanceof ItemCreditCard)		
					{
						ItemStack stackIn = player.inventory.getStackInSlot(i);
						if(stackIn.hasTag() && stackIn.getTag().getBoolean("Owned"))
						{
							NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)this, buf -> buf.writeBlockPos(player.getPosition()));	
							return true;
						}
					}
				}
			}
		}
		return false;
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
	

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new Container(ContainerType.FURNACE /** TO CHANGE **/, id) {
			
			@Override
			public boolean canInteractWith(PlayerEntity playerIn) {
				// TODO Auto-generated method stub
				return true;
			}
			
		};
	}


}
