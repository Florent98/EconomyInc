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

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import fr.fifoube.blocks.tileentity.TileEntityBlockBuyer;
import fr.fifoube.gui.container.ContainerBuyerCreation;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.packets.PacketBuyerCreation;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class GuiBuyerCreation extends ContainerScreen<ContainerBuyerCreation>
{
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID , "textures/gui/container/gui_buyer.png");
	private TileEntityBlockBuyer tile;
	protected int xSize = 176;
	protected int ySize = 168;
	protected int guiLeft;
	protected int guiTop;
	private TextFieldWidget costField;
	private boolean validCost = false;

	private ItemStack stackInSlot = ItemStack.EMPTY;
	private double moneyInTile = 0;
	
	private Button validate;
	
	public GuiBuyerCreation(ContainerBuyerCreation screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.tile = getContainer().getTile();
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		stackInSlot = container.inventorySlots.get(0).getStack();
		moneyInTile = tile.getAccountMoney();
		this.validCost = checkIfTextCanBeParsedToPositiveDouble(this.costField.getText());

	}
	
	@Override
	protected void init() {
		
	    super.init();
		this.fieldInit();
		this.validate = this.addButton(new Button(width / 2 - 50, height / 2 + 83, 100, 20, new TranslationTextComponent("title.validate"),(press) -> actionPerformed(0))); 


	}
	
	protected void actionPerformed(int buttonId)
	{		
		if(buttonId == 0)
		{
			if(this.validCost && stackInSlot != ItemStack.EMPTY)
			{
				PacketsRegistery.CHANNEL.sendToServer(new PacketBuyerCreation(Double.valueOf(costField.getText()), tile.getPos(), stackInSlot, moneyInTile));
				getContainer().closeContainer(playerInventory.player, false);
				closeScreen();
			}
		}
	}
	
	
	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		
		String s = this.costField.getText();
		this.init(minecraft, width, height);
		this.costField.setText(s);
	}
	
	protected void fieldInit()
	{
	      this.minecraft.keyboardListener.enableRepeatEvents(true);
	      int i = (this.width - this.xSize) / 2;
	      int j = (this.height - this.ySize) / 2;
	      this.costField = new TextFieldWidget(this.font, i + 121, j + 15, 38, 12, new TranslationTextComponent("title.cost"));
	      this.costField.setCanLoseFocus(false);
	      this.costField.setTextColor(-1);
	      this.costField.setDisabledTextColour(-1);
	      this.costField.setEnableBackgroundDrawing(false);
	      this.costField.setMaxStringLength(35);
	      this.children.add(this.costField);
	      this.setFocusedDefault(this.costField);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		      if (keyCode == 256) {
				getContainer().closeContainer(playerInventory.player, true);
		         this.minecraft.player.closeScreen();
		      }

		      return !this.costField.keyPressed(keyCode, scanCode, modifiers) && !this.costField.canWrite() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
	}
	   
	
	@Override
	public boolean isPauseScreen() {
			return false;
		}
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {

	    if(stackInSlot != ItemStack.EMPTY)
	    {
	    	this.font.drawString(matrixStack, stackInSlot.getDisplayName().getString(), 30.0f, 29.0f, Color.DARK_GRAY.getRGB());
	    }
	    this.font.drawString(matrixStack, I18n.format("title.fundsCard") +  ": " + moneyInTile, 30.0f, 44.0f, Color.DARK_GRAY.getRGB());
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		RenderSystem.disableBlend();
		this.costField.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

		this.minecraft.getTextureManager().bindTexture(background); 
	    int k = (this.width - this.xSize) / 2; 
	    int l = (this.height - this.ySize) / 2;
	    this.blit(matrixStack, k, l, 0, 0, this.xSize, this.ySize); 
	    this.blit(matrixStack, k + 117, l + 11, 0, this.ySize + 32, 110, 16);
		this.blit(matrixStack, k + 117, l + 11, 0, this.ySize + (this.validCost ? 0 : 16), 110, 16);
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