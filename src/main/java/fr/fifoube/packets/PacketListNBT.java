/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.packets;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault;
import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketListNBT {

    private String names;
    private int x;
    private int y;
    private int z;
    private boolean isBlock2x2;
    private String addrem;
    private int index;


    public PacketListNBT() {

    }

    public PacketListNBT(String names, int x, int y, int z, boolean isBlock2x2, String addrem, int index) {
        this.names = names;
        this.x = x;
        this.y = y;
        this.z = z;
        this.isBlock2x2 = isBlock2x2;
        this.addrem = addrem;
        this.index = index;
    }

    public static PacketListNBT decode(PacketBuffer buf) {
        String names = buf.readString(32767);
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        boolean isBlock2x2 = buf.readBoolean();
        String addrem = buf.readString(32767);
        int index = buf.readInt();
        return new PacketListNBT(names, x, y, z, isBlock2x2, addrem, index);
    }


    public static void encode(PacketListNBT packet, PacketBuffer buf) {
        buf.writeString(packet.names);
        buf.writeInt(packet.x);
        buf.writeInt(packet.y);
        buf.writeInt(packet.z);
        buf.writeBoolean(packet.isBlock2x2);
        buf.writeString(packet.addrem);
        buf.writeInt(packet.index);
    }


    public static void handle(PacketListNBT packet, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            PlayerEntity player = ctx.get().getSender(); // GET PLAYER
            World worldIn = player.world; // GET WORLD
            if (packet.addrem.equals("add")) {
                if (packet.isBlock2x2) {
                    TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2) worldIn.getTileEntity(new BlockPos(packet.x, packet.y, packet.z));
                    if (te != null) {
                        te.addAllowedPlayers(packet.names);
                        te.addToMax();
                        te.markDirty();

                    }
                } else {
                    TileEntityBlockVault te = (TileEntityBlockVault) worldIn.getTileEntity(new BlockPos(packet.x, packet.y, packet.z));
                    if (te != null) {
                        te.markDirty();
                    }
                }
            } else if (packet.addrem.equals("remove")) {
                if (packet.isBlock2x2) {
                    TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2) worldIn.getTileEntity(new BlockPos(packet.x, packet.y, packet.z));
                    if (te != null) {
                        te.getAllowedPlayers().remove(packet.index);
                        te.removeToMax();
                        te.markDirty();
                    }
                } else {
                    TileEntityBlockVault te = (TileEntityBlockVault) worldIn.getTileEntity(new BlockPos(packet.x, packet.y, packet.z));
                    if (te != null) {
                        te.markDirty();
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
