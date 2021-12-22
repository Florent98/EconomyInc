/**
 * Copyright 2020, Turrioni Florent, All rights reserved.
 * <p>
 * This program is copyrighted for all the files and code
 * included in this program. No reuse, modification or
 * reselling is authorized without any legal document
 * approved by the owner*.
 * <p>
 * *Owner : Turrioni Florent resident in Belgium and
 * contactable at florent_turrioni@hotmail.com
 */

package fr.fifoube.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RefillIconButton extends Button {

    private ResourceLocation resources = null;
    private boolean refill = true;

    public RefillIconButton(int x, int y, Button.IPressable press, ResourceLocation resource) {
        super(x, y, 20, 20, new TranslationTextComponent("narrator.button.restock"), press);
        this.resources = resource;
    }


    @Override
    protected IFormattableTextComponent getNarrationMessage() {
        return super.getNarrationMessage().appendString(". ").appendSibling(new TranslationTextComponent("narrator.button.restock"));
    }


    @Override
    public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        Minecraft.getInstance().getTextureManager().bindTexture(resources);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RefillIconButton.Icon refillIcon;
        if (this.isHovered()) {
            refillIcon = this.refill ? RefillIconButton.Icon.REFILL_HOVER : RefillIconButton.Icon.REFILL_HOVER_DISABLED;
        } else {
            refillIcon = this.refill ? RefillIconButton.Icon.REFILL : RefillIconButton.Icon.REFILL_DISABLED;
        }
        this.blit(matrixStack, this.x, this.y, refillIcon.getX(), refillIcon.getY(), this.width, this.height);
    }

    public boolean isRefillEnabled() {
        return refill;
    }

    public void setRefillMode(boolean mode) {
        this.refill = mode;
    }

    @OnlyIn(Dist.CLIENT)
    static enum Icon {

        REFILL(176, 0),
        REFILL_HOVER(176, 20),
        REFILL_DISABLED(176, 40),
        REFILL_HOVER_DISABLED(176, 60);

        private final int x;
        private final int y;

        private Icon(int xIn, int yIn) {
            this.x = xIn;
            this.y = yIn;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }

}
