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
		nbt.putBoolean("linked", instance.getLinked());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IMoney> capability, IMoney instance, Direction side, INBT nbt) {
		
		final CompoundNBT tag = (CompoundNBT)nbt;
		System.out.println(tag.getDouble("money"));
		System.out.println(tag.getBoolean("linked"));
		instance.setMoney(tag.getDouble("money"));
		instance.setLinked(tag.getBoolean("linked"));
	}

}
