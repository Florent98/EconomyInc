/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import fr.fifoube.blocks.blockentity.BlockEntityBuyer;
import fr.fifoube.blocks.blockentity.BlockEntityVault2by2;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientGuiScreen {

	
	public static void openGui(int id, BlockEntity te) {

		Minecraft mc = Minecraft.getInstance();
		switch (id) {
		case 0:
			mc.setScreen(new GuiCreditCard());
			break;
		case 1:
			break;
		case 2:
			mc.setScreen(new GuiVaultSettings2by2((BlockEntityVault2by2)te));
			break;
		case 3:
			mc.setScreen(new GuiBuyerSell((BlockEntityBuyer)te));
		default:
			break;
		}
	}
}
