package fr.fifoube.main.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import fr.fifoube.main.logger.MoneyLogFormatter;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CommandMoneyLog {

    private static final Path LOG_PATH = Paths.get("logs/economyinc-money.log");

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("moneylog")
                        .requires(src -> src.hasPermission(3))
                        .then(Commands.literal("export")
                                .executes(ctx -> export(ctx.getSource(), null, 50))
                                .then(Commands.argument("player", StringArgumentType.string())
                                        .executes(ctx -> export(ctx.getSource(), StringArgumentType.getString(ctx, "player"), 50))
                                        .then(Commands.argument("lines", IntegerArgumentType.integer(1, 500))
                                                .executes(ctx -> export(
                                                        ctx.getSource(),
                                                        StringArgumentType.getString(ctx, "player"),
                                                        IntegerArgumentType.getInteger(ctx, "lines"))))))
        );
    }

    private static int export(CommandSourceStack src, String playerFilter, int maxLines) {
        if (!Files.exists(LOG_PATH)) {
            src.sendFailure(Component.translatable("commands.moneylog.missing"));
            return 0;
        }
        MinecraftServer server = src.getServer();
        if (server == null) {
            src.sendFailure(Component.translatable("commands.moneylog.error"));
            return 0;
        }
        try {
            List<String> filters = MoneyLogFormatter.resolvePlayerFilters(server, playerFilter);
            List<String> lines = Files.readAllLines(LOG_PATH, StandardCharsets.UTF_8);
            List<String> filtered = lines.stream()
                    .filter(line -> MoneyLogFormatter.matchesFilter(line, filters))
                    .map(line -> MoneyLogFormatter.formatForDisplay(line, server))
                    .collect(Collectors.toList());
            int from = Math.max(0, filtered.size() - maxLines);
            List<String> slice = filtered.subList(from, filtered.size());
            if (slice.isEmpty()) {
                src.sendFailure(Component.translatable("commands.moneylog.noMatch"));
                return 0;
            }
            src.sendSuccess(() -> Component.translatable("commands.moneylog.header", slice.size())
                    .withStyle(ChatFormatting.GOLD), false);
            for (String line : slice) {
                String entry = line;
                src.sendSuccess(() -> Component.literal(entry).withStyle(ChatFormatting.GRAY), false);
            }
            return slice.size();
        } catch (IOException e) {
            src.sendFailure(Component.translatable("commands.moneylog.error"));
            return 0;
        }
    }
}
