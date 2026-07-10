package fr.fifoube.main.economy;

import fr.fifoube.world.saveddata.EconomyLeaderboardSavedData;
import net.minecraft.server.level.ServerPlayer;

public final class EconomyLeaderboardService {

    private EconomyLeaderboardService() {
    }

    public static void update(ServerPlayer player, double balance) {
        EconomyLeaderboardSavedData.get(player.serverLevel())
                .update(player.getUUID(), player.getDisplayName().getString(), balance);
    }
}
