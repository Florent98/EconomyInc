/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import fr.fifoube.blocks.blockentity.BlockEntitySeller;
import fr.fifoube.gui.container.MenuSeller;
import fr.fifoube.gui.utilities.GuiUtilities;
import fr.fifoube.gui.widget.RefillIconButton;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.gui.utilities.GuiText;
import fr.fifoube.main.economy.MoneyFormat;
import fr.fifoube.packets.PacketSellerCreated;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
			this.validate = this.addRenderableWidget(Button.builder(Component.translatable("title.validate"), button -> { actionPerformed(this.validate);}).pos(width / 2 + 26, height / 2 + 83).size(55, 20).build());

			if(player.isCreative())
			{
				this.unlimitedStack = this.addRenderableWidget(Button.builder(Component.translatable("title.unlimited"), button -> { actionPerformed(this.unlimitedStack);}).pos(width / 2 + 2, height / 2 - 96).size(80, 13).build());
			}
		}
		this.fieldInit();
	}

	protected void fieldInit() {

		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.costField = new EditBox(this.font, i + 121, j + 15, 38, 12, Component.translatable("title.cost"));
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
					PacketsRegistery.CHANNEL.sendToServer(new PacketSellerCreated(this.cost, tile.getBlockPos(), this.admin, this.autoRefill.isRefillEnabled()));
					playerIn.closeContainer();
				}
				else
				{
					playerIn.sendSystemMessage(Component.translatable("title.noValidCost"));
				}
			}
		}
	}
	
	@Override
		public boolean isPauseScreen() {
			return false;
		}
	

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		MutableComponent s = this.admin ? Component.translatable("title.unlimitedStack") : Component.translatable("title.limitedStack");
		GuiText.draw(this.font, guiGraphics, Component.translatable("title.cost", MoneyFormat.display(tile.getCost())), this.getGuiLeft() + 100, this.getGuiTop() + 33, Color.DARK_GRAY.getRGB());
		GuiText.draw(this.font, guiGraphics, Component.translatable("title.mode", s), this.getGuiLeft() + 100, this.getGuiTop() + 44, Color.DARK_GRAY.getRGB());
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}
	
	
	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
	    int k = (this.width - this.xSize) / 2; 
	    int l = (this.height - this.ySize) / 2;
	    guiGraphics.blit(background, k, l, 0, 0, this.xSize, this.ySize);
		if(!tile.getCreated())
		{
			guiGraphics.blit(background, k + 117, l + 11, 0, this.ySize + 32, 110, 16);
			guiGraphics.blit(background, k + 117, l + 11, 0, this.ySize + (this.validCost ? 0 : 16), 110, 16);
		}
	}
}
