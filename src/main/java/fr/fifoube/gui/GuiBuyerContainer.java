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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.fifoube.blocks.blockentity.BlockEntityBuyer;
import fr.fifoube.gui.container.MenuBuyer;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
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
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);
		this.renderTooltip(stack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {

		this.font.draw(matrixStack, this.tile.getDisplayName(), 8.0F, 5, 4210752);
		this.font.draw(matrixStack, this.inv.getDisplayName().getString(), 8.0F, (float)(this.ySize - 94), 4210752);

	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, background);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(stack, k, l, 0, 0, this.xSize, this.ySize);
	}


}