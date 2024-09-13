/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.fifoube.blocks.blockentity.BlockEntitySeller;
import fr.fifoube.gui.container.MenuSeller;
import fr.fifoube.gui.utilities.GuiUtilities;
import fr.fifoube.gui.widget.RefillIconButton;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.packets.PacketSellerCreated;
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
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class GuiSeller extends AbstractContainerScreen<MenuSeller> {

	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/container/gui_seller.png");
	private BlockEntitySeller tile;
	protected int xSize = 176;
	protected int ySize = 168;

	private Button validate;
	private Button unlimitedStack;
	private RefillIconButton autoRefill;
	private EditBox costField;

	private double cost;
	private boolean admin = false;
	private boolean validCost = false;

	public GuiSeller(MenuSeller container, Inventory playerInventory, Component name) 
	{
		super(container, playerInventory, name);
		this.tile = container.getTile();
	}
	
	@Override
	protected void containerTick() {
		
		super.containerTick();
		this.validCost = GuiUtilities.parseToDouble(this.costField.getValue());

	}

	@Override
	protected void init() {
		super.init();
		Player player = minecraft.player;
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		if(!tile.getCreated())
		{
			this.autoRefill = this.addRenderableWidget(new RefillIconButton(width / 2 + 87, height / 2 - 75, (onPress) -> { actionPerformed(this.autoRefill); }, background));
			this.validate = this.addRenderableWidget(new Button(width / 2 + 26, height / 2 + 83, 55, 20, new TranslatableComponent("title.validate"), (onPress) -> { actionPerformed(this.validate); }));
			if(player.isCreative())
			{
				this.unlimitedStack = this.addRenderableWidget(new Button(width /2 + 2, height / 2 - 96, 80, 13, new TranslatableComponent("title.unlimited"), (onPress) -> { actionPerformed(this.unlimitedStack); }));
			}
		}
		this.fieldInit();
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
		if(!tile.getCreated()) {
			this.addRenderableWidget(this.costField);
		}
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {

		String s = this.costField.getValue();
		this.init(minecraft, width, height);
		this.costField.setValue(s);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == 256) {
			onClose();
		}
		return !this.costField.keyPressed(keyCode, scanCode, modifiers) && !this.costField.isFocused() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
	}

	protected void actionPerformed(Button button)
	{
		Player playerIn = minecraft.player;
		if(tile != null)
		{
			if(button == this.unlimitedStack)
			{
				if(!this.admin)
				{
					this.admin = true;
				}
				else
				{
					this.admin = false;
				}
			}
			else if(button == this.autoRefill)
			{
				if(this.autoRefill.isRefillEnabled())
				{
					this.autoRefill.setRefillMode(false);
				}
				else
				{
					this.autoRefill.setRefillMode(true);
				}
			}
			else if(button == this.validate)
			{
				if(this.validCost)
				{
					this.cost = Double.valueOf(this.costField.getValue());
					PacketsRegistery.CHANNEL.sendToServer(new PacketSellerCreated(this.cost, tile.getBlockPos(), this.admin, this.autoRefill.isRefillEnabled())); // SEND SERVER PACKET
					playerIn.closeContainer(); // CLOSE SCREEN
				}
				else
				{
					playerIn.sendMessage(new TranslatableComponent("title.noValidCost"), playerIn.getUUID());
				}
			}
		}
	}
	
	@Override
		public boolean isPauseScreen() {
			return false;
		}
	

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

		this.renderBackground(stack);
		if(!tile.getCreated())
		{
			this.autoRefill.render(stack, mouseX, mouseY, partialTicks);
			this.costField.render(stack, mouseX, mouseY, partialTicks);
		}
		super.render(stack, mouseX, mouseY, partialTicks);
		TranslatableComponent s = this.admin ? new TranslatableComponent("title.unlimitedStack") : new TranslatableComponent("title.limitedStack");
		this.font.draw(stack, new TranslatableComponent("title.cost",  tile.getCost()), this.getGuiLeft() + 100, this.getGuiTop() + 33, Color.DARK_GRAY.getRGB());
		this.font.draw(stack, new TranslatableComponent("title.mode", s), this.getGuiLeft() + 100, this.getGuiTop() + 44, Color.DARK_GRAY.getRGB());
		RenderSystem.disableBlend();
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
		if(!tile.getCreated())
		{
			this.blit(stack, k + 117, l + 11, 0, this.ySize + 32, 110, 16);
			this.blit(stack, k + 117, l + 11, 0, this.ySize + (this.validCost ? 0 : 16), 110, 16);
		}
	}
	
	
}
