/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.fifoube.blocks.blockentity.BlockEntitySeller;
import fr.fifoube.gui.container.MenuSellerBuy;
import fr.fifoube.gui.utilities.GuiUtilities;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.packets.PacketSellerFundsTotal;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class GuiSellerBuy extends AbstractContainerScreen<MenuSellerBuy>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/screen/gui_item.png");
	private BlockEntitySeller tile;
	private MenuSellerBuy menu;
	protected int xSize = 256;
	protected int ySize = 124;
	protected int guiLeft;
	protected int guiTop;
	
	private Button buy;
	private Button takeFunds;
	private String owner = "";
	private String itemName = "";
	private double cost;
	private int amount;
	private double fundsTotalRecovery;
	private UUID sellerOwner;
	private UUID worldPlayer;
	private ItemRenderer renderer = null;
	
	
	public GuiSellerBuy(MenuSellerBuy menu, Inventory inv, Component comp) {
		super(menu, inv, comp);
		this.menu = menu;
		this.tile = menu.getTile();
	}
	
	@Override
	protected void containerTick() {
		
		super.containerTick();
		this.amount = tile.getAmount();
		this.fundsTotalRecovery = tile.getFundsTotal();
		if(menu.getCooldown() != 0)
		{
			buy.active = false;
		}
		else
		{
			buy.active = true;
		}
	}
	
	@Override
	protected void init() {
		
		this.guiLeft = (this.width - this.xSize) / 2;
	    this.guiTop = (this.height - this.ySize) / 2;
	    this.renderer = Minecraft.getInstance().getItemRenderer();
		if(tile != null)
		{
			this.owner = tile.getOwnerName();
			this.itemName = tile.getItem();
			this.cost = tile.getCost();
			this.buy = this.addRenderableWidget(new Button(width / 2 - 50, height / 2 + 27, 100, 20, new TranslatableComponent("title.buy"),(press) -> actionPerformed(0))); 
             
			sellerOwner = tile.getOwner();
			worldPlayer = minecraft.player.getUUID();
			if(sellerOwner.equals(worldPlayer))
			{
				this.takeFunds = this.addRenderableWidget(new Button(width / 2 + 20, height / 2 - 75, 100, 13, new TranslatableComponent("title.recover"),(press) -> actionPerformed(1))); 
			}
			
		}
		super.init();
	}
	
	
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	protected void actionPerformed(int buttonId)
	{		
		switch (buttonId) {
		case 0:
			PacketsRegistery.CHANNEL.sendToServer(new PacketSellerFundsTotal(tile.getBlockPos(), false)); //SENDING PACKET TO LET SERVER KNOW CHANGES WITH TOTAL FUNDS, COORDINATES AND AMOUNT
			break;
		case 1:
			PacketsRegistery.CHANNEL.sendToServer(new PacketSellerFundsTotal(tile.getBlockPos(), true)); //SENDING PACKET TO LET SERVER KNOW CHANGES WITH TOTAL FUNDS, COORDINATES AND AMOUNT
			break;
		}
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		
		this.renderBackground(poseStack);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);		
        this.blit(poseStack, this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
		this.drawImageInGui((this.width / 2) + 85, (this.height / 2) - 40);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.font.draw(poseStack, new TranslatableComponent("title.seller",  owner).withStyle(ChatFormatting.BOLD), (this.width / 2) - 120, (this.height / 2)- 55, Color.BLACK.getRGB());
		this.font.draw(poseStack, new TranslatableComponent("title.item", itemName).withStyle(ChatFormatting.BOLD), (this.width / 2) - 120, (this.height / 2)- 45, Color.BLACK.getRGB());
		this.font.draw(poseStack, new TranslatableComponent("title.cost", cost).withStyle(ChatFormatting.BOLD), (this.width / 2) - 120, (this.height / 2)- 35, Color.BLACK.getRGB());
		this.font.draw(poseStack, new TranslatableComponent("title.amount", amount).withStyle(ChatFormatting.BOLD), (this.width / 2) - 120, (this.height / 2)- 25, Color.BLACK.getRGB());
		if(sellerOwner.equals(worldPlayer))
		{
			this.font.draw(poseStack, new TranslatableComponent("title.fundsToRecover", fundsTotalRecovery).withStyle(ChatFormatting.BOLD), (this.width / 2) - 120, (this.height / 2)- 15, Color.BLACK.getRGB());
		}

	}
	
	@Override
	protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {
		
	}
	 

	@Override
	protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
	}

	public void drawImageInGui(int posX, int posY) 
	{
		ItemStack stack = new ItemStack(Blocks.BARRIER,1);
		if(!(tile.getAmount() == 0))
		{
			stack = new ItemStack(tile.getInventory().getStackInSlot(0).getItem(), 1);
		}
		GuiUtilities.renderGuiItem(renderer, this.getBlitOffset(), stack, posX, posY, renderer.getModel(stack, (Level)null, (LivingEntity)null, 0));
	}

	@Override
	public void onClose() {

		if(tile.getAutoRefill())
		{
			tile.refill();
		}
		super.onClose();
	}
}