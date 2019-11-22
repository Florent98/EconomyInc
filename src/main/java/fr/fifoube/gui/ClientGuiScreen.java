package fr.fifoube.gui;

import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;


public class ClientGuiScreen {

	
	public static void openGui(int id, TileEntity te) {
		switch (id) {
		case 0:
			Minecraft.getInstance().displayGuiScreen(new GuiCreditCard());
			break;
		case 1:
			Minecraft.getInstance().displayGuiScreen(new GuiSellerBuy((TileEntityBlockSeller) te));
			break;
		default:
			break;
		}
	}
}
