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

package fr.fifoube.gui;

import fr.fifoube.blocks.tileentity.TileEntityBlockBuyer;
import fr.fifoube.main.capabilities.CapabilityMoney;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.function.Supplier;

public class PacketBuyerChange {

    private BlockPos pos;
    private boolean isOneSell;


    public PacketBuyerChange() {
    }

    public PacketBuyerChange(BlockPos pos, boolean isOneSell) {
        this.pos = pos;
        this.isOneSell = isOneSell;
    }

    public static PacketBuyerChange decode(PacketBuffer buf) {
        BlockPos pos = buf.readBlockPos();
        boolean isOneSell = buf.readBoolean();
        return new PacketBuyerChange(pos, isOneSell);
    }


    public static void encode(PacketBuyerChange packet, PacketBuffer buf) {
        buf.writeBlockPos(packet.pos);
        buf.writeBoolean(packet.isOneSell);
    }


    public static void handle(PacketBuyerChange packet, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            PlayerEntity player = ctx.get().getSender(); // GET PLAYER
            World world = player.world;
            TileEntityBlockBuyer te = (TileEntityBlockBuyer) world.getTileEntity(packet.pos); //WE TAKE THE POSITION OF THE TILE ENTITY TO ADD INFO
            if (te != null) // CHECK IF PLAYER HAS NOT DESTROYED TILE ENTITY IN THE SHORT TIME OF SENDING PACKET
            {
                int quantityFound = 0;
                ArrayList<Integer> indexes = new ArrayList<Integer>();
                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                    if (player.inventory.getStackInSlot(i).getItem() == te.getItemStackToBuy().getItem()) {
                        quantityFound += player.inventory.getStackInSlot(i).getCount();
                        indexes.add(i);
                    }
                }

                if (quantityFound != 0) {
                    int multiplier = packet.isOneSell ? 1 : quantityFound;
                    double moneyNeededAndToGive = te.getCost() * multiplier;
                    if (moneyNeededAndToGive <= te.getAccountMoney()) {
                        if (packet.isOneSell) {
                            player.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> {

                                ItemStack stackToInsert = player.inventory.getStackInSlot(indexes.get(0)).split(1);
                                data.setMoney(data.getMoney() + moneyNeededAndToGive);
                                te.setAccount(te.getAccountMoney() - moneyNeededAndToGive);
                                ItemStack stack = ItemHandlerHelper.insertItem(te.getInventoryHandler(), stackToInsert, false);
                            });
                        } else {

                        }
                    } else {
                        System.out.println("pas asser dargent");
                    }
                } else {
                    System.out.println("pas trouver d'item correspondant");
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
