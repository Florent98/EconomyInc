package fr.fifoube.blocks;


import java.util.List;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext.Builder;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockVault2by2 extends ContainerBlock {

	public BlockVault2by2(Properties properties) {
		super(properties);
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TileEntityBlockVault2by2();
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) 
	{
		/*TileEntity tileentity = world.getTileEntity(pos);
		if(tileentity instanceof TileEntityBlockVault)
		{
			TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2)tileentity;
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
		}			
		super.getDrops(state, drops, world, pos, fortune);*/
		return null;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
    	if(tileentity instanceof TileEntityBlockVault2by2)
    	{
    		TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2)tileentity;
    		te.setString(placer.getUniqueID().toString());
    		te.ownerS = placer.getUniqueID().toString();
    		int direction = MathHelper.floor((double) (placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
    		te.setDirection((byte) direction);
    	}
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);		
			if(tileentity instanceof TileEntityBlockVault2by2)
			{
				TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2)tileentity;
				if(te.getOwnerS() != null)
				{
					String checkONBT = te.getOwnerS();
					String checkOBA = player.getUniqueID().toString();
					
					if(checkONBT.equals(checkOBA))
					{
			            NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)tileentity, buf -> buf.writeBlockPos(pos));
						te.markDirty();
						
					}
					else
					{
						for(int i = 0; i < te.getAllowedPlayers().size(); i++)
						{
							String listToCheck = te.getAllowedPlayers().get(i).toString();
							if(player.getName().equals(listToCheck))
							{
					            NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)tileentity, buf -> buf.writeBlockPos(pos));
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
		if(tileentity instanceof TileEntityBlockVault2by2)
		{
			TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2) tileentity;
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
	public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
	     return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}
	
}
