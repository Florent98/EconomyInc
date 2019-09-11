package fr.fifoube.main.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class MoneyWrapper implements ICapabilitySerializable<INBT> {

	private IMoney holder;
    private final LazyOptional<IMoney> lazyOptional = LazyOptional.of(() -> this.holder);
    
    public MoneyWrapper(IMoney holder) {
        this.holder = holder;
    }
    
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return CapabilityMoney.MONEY_CAPABILITY.orEmpty(cap, lazyOptional);
	}

	@Override
	public INBT serializeNBT() {
		 return CapabilityMoney.MONEY_CAPABILITY.writeNBT(this.holder, null);	
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		CapabilityMoney.MONEY_CAPABILITY.readNBT(holder, null, nbt);
	}

}
