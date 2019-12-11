package fr.fifoube.blocks;

import fr.fifoube.blocks.tileentity.TileEntityBlockChanger;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockChanger extends ContainerBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	private static final TranslationTextComponent NAME = new TranslationTextComponent("container.changer");

	public BlockChanger(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TileEntityBlockChanger();
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
		
		if(!worldIn.isRemote)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if(tileentity instanceof TileEntityBlockChanger)
			{
				TileEntityBlockChanger te = (TileEntityBlockChanger)tileentity;
				if(te != null)
				{
					if(te.getNumbUse() < 1)
					{
			            NetworkHooks.openGui((ServerPlayerEntity)playerIn, (INamedContainerProvider)te, buf -> buf.writeBlockPos(pos));
						te.setNumbUse(1);
						te.setEntityPlayer(playerIn);
						te.markDirty();
	
					}
					else
					{
						playerIn.sendMessage(new StringTextComponent(I18n.format("title.alreadyUsed")));
					}
				}
			}
		}
         return true;
	}
	
	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if(tileentity instanceof TileEntityBlockChanger)
		{
			TileEntityBlockChanger te = (TileEntityBlockChanger)tileentity;
			ItemStack stack = player.getHeldItemMainhand();
				
				if(te != null)
				{
					if(stack.isItemEqual(new ItemStack(ItemsRegistery.ITEM_REMOVER)))
					{
						if(te.getNumbUse() < 1)
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
    	worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()), 2);
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

	            if (dir == Direction.NORTH && blockstate.isSolid() && blockstate1.isSolid())
	            {
	                dir = Direction.SOUTH;
	            }
	            else if (dir == Direction.SOUTH && blockstate1.isSolid() && blockstate.isSolid())
	            {
	            	dir = Direction.NORTH;
	            }
	            else if (dir == Direction.WEST && blockstate2.isSolid() && blockstate3.isSolid())
	            {
	            	dir = Direction.EAST;
	            }
	            else if (dir == Direction.EAST && blockstate3.isSolid() && blockstate2.isSolid())
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
		public BlockRenderLayer getRenderLayer() {

			return BlockRenderLayer.SOLID;
		}

		@Override
		public boolean isSolid(BlockState state) {
			return true;
		}
		 

}
