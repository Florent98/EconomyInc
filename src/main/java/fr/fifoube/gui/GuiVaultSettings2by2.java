/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.fifoube.blocks.blockentity.BlockEntityVault2by2;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.packets.PacketVaultSettings;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class GuiVaultSettings2by2  extends Screen
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(ModEconomyInc.MOD_ID ,"textures/gui/screen/gui_item.png");
	protected BlockEntityVault2by2 tile;
	protected EditBox commandTextField;
	protected int xSize = 256;
	protected int ySize = 124;
	protected int guiLeft;
	protected int guiTop;
		
	List<Button> buttonList = new ArrayList<Button>();
		
	public GuiVaultSettings2by2(BlockEntityVault2by2 te) {
		super(Component.translatable("gui.vaultsettings"));
		this.tile = te;
	}
	
	@Override
	public void tick() {
		
		this.commandTextField.tick();
		if(tile.getMax() == 5)
		{
			this.commandTextField.active = false;
			this.commandTextField.insertText("Max players allowed reached");
		}	
	}

	@Override
	protected void init() {
		
		this.guiLeft = (this.width - this.xSize) / 2;
	    this.guiTop = (this.height - this.ySize) / 2;
		this.commandTextField = new EditBox(this.font, this.width / 2 - 75, this.height / 2 - 70, 150, 20, Component.translatable("gui.vaultsettings"));
	    this.commandTextField.setMaxLength(35);
	    this.commandTextField.insertText("Add other players.");
	    this.addWidget(this.commandTextField);
	    buttonList.clear();
	    for(int i = 0; i < 5; i++)
	    {
	    	int id = i;
			Button button = Button.builder(Component.literal("✖").withStyle(ChatFormatting.DARK_RED), (press) -> { this.actionPerformed(id);}).pos(((this.width - this.xSize) / 2) + 164, ((this.height - this.ySize) / 2) + (18 * (i + 1))).size(40, 13).build();
	    	buttonList.add(id, button);
	    	button.active = false;
	    	this.addRenderableWidget(button);
	    }
	    super.init();
	}
	
	
	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		
		this.renderBackground(stack);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
	    this.blit(stack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize); 
	    super.render(stack, mouseX, mouseY, partialTicks);
	    if(!Minecraft.getInstance().hasSingleplayerServer())
	    {
		    if(tile.getOwner().equals(Minecraft.getInstance().player.getUUID()))
		    {
		    	this.commandTextField.render(stack, mouseX, mouseY, partialTicks);
		    }
	    }
	    for (int i = 0; i < tile.getAllowedPlayers().size(); i++) {
	    	
    		String playerName = tile.getAllowedPlayers().get(i).substring(0, tile.getAllowedPlayers().get(i).indexOf(","));
    		this.font.draw(stack, playerName, ((this.width - this.xSize) / 2) + 52 , ((this.height - this.ySize) / 2) + (20 * (i + 1)), 0x00);
		}
	    for (int j = 0; j < 5; j++) {
			
	    	if(!tile.getAllowedPlayers().isEmpty())
	    	{
	    		if(tile.getAllowedPlayers().size() > j)
	    		{
	    			buttonList.get(j).active = true;
	    		}
	    		else
	    		{
	    			buttonList.get(j).active = false;
	    		}		
	    	}
	    	else
	    	{
    			buttonList.get(j).active = false;
	    	}
		}
	}
	
	
	
	protected void actionPerformed(int id)
	{		
        PacketsRegistery.CHANNEL.sendToServer(new PacketVaultSettings(tile.getBlockPos(), "", true, id));
	}

	
	
	@Override
	public boolean keyPressed(int keyPressedCode, int p_keyPressed_2_, int p_keyPressed_3_) {

	      if (keyPressedCode == 257 || keyPressedCode == 335)
	      {
	    	  if(tile.getMax() < 5)
	        	{
			        this.addPlayerToTileEntity();
			        this.commandTextField.insertText("");	
			        return true;
			    }
	        	else
	        	{
	        		this.commandTextField.insertText("Max players allowed reached ");
	        		return false;
	        	}
	      } 
	      return super.keyPressed(keyPressedCode, p_keyPressed_2_, p_keyPressed_3_);
	}
	
	private void addPlayerToTileEntity() {
		
		    String s = this.commandTextField.getValue();
		    List<AbstractClientPlayer> playerList = Minecraft.getInstance().level.players();
		    if(playerList != null)
		    {
		    	for(int i = 0; i < playerList.size(); i++)
		    	{
		    		if(playerList.get(i).getName().getString().equals(s))
		    		{
		    			if(!playerList.get(i).getUUID().equals(tile.getOwner()))
		    			{
		    				UUID playerUUID = playerList.get(i).getUUID();
		    				boolean flag = checkForSamePlayer(playerUUID);
		    				if(flag)
		    				{
		    					String playerName = playerList.get(i).getDisplayName().getString();
		    					PacketsRegistery.CHANNEL.sendToServer(new PacketVaultSettings(tile.getBlockPos(), playerName + "," + playerUUID.toString(), false, i));			    					
		    				}
		    			}
		    		}
		    	}
		    }
	}
	
	private boolean checkForSamePlayer(UUID uuid)
	{
		if(!tile.getAllowedPlayers().isEmpty())
		{
			for (int i = 0; i < tile.getAllowedPlayers().size(); i++) {
				
				String playerUUID = tile.getAllowedPlayers().get(i).substring(tile.getAllowedPlayers().get(i).indexOf(",") + 1);
				UUID uuidPlayer = UUID.fromString(playerUUID);
				if(uuidPlayer.equals(uuid))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public void onClose() {
		super.onClose();
	}

	
}