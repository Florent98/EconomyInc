package fr.fifoube.main.economy;

import net.minecraft.nbt.CompoundTag;

public record TransactionRecord(TransactionType type, double amount, long timestamp, String detail) {

    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", type.getId());
        tag.putDouble("amount", amount);
        tag.putLong("timestamp", timestamp);
        tag.putString("detail", detail == null ? "" : detail);
        return tag;
    }

    public static TransactionRecord fromNbt(CompoundTag tag) {
        return new TransactionRecord(
                TransactionType.fromId(tag.getString("type")),
                tag.getDouble("amount"),
                tag.getLong("timestamp"),
                tag.getString("detail")
        );
    }
}
