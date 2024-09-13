package fr.fifoube.main.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerMoneyWrapper implements ICapabilitySerializable<CompoundTag> {

    private MoneyHolder holder = new MoneyHolder();
    private final LazyOptional<MoneyHolder> lazyOptional = LazyOptional.of(() -> this.holder);

	public PlayerMoneyWrapper(MoneyHolder holder) {

        this.holder = holder;

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction direction) {
        return cap == CapabilityMoney.MONEY_CAPABILITY ? (LazyOptional<T>) this.lazyOptional : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {

        return this.holder.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        this.holder.deserializeNBT(nbt);
    }

}