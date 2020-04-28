/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import java.util.List;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import fr.fifoube.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifoube.gui.container.ContainerVault2by2;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class GuiVaultSettings2by2  extends ContainerScreen<ContainerVault2by2>
{
	private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/screen/gui_item.png");
	protected int xSize = 256;
	protected int ySize = 124;
	protected int guiLeft;
	protected int guiTop;
	protected TextFieldWidget commandTextField;
	protected List<Button> properButtonList = Lists.<Button>newArrayList();
	protected TileEntityBlockVault2by2 tile;
	protected Button properSelectedButton;
	
	
	public GuiVaultSettings2by2(ContainerVault2by2 container, PlayerInventory playerInventory, ITextComponent name) 
	{
		super(container, playerInventory, name);
		this.tile = getContainer().getTile();
	}
	
	@Override
	public void tick() {
		
	    this.commandTextField.tick();
	    this.properButtonList.clear();
	    for(int i = 0; i < tile.getAllowedPlayers().size(); i++)
	    {
	    	this.properButtonList.add(new Button((width / 2) + 35, ((height /2) - 55) + i * 20, 40, 13, TextFormatting.DARK_RED + "✖", actionPerformed()));
	    }
		if(tile.getMax() == 5)
		{
			this.commandTextField.setEnabled(false);
			this.commandTextField.setText("Max players allowed reached");
		}
	}

	private IPressable actionPerformed() {
		return null;
	}

	@Override
	protected void init() {
		super.init();
		this.minecraft.keyboardListener.enableRepeatEvents(true);
		this.commandTextField = new TextFieldWidget(this.font, this.width / 2 - 75, this.height / 2 - 70, 150, 20, null);
	    this.commandTextField.setMaxStringLength(35);
	    this.commandTextField.setText("Add other players.");
	    this.children.add(this.commandTextField);
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) 
	{
		this.renderBackground();
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); 
	    this.minecraft.getTextureManager().bindTexture(background); 
	    int k = (this.width - this.xSize) / 2; 
	    int l = (this.height - this.ySize) / 2;
	    this.blit(k, l, 0, 0, this.xSize, this.ySize); 
	    super.render(mouseX, mouseY, partialTicks);
	    if(!Minecraft.getInstance().isSingleplayer())
	    {
		    if(tile.getOwnerS().equals(Minecraft.getInstance().player.getUniqueID().toString()))
		    {
		    	this.commandTextField.render(mouseX, mouseY, partialTicks);
		    }
	    }
	    for(int i = 0; i < tile.getAllowedPlayers().size(); i++)
	    {
	    	this.drawString(font, tile.getAllowedPlayers().get(i).toString(), (width / 2) - 70, ((height /2) - 52) + i * 20, 0x00);
	    }
	    for (int j = 0; j < this.properButtonList.size(); ++j)
	    {
	        ((Button)this.properButtonList.get(j)).renderButton(mouseX, mouseY, partialTicks);
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
		    List<AbstractClientPlayerEntity> playerList = Minecraft.getInstance().world.getPlayers();
		    if(playerList != null)
		    {
		    	for(int i = 0; i < playerList.size(); i++)
		    	{
		    		if(playerList.get(i).getName().getString().equals(s))
		    		{
		    			UUID playerUUID = playerList.get(i).getUniqueID();
		    			tile.addAllowedPlayers(playerUUID.toString());
		    		}
		    	}
		        tile.addToMax();
		    }
	}
	
	@Override
	public void onClose() {
		super.onClose();
		this.getMinecraft().keyboardListener.enableRepeatEvents(false);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
	}
	
}