/*******************************************************************************
 *******************************************************************************/

package fr.fifoube.main.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class DefaultMoneyStorage implements Capability.IStorage<IMoney> {

    @Override
    public INBT writeNBT(Capability<IMoney> capability, IMoney instance, Direction side) {

        CompoundNBT nbt = new CompoundNBT();
        nbt.putDouble("money", instance.getMoney());
        return nbt;
    }

    @Override
    public void readNBT(Capability<IMoney> capability, IMoney instance, Direction side, INBT nbt) {

        final CompoundNBT tag = (CompoundNBT) nbt;
        instance.setMoney(tag.getDouble("money"));
    }

}
