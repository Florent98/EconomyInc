package fr.fifoube.main.logger;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MoneyLogFormatter {

    private static final Pattern ANY_UUID = Pattern.compile(
            "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

    private MoneyLogFormatter() {
    }

    public static List<String> resolvePlayerFilters(MinecraftServer server, String playerFilter) {
        List<String> tokens = new ArrayList<>();
        if (playerFilter == null || playerFilter.isBlank()) {
            return tokens;
        }
        String lower = playerFilter.toLowerCase(Locale.ROOT);
        tokens.add(lower);
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            String name = player.getGameProfile().getName();
            if (name.toLowerCase(Locale.ROOT).contains(lower)) {
                tokens.add(name.toLowerCase(Locale.ROOT));
                tokens.add(player.getStringUUID().toLowerCase(Locale.ROOT));
            }
        }
        Optional<GameProfile> exact = server.getProfileCache().get(playerFilter);
        if (exact.isPresent()) {
            tokens.add(exact.get().getName().toLowerCase(Locale.ROOT));
            tokens.add(exact.get().getId().toString().toLowerCase(Locale.ROOT));
        }
        return tokens.stream().distinct().toList();
    }

    public static boolean matchesFilter(String line, List<String> filters) {
        if (filters.isEmpty()) {
            return true;
        }
        String lowerLine = line.toLowerCase(Locale.ROOT);
        return filters.stream().anyMatch(lowerLine::contains);
    }

    public static String formatForDisplay(String line, MinecraftServer server) {
        Matcher matcher = ANY_UUID.matcher(line);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String name = resolveName(server, matcher.group());
            matcher.appendReplacement(buffer, Matcher.quoteReplacement(name));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static String resolveName(MinecraftServer server, String uuidRaw) {
        try {
            UUID uuid = UUID.fromString(uuidRaw);
            ServerPlayer online = server.getPlayerList().getPlayer(uuid);
            if (online != null) {
                return online.getGameProfile().getName();
            }
            Optional<GameProfile> cached = server.getProfileCache().get(uuid);
            if (cached.isPresent()) {
                return cached.get().getName();
            }
        } catch (IllegalArgumentException ignored) {
        }
        return uuidRaw;
    }
}
