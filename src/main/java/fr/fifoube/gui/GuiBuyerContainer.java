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

package fr.fifoube.gui;

import fr.fifoube.blocks.blockentity.BlockEntityBuyer;
import fr.fifoube.gui.container.MenuBuyer;
import fr.fifoube.gui.utilities.GuiText;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class GuiBuyerContainer extends AbstractContainerScreen<MenuBuyer>
{
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/container/gui_vault.png");
	protected int xSize = 176;
	protected int ySize = 168;
	protected int guiLeft;
	protected int guiTop;
	protected BlockEntityBuyer tile;
	protected Inventory inv;


	public GuiBuyerContainer(MenuBuyer menu, Inventory inv, Component name)
	{
		super(menu, inv, name);
		this.tile = menu.getTile();
		this.inv = inv;
	}
	
   
	@Override
	protected void init() {
		super.init();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
	}

	
    protected void actionPerformed(int buttonId)
	{		
		switch (buttonId) {
		case 0:
			break;

		default:
			break;
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		GuiText.draw(this.font, guiGraphics, this.tile.getDisplayName(), 8, 5, 4210752);
		GuiText.draw(this.font, guiGraphics, this.inv.getDisplayName().getString(), 8, this.ySize - 94, 4210752);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		guiGraphics.blit(background, k, l, 0, 0, this.xSize, this.ySize);
	}


}
