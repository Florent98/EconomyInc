/**
 * Copyright 2020, Turrioni Florent, All rights reserved.
 * <p>
 * This program is copyrighted for all the files and code
 * included in this program. No reuse, modification or
 * reselling is authorized without any legal document
 * approved by the owner*.
 * <p>
 * *Owner : Turrioni Florent resident in Belgium and
 * contactable at florent_turrioni@hotmail.com
 */

package fr.fifoube.blocks;

import fr.fifoube.blocks.tileentity.TileEntityBlockBuyer;
import fr.fifoube.gui.ClientGuiScreen;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;

public class BlockBuyer extends ContainerBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final TranslationTextComponent NAME = new TranslationTextComponent("container.buyer");

    public BlockBuyer(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(POWERED, false));

    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityBlockBuyer();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }


    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
                                             Hand handIn, BlockRayTraceResult hit) {

        if (!worldIn.isRemote) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntityBlockBuyer) {
                TileEntityBlockBuyer te = (TileEntityBlockBuyer) tileentity;
                if (!te.isCreated()) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, te, buf -> buf.writeBlockPos(pos));
                } else {
                    if (player.getHeldItemMainhand().getItem() == ItemsRegistery.ITEM_REMOVER && te.getOwner().equals(player.getUniqueID())) {
                        NetworkHooks.openGui((ServerPlayerEntity) player, te, buf -> buf.writeBlockPos(pos));
                    } else {
                        ClientGuiScreen.openGui(3, te);
                    }
                }
            }

        }
        return ActionResultType.FAIL;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {

        worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing()), 2);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityBlockBuyer) {
            TileEntityBlockBuyer te = (TileEntityBlockBuyer) tileentity;
            te.setOwnerName(placer.getDisplayName().getString());
            te.setOwner(placer.getUniqueID());
            te.setFacing(state.toString().substring(38, 43));
        }
    }


    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {

        this.setDefaultFacing(worldIn, pos, state);
        for (Direction direction : Direction.values()) {
            worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
        }
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {

        if (!isMoving) {
            for (Direction direction : Direction.values()) {
                worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
            }

        }
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
            } else if (dir == Direction.SOUTH && blockstate1.isCollisionShapeLargerThanFullBlock() && !blockstate.isSolid()) {
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
        builder.add(FACING, POWERED);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    //REDSTONE HANDLING

    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {

        return state.get(POWERED) ? 15 : 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {

        super.tick(state, worldIn, pos, rand);
        if (state.get(POWERED)) {

            worldIn.setBlockState(pos, state.with(POWERED, Boolean.valueOf(false)), 3);
            this.updateNeighbors(state, worldIn, pos);

        }
    }


    public void scheduleTick(BlockState state, World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, state.with(POWERED, Boolean.valueOf(true)), 3);
        this.updateNeighbors(state, worldIn, pos);
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, 20);
    }

    private void updateNeighbors(BlockState state, World worldIn, BlockPos pos) {
        worldIn.notifyNeighborsOfStateChange(pos, this);
        worldIn.notifyNeighborsOfStateChange(pos.offset(state.get(FACING).getOpposite()), this);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                   boolean willHarvest, FluidState fluid) {

        world.removeTileEntity(pos);
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

}
