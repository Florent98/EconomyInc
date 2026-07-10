package fr.fifoube.main.commands;

import com.mojang.brigadier.CommandDispatcher;
import fr.fifoube.main.config.ConfigFile;
import fr.fifoube.main.economy.MoneyFormat;
import fr.fifoube.world.saveddata.EconomyLeaderboardSavedData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class CommandBaltop {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("baltop")
                        .requires(src -> src.hasPermission(0))
                        .executes(ctx -> showTop(ctx.getSource()))
        );
        dispatcher.register(
                Commands.literal("fortunes")
                        .requires(src -> src.hasPermission(0))
                        .executes(ctx -> showTop(ctx.getSource()))
        );
    }

    private static int showTop(CommandSourceStack src) {
        if (!(src.getEntity() instanceof ServerPlayer player)) {
            return 0;
        }
        ServerLevel level = player.serverLevel();
        List<EconomyLeaderboardSavedData.Entry> top = EconomyLeaderboardSavedData.get(level).getTop(ConfigFile.baltopSize);
        if (top.isEmpty()) {
            src.sendSuccess(() -> Component.translatable("commands.baltop.empty"), false);
            return 1;
        }
        src.sendSuccess(() -> Component.translatable("commands.baltop.header"), false);
        int rank = 1;
        for (EconomyLeaderboardSavedData.Entry entry : top) {
            int currentRank = rank++;
            src.sendSuccess(() -> Component.translatable("commands.baltop.entry", currentRank, entry.name(), MoneyFormat.display(entry.balance())), false);
        }
        return 1;
    }
}
