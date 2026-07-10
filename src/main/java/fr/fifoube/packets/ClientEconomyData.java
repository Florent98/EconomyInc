package fr.fifoube.packets;

import fr.fifoube.main.economy.EconomyClientConfig;
import fr.fifoube.main.economy.TransactionRecord;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public final class ClientEconomyData {

    private static List<TransactionRecord> history = List.of();
    private static EconomyClientConfig config = EconomyClientConfig.defaults();

    private ClientEconomyData() {
    }

    public static void setHistory(List<TransactionRecord> records) {
        history = new ArrayList<>(records);
    }

    public static List<TransactionRecord> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public static void setConfig(EconomyClientConfig economyConfig) {
        config = economyConfig;
    }

    public static EconomyClientConfig getConfig() {
        return config;
    }
}
