/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.fifoube.blocks.blockentity.BlockEntityVault2by2;
import fr.fifoube.gui.container.MenuVault2by2;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiVault2by2 extends AbstractContainerScreen<MenuVault2by2>
{
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/container/gui_vault2by2.png");
	protected BlockEntityVault2by2 tile_getter;
	protected Inventory player_inventory;
	protected int xSize = 176;
	protected int ySize = 222;
	protected int guiLeft;
	protected int guiTop;
	private Button settings;

	
	public GuiVault2by2(MenuVault2by2 container, Inventory playerInventory, Component name) 
	{
		super(container, playerInventory, name);
		this.tile_getter = container.getTile();
		this.player_inventory = playerInventory;	
	    this.inventoryLabelX = 8;
	    this.inventoryLabelY = this.ySize - 122;
	    this.titleLabelX = 8;
	    this.titleLabelY = -22;
	}
	
	@Override
	protected void init() {
		super.init();
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		if(tile_getter.getOwner().equals(this.minecraft.player.getUUID()) && !Minecraft.getInstance().hasSingleplayerServer())
		{
			this.settings = this.addRenderableWidget(Button.builder(Component.literal("⚙").withStyle(ChatFormatting.BOLD, ChatFormatting.WHITE), button -> { actionPerformed(0);}).pos(i + 175, j + 5).size(20, 20).build());
		}
	}
	
	protected void actionPerformed(int buttonId)
	{		
		switch (buttonId) {
		case 0:
			ClientGuiScreen.openGui(2, tile_getter);
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