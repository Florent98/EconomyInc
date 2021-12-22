/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks.tileentity;

import fr.fifoube.gui.container.ContainerSeller;
import fr.fifoube.packets.PacketRefillSeller;
import fr.fifoube.packets.PacketsRegistery;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

import java.util.UUID;

public class TileEntityBlockSeller extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    private static final TranslationTextComponent NAME = new TranslationTextComponent("container.seller");
    ItemStackHandler inventory_seller = new ItemStackHandler(1); //STACK HANDLER FOR ONE SLOT = 0
    private ITextComponent customName;
    private UUID owner;
    private String ownerName = "";
    private String item = "";
    private String facing = "";
    private double funds_total;
    private double cost;
    private int amount;
    private int timer;
    private boolean autorefill;
    private boolean admin;
    private boolean created;

    public TileEntityBlockSeller() {
        this(TileEntityRegistery.TILE_SELLER);
    }

    public TileEntityBlockSeller(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public ItemStackHandler getHandler() {
        return inventory_seller;
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


    public ItemStack getStackInSlot(int slot) {
        return inventory_seller.getStackInSlot(slot);
    }

    public ItemStack removeStackInSlot(int slot) {
        return inventory_seller.getStackInSlot(slot).split(1);
    }

    public String getFacing() {
        return this.facing;
    }

    public void setFacing(String face) {
        this.facing = face;
    }

    public boolean getAdmin() {
        return this.admin;
    }

    public void setAdmin(Boolean adminS) {
        this.admin = adminS;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public void setOwner(UUID uuid) {
        this.owner = uuid;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String stringName) {
        this.ownerName = stringName;
    }

    public double getCost() {
        return this.cost;
    }

    public void setCost(double costS) {
        this.cost = costS;
    }

    public double getFundsTotal() {
        return this.funds_total;
    }

    public void setFundsTotal(double fundsS) {
        this.funds_total = fundsS;
    }

    public boolean getCreated() {
        return this.created;
    }

    public void setCreated(boolean createdS) {
        this.created = createdS;
    }

    public String getItem() {
        return this.inventory_seller.getStackInSlot(0).getDisplayName().getString();
    }

    public void setItem(String itemS) {
        this.item = itemS;
    }

    public int getAmount() {
        return this.inventory_seller.getStackInSlot(0).getCount();
    }

    public int getTime() {
        return this.timer;
    }

    public void setTime(int time) {
        this.timer = time;
    }

    public boolean getAutoRefill() {
        return this.autorefill;
    }

    public void setAutoRefill(boolean restock) {
        this.autorefill = restock;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inventory", inventory_seller.serializeNBT());
        if (this.owner != null) {
            compound.putUniqueId("ownerUUID", this.owner);
        }
        compound.putString("ownerName", this.ownerName);
        compound.putDouble("cost", this.cost);
        compound.putInt("amount", this.amount);
        compound.putString("item", this.item);
        compound.putDouble("funds_total", this.funds_total);
        compound.putBoolean("created", this.created);
        compound.putBoolean("admin", this.admin);
        compound.putString("facing", this.facing);
        if (this.getDisplayName() != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(this.getDisplayName()));
        }
        compound.putInt("timer", this.timer);
        compound.putBoolean("restock", this.autorefill);
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {

        super.read(state, compound);
        inventory_seller.deserializeNBT(compound.getCompound("inventory"));
        this.owner = compound.getUniqueId("ownerUUID");
        this.ownerName = compound.getString("ownerName");
        this.cost = compound.getDouble("cost");
        this.amount = compound.getInt("amount");
        this.item = compound.getString("item");
        this.funds_total = compound.getDouble("funds_total");
        this.created = compound.getBoolean("created");
        this.admin = compound.getBoolean("admin");
        this.facing = compound.getString("facing");
        if (compound.contains("CustomName", Constants.NBT.TAG_STRING)) {
            this.customName = ITextComponent.Serializer.getComponentFromJson(compound.getString("CustomName"));
        }
        this.timer = compound.getInt("timer");
        this.autorefill = compound.getBoolean("restock");
    }


    @Override
    public void markDirty() {
        BlockState state = this.world.getBlockState(getPos());
        this.world.notifyBlockUpdate(getPos(), state, state, 3);
    }

    @Override
    public Container createMenu(int id, PlayerInventory inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerSeller(id, inventoryPlayer, getPos());
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

    }

    public void refill() {
        BlockPos pos = getPos().down();
        if (getWorld().getTileEntity(pos) instanceof TileEntityBlockVault) {
            TileEntityBlockVault te = (TileEntityBlockVault) getWorld().getTileEntity(pos);
            if (te.getOwner().equals(getOwner())) {
                ItemStack stack = getStackInSlot(0);
                if (stack.getCount() != stack.getMaxStackSize()) {
                    PacketsRegistery.CHANNEL.sendToServer(new PacketRefillSeller(getPos(), getPos().down(), stack));
                }
            }
        }
    }

}
