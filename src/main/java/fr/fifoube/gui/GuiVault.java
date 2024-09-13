/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.fifoube.blocks.blockentity.BlockEntityVault;
import fr.fifoube.gui.container.MenuVault;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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
			//PacketsRegistery.network.sendToServer(new PacketIsOpen(tile_getter.getPos().getX(), tile_getter.getPos().getY(), tile_getter.getPos().getZ(), false));
		}
    }
    
	
    protected void actionPerformed(int buttonId)
	{		
		switch (buttonId) {
		case 0:
			Minecraft.getInstance().player.sendMessage(new TextComponent("Not available right now."), Minecraft.getInstance().player.getUUID());
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
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
    	
	     RenderSystem.setShader(GameRenderer::getPositionTexShader);
	     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	     RenderSystem.setShaderTexture(0, background);

	     int k = (this.width - this.xSize) / 2; 
	     int l = (this.height - this.ySize) / 2;
	     this.blit(stack, k, l, 0, 0, this.xSize, this.ySize); 
    	
    }	
}