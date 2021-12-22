/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui.container;

import fr.fifoube.blocks.tileentity.TileEntityBlockChanger;
import fr.fifoube.gui.container.type.ContainerTypeRegistery;
import fr.fifoube.items.ItemsRegistery;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerChanger extends Container {

    public TileEntityBlockChanger te;

    public ContainerChanger(int windowId, PlayerInventory playerInv, PacketBuffer extraData) {
        this(windowId, playerInv, extraData.readBlockPos());
    }


    public ContainerChanger(int windowId, PlayerInventory playerInv, BlockPos pos) {
        super(ContainerTypeRegistery.CHANGER_TYPE, windowId);
        TileEntity te = playerInv.player.world.getTileEntity(pos);
        if (te instanceof TileEntityBlockChanger) {
            TileEntityBlockChanger tile = (TileEntityBlockChanger) te;
            IItemHandler inventory = tile.getHandler();
            this.addSlot(new SlotItemHandler(inventory, 0, 56, 16));
            this.addSlot(new SlotItemHandler(inventory, 1, 56, 52));
            this.addSlot(new SlotItemHandler(inventory, 2, 116, 34));
            this.bindPlayerInventory(playerInv);
            this.te = tile;
        }
    }

    private void bindPlayerInventory(PlayerInventory inventoryPlayer) {
        int i;
        for (i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 48 + i * 18 + 35));
            }
        }

        for (i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 106 + 35));
        }
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 1 && index != 0) {
                if (itemstack1.getItem() == ItemsRegistery.ITEM_GOLDNUGGET) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack1.getItem() == ItemsRegistery.ITEM_CREDITCARD) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        World worldIn = playerIn.world;
        if (!worldIn.isRemote) {
            TileEntityBlockChanger te = this.te;
            int x = te.getPos().getX();
            int y = te.getPos().getY();
            int z = te.getPos().getZ();
            if (te.getNumbUse() > 0) {
                te.setNumbUse(0);
                te.setEntityPlayer((PlayerEntity) null);
                te.markDirty();
            }


            ItemStack itemstack = te.getStackInSlot(0).split(1);

            if (!itemstack.isEmpty()) {
                worldIn.addEntity(new ItemEntity(worldIn, x, y, z, itemstack));
            }

            itemstack = te.getStackInSlot(1).split(1);

            if (!itemstack.isEmpty()) {
                worldIn.addEntity(new ItemEntity(worldIn, x, y, z, itemstack));
            }

            itemstack = te.getStackInSlot(2).split(1);

            if (!itemstack.isEmpty()) {
                worldIn.addEntity(new ItemEntity(worldIn, x, y, z, itemstack));
            }
        }
    }

    public TileEntityBlockChanger getTile() {
        return this.te;
    }

}
