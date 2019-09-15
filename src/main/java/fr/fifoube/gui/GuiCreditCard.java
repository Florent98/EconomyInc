package fr.fifoube.gui;


import com.mojang.blaze3d.platform.GlStateManager;

import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.packets.PacketCardChange;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GuiCreditCard extends Screen {

	public GuiCreditCard(ITextComponent titleIn) {
		super(titleIn);
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
	private String owner = Minecraft.getInstance().player.getDisplayName().getFormattedText();
	private boolean linked;
	
	protected int xSize = 256;
	protected int ySize = 124;
	protected int guiLeft;
	protected int guiTop;

	@Override
	public void init(Minecraft mc, int p_init_2_, int p_init_3_) {
		
		this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        
        this.oneB = this.addButton(new Button(width / 2 + 90, height / 2 - 55, 30, 20, TextFormatting.GREEN + "+1", actionPerformed(0))); 
        this.fiveB = this.addButton(new Button(width / 2 - 120, height / 2 , 30, 20, TextFormatting.GREEN + "+5", actionPerformed(1)));
        this.tenB = this.addButton(new Button(width / 2 - 85, height / 2 , 30, 20, TextFormatting.GREEN + "+10", actionPerformed(2)));
        this.twentyB = this.addButton(new Button(width /2 - 50, height / 2 , 30, 20, TextFormatting.GREEN + "+20", actionPerformed(3)));
        this.fiftyB = this.addButton(new Button(width / 2 - 15, height / 2 , 30, 20, TextFormatting.GREEN + "+50", actionPerformed(4)));
        this.hundreedB = this.addButton(new Button(width / 2 + 20, height / 2 , 30, 20, TextFormatting.GREEN + "+100", actionPerformed(5)));
        this.twoHundreedB = this.addButton(new Button(width / 2 + 55, height / 2 , 30, 20, TextFormatting.GREEN + "+200", actionPerformed(6)));
        this.fiveHundreedB = this.addButton(new Button(width / 2 + 90, height / 2 , 30, 20, TextFormatting.GREEN + "+500", actionPerformed(7)));
		
        this.oneBMinus = this.addButton(new Button(width / 2 + 90, height / 2 - 25, 30, 20,TextFormatting.RED +  "-1", actionPerformed(8)));
        this.fiveBMinus = this.addButton(new Button(width / 2 - 120, height / 2 + 30, 30, 20,TextFormatting.RED +  "-5", actionPerformed(9)));
        this.tenBMinus = this.addButton(new Button(width / 2 - 85, height / 2 + 30, 30, 20, TextFormatting.RED + "-10", actionPerformed(10)));
        this.twentyBMinus = this.addButton(new Button(width /2 - 50, height / 2 + 30, 30, 20, TextFormatting.RED + "-20", actionPerformed(11)));
        this.fiftyBMinus = this.addButton(new Button(width / 2 - 15, height / 2 + 30, 30, 20, TextFormatting.RED + "-50", actionPerformed(12)));
        this.hundreedBMinus = this.addButton(new Button(width / 2 + 20, height / 2 + 30, 30, 20, TextFormatting.RED + "-100", actionPerformed(13)));
        this.twoHundreedBMinus = this.addButton(new Button(width / 2 + 55, height / 2 + 30, 30, 20, TextFormatting.RED + "-200", actionPerformed(14)));
        this.fiveHundreedBMinus = this.addButton(new Button(width / 2 + 90, height / 2 + 30, 30, 20, TextFormatting.RED + "-500", actionPerformed(15)));
            
		
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void tick() {
		super.tick();
		PlayerEntity playerIn = Minecraft.getInstance().player;
		playerIn.getCapability(CapabilityMoney.MONEY_CAPABILITY).ifPresent(data ->{
			this.funds_s = data.getMoney();
			this.linked = data.getLinked();
		});
	}

	public IPressable actionPerformed(int buttonId)
	{
		/*int listNumber[] = {-1,-5,-10,-20,-50,-100,-200,-500,1,5,10,20,50,100,200,500};
		for(int i = 0; i < 15; i++)
		{
			PacketsRegistery.CHANNEL.sendToServer(new PacketCardChange(listNumber[i]));
		}*/
		return null;
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		 this.renderBackground();
	}
	
	
	 /**
     * Draws an entity on the screen looking forward.
     */
    public static void drawEntityOnScreen(int posX, int posY, float scale, float mouseX, float mouseY, LivingEntity ent)
    {   
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)posX - 25, (float)(posY - 22.5), 50.0F);
        GlStateManager.scalef((float)(-scale), (float)scale, (float)scale);
        GlStateManager.scalef(0.75F, 0.75F, 0.75F);
        GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotatef(0.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotatef(0.0F, 0.0F, 1.0F, 0.0F);
        float f5 = -25.0F;
        ent.renderYawOffset = f5;
        ent.rotationYaw = f5;
        ent.rotationPitch = 0.0F;
        ent.rotationYawHead = 0.0F;
        ent.prevRotationYawHead = 0.0F;
        GlStateManager.translatef(0.0F, 0.0F, 0.0F);
        EntityRendererManager rendermanager = Minecraft.getInstance().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.activeTexture(1);
        GlStateManager.disableTexture();
        GlStateManager.activeTexture(0);
    }
	
	
}
