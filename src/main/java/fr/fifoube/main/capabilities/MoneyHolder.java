package fr.fifoube.main.capabilities;

import net.minecraft.nbt.CompoundTag;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyHolder implements IMoney {

    private double money = 0;

    @Override
    public double getMoney() {

        return this.money;
    }

    @Override
    public void setMoney(double money) {

        this.money = round(money, 2);

    }

    @Override
    public void addMoney(double moneyToAdd) {

        this.money += moneyToAdd;

    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public CompoundTag serializeNBT() {

        CompoundTag tag = new CompoundTag();
        tag.putDouble("money", this.money);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        this.money = nbt.getDouble("money");

    }

}
