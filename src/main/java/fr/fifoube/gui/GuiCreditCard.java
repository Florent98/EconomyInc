package fr.fifoube.gui;

import java.awt.Color;


import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.packets.PacketCardChange;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiCreditCard extends Screen {

	public GuiCreditCard() {
		super(new TranslationTextComponent("gui.creditcard"));
	}

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
	private boolean linked;
	private String name = Minecraft.getInstance().player.getDisplayName().getString();
	
	protected int xSize = 256;
	protected int ySize = 124;
	protected int guiLeft;
	protected int guiTop;
	
	@Override
	public void init() {
		
		this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        
        this.oneB = this.addButton(new Button(width / 2 + 90, height / 2 - 55, 30, 20, TextFormatting.GREEN + "+1", (press) -> actionPerformed(0))); 
        this.fiveB = this.addButton(new Button(width / 2 - 120, height / 2 , 30, 20, TextFormatting.GREEN + "+5", (press) ->actionPerformed(1)));
        this.tenB = this.addButton(new Button(width / 2 - 85, height / 2 , 30, 20, TextFormatting.GREEN + "+10", (press) ->actionPerformed(2)));
        this.twentyB = this.addButton(new Button(width /2 - 50, height / 2 , 30, 20, TextFormatting.GREEN + "+20",(press) -> actionPerformed(3)));
        this.fiftyB = this.addButton(new Button(width / 2 - 15, height / 2 , 30, 20, TextFormatting.GREEN + "+50", (press) ->actionPerformed(4)));
        this.hundreedB = this.addButton(new Button(width / 2 + 20, height / 2 , 30, 20, TextFormatting.GREEN + "+100", (press) ->actionPerformed(5)));
        this.twoHundreedB = this.addButton(new Button(width / 2 + 55, height / 2 , 30, 20, TextFormatting.GREEN + "+200", (press) ->actionPerformed(6)));
        this.fiveHundreedB = this.addButton(new Button(width / 2 + 90, height / 2 , 30, 20, TextFormatting.GREEN + "+500", (press) ->actionPerformed(7)));
		
        this.oneBMinus = this.addButton(new Button(width / 2 + 90, height / 2 - 25, 30, 20,TextFormatting.RED +  "-1", (press) ->actionPerformed(8)));
        this.fiveBMinus = this.addButton(new Button(width / 2 - 120, height / 2 + 30, 30, 20,TextFormatting.RED +  "-5", (press) ->actionPerformed(9)));
        this.tenBMinus = this.addButton(new Button(width / 2 - 85, height / 2 + 30, 30, 20, TextFormatting.RED + "-10", (press) ->actionPerformed(10)));
        this.twentyBMinus = this.addButton(new Button(width /2 - 50, height / 2 + 30, 30, 20, TextFormatting.RED + "-20", (press) ->actionPerformed(11)));
        this.fiftyBMinus = this.addButton(new Button(width / 2 - 15, height / 2 + 30, 30, 20, TextFormatting.RED + "-50", (press) ->actionPerformed(12)));
        this.hundreedBMinus = this.addButton(new Button(width / 2 + 20, height / 2 + 30, 30, 20, TextFormatting.RED + "-100", (press) ->actionPerformed(13)));
        this.twoHundreedBMinus = this.addButton(new Button(width / 2 + 55, height / 2 + 30, 30, 20, TextFormatting.RED + "-200", (press) ->actionPerformed(14)));
        this.fiveHundreedBMinus = this.addButton(new Button(width / 2 + 90, height / 2 + 30, 30, 20, TextFormatting.RED + "-500", (press) ->actionPerformed(15)));
        super.init();
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
		PlayerEntity playerIn = getMinecraft().player;
		playerIn.getCapability(CapabilityMoney.MONEY_CAPABILITY, null).ifPresent(data -> {
			this.funds_s = data.getMoney();
			this.linked = data.getLinked();
		});
	}

	public void actionPerformed(int buttonId)
	{
		int listNumber[] = {-1,-5,-10,-20,-50,-100,-200,-500,1,5,10,20,50,100,200,500};
		PacketsRegistery.CHANNEL.sendToServer(new PacketCardChange(listNumber[buttonId]));
	}	
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		this.minecraft.getTextureManager().bindTexture(background);
		this.blit(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
        InventoryScreen.drawEntityOnScreen(this.guiLeft + 25, this.guiTop + 58, 25, (float)(this.guiLeft + 51) - mouseX, (float)(this.guiTop + 75 - 50) - mouseY, this.getMinecraft().player);
		super.render(mouseX, mouseY, partialTicks);
		this.font.drawString(new TranslationTextComponent(I18n.format("title.ownerCard")+ ": " + name).getFormattedText(), (this.width / 2) - 75, (this.height / 2)- 55, Color.DARK_GRAY.getRGB());
		this.font.drawString(new TranslationTextComponent(I18n.format("title.fundsCard")+ ": " + String.valueOf(funds_s)).getFormattedText(), (this.width / 2) - 75, (this.height / 2)- 45, Color.DARK_GRAY.getRGB());

	}
	
}
