package fr.fifoube.main.economy;

import fr.fifoube.packets.PacketEconomyConfigSync;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public final class EconomyConfigService {

    private EconomyConfigService() {
    }

    public static void syncToClient(ServerPlayer player) {
        if (player.connection == null) {
            return;
        }
        PacketsRegistery.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> player),
                new PacketEconomyConfigSync(EconomyClientConfig.fromServer())
        );
    }
}
