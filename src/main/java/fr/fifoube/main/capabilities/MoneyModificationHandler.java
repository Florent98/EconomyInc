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

import fr.fifoube.main.ModEconomyInc;

import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModEconomyInc.MOD_ID)
public class MoneyModificationHandler {

	@SubscribeEvent
	public static void onDimensionTravel(PlayerChangedDimensionEvent event)
	{
		if(!event.getPlayer().world.isRemote)
			event.getPlayer().getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data -> { 
				data.setMoney(data.getMoney());
			});
	}
	
	
}
