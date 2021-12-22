/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks;

import fr.fifoube.blocks.tileentity.TileEntityBlockChanger;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
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
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {

        boolean canOpen = true;
        if (!worldIn.isRemote) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntityBlockChanger) {
                TileEntityBlockChanger te = (TileEntityBlockChanger) tileentity;
                if (te != null) {
                    if (te.getNumbUse() >= 1) {
                        canOpen = false;

                    }
                    if (canOpen) {
                        NetworkHooks.openGui((ServerPlayerEntity) playerIn, te, buf -> buf.writeBlockPos(pos));
                        te.setNumbUse(1);
                        te.setEntityPlayer(playerIn);
                        te.markDirty();
                        return ActionResultType.SUCCESS;

                    } else {
                        playerIn.sendStatusMessage(new TranslationTextComponent("title.alreadyUsed"), true);
                        return ActionResultType.FAIL;
                    }
                }
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {

        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityBlockChanger) {
            TileEntityBlockChanger te = (TileEntityBlockChanger) tileentity;
            ItemStack stack = player.getHeldItemMainhand();

            if (te != null) {
                if (stack.isItemEqual(new ItemStack(ItemsRegistery.ITEM_REMOVER))) {
                    if (te.getNumbUse() < 1) {
                        worldIn.destroyBlock(pos, true);
                        worldIn.removeTileEntity(pos);

                        //DROPPING ITEMS
                        ItemEntity itemBase = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(BlocksRegistry.BLOCK_CHANGER));
                        worldIn.addEntity(itemBase);
                        for (int i = 0; i < te.getHandler().getSlots(); i++) {
                            Item toDrop = te.getStackInSlot(i).getItem();
                            if (toDrop != null && toDrop != Items.AIR) {
                                ItemEntity item = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(toDrop));

                                float multiplier = 0.1f;
                                float motionX = worldIn.rand.nextFloat() - 0.5F;
                                float motionY = worldIn.rand.nextFloat() - 0.5F;
                                float motionZ = worldIn.rand.nextFloat() - 0.5F;

                                item.lastTickPosX = motionX * multiplier;
                                item.lastTickPosY = motionY * multiplier;
                                item.lastTickPosZ = motionZ * multiplier;

                                worldIn.addEntity(item);
                            }
                        }
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
        if (!worldIn.isRemote) {
            BlockState blockstate = worldIn.getBlockState(pos.north());
            BlockState blockstate1 = worldIn.getBlockState(pos.south());
            BlockState blockstate2 = worldIn.getBlockState(pos.west());
            BlockState blockstate3 = worldIn.getBlockState(pos.east());
            Direction dir = state.get(FACING);

            if (dir == Direction.NORTH && blockstate.isCollisionShapeLargerThanFullBlock() && !blockstate1.isCollisionShapeLargerThanFullBlock()) {
                dir = Direction.SOUTH;
            } else if (dir == Direction.SOUTH && blockstate1.isCollisionShapeLargerThanFullBlock() && !blockstate.isCollisionShapeLargerThanFullBlock()) {
                dir = Direction.NORTH;
            } else if (dir == Direction.WEST && blockstate2.isCollisionShapeLargerThanFullBlock() && !blockstate3.isCollisionShapeLargerThanFullBlock()) {
                dir = Direction.EAST;
            } else if (dir == Direction.EAST && blockstate3.isCollisionShapeLargerThanFullBlock() && !blockstate2.isCollisionShapeLargerThanFullBlock()) {
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
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


}
