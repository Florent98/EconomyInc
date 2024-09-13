/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.packets.PacketCardChange;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class GuiCreditCard extends Screen {

	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/screen/gui_item.png");
	private Button oneB;
	private Button fiveB;
	private Button tenB;
	private Button twentyB;
	private Button fiftyB;
	private Button hundreedB;
	private Button twoHundreedB;
	private Button fiveHundreedB;
	
	private Button oneBMinus;
	private Button fiveBMinus;
	private Button tenBMinus;
	private Button twentyBMinus;
	private Button fiftyBMinus;
	private Button hundreedBMinus;
	private Button twoHundreedBMinus;
	private Button fiveHundreedBMinus;
		
	private double funds_s;
	private String name = Minecraft.getInstance().player.getDisplayName().getString();
	
	protected int xSize = 256;
	protected int ySize = 124;
	protected int guiLeft;
	protected int guiTop;

	public GuiCreditCard() {
		super(new TranslatableComponent("gui.creditcard"));
	}

	@Override
	public void init() {
		
		super.init();
		this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        this.oneB = this.addRenderableWidget(new Button(width / 2 + 90, height / 2 - 55, 30, 20, new TextComponent("+1").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(0);}));
		this.fiveB = this.addRenderableWidget(new Button(width / 2 - 120, height / 2 + 5, 30, 20, new TextComponent("+5").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(1);}));
		this.tenB = this.addRenderableWidget(new Button(width / 2 - 85, height / 2 + 5, 30, 20, new TextComponent("+10").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(2);}));
		this.twentyB = this.addRenderableWidget(new Button(width / 2 - 50, height / 2 + 5, 30, 20, new TextComponent("+20").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(3);}));
		this.fiftyB = this.addRenderableWidget(new Button(width / 2 - 15, height / 2 + 5, 30, 20, new TextComponent("+50").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(4);}));
		this.hundreedB = this.addRenderableWidget(new Button(width / 2 + 20, height / 2 + 5, 30, 20, new TextComponent("+100").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(5);}));
		this.twoHundreedB = this.addRenderableWidget(new Button(width / 2 + 55 , height / 2 + 5, 30, 20, new TextComponent("+200").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(6);}));
		this.fiveHundreedB = this.addRenderableWidget(new Button(width / 2 + 90, height / 2 + 5, 30, 20, new TextComponent("+500").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(7);}));

		this.oneBMinus = this.addRenderableWidget(new Button(width / 2 + 90, height / 2 - 25, 30, 20, new TextComponent("-1").withStyle(ChatFormatting.RED), button -> { actionPerformed(8);}));
		this.fiveBMinus = this.addRenderableWidget(new Button(width / 2 - 120, height / 2 + 30, 30, 20, new TextComponent("-5").withStyle(ChatFormatting.RED), button -> { actionPerformed(9);}));
		this.tenBMinus = this.addRenderableWidget(new Button(width / 2 - 85, height / 2 + 30, 30, 20, new TextComponent("-10").withStyle(ChatFormatting.RED), button -> { actionPerformed(10);}));
		this.twentyBMinus = this.addRenderableWidget(new Button(width / 2 - 50, height / 2 + 30, 30, 20, new TextComponent("-20").withStyle(ChatFormatting.RED), button -> { actionPerformed(11);}));
		this.fiftyBMinus = this.addRenderableWidget(new Button(width / 2 - 15, height / 2 + 30, 30, 20, new TextComponent("-50").withStyle(ChatFormatting.RED), button -> { actionPerformed(12);}));
		this.hundreedBMinus = this.addRenderableWidget(new Button(width / 2 + 20, height / 2 + 30, 30, 20, new TextComponent("-100").withStyle(ChatFormatting.RED), button -> { actionPerformed(13);}));
		this.twoHundreedBMinus = this.addRenderableWidget(new Button(width / 2 + 55, height / 2 + 30, 30, 20, new TextComponent("-200").withStyle(ChatFormatting.RED), button -> { actionPerformed(14);}));
		this.fiveHundreedBMinus = this.addRenderableWidget(new Button(width / 2 + 90, height / 2 + 30, 30, 20, new TextComponent("-500").withStyle(ChatFormatting.RED), button -> { actionPerformed(15);}));
	}
	
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}
	
	@Override
	public void tick() {
		super.tick();
		Player playerIn = Minecraft.getInstance().player;
		playerIn.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
			this.funds_s = data.getMoney();
		});
	}

	public void actionPerformed(int buttonId)
	{
		int listNumber[] = {-1,-5,-10,-20,-50,-100,-200,-500,1,5,10,20,50,100,200,500};
		PacketsRegistery.CHANNEL.sendToServer(new PacketCardChange(listNumber[buttonId]));
	}	
	
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		
		this.renderBackground(poseStack);
		int i = this.guiLeft;
		int j = this.guiTop;
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, background);		
        this.blit(poseStack, this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
        InventoryScreen.renderEntityInInventory(i + 28, j + 58, 25, (float)(i + 51) - mouseX, (float)(j + 75 - 50) - mouseY, this.minecraft.player);

		super.render(poseStack, mouseX, mouseY, partialTicks);
		this.font.draw(poseStack, new TranslatableComponent("title.ownerCard", name), (this.width / 2) - 75, (this.height / 2)- 55, Color.DARK_GRAY.getRGB());
		this.font.draw(poseStack, new TranslatableComponent("title.fundsCard", funds_s), (this.width / 2) - 75, (this.height / 2)- 45, Color.DARK_GRAY.getRGB());
		 
	}
}
