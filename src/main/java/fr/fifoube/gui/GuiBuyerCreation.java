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
import fr.fifoube.gui.container.MenuBuyerCreation;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.packets.PacketBuyerCreation;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class GuiBuyerCreation extends AbstractContainerScreen<MenuBuyerCreation>
{
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID , "textures/gui/container/gui_buyer.png");
	private BlockEntityBuyer tile;
	protected int xSize = 176;
	protected int ySize = 168;
	protected int guiLeft;
	protected int guiTop;
	private EditBox costField;
	private boolean validCost = false;

	private ItemStack stackInSlot = ItemStack.EMPTY;
	private double moneyInTile = 0;
	
	private Button validate;
	
	public GuiBuyerCreation(MenuBuyerCreation menu, Inventory inv, Component comp) {
		super(menu, inv, comp);
		this.tile = menu.getBlockEntity();
	}
	
	@Override
	protected void containerTick() {
		super.containerTick();
		stackInSlot = menu.slots.get(0).getItem();
		moneyInTile = tile.getAccountMoney();
		this.validCost = checkIfTextCanBeParsedToPositiveDouble(this.costField.getValue());
	}

	
	@Override
	protected void init() {
		
	    super.init();
		this.fieldInit();
		this.validate = this.addRenderableWidget(new Button(width / 2 - 50, height / 2 + 83, 100, 20, new TranslatableComponent("title.validate"),(press) -> actionPerformed(0))); 


	}
	
	protected void actionPerformed(int buttonId)
	{		
		if(buttonId == 0)
		{
			if(this.validCost && stackInSlot != ItemStack.EMPTY)
			{
				PacketsRegistery.CHANNEL.sendToServer(new PacketBuyerCreation(Double.valueOf(costField.getValue()), tile.getBlockPos(), stackInSlot));
				onClose();
			}
		}
	}
	
	
	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		
		String s = this.costField.getValue();
		this.init(minecraft, width, height);
		this.costField.setValue(s);
	}
	
	protected void fieldInit() {
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.costField = new EditBox(this.font, i + 121, j + 15, 38, 12, new TranslatableComponent("title.cost"));
		this.costField.setMaxLength(35);
		this.costField.setBordered(false);
		this.costField.setVisible(true);
		this.setFocused(this.costField);
		this.addRenderableWidget(this.costField);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == 256) {
			onClose();
		}
		return !this.costField.keyPressed(keyCode, scanCode, modifiers) && !this.costField.isFocused() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
	}
	   
	
	@Override
	public boolean isPauseScreen() {
			return false;
		}
	

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {

		if(stackInSlot != ItemStack.EMPTY)
		{
			this.font.draw(matrixStack, stackInSlot.getDisplayName().getString(), 30.0f, 29.0f, Color.DARK_GRAY.getRGB());
		}
		this.font.draw(matrixStack, new TranslatableComponent("title.fundsCard").getString() + moneyInTile, 30.0f, 44.0f, Color.DARK_GRAY.getRGB());
		super.renderLabels(matrixStack, mouseX, mouseY);
	}


	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		RenderSystem.disableBlend();
		this.costField.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, background);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(stack, k, l, 0, 0, this.xSize, this.ySize);
		this.blit(stack, k + 117, l + 11, 0, this.ySize + 32, 110, 16);
		this.blit(stack, k + 117, l + 11, 0, this.ySize + (this.validCost ? 0 : 16), 110, 16);
	}

	
	private boolean checkIfTextCanBeParsedToPositiveDouble(String field)
	{
		if(field != null) {
			try
			{	
				double value = Double.parseDouble(field);
				if(value > 0)
				{
					return true;
				}
				return false;
			}
			catch(NumberFormatException e)
			{
			  return false;
			}
		}
		return false;
	}
	
}