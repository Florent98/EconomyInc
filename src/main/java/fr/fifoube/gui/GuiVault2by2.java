package fr.fifoube.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifoube.gui.container.ContainerVault2by2;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiVault2by2 extends GuiContainer
{

	protected TileEntityBlockVault2by2 tile_getter;
	protected InventoryPlayer playerInventory_getter;
	
	public GuiVault2by2(InventoryPlayer playerInventory, TileEntityBlockVault2by2 tile, World worldIn) 
	{
		super(new ContainerVault2by2(playerInventory, tile));
		this.tile_getter = tile;
		this.playerInventory_getter = playerInventory;
	}
	
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/container/gui_vault2by2.png");
	protected int xSize = 176;
	protected int ySize = 222;
	protected int guiLeft;
	protected int guiTop;
	
	@Override
	public void initGui() {
		
		super.initGui();
	    this.mc.keyboardListener.enableRepeatEvents(true);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		if(tile_getter.getOwnerS().equals(mc.player.getUniqueID().toString()) && !Minecraft.getInstance().isSingleplayer())
        {
		    this.addButton(new GuiButtonExt(0, i + 161, j, 15, 15, TextFormatting.BOLD.toString() + TextFormatting.WHITE + "⚙") {

                public void onClick(double mouseX, double mouseY) {
                	GuiVault2by2.this.mc.displayGuiScreen(new GuiVaultSettings2by2(tile_getter));
                }
             });
        }
	}
	

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		fontRenderer.drawString(new TextComponentTranslation(I18n.format("title.block_vault")).getFormattedText(), 7, -22, Color.DARK_GRAY.getRGB());
		fontRenderer.drawString(new TextComponentTranslation("Inventory").getFormattedText(), 7, 101, Color.DARK_GRAY.getRGB());
		
	}


	
				
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) 
	{
		this.drawDefaultBackground();
	    super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
	       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); 
	       this.mc.getTextureManager().bindTexture(background); 
	       int k = (this.width - this.xSize) / 2; 
	       int l = (this.height - this.ySize) / 2;
	       this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize); 
	}
	
	@Override
	public void onGuiClosed() 
	{
		super.onGuiClosed();
	}
	
}