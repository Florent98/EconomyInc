package fr.fifoube.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.packets.PacketListNBT;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiVaultSettings2by2  extends GuiScreen
{
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/screen/gui_item.png");
	protected int xSize = 256;
	protected int ySize = 124;
	protected int guiLeft;
	protected int guiTop;
	protected GuiTextField commandTextField;
	protected List<GuiButton> properButtonList = Lists.<GuiButton>newArrayList();
	protected TileEntityBlockVault2by2 tile;
	protected GuiButton properSelectedButton;
	
	
	public GuiVaultSettings2by2(TileEntityBlockVault2by2 te) {
		
		this.tile = te;
	}
	
	@Override
	public void tick() {
		
	    this.commandTextField.tick();
	    this.properButtonList.clear();
	    for(int i = 0; i < tile.getAllowedPlayers().size(); i++)
	    {
	    	this.properButtonList.add(new GuiButtonExt(i, (width / 2) + 35, ((height /2) - 55) + i * 20, 40, 13, TextFormatting.DARK_RED + "✖"));
	    }
		if(tile.getMax() == 5)
		{
			this.commandTextField.setEnabled(false);
			this.commandTextField.setText("Max players allowed reached");
		}
	}

	@Override
	protected void initGui() {
		this.mc.keyboardListener.enableRepeatEvents(true);
		this.commandTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 75, this.height / 2 - 70, 150, 20) {

	      };
	      this.commandTextField.setMaxStringLength(35);
	      this.commandTextField.setText("Add other players.");
	      this.children.add(this.commandTextField);

	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) 
	{
		this.drawDefaultBackground();
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); 
	    this.mc.getTextureManager().bindTexture(background); 
	    int k = (this.width - this.xSize) / 2; 
	    int l = (this.height - this.ySize) / 2;
	    this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize); 
	    super.render(mouseX, mouseY, partialTicks);
	    if(!Minecraft.getInstance().isSingleplayer())
	    {
		    if(tile.getOwnerS().equals(Minecraft.getInstance().player.getUniqueID().toString()))
		    {
		    	this.commandTextField.drawTextField(mouseX, mouseY, partialTicks);
		    }
	    }
	    for(int i = 0; i < tile.getAllowedPlayers().size(); i++)
	    {
	    	this.fontRenderer.drawString(tile.getAllowedPlayers().get(i).toString(), (width / 2) - 70, ((height /2) - 52) + i * 20, 0x00);
	    }
	    for (int j = 0; j < this.properButtonList.size(); ++j)
	    {
	        ((GuiButton)this.properButtonList.get(j)).drawButtonForegroundLayer(mouseX, mouseY);
	    }
	}
	
	
	@Override
	public boolean keyPressed(int keyPressedCode, int p_keyPressed_2_, int p_keyPressed_3_) {

	      if (keyPressedCode == 257 || keyPressedCode == 335)
	      {
	    	  if(tile.getMax() < 5)
	        	{
			        this.addPlayerToTileEntity();
			        this.commandTextField.setText("");	
			        return true;
	        	}
	        	else
	        	{
	        		this.commandTextField.setText("Max players allowed reached ");
	        		return false;
	        	}
	      } 
	      else 
	      {
	          return super.keyPressed(keyPressedCode, p_keyPressed_2_, p_keyPressed_3_);
	      }
	}
	
	private void addPlayerToTileEntity() {
		
		    String s = this.commandTextField.getText();
		    EntityPlayer playerAdd = Minecraft.getInstance().world.getPlayerEntityByName(s);
		    if(playerAdd != null)
		    {
		        String playerAddUUID = playerAdd.getScoreboardName();
		        tile.addToMax();
		        PacketsRegistery.CHANNEL.sendToServer(new PacketListNBT(playerAddUUID, tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), true, "add", 0)); 
		    }
	}
	
	
	@Override
	public void onGuiClosed() 
	{
		super.onGuiClosed();
		this.mc.keyboardListener.enableRepeatEvents(false);
	}
}