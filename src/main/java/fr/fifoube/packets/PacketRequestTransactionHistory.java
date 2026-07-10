package fr.fifoube.packets;

import fr.fifoube.main.economy.TransactionHistoryService;
import fr.fifoube.main.capabilities.CapabilityMoney;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketRequestTransactionHistory {

    public PacketRequestTransactionHistory() {
    }

    public static void encode(PacketRequestTransactionHistory packet, FriendlyByteBuf buf) {
    }

    public static PacketRequestTransactionHistory decode(FriendlyByteBuf buf) {
        return new PacketRequestTransactionHistory();
    }

    public static void handle(PacketRequestTransactionHistory packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                player.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data ->
                        TransactionHistoryService.syncToClient(player, TransactionHistoryService.copyHistory(data)));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
