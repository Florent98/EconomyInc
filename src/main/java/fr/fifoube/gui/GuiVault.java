package fr.fifoube.gui;

import java.awt.Color;
import org.lwjgl.opengl.GL11;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault;
import fr.fifoube.gui.container.ContainerVault;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiVault extends GuiContainer
{
	protected TileEntityBlockVault tile_getter;
	protected InventoryPlayer playerInventory_getter;

	public GuiVault(InventoryPlayer playerInventory, TileEntityBlockVault tile, World worldIn) 
	{
		super(new ContainerVault(playerInventory, tile));
		this.tile_getter = tile;
		this.playerInventory_getter = playerInventory;
		
	}
	
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/container/gui_vault.png");
	protected int xSize = 176;
	protected int ySize = 168;
	protected int guiLeft;
	protected int guiTop;
   
    public void initGui()
    {
        super.initGui();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if(tile_getter.getOwnerS().equals(mc.player.getUniqueID().toString()) && !Minecraft.getInstance().isSingleplayer())
        {
        	this.buttons.add(new GuiButtonExt(0, i + 161, j, 15, 15, TextFormatting.BOLD.toString() + TextFormatting.WHITE + "⚙"));
        }
    }
    
	@Override
	public void onGuiClosed() 
	{
		super.onGuiClosed();
		if(tile_getter.getIsOpen())
		{
			tile_getter.setIsOpen(false);
			tile_getter.markDirty();
			//PacketsRegistery.network.sendToServer(new PacketIsOpen(tile_getter.getPos().getX(), tile_getter.getPos().getY(), tile_getter.getPos().getZ(), false));
		}
	}
	
	/*@Override
	protected void actionPerformed(GuiButton button) throws IOException 
	{
		switch (button.id) {
		case 0:
			playerInventory_getter.player.openGui(ModEconomy.instance, GuiHandler.BLOCK_VAULT_SETTINGS, playerInventory_getter.player.world, tile_getter.getPos().getX(), tile_getter.getPos().getY(), tile_getter.getPos().getZ());
			break;

		}
	}*/
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		fontRenderer.drawString(new TextComponentTranslation(I18n.format("title.block_vault")).getFormattedText(), 8, 5, Color.DARK_GRAY.getRGB());
		fontRenderer.drawString(new TextComponentTranslation("Inventory").getFormattedText(), 8, 73, Color.DARK_GRAY.getRGB());
		
	}
				
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
	       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); 
	       this.mc.getTextureManager().bindTexture(background); 
	       int k = (this.width - this.xSize) / 2; 
	       int l = (this.height - this.ySize) / 2;
	       this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize); 
	}	
	
}