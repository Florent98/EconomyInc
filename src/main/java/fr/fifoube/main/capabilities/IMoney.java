/** 
 *  Copyright 2020, Turrioni Florent, All rights reserved.
 *  
 * 	This program is copyrighted for all the files and code 
 * 	included in this program. No reuse, modification or 
 * 	reselling is authorized without any legal document 
 *  approved by the owner*.
 * 
 * 	*Owner : Turrioni Florent resident in Belgium and 
 *  contactable at florent_turrioni@hotmail.com
 *  
 * */

package fr.fifoube.main.capabilities;

import net.minecraft.nbt.CompoundTag;

public interface IMoney {

	double getMoney();
	void setMoney(double money);
	void addMoney(double moneyToAdd);
	CompoundTag serializeNBT();
	void deserializeNBT(CompoundTag nbt);
}
