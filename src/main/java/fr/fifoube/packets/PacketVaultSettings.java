/*****************************************************/

package fr.fifoube.packets;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketVaultSettings {

    private BlockPos pos;
    private String playerToAdd;
    private boolean remove;
    private int id;

    public PacketVaultSettings() {

    }

    public PacketVaultSettings(BlockPos pos, String playerToAddOrRemove, boolean remove, int id) {
        this.pos = pos;
        this.playerToAdd = playerToAddOrRemove;
        this.remove = remove;
        this.id = id;
    }

    public static PacketVaultSettings decode(PacketBuffer buf) {
        BlockPos pos = buf.readBlockPos();
        String playerToAdd = buf.readString(32767);
        boolean remove = buf.readBoolean();
        int id = buf.readInt();
        return new PacketVaultSettings(pos, playerToAdd, remove, id);
    }


    public static void encode(PacketVaultSettings packet, PacketBuffer buf) {
        buf.writeBlockPos(packet.pos);
        buf.writeString(packet.playerToAdd);
        buf.writeBoolean(packet.remove);
        buf.writeInt(packet.id);
    }

    public static void handle(PacketVaultSettings packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender(); // GET PLAYER
            TileEntity tile = player.world.getTileEntity(packet.pos);
            if (tile instanceof TileEntityBlockVault2by2) {
                TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2) tile;
                if (!packet.remove) {
                    te.addAllowedPlayers(packet.playerToAdd);
                    te.addToMax();
                    te.markDirty();
                } else {
                    te.getAllowedPlayers().remove(packet.id);
                    te.removeToMax();
                    te.markDirty();
                }
            }
        });
    }
}
