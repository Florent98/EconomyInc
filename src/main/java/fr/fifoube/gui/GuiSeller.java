/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import fr.fifoube.blocks.tileentity.TileEntityBlockSeller;
import fr.fifoube.gui.container.ContainerSeller;
import fr.fifoube.gui.widget.RefillIconButton;
import fr.fifoube.main.ModEconomyInc;
import fr.fifoube.packets.PacketSellerCreated;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class GuiSeller extends ContainerScreen<ContainerSeller> {

    private static final ResourceLocation background = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/gui/container/gui_seller.png");
    protected int xSize = 176;
    protected int ySize = 168;
    protected int guiLeft;
    protected int guiTop;
    private TileEntityBlockSeller tile;
    private Button validate;
    private Button unlimitedStack;
    private RefillIconButton autoRefill;
    private TextFieldWidget costField;

    private double cost;
    private boolean admin = false;

    private boolean validCost = false;

    public GuiSeller(ContainerSeller container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
        this.tile = getContainer().getTile();
    }


    @Override
    public void tick() {
        super.tick();
        this.validCost = checkIfTextCanBeParsedToPositiveDouble(this.costField.getText());
    }

    @Override
    protected void init() {

        super.init();
        this.fieldInit();
        PlayerEntity player = minecraft.player;
        if (!tile.getCreated()) {
            this.autoRefill = this.addButton(new RefillIconButton(width / 2 + 87, height / 2 - 75, (onPress) -> {
                actionPerformed(this.autoRefill);
            }, background));
            this.validate = this.addButton(new Button(width / 2 + 26, height / 2 + 83, 55, 20, new TranslationTextComponent("title.validate"), (onPress) -> {
                actionPerformed(this.validate);
            }));
            if (player.isCreative() == true) {
                this.unlimitedStack = this.addButton(new Button(width / 2 + 2, height / 2 - 96, 80, 13, new TranslationTextComponent(""), (onPress) -> {
                    actionPerformed(this.unlimitedStack);
                }));
            }
        }
    }

    protected void fieldInit() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.costField = new TextFieldWidget(this.font, i + 121, j + 15, 38, 12, new TranslationTextComponent("title.cost"));
        this.costField.setCanLoseFocus(false);
        this.costField.setTextColor(-1);
        this.costField.setDisabledTextColour(-1);
        this.costField.setEnableBackgroundDrawing(false);
        this.costField.setMaxStringLength(35);
        this.children.add(this.costField);
        this.setFocusedDefault(this.costField);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {

        String s = this.costField.getText();
        this.init(minecraft, width, height);
        this.costField.setText(s);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.minecraft.player.closeScreen();
        }

        return !this.costField.keyPressed(keyCode, scanCode, modifiers) && !this.costField.canWrite() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
    }

    protected void actionPerformed(Button button) {
        PlayerEntity playerIn = minecraft.player;
        if (tile != null) {
            if (button == this.unlimitedStack) {
                if (this.admin == false) {
                    this.admin = true;
                    tile.setAdmin(true);
                } else if (this.admin == true) {
                    this.admin = false;
                    tile.setAdmin(false);
                }
            } else if (button == this.autoRefill) {
                if (this.autoRefill.isRefillEnabled()) {
                    this.autoRefill.setRefillMode(false);
                } else {
                    this.autoRefill.setRefillMode(true);
                }
            } else if (button == this.validate) {
                if (this.validCost) {
                    this.cost = Double.valueOf(this.costField.getText());
                    tile.setCost(Double.valueOf(this.costField.getText()));
                    if (!(tile.getCost() == 0)) // IF TILE HAS NOT A COST OF 0 THEN WE PASS TO THE OTHER
                    {
                        if (tile.getStackInSlot(0).getItem() != Items.AIR) // IF SLOT 0 IS NOT BLOCKS.AIR, WE PASS
                        {
                            if (!this.admin) //ADMIN HASN'T SET : UNLIMITED STACK
                            {
                                tile.setAdmin(false);
                                tile.setCreated(true); // CLIENT SET CREATED AT TRUE
                                final int x = tile.getPos().getX(); // GET X
                                final int y = tile.getPos().getY(); // GET Y
                                final int z = tile.getPos().getZ(); // GET Z
                                int amount = tile.getStackInSlot(0).getCount(); // GET COUNT IN TILE THANKS TO STACK IN SLOT
                                String name = tile.getStackInSlot(0).getDisplayName().getString(); // GET ITEM NAME IN TILE THANKS TO STACK IN SLOT
                                tile.setItem(name); // CLIENT SET ITEM NAME
                                tile.setAutoRefill(this.autoRefill.isRefillEnabled());
                                tile.markDirty();
                                PacketsRegistery.CHANNEL.sendToServer(new PacketSellerCreated(true, this.cost, name, amount, x, y, z, false, tile.getAutoRefill())); // SEND SERVER PACKET FOR CREATED, COST, NAME, AMOUNT, X,Y,Z ARE TILE COORDINATES
                                playerIn.closeScreen(); // CLOSE SCREEN
                            } else if (this.admin) //ADMIN HAS SET : UNLIMITED STACK
                            {
                                tile.setAdmin(true);
                                tile.setCreated(true); // CLIENT SET CREATED AT TRUE
                                final int x = tile.getPos().getX(); // GET X
                                final int y = tile.getPos().getY(); // GET Y
                                final int z = tile.getPos().getZ(); // GET Z
                                int amount = tile.getStackInSlot(0).getCount(); // GET COUNT IN TILE THANKS TO STACK IN SLOT
                                String name = tile.getStackInSlot(0).getDisplayName().getString(); // GET ITEM NAME IN TILE THANKS TO STACK IN SLOT
                                tile.setItem(name); // CLIENT SET ITEM NAME
                                tile.setAutoRefill(this.autoRefill.isRefillEnabled());
                                tile.markDirty();
                                PacketsRegistery.CHANNEL.sendToServer(new PacketSellerCreated(true, this.cost, name, amount, x, y, z, true, tile.getAutoRefill())); // SEND SERVER PACKET FOR CREATED, COST, NAME, AMOUNT, X,Y,Z ARE TILE COORDINATES
                                playerIn.closeScreen(); // CLOSE SCREEN
                            }

                        } else // PROVIDE PLAYER TO SELL AIR
                        {
                            playerIn.sendMessage(new StringTextComponent(I18n.format("title.sellAir")), playerIn.getUniqueID());
                        }
                    } else // IT MEANS THAT PLAYER HAS NOT SELECTED A COST
                    {
                        playerIn.sendMessage(new StringTextComponent(I18n.format("title.noCost")), playerIn.getUniqueID());
                    }
                } else {
                    playerIn.sendMessage(new StringTextComponent(I18n.format("title.noValidCost")), playerIn.getUniqueID());
                }
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (!tile.getCreated()) {
            this.font.drawStringWithShadow(matrixStack, this.admin ? I18n.format("title.unlimited") : I18n.format("title.limited"), this.xSize - 82, this.ySize - 177, Color.WHITE.getRGB());
        }
        this.font.drawString(matrixStack, I18n.format("title.block_seller"), 8.0F, 5, Color.DARK_GRAY.getRGB());
        this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0F, (float) (this.ySize - 94), 4210752);
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.disableBlend();
        if (!tile.getCreated()) {
            this.costField.render(matrixStack, mouseX, mouseY, partialTicks);
        }
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {

        this.minecraft.getTextureManager().bindTexture(background);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.blit(matrixStack, k, l, 0, 0, this.xSize, this.ySize);
        this.blit(matrixStack, k + 117, l + 11, 0, this.ySize + 32, 110, 16);
        if (!tile.getCreated()) {
            this.blit(matrixStack, k + 117, l + 11, 0, this.ySize + (this.validCost ? 0 : 16), 110, 16);
        }

    }

    private boolean checkIfTextCanBeParsedToPositiveDouble(String field) {
        if (field != null) {
            try {
                double value = Double.parseDouble(field);
                if (value > 0) {
                    return true;
                }
                return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
