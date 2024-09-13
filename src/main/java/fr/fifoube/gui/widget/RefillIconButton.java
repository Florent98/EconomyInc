/** 
 *  Copyright 2020, Turrioni Florent, All rights reserved.
 *  
 * 	This program is copyrighted for all the files and code 
 * 	included in this program. No reuse, modification or 
 * 	reselling is authorized without any legal document 
 *  approved by the owner*.
 * 
 * 	*Owner : Turrioni Florent resident in Belgium and 
 *  contactable at florent_turrioni@hotmail.com
 *  
 * */

package fr.fifoube.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RefillIconButton extends Button {

	private ResourceLocation resources = null;
	private boolean refill = true;

	public RefillIconButton(int x, int y, Button.OnPress press, ResourceLocation resource) {
		 super(x, y, 20, 20, new TranslatableComponent("narrator.button.restock"), press);
		 this.resources = resource;
	}

	@Override
	protected MutableComponent createNarrationMessage() {
		return super.createNarrationMessage().append(". ").append(new TranslatableComponent("narrator.button.restock"));
	}


	@Override
	public void renderButton(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

		Minecraft.getInstance().getTextureManager().getTexture(resources);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RefillIconButton.Icon refillIcon;
		if(this.isHovered)
		{
			refillIcon = this.refill ? RefillIconButton.Icon.REFILL_HOVER : RefillIconButton.Icon.REFILL_HOVER_DISABLED;
		}
		else
		{
			refillIcon = this.refill ? RefillIconButton.Icon.REFILL : RefillIconButton.Icon.REFILL_DISABLED;
		}
		this.blit(stack, this.x, this.y, refillIcon.getX(), refillIcon.getY(), this.width, this.height);

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
