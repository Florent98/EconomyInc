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
import com.mojang.blaze3d.platform.GlStateManager;
import fr.fifoube.blocks.tileentity.TileEntityBlockBuyer;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiBuyerSell extends Screen
{
	private TileEntityBlockBuyer tile;
	
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/screen/gui_item.png");
	protected int xSize = 256;
	protected int ySize = 124;
	protected int guiLeft;
	protected int guiTop;
	private Button sellOne;
	private Button sellAll;
	
	public GuiBuyerSell(TileEntityBlockBuyer te) {
		super(new TranslationTextComponent("gui.sellerbuy"));
		this.tile = te;
	}
	
	@Override
	protected void init() {
		
		this.guiLeft = (this.width - this.xSize) / 2;
	    this.guiTop = (this.height - this.ySize) / 2;
		this.sellOne = this.addButton(new Button(width / 2 - 110, height / 2 + 27, 100, 20, new TranslationTextComponent("title.sellOne"),(press) -> actionPerformed(0))); 
		this.sellAll = this.addButton(new Button(width / 2 + 10, height / 2 + 27, 100, 20, new TranslationTextComponent("title.sellAll"),(press) -> actionPerformed(1))); 

	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		// added
        this.getMinecraft().getTextureManager().bindTexture(background);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        FontRenderer font = Minecraft.getInstance().fontRenderer;
		this.font.drawString(matrixStack, TextFormatting.BOLD + I18n.format("title.seller") + tile.getOwnerName(), (this.width / 2) - 120, (this.height / 2)- 55, Color.BLACK.getRGB());
		this.font.drawString(matrixStack, TextFormatting.BOLD + I18n.format("title.item") + tile.getItemStackToBuy().getDisplayName().getString(), (this.width / 2) - 120, (this.height / 2)- 45, Color.BLACK.getRGB());
		this.font.drawString(matrixStack, TextFormatting.BOLD + I18n.format("title.cost") + tile.getCost(), (this.width / 2) - 120, (this.height / 2)- 35, Color.BLACK.getRGB());
		super.render(matrixStack, mouseX, mouseY, partialTicks);
        drawImageInGui();

    }

	public void drawImageInGui() 
	{
        int i = this.guiLeft;
        int j = this.guiTop;
        GL11.glPushMatrix();
		GlStateManager.enableRescaleNormal();
	    RenderHelper.enableStandardItemLighting();
	    GL11.glScaled(2, 2, 2);
	    ItemStack stack = new ItemStack(Blocks.BARRIER,1);
	    if(!tile.getItemStackToBuy().isEmpty())
	    {
		    stack = new ItemStack(tile.getItemStackToBuy().getItem(), 1);
	    }
	    this.itemRenderer.renderItemIntoGUI(stack, (i / 2) + 105 , (j /2) + 5);
	    RenderHelper.disableStandardItemLighting();
	    GlStateManager.disableRescaleNormal();
	    GL11.glPopMatrix();   
	}

	@Override
	public boolean isPauseScreen() {
		
		return false;
	}
	
	protected void actionPerformed(int buttonId)
	{		
		if(buttonId == 0)
		{
			PacketsRegistery.CHANNEL.sendToServer(new PacketBuyerChange(tile.getPos(), true)); //SENDING PACKET TO LET SERVER KNOW CHANGES WITH TOTAL FUNDS, COORDINATES AND AMOUNT

		}
		else if(buttonId == 1)
		{
			PacketsRegistery.CHANNEL.sendToServer(new PacketBuyerChange(tile.getPos(), false)); //SENDING PACKET TO LET SERVER KNOW CHANGES WITH TOTAL FUNDS, COORDINATES AND AMOUNT
		}
	}
	

}
