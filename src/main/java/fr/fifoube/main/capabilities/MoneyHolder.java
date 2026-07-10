package fr.fifoube.main.capabilities;

import fr.fifoube.main.config.ConfigFile;
import fr.fifoube.main.economy.MoneyFormat;
import fr.fifoube.main.economy.TransactionRecord;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoneyHolder implements IMoney {

    private double money = 0;
    private final List<TransactionRecord> transactionHistory = new ArrayList<>();

    @Override
    public double getMoney() {
        return this.money;
    }

    @Override
    public void setMoney(double money) {
        this.money = MoneyFormat.toWhole(money);
    }

    @Override
    public void addMoney(double moneyToAdd) {
        this.money = MoneyFormat.toWhole(this.money + moneyToAdd);
    }

    public List<TransactionRecord> getTransactionHistory() {
        return Collections.unmodifiableList(this.transactionHistory);
    }

    public void addTransaction(TransactionRecord record) {
        this.transactionHistory.add(0, record);
        while (this.transactionHistory.size() > ConfigFile.maxTransactionHistory) {
            this.transactionHistory.remove(this.transactionHistory.size() - 1);
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("money", this.money);
        ListTag historyTag = new ListTag();
        for (TransactionRecord record : this.transactionHistory) {
            historyTag.add(record.toNbt());
        }
        tag.put("transactionHistory", historyTag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.money = MoneyFormat.toWhole(nbt.getDouble("money"));
        this.transactionHistory.clear();
        if (nbt.contains("transactionHistory", Tag.TAG_LIST)) {
            ListTag historyTag = nbt.getList("transactionHistory", Tag.TAG_COMPOUND);
            for (int i = 0; i < historyTag.size(); i++) {
                this.transactionHistory.add(TransactionRecord.fromNbt(historyTag.getCompound(i)));
            }
        }
    }
}
