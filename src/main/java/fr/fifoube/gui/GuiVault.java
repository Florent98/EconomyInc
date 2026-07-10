/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import fr.fifoube.blocks.blockentity.BlockEntityVault;
import fr.fifoube.gui.container.MenuVault;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiVault extends AbstractContainerScreen<MenuVault>
{
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/container/gui_vault.png");
	protected BlockEntityVault tile_getter;
	protected Inventory playerInventory_getter;
	protected int xSize = 176;
	protected int ySize = 168;
	protected int guiLeft;
	protected int guiTop;

	public GuiVault(MenuVault container, Inventory playerInventory, Component name) 
	{
		super(container, playerInventory, name);
		this.tile_getter = container.getTile();
		this.playerInventory_getter = playerInventory;
	}
	
   
	@Override
	protected void init() {
		super.init();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
	}

    
    @Override
    public void onClose() {
    	
    	super.onClose();
		if(tile_getter.getIsOpen())
		{
			tile_getter.setIsOpen(false);
			tile_getter.setChanged();
		}
    }
    
	
    protected void actionPerformed(int buttonId)
	{		
		switch (buttonId) {
		case 0:
			net.minecraft.client.Minecraft.getInstance().player.sendSystemMessage(Component.literal("Not available right now."));
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
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
	     int k = (this.width - this.xSize) / 2; 
	     int l = (this.height - this.ySize) / 2;
	     guiGraphics.blit(background, k, l, 0, 0, this.xSize, this.ySize); 
    }	
}
