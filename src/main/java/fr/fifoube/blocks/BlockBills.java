/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks;

import fr.fifoube.blocks.blockentity.BlockEntityBills;
import fr.fifoube.blocks.blockentity.BlockEntityTypeRegistery;
import fr.fifoube.items.IValue;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


public class BlockBills extends Block implements EntityBlock {

	private static final VoxelShape DEFAULT_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);


	public BlockBills(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntityTypeRegistery.TILE_BILLS.get().create(pos, state);
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity living, ItemStack stack) {
		BlockEntity tileentity = level.getBlockEntity(pos);
		if(tileentity instanceof BlockEntityBills te)
		{
	        if(!level.isClientSide)
	        {
	        	int direction = Mth.floor((double) (living.getYRot() * 4.0F / 360.0F) + 2.5D) & 3;
	        	te.setDirection((byte) direction);
	        	te.setChanged();
	        }
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if(!level.isClientSide)
		{
	    	BlockEntity tileentity = level.getBlockEntity(pos);
	    	if(tileentity instanceof BlockEntityBills te)
	    	{
	    		ItemStack heldStack = player.getItemInHand(hand);
	    		if (heldStack.isEmpty() && te.getNumbBills() > 0) {
	    			if (hand == InteractionHand.MAIN_HAND && player.isShiftKeyDown()) {
	    				ItemStack bill = te.getItemBill(te.getBillValue());
	    				bill.setCount(te.getNumbBills());
	    				if (!player.addItem(bill)) {
	    					player.drop(bill, false);
	    				}
	    				te.setNumbUse(0);
	    				te.setBillValue(0);
	    				te.setChanged();
	    				return InteractionResult.SUCCESS;
	    			}
	    			player.displayClientMessage(net.minecraft.network.chat.Component.translatable("title.billsStorageValue", te.getTotalValue()), true);
	    			return InteractionResult.SUCCESS;
	    		}
				if(te.getNumbBills() != 64)
		    	{
	    			Item item = heldStack.getItem();
		    		if(te.getNumbBills() == 0)
		    		{
						if(ItemsRegistery.availableBills().contains(item))
			    		{
							checkBillRef(te, level, player, hand);
					    	te.addBill();
					    	if(!player.getAbilities().instabuild) {
					    		heldStack.shrink(1);
					    	}
					    	te.setChanged();
					    	return InteractionResult.SUCCESS;
			    		}
		    		}
		    		else if(item instanceof IValue value && te.getBillValue() == value.getValue())
	    			{
	    				te.addBill();
	    				if(!player.getAbilities().instabuild) {
	    					heldStack.shrink(1);
	    				}
				    	te.setChanged();
				    	return InteractionResult.SUCCESS;
	    			}
		    	}
	    	}
		}
		return InteractionResult.CONSUME;
	}

	@Override
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockentity, ItemStack stack) {
		if (!level.isClientSide) {
			if (blockentity instanceof BlockEntityBills te) {
				ItemEntity itemBase = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(BlocksRegistry.BLOCK_BILLS.get()));
				level.addFreshEntity(itemBase);
				ItemStack bill = te.getItemBill(te.getBillValue());
				if (!bill.isEmpty() && te.getNumbBills() > 0) {
					bill.setCount(te.getNumbBills());
					ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, bill);
					level.addFreshEntity(item);
				}
			}
			super.playerDestroy(level, player, pos, state, blockentity, stack);
		}
	}

	public void checkBillRef(BlockEntityBills te, Level level, Player playerIn, InteractionHand hand)
	{
		if(!level.isClientSide)
		{
			int value = 0;
			if(playerIn.getItemInHand(hand).getItem() instanceof IValue givenValue)
			{
				value = givenValue.getValue();
			}

			switch (value) {
				case 1 -> te.setBillValue(1);
				case 5 -> te.setBillValue(5);
				case 10 -> te.setBillValue(10);
				case 20 -> te.setBillValue(20);
				case 50 -> te.setBillValue(50);
				case 100 -> te.setBillValue(100);
				case 200 -> te.setBillValue(200);
				case 500 -> te.setBillValue(500);
				default -> te.setBillValue(0);
			}
			te.setChanged();
		}
	}

	@Override
	public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
		 BlockEntity tileentity = level.getBlockEntity(pos);
	     return tileentity != null && tileentity.triggerEvent(id, param);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	private static VoxelShape getShapeFromCount(BlockEntityBills be)
	{
		if(be != null && be.getNumbBills() > 0)
		{
			int number = be.getNumbBills() / 8;
			return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D + number, 16.0D);
		}
		return DEFAULT_SHAPE;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext ctx) {
		if(getter.getBlockEntity(pos) instanceof BlockEntityBills be) {
			return getShapeFromCount(be);
		}
		return DEFAULT_SHAPE;
	}

	@Override
	public VoxelShape getInteractionShape(BlockState state, BlockGetter getter, BlockPos pos) {
		if(getter.getBlockEntity(pos) instanceof BlockEntityBills be) {
			return getShapeFromCount(be);
		}
		return DEFAULT_SHAPE;
	}
}
