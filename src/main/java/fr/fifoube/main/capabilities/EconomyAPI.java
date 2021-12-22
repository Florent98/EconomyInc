package fr.fifoube.main.capabilities;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.UUID;

public class EconomyAPI {
    private EconomyAPI() {
    }

    public static double getBalance(UUID uuid) {
        ServerPlayerEntity serverPlayerEntity = getPlayerByUUID(uuid);
        if (serverPlayerEntity == null) return -1;

        AtomicDouble atomicDouble = new AtomicDouble(0);
        serverPlayerEntity.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> atomicDouble.set(data.getMoney()));
        return atomicDouble.doubleValue();
    }

    public static void setBalance(UUID uuid, double money) {
        ServerPlayerEntity serverPlayerEntity = getPlayerByUUID(uuid);
        if (serverPlayerEntity == null) return;
        serverPlayerEntity.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
            data.setMoney(money);
        });
    }

    public static ServerPlayerEntity getPlayerByUUID(UUID uuid) {
        MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
        if (currentServer == null) return null;

        PlayerList playerList = currentServer.getPlayerList();
        return playerList.getPlayerByUUID(uuid);
    }
}
