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
import fr.fifoube.gui.utilities.GuiUtilities;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.packets.PacketBuyerChange;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.awt.*;

public class GuiBuyerSell extends Screen
{
	private BlockEntityBuyer tile;
	
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/screen/gui_item.png");
	protected int xSize = 256;
	protected int ySize = 124;
	protected int guiLeft;
	protected int guiTop;
	private Button sellOne;
	private Button sellAll;
	private ItemRenderer renderer;


	public GuiBuyerSell(BlockEntityBuyer te) {
		super(te.getDisplayName());
		this.tile = te;
	}
	
	@Override
	protected void init() {
		
		this.guiLeft = (this.width - this.xSize) / 2;
	    this.guiTop = (this.height - this.ySize) / 2;
		this.sellOne = this.addRenderableWidget(new Button(width / 2 - 110, height / 2 + 27, 100, 20, new TranslatableComponent("title.sellOne"),(press) -> actionPerformed(0)));
		this.sellAll = this.addRenderableWidget(new Button(width / 2 + 10, height / 2 + 27, 100, 20, new TranslatableComponent("title.sellAll"),(press) -> actionPerformed(1)));
		this.renderer = Minecraft.getInstance().getItemRenderer();

	}

	@Override
	public void tick() {
		if(this.tile.getTimer() != 0)
		{
			this.sellOne.active = false;
			this.sellAll.active = false;
		}
		else
		{
			this.sellOne.active = true;
			this.sellAll.active = true;
		}
	}

	public void drawImageInGui(int posX, int posY) {
		ItemStack stack = new ItemStack(Blocks.BARRIER, 1);
		if (!tile.getItemStackToBuy().isEmpty()) {
			stack = new ItemStack(tile.getItemStackToBuy().getItem(), 1);
		}
		GuiUtilities.renderGuiItem(renderer, this.getBlitOffset(), stack, posX, posY, renderer.getModel(stack, (Level)null, (LivingEntity)null, 0));
	}

	@Override
	public boolean isPauseScreen() {
		
		return false;
	}
	
	protected void actionPerformed(int buttonId)
	{		
		if(buttonId == 0)
		{
			PacketsRegistery.CHANNEL.sendToServer(new PacketBuyerChange(tile.getBlockPos(), true)); //SENDING PACKET TO LET SERVER KNOW CHANGES WITH TOTAL FUNDS, COORDINATES AND AMOUNT

		}
		else if(buttonId == 1)
		{
			PacketsRegistery.CHANNEL.sendToServer(new PacketBuyerChange(tile.getBlockPos(), false)); //SENDING PACKET TO LET SERVER KNOW CHANGES WITH TOTAL FUNDS, COORDINATES AND AMOUNT
		}
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

		this.renderBackground(stack);
		int i = this.guiLeft;
		int j = this.guiTop;
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, background);
		this.blit(stack, this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
		this.drawImageInGui((this.width / 2) + 85, (this.height / 2) - 40);
		this.font.draw(stack, new TranslatableComponent("title.seller",  tile.getOwnerName()).withStyle(ChatFormatting.BOLD), (this.width / 2) - 120, (this.height / 2)- 55, Color.BLACK.getRGB());
		this.font.draw(stack, new TranslatableComponent("title.item", tile.getItemStackToBuy().getDisplayName().getString()).withStyle(ChatFormatting.BOLD), (this.width / 2) - 120, (this.height / 2)- 45, Color.BLACK.getRGB());
		this.font.draw(stack, new TranslatableComponent("title.cost", tile.getCost()).withStyle(ChatFormatting.BOLD), (this.width / 2) - 120, (this.height / 2)- 35, Color.BLACK.getRGB());
		super.render(stack, mouseX, mouseY, partialTicks);
	}




}
