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

package fr.fifoube.packets;

import fr.fifoube.blocks.tileentity.TileEntityBlockBuyer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketBuyerCreation {

    private double cost;
    private BlockPos pos;
    private ItemStack stack;
    private double moneyBudget;

    public PacketBuyerCreation() {
    }

    public PacketBuyerCreation(double cost, BlockPos pos, ItemStack stack, double moneyBudget) {
        this.cost = cost;
        this.pos = pos;
        this.stack = stack;
        this.moneyBudget = moneyBudget;
    }

    public static PacketBuyerCreation decode(PacketBuffer buf) {
        double cost = buf.readDouble();
        BlockPos pos = buf.readBlockPos();
        ItemStack stack = buf.readItemStack();
        double budget = buf.readDouble();
        return new PacketBuyerCreation(cost, pos, stack, budget);
    }


    public static void encode(PacketBuyerCreation packet, PacketBuffer buf) {
        buf.writeDouble(packet.cost);
        buf.writeBlockPos(packet.pos);
        buf.writeItemStack(packet.stack);
        buf.writeDouble(packet.moneyBudget);
    }


    public static void handle(PacketBuyerCreation packet, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            PlayerEntity player = ctx.get().getSender(); // GET PLAYER
            World world = player.world;
            TileEntityBlockBuyer te = (TileEntityBlockBuyer) world.getTileEntity(packet.pos); //WE TAKE THE POSITION OF THE TILE ENTITY TO ADD INFO
            BlockState state = world.getBlockState(packet.pos);
            if (te != null) // CHECK IF PLAYER HAS NOT DESTROYED TILE ENTITY IN THE SHORT TIME OF SENDING PACKET
            {
                if (packet.cost != 0 && packet.stack != ItemStack.EMPTY) {
                    te.setCreated(true);
                    te.setAccount(packet.moneyBudget);
                    te.setCost(packet.cost);
                    te.setItemStackToBuy(packet.stack);
                    te.markDirty();

                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
