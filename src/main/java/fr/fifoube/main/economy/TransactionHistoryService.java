package fr.fifoube.main.economy;

import fr.fifoube.main.capabilities.IMoney;
import fr.fifoube.main.capabilities.MoneyHolder;
import fr.fifoube.main.config.ConfigFile;
import fr.fifoube.packets.PacketTransactionHistory;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public final class TransactionHistoryService {

    private TransactionHistoryService() {
    }

    public static void record(ServerPlayer player, IMoney account, TransactionType type, double amount, String detail) {
        if (!(account instanceof MoneyHolder holder)) {
            return;
        }
        holder.addTransaction(new TransactionRecord(type, amount, System.currentTimeMillis(), detail));
        syncToClient(player, holder.getTransactionHistory());
    }

    public static void syncToClient(ServerPlayer player, List<TransactionRecord> history) {
        if (player.connection == null) {
            return;
        }
        PacketsRegistery.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new PacketTransactionHistory(history));
    }

    public static List<TransactionRecord> copyHistory(IMoney account) {
        if (account instanceof MoneyHolder holder) {
            return new ArrayList<>(holder.getTransactionHistory());
        }
        return List.of();
    }
}
