package fr.fifoube.world.saveddata;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EconomyLeaderboardSavedData extends SavedData {

    public static final String DATA_NAME = "economyinc_leaderboard";

    public record Entry(UUID uuid, String name, double balance) {
    }

    private final Map<UUID, Entry> entries = new HashMap<>();

    public EconomyLeaderboardSavedData() {
    }

    public EconomyLeaderboardSavedData(CompoundTag tag) {
        ListTag list = tag.getList("entries", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag entryTag = list.getCompound(i);
            UUID uuid = UUID.fromString(entryTag.getString("uuid"));
            entries.put(uuid, new Entry(uuid, entryTag.getString("name"), entryTag.getDouble("balance")));
        }
    }

    public static EconomyLeaderboardSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(EconomyLeaderboardSavedData::new, EconomyLeaderboardSavedData::new, DATA_NAME);
    }

    public void update(UUID uuid, String name, double balance) {
        entries.put(uuid, new Entry(uuid, name, balance));
        setDirty();
    }

    public List<Entry> getTop(int limit) {
        return entries.values().stream()
                .sorted(Comparator.comparingDouble(Entry::balance).reversed())
                .limit(limit)
                .toList();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        for (Entry entry : entries.values()) {
            CompoundTag entryTag = new CompoundTag();
            entryTag.putString("uuid", entry.uuid.toString());
            entryTag.putString("name", entry.name);
            entryTag.putDouble("balance", entry.balance);
            list.add(entryTag);
        }
        tag.put("entries", list);
        return tag;
    }
}
