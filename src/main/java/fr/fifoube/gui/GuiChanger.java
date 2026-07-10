/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import fr.fifoube.blocks.blockentity.BlockEntityChanger;
import fr.fifoube.gui.container.MenuChanger;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.packets.PacketChangerUpdate;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiChanger extends AbstractContainerScreen<MenuChanger> {


	private BlockEntityChanger tile;
	private MenuChanger menu;
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/container/gui_changer.png");
	protected int xSize = 176;
	protected int ySize = 168;
	protected int guiLeft;
	protected int guiTop;
	
	public GuiChanger(MenuChanger menu, Inventory inv, Component comp) {
		super(menu, inv, comp);
		this.menu = menu;
		this.tile = menu.getBlockEntity();
	}
	
	@Override
	protected void containerTick() {
		super.containerTick();
	}
	
	@Override
	protected void init() {
		super.init();
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, x, y, partialTicks);
        this.renderTooltip(guiGraphics, x, y);
	}
	

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float mouseX, int mouseY, int partialTicks) {
		int k = (this.width - this.xSize) / 2; 
		int l = (this.height - this.ySize) / 2;
		guiGraphics.blit(background, k, l, 0, 0, this.xSize, this.ySize); 
		if(tile != null)
		{
			float display = (this.menu.getTimePassed() / Float.valueOf(this.menu.getProcessTime())) * 56;
			if(this.menu.isProcessing() == 1)
			{	   
				guiGraphics.blit(background, k + 55, l + 34, 176, 0, Math.round(display), this.ySize);
			}
		}					
	}
	

	@Override
	public void onClose() {
		super.onClose();
		tile.setNumbUse(0);
		PacketsRegistery.CHANNEL.sendToServer(new PacketChangerUpdate(tile.getBlockPos()));
	}
}
