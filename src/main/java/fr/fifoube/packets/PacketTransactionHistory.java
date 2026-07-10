package fr.fifoube.packets;

import fr.fifoube.main.economy.TransactionRecord;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PacketTransactionHistory {

    private final List<TransactionRecord> history;

    public PacketTransactionHistory(List<TransactionRecord> history) {
        this.history = history;
    }

    public PacketTransactionHistory() {
        this.history = List.of();
    }

    public static void encode(PacketTransactionHistory packet, FriendlyByteBuf buf) {
        buf.writeVarInt(packet.history.size());
        for (TransactionRecord record : packet.history) {
            buf.writeNbt(record.toNbt());
        }
    }

    public static PacketTransactionHistory decode(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        List<TransactionRecord> history = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            history.add(TransactionRecord.fromNbt(buf.readNbt()));
        }
        return new PacketTransactionHistory(history);
    }

    public static void handle(PacketTransactionHistory packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide().isClient()) {
                handleClient(packet);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleClient(PacketTransactionHistory packet) {
        ClientEconomyData.setHistory(packet.history);
    }
}
