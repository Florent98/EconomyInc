/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;


import org.lwjgl.opengl.GL11;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifoube.gui.container.ContainerVault2by2;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiVault2by2 extends ContainerScreen<ContainerVault2by2>
{

	protected TileEntityBlockVault2by2 tile_getter;
	protected PlayerInventory playerInventory_getter;
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/container/gui_vault2by2.png");
	protected int xSize = 176;
	protected int ySize = 222;
	protected int guiLeft;
	protected int guiTop;
	private Button settings;

	
	public GuiVault2by2(ContainerVault2by2 container, PlayerInventory playerInventory, ITextComponent name) 
	{
		super(container, playerInventory, name);
		this.tile_getter = getContainer().getTile();
		this.playerInventory_getter = playerInventory;	
	}
	
	@Override
	protected void init() {
		super.init();
		this.minecraft.keyboardListener.enableRepeatEvents(true);
			int i = (this.width - this.xSize) / 2;
			int j = (this.height - this.ySize) / 2;
			if(tile_getter.getOwnerS().equals(this.minecraft.player.getUniqueID().toString()) && !Minecraft.getInstance().isSingleplayer())
	        {
	        	this.settings = this.addButton(new Button(i + 161, j, 15, 15, TextFormatting.BOLD.toString() + TextFormatting.WHITE + "⚙",(press) -> actionPerformed(0)));
	        }
	}
	
	protected void actionPerformed(int buttonId)
	{		
		switch (buttonId) {
		case 0:
			Minecraft.getInstance().player.sendMessage(new StringTextComponent("Not available right now."));
			break;

		default:
			break;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		this.font.drawString(new TranslationTextComponent(I18n.format("title.block_vault")).getFormattedText(), 8.0F, -22, 4210752);
	    this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 122), 4210752);		
	}
				
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) 
	{
		this.renderBackground();
	    super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
	       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); 
	       this.minecraft.getTextureManager().bindTexture(background); 
	       int k = (this.width - this.xSize) / 2; 
	       int l = (this.height - this.ySize) / 2;
	       this.blit(k, l, 0, 0, this.xSize, this.ySize); 
	}
	
	
}