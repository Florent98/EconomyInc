/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks;

import fr.fifoube.blocks.tileentity.TileEntityBlockBills;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockBills extends ContainerBlock {

    public static final AxisAlignedBB platform = new AxisAlignedBB(0, 0, 0, 1D, 1 / 16D, 1D);
    public static VoxelShape shapeMain;
    public ItemEntity item;

    public BlockBills(Properties properties) {
        super(properties);
        VoxelShape shape = VoxelShapes.create(platform);
        shapeMain = shape;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityBlockBills();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {

        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityBlockBills) {
            TileEntityBlockBills te = (TileEntityBlockBills) tileentity;
            if (!worldIn.isRemote) {
                int direction = MathHelper.floor((double) (placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
                te.setDirection((byte) direction);
                te.markDirty();
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntityBlockBills) {
                TileEntityBlockBills te = (TileEntityBlockBills) tileentity;
                if (te.getNumbBills() != 64) {
                    String unNa = player.getHeldItem(hand).getTranslationKey();
                    if (te.getNumbBills() == 0) {
                        if (unNa.equals("item.economyinc.item_oneb") || unNa.equals("item.economyinc.item_fiveb") || unNa.equals("item.economyinc.item_tenb") || unNa.equals("item.economyinc.item_twentyb") || unNa.equals("item.economyinc.item_fiftybe") || unNa.equals("item.economyinc.item_hundreedb") || unNa.equals("item.economyinc.item_twohundreedb") || unNa.equals("item.economyinc.item_fivehundreedb")) {
                            checkBillRef(te, worldIn, player, hand);
                            te.addBill();
                            player.getHeldItem(hand).setCount(player.getHeldItemMainhand().getCount() - 1);
                            te.markDirty();
                            return ActionResultType.SUCCESS;
                        }
                    } else {
                        if (te.getBillRef().equals(unNa)) {
                            te.addBill();
                            player.getHeldItem(hand).setCount(player.getHeldItemMainhand().getCount() - 1);
                            te.markDirty();
                            return ActionResultType.SUCCESS;
                        }
                    }

                }
            }
        }
        return ActionResultType.FAIL;

    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        dropBlocks(tileentity, worldIn, pos);
    }

    public void dropBlocks(TileEntity tileentity, World world, BlockPos pos) {

        if (tileentity instanceof TileEntityBlockBills) {
            TileEntityBlockBills te = (TileEntityBlockBills) tileentity;
            ItemEntity itemBase = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(BlocksRegistry.BLOCK_BILLS));
            world.addEntity(itemBase);
            for (int i = 0; i < te.getNumbBills(); i++) {
                Item bill = te.getItemBill();
                if (bill != null) {
                    ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(bill));

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

    public void checkBillRef(TileEntityBlockBills te, IWorld worldIn, PlayerEntity playerIn, Hand hand) {
        if (!worldIn.isRemote()) {
            switch (playerIn.getHeldItem(hand).getTranslationKey()) {
                case "item.economyinc.item_oneb":
                    te.setBillRef("item.economyinc.item_oneb");
                    te.markDirty();
                    break;
                case "item.economyinc.item_fiveb":
                    te.setBillRef("item.economyinc.item_fiveb");
                    te.markDirty();
                    break;
                case "item.economyinc.item_tenb":
                    te.setBillRef("item.economyinc.item_tenb");
                    te.markDirty();
                    break;
                case "item.economyinc.item_twentyb":
                    te.setBillRef("item.economyinc.item_twentyb");
                    te.markDirty();
                    break;
                case "item.economyinc.item_fiftybe":
                    te.setBillRef("item.economyinc.item_fiftybe");
                    te.markDirty();
                    break;
                case "item.economyinc.item_hundreedb":
                    te.setBillRef("item.economyinc.item_hundreedb");
                    te.markDirty();
                    break;
                case "item.economyinc.item_twohundreedb":
                    te.setBillRef("item.economyinc.item_twohundreedb");
                    te.markDirty();
                    break;
                case "item.economyinc.item_fivehundreedb":
                    te.setBillRef("item.economyinc.item_fivehundreedb");
                    te.markDirty();
                    break;
                default:
                    te.setBillRef("item.economyinc.item_zerob");
                    te.markDirty();
                    break;
            }
        }
    }

    public void checkBillRefForDrop(TileEntityBlockBills te, World worldIn, BlockPos pos) {
        if (!worldIn.isRemote()) {
            switch (te.getBillRef()) {
                case "item.economyinc.item_oneb":
                    item = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(ItemsRegistery.ITEM_ONEB));
                    break;
                case "item.economyinc.item_fiveb":
                    item = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(ItemsRegistery.ITEM_FIVEB));
                    break;
                case "item.economyinc.item_tenb":
                    item = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(ItemsRegistery.ITEM_TENB));
                    break;
                case "item.economyinc.item_twentyb":
                    item = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(ItemsRegistery.ITEM_TWENTYB));
                    break;
                case "item.economyinc.item_fiftybe":
                    item = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(ItemsRegistery.ITEM_FIFTYB));
                    break;
                case "item.economyinc.item_hundreedb":
                    item = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(ItemsRegistery.ITEM_HUNDREEDB));
                    break;
                case "item.economyinc.item_twohundreedb":
                    item = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(ItemsRegistery.ITEM_TWOHUNDREEDB));
                    break;
                case "item.economyinc.item_fivehundreedb":
                    item = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(ItemsRegistery.ITEM_FIVEHUNDREEDB));
                    break;
                default:
                    break;
            }
        }
    }
    //TILE ENTITY

    @Override
    public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }


    //RENDER


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return shapeMain;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return shapeMain;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
                                        ISelectionContext context) {
        return shapeMain;
    }
}

