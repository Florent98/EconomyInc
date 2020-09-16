/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import fr.fifoube.blocks.tileentity.TileEntityBlockChanger;
import fr.fifoube.gui.container.ContainerChanger;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.config.ConfigFile;
import fr.fifoube.packets.PacketChangerUpdate;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiChanger extends ContainerScreen<ContainerChanger> {

	private TileEntityBlockChanger tile;

	public GuiChanger(ContainerChanger container, PlayerInventory playerInventory, ITextComponent name) 
	{
		super(container, playerInventory, name);
		this.tile = getContainer().getTile();
	}
	
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/container/gui_changer.png");
	protected int xSize = 176;
	protected int ySize = 168;
	protected int guiLeft;
	protected int guiTop;
	private boolean isProcessing;
	
	@Override
	public void tick() {
		super.tick();
	}
	
	@Override
	protected void init() {
		super.init();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {

		this.font.drawString(matrixStack, I18n.format("title.block_changer"), 8.0F, 5, 4210752);
	    this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0F, (float)(this.ySize - 95), 4210752);
		
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		  
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); 
	       this.getMinecraft().getTextureManager().bindTexture(background); 
	       int k = (this.width - this.xSize) / 2; 
	       int l = (this.height - this.ySize) / 2;
	       this.blit(matrixStack, k, l, 0, 0, this.xSize, this.ySize); 
	       //BURNING
	       if(tile != null)
	       {
	    	   float display = (tile.getTimePassed() / Float.valueOf(tile.timeProcess)) * 56;
	    	   if(tile.isProcessing)
	    	   {	   
	    		   this.blit(matrixStack, k + 55, l + 34, 176, 0, Math.round(display), this.ySize);
	    	   }
	       }		
	}
	
	
	@Override
	public void onClose() {
		
		tile.setNumbUse(0);
		PacketsRegistery.CHANNEL.sendToServer(new PacketChangerUpdate(tile.getPos()));
	}
	

}
