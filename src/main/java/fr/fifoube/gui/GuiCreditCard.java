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
import net.minecraft.network.chat.Component;
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
		super(net.minecraft.network.chat.Component.translatable("gui.creditcard"));
	}

	@Override
	public void init() {
		
		super.init();
		this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

		this.oneB = this.addRenderableWidget(Button.builder(Component.literal("+1").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(0);}).pos(width / 2 + 90, height / 2 - 55).size(30, 20).build());
		this.fiveB = this.addRenderableWidget(Button.builder(Component.literal("+5").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(1);}).pos(width / 2 - 120, height / 2 + 5).size(30, 20).build());
		this.tenB = this.addRenderableWidget(Button.builder(Component.literal("+10").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(2);}).pos(width / 2 - 85, height / 2 + 5 ).size(30, 20).build());
		this.twentyB = this.addRenderableWidget(Button.builder(Component.literal("+20").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(3);}).pos(width / 2 - 50, height / 2 + 5).size(30, 20).build());
		this.fiftyB = this.addRenderableWidget(Button.builder(Component.literal("+50").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(4);}).pos(width / 2 - 15, height / 2 + 5).size(30, 20).build());
		this.hundreedB = this.addRenderableWidget(Button.builder(Component.literal("+100").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(5);}).pos(width / 2 + 20, height / 2 + 5).size(30, 20).build());
		this.twoHundreedB = this.addRenderableWidget(Button.builder(Component.literal("+200").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(6);}).pos(width / 2 + 55, height / 2 + 5).size(30, 20).build());
		this.fiveHundreedB = this.addRenderableWidget(Button.builder(Component.literal("+500").withStyle(ChatFormatting.GREEN), button -> { actionPerformed(7);}).pos(width / 2 + 90, height / 2 + 5).size(30, 20).build());


		this.oneBMinus = this.addRenderableWidget(Button.builder(Component.literal("-1").withStyle(ChatFormatting.RED), button -> { actionPerformed(8);}).pos(width / 2 + 90, height / 2 - 25).size(30, 20).build());
		this.fiveBMinus = this.addRenderableWidget(Button.builder(Component.literal("-5").withStyle(ChatFormatting.RED), button -> { actionPerformed(9);}).pos(width / 2 - 120, height / 2 + 30).size(30, 20).build());
		this.tenBMinus = this.addRenderableWidget(Button.builder(Component.literal("-10").withStyle(ChatFormatting.RED), button -> { actionPerformed(10);}).pos(width / 2 - 85, height / 2 + 30).size(30, 20).build());
		this.twentyBMinus = this.addRenderableWidget(Button.builder(Component.literal("-20").withStyle(ChatFormatting.RED), button -> { actionPerformed(11);}).pos(width / 2 - 50, height / 2 + 30).size(30, 20).build());
		this.fiftyBMinus = this.addRenderableWidget(Button.builder(Component.literal("-50").withStyle(ChatFormatting.RED), button -> { actionPerformed(12);}).pos(width / 2 - 15, height / 2 + 30).size(30, 20).build());
		this.hundreedBMinus = this.addRenderableWidget(Button.builder(Component.literal("-100").withStyle(ChatFormatting.RED), button -> { actionPerformed(13);}).pos(width / 2 + 20, height / 2 + 30).size(30, 20).build());
		this.twoHundreedBMinus = this.addRenderableWidget(Button.builder(Component.literal("-200").withStyle(ChatFormatting.RED), button -> { actionPerformed(14);}).pos(width / 2 + 55, height / 2 + 30).size(30, 20).build());
		this.fiveHundreedBMinus = this.addRenderableWidget(Button.builder(Component.literal("-500").withStyle(ChatFormatting.RED), button -> { actionPerformed(15);}).pos(width / 2 + 90, height / 2 + 30).size(30, 20).build());
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
        InventoryScreen.renderEntityInInventoryFollowsMouse(poseStack,i + 28, j + 58, 25, (float)(i + 51) - mouseX, (float)(j + 75 - 50) - mouseY, this.minecraft.player);
		super.render(poseStack, mouseX, mouseY, partialTicks);
		this.font.draw(poseStack, Component.translatable("title.ownerCard", name), (this.width / 2) - 75, (this.height / 2)- 55, Color.DARK_GRAY.getRGB());
		this.font.draw(poseStack, Component.translatable("title.fundsCard", funds_s), (this.width / 2) - 75, (this.height / 2)- 45, Color.DARK_GRAY.getRGB());
		 
	}
}
