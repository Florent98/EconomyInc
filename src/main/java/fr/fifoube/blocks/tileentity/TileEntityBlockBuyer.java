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

package fr.fifoube.blocks.tileentity;

import fr.fifoube.gui.container.ContainerBuyer;
import fr.fifoube.gui.container.ContainerBuyerCreation;
import fr.fifoube.items.IValue;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.UUID;

public class TileEntityBlockBuyer extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    private static final TranslationTextComponent NAME = new TranslationTextComponent("container.buyer");
    ItemStackHandler inventory_buyer = new ItemStackHandler(27);
    ItemStackHandler money_handler = new ItemStackHandler(1);
    private final ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
    private ITextComponent customName;
    private String ownerName = "";
    private UUID owner;
    private String facing = "";
    private int timer;
    private double cost;
    private boolean admin;
    private boolean isCreated;
    private double moneyInBlock = 0;
    private ItemStack stackToBuy = ItemStack.EMPTY;

    public TileEntityBlockBuyer() {
        this(TileEntityRegistery.TILE_BUYER);
    }

    public TileEntityBlockBuyer(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public ItemStackHandler getInventoryHandler() {
        return inventory_buyer;
    }

    public ItemStackHandler getMoneyHandler() {
        return money_handler;
    }

    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 1, this.getUpdateTag());
    }

    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        deserializeNBT(pkt.getNbtCompound());
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String name) {
        this.ownerName = name;
    }

    public ItemStack getStackInSlot(int slot) {
        return inventory_buyer.getStackInSlot(slot);
    }

    public ItemStack removeStackInSlot(int slot) {
        return inventory_buyer.getStackInSlot(slot).split(1);
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getFacing() {
        return this.facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public int getTimer() {
        return this.timer;
    }

    public void setTime(int timer) {
        this.timer = timer;
    }


    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean isCreated) {
        this.isCreated = isCreated;
    }

    public void addToAccount(double value) {
        this.moneyInBlock = this.moneyInBlock + value;
    }

    public double getAccountMoney() {
        return moneyInBlock;
    }

    public void setAccount(double value) {
        this.moneyInBlock = value;
    }

    public ArrayList<ItemStack> getStackList() {
        return stackList;
    }

    public ItemStack getItemStackToBuy() {
        return this.stackToBuy;
    }

    public void setItemStackToBuy(ItemStack stack) {
        this.stackToBuy = stack;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inventory_buyer", this.inventory_buyer.serializeNBT());
        compound.put("money_handler", this.money_handler.serializeNBT());
        compound.putString("ownerName", this.ownerName);
        if (this.owner != null) {
            compound.putUniqueId("ownerUUID", this.owner);
        }
        compound.putString("facing", this.facing);
        compound.putInt("timer", this.timer);
        compound.putDouble("cost", this.cost);
        compound.putBoolean("adminMode", this.admin);
        compound.putBoolean("isCreated", this.isCreated);
        compound.putDouble("moneyInBlock", this.moneyInBlock);
        compound.put("stackToBuy", this.getItemStackToBuy().write(new CompoundNBT()));
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {

        super.read(state, compound);
        this.inventory_buyer.deserializeNBT(compound.getCompound("inventory_buyer"));
        this.money_handler.deserializeNBT(compound.getCompound("money_handler"));
        this.ownerName = compound.getString("ownerName");
        this.owner = compound.getUniqueId("ownerUUID");
        this.facing = compound.getString("facing");
        this.timer = compound.getInt("timer");
        this.cost = compound.getDouble("cost");
        this.admin = compound.getBoolean("adminMode");
        this.isCreated = compound.getBoolean("isCreated");
        this.moneyInBlock = compound.getDouble("moneyInBlock");
        this.stackToBuy = ItemStack.read(compound.getCompound("stackToBuy"));
    }

    @Override
    public void markDirty() {
        BlockState state = this.world.getBlockState(getPos());
        this.world.notifyBlockUpdate(getPos(), state, state, 3);
    }

    @Override
    public Container createMenu(int id, PlayerInventory inventoryPlayer, PlayerEntity playerEntity) {

        return isCreated ? new ContainerBuyer(id, inventoryPlayer, getPos()) : new ContainerBuyerCreation(id, inventoryPlayer, getPos());
    }


    @Override
    public ITextComponent getDisplayName() {
        return NAME;
    }

    @Override
    public void tick() {

        if (this.timer != 0) {
            --this.timer;
        }

        ItemStack stack = this.money_handler.getStackInSlot(0);

        if (stack != ItemStack.EMPTY && stack.getItem() instanceof IValue) {
            IValue value = (IValue) stack.getItem();
            addToAccount(value.getValue() * stack.getCount());
            stackList.add(stack.copy());
            stack.split(stack.getCount());
        }

    }

}
