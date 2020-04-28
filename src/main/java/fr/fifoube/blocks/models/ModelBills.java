/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks.models;

import java.util.function.Function;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import fr.fifoube.blocks.tileentity.TileEntityBlockBills;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelBills extends Model
{
	TileEntityBlockBills te;
	ModelRenderer Bills01;
	ModelRenderer Bills02;
	ModelRenderer Bills03;
	ModelRenderer Bills04;
	ModelRenderer Bills05;
	ModelRenderer Bills06;
	ModelRenderer Bills07;
    ModelRenderer Bills08;
    ModelRenderer Bills09;
    ModelRenderer Bills10;
    ModelRenderer Bills11;
    ModelRenderer Bills12;
    ModelRenderer Bills13;
    ModelRenderer Bills14;
    ModelRenderer Bills15;
    ModelRenderer Bills16;
    ModelRenderer Bills17;
    ModelRenderer Bills18;
    ModelRenderer Bills19;
    ModelRenderer Bills20;
    ModelRenderer Bills21;
    ModelRenderer Bills22;
    ModelRenderer Bills23;
    ModelRenderer Bills24;
    ModelRenderer Bills25;
    ModelRenderer Bills26;
    ModelRenderer Bills27;
    ModelRenderer Bills28;
    ModelRenderer Bills29;
    ModelRenderer Bills30;
    ModelRenderer Bills31;
    ModelRenderer Bills32;
    ModelRenderer Bills33;
    ModelRenderer Bills34;
    ModelRenderer Bills35;
    ModelRenderer Bills36;
    ModelRenderer Bills37;
    ModelRenderer Bills38;
    ModelRenderer Bills39;
    ModelRenderer Bills40;
    ModelRenderer Bills41;
    ModelRenderer Bills42;
    ModelRenderer Bills43;
    ModelRenderer Bills44;
    ModelRenderer Bills45;
    ModelRenderer Bills46;
    ModelRenderer Bills47;
    ModelRenderer Bills48;
    ModelRenderer Bills49;
    ModelRenderer Bills50;
    ModelRenderer Bills51;
    ModelRenderer Bills52;
    ModelRenderer Bills53;
    ModelRenderer Bills54;
    ModelRenderer Bills55;
    ModelRenderer Bills56;
    ModelRenderer Bills57;
    ModelRenderer Bills58;
    ModelRenderer Bills59;
    ModelRenderer Bills60;
    ModelRenderer Bills61;
    ModelRenderer Bills62;
    ModelRenderer Bills63;
    ModelRenderer Bills64;
    ModelRenderer Platform;
  
    
    public ModelBills(Function<ResourceLocation, RenderType> f) {
    	
    	super(f);
    	Bills01 = new ModelRenderer( this, 0, 0 );
    	Bills01.setTextureSize( 256, 128 );
    	Bills01.addBox( -6F, -1.5F, -12F, 12, 3, 24);
    	Bills01.setRotationPoint( 0F, 21F, 0F );
        Bills02 = new ModelRenderer( this, 0, 0 );
        Bills02.setTextureSize( 256, 128 );
        Bills02.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills02.setRotationPoint( 12F, 21F, 0F );
        Bills03 = new ModelRenderer( this, 0, 0 );
        Bills03.setTextureSize( 256, 128 );
        Bills03.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills03.setRotationPoint( 24F, 21F, 0F );
        Bills04 = new ModelRenderer( this, 0, 0 );
        Bills04.setTextureSize( 256, 128 );
        Bills04.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills04.setRotationPoint( 36F, 21F, 0F );
        Bills05 = new ModelRenderer( this, 0, 0 );
        Bills05.setTextureSize( 256, 128 );
        Bills05.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills05.setRotationPoint( 0F, 21F, -24F );
        Bills06 = new ModelRenderer( this, 0, 0 );
        Bills06.setTextureSize( 256, 128 );
        Bills06.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills06.setRotationPoint( 12F, 21F, -24F );
        Bills07 = new ModelRenderer( this, 0, 0 );
        Bills07.setTextureSize( 256, 128 );
        Bills07.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills07.setRotationPoint( 24F, 21F, -24F );
        Bills08 = new ModelRenderer( this, 0, 0 );
        Bills08.setTextureSize( 256, 128 );
        Bills08.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills08.setRotationPoint( 36F, 21F, -24F );
        Bills09 = new ModelRenderer( this, 0, 0 );
        Bills09.setTextureSize( 256, 128 );
        Bills09.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills09.setRotationPoint( 0F, 18F, 0F );
        Bills10 = new ModelRenderer( this, 0, 0 );
        Bills10.setTextureSize( 256, 128 );
        Bills10.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills10.setRotationPoint( 12F, 18F, 0F );
        Bills11 = new ModelRenderer( this, 0, 0 );
        Bills11.setTextureSize( 256, 128 );
        Bills11.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills11.setRotationPoint( 24F, 18F, 0F );
        Bills12 = new ModelRenderer( this, 0, 0 );
        Bills12.setTextureSize( 256, 128 );
        Bills12.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills12.setRotationPoint( 36F, 18F, 0F );
        Bills13 = new ModelRenderer( this, 0, 0 );
        Bills13.setTextureSize( 256, 128 );
        Bills13.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills13.setRotationPoint( 0F, 18F, -24F );
        Bills14 = new ModelRenderer( this, 0, 0 );
        Bills14.setTextureSize( 256, 128 );
        Bills14.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills14.setRotationPoint( 12F, 18F, -24F );
        Bills15 = new ModelRenderer( this, 0, 0 );
        Bills15.setTextureSize( 256, 128 );
        Bills15.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills15.setRotationPoint( 24F, 18F, -24F );
        Bills16 = new ModelRenderer( this, 0, 0 );
        Bills16.setTextureSize( 256, 128 );
        Bills16.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills16.setRotationPoint( 36F, 18F, -24F );
        Bills17 = new ModelRenderer( this, 0, 0 );
        Bills17.setTextureSize( 256, 128 );
        Bills17.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills17.setRotationPoint( 0F, 15F, 0F );
        Bills18 = new ModelRenderer( this, 0, 0 );
        Bills18.setTextureSize( 256, 128 );
        Bills18.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills18.setRotationPoint( 12F, 15F, 0F );
        Bills19 = new ModelRenderer( this, 0, 0 );
        Bills19.setTextureSize( 256, 128 );
        Bills19.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills19.setRotationPoint( 24F, 15F, 0F );
        Bills20 = new ModelRenderer( this, 0, 0 );
        Bills20.setTextureSize( 256, 128 );
        Bills20.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills20.setRotationPoint( 36F, 15F, 0F );
        Bills21 = new ModelRenderer( this, 0, 0 );
        Bills21.setTextureSize( 256, 128 );
        Bills21.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills21.setRotationPoint( 0F, 15F, -24F );
        Bills22 = new ModelRenderer( this, 0, 0 );
        Bills22.setTextureSize( 256, 128 );
        Bills22.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills22.setRotationPoint( 12F, 15F, -24F );
        Bills23 = new ModelRenderer( this, 0, 0 );
        Bills23.setTextureSize( 256, 128 );
        Bills23.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills23.setRotationPoint( 24F, 15F, -24F );
        Bills24 = new ModelRenderer( this, 0, 0 );
        Bills24.setTextureSize( 256, 128 );
        Bills24.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills24.setRotationPoint( 36F, 15F, -24F );
        Bills25 = new ModelRenderer( this, 0, 0 );
        Bills25.setTextureSize( 256, 128 );
        Bills25.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills25.setRotationPoint( 0F, 12F, 0F );
        Bills26 = new ModelRenderer( this, 0, 0 );
        Bills26.setTextureSize( 256, 128 );
        Bills26.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills26.setRotationPoint( 12F, 12F, 0F );
        Bills27 = new ModelRenderer( this, 0, 0 );
        Bills27.setTextureSize( 256, 128 );
        Bills27.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills27.setRotationPoint( 24F, 12F, 0F );
        Bills28 = new ModelRenderer( this, 0, 0 );
        Bills28.setTextureSize( 256, 128 );
        Bills28.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills28.setRotationPoint( 36F, 12F, 0F );
        Bills29 = new ModelRenderer( this, 0, 0 );
        Bills29.setTextureSize( 256, 128 );
        Bills29.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills29.setRotationPoint( 0F, 12F, -24F );
        Bills30 = new ModelRenderer( this, 0, 0 );
        Bills30.setTextureSize( 256, 128 );
        Bills30.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills30.setRotationPoint( 12F, 12F, -24F );
        Bills31 = new ModelRenderer( this, 0, 0 );
        Bills31.setTextureSize( 256, 128 );
        Bills31.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills31.setRotationPoint( 24F, 12F, -24F );
        Bills32 = new ModelRenderer( this, 0, 0 );
        Bills32.setTextureSize( 256, 128 );
        Bills32.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills32.setRotationPoint( 36F, 12F, -24F );
        Bills33 = new ModelRenderer( this, 0, 0 );
        Bills33.setTextureSize( 256, 128 );
        Bills33.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills33.setRotationPoint( 0F, 9F, 0F );
        Bills34 = new ModelRenderer( this, 0, 0 );
        Bills34.setTextureSize( 256, 128 );
        Bills34.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills34.setRotationPoint( 12F, 9F, 0F );
        Bills35 = new ModelRenderer( this, 0, 0 );
        Bills35.setTextureSize( 256, 128 );
        Bills35.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills35.setRotationPoint( 24F, 9F, 0F );
        Bills36 = new ModelRenderer( this, 0, 0 );
        Bills36.setTextureSize( 256, 128 );
        Bills36.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills36.setRotationPoint( 36F, 9F, 0F );
        Bills37 = new ModelRenderer( this, 0, 0 );
        Bills37.setTextureSize( 256, 128 );
        Bills37.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills37.setRotationPoint( 0F, 9F, -24F );
        Bills38 = new ModelRenderer( this, 0, 0 );
        Bills38.setTextureSize( 256, 128 );
        Bills38.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills38.setRotationPoint( 12F, 9F, -24F );
        Bills39 = new ModelRenderer( this, 0, 0 );
        Bills39.setTextureSize( 256, 128 );
        Bills39.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills39.setRotationPoint( 24F, 9F, -24F );
        Bills40 = new ModelRenderer( this, 0, 0 );
        Bills40.setTextureSize( 256, 128 );
        Bills40.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills40.setRotationPoint( 36F, 9F, -24F );
        Bills41 = new ModelRenderer( this, 0, 0 );
        Bills41.setTextureSize( 256, 128 );
        Bills41.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills41.setRotationPoint( 0F, 6F, 0F );
        Bills42 = new ModelRenderer( this, 0, 0 );
        Bills42.setTextureSize( 256, 128 );
        Bills42.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills42.setRotationPoint( 12F, 6F, 0F );
        Bills43 = new ModelRenderer( this, 0, 0 );
        Bills43.setTextureSize( 256, 128 );
        Bills43.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills43.setRotationPoint( 24F, 6F, 0F );
        Bills44 = new ModelRenderer( this, 0, 0 );
        Bills44.setTextureSize( 256, 128 );
        Bills44.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills44.setRotationPoint( 36F, 6F, 0F );
        Bills45 = new ModelRenderer( this, 0, 0 );
        Bills45.setTextureSize( 256, 128 );
        Bills45.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills45.setRotationPoint( 0F, 6F, -24F );
        Bills46 = new ModelRenderer( this, 0, 0 );
        Bills46.setTextureSize( 256, 128 );
        Bills46.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills46.setRotationPoint( 12F, 6F, -24F );
        Bills47 = new ModelRenderer( this, 0, 0 );
        Bills47.setTextureSize( 256, 128 );
        Bills47.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills47.setRotationPoint( 24F, 6F, -24F );
        Bills48 = new ModelRenderer( this, 0, 0 );
        Bills48.setTextureSize( 256, 128 );
        Bills48.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills48.setRotationPoint( 36F, 6F, -24F );
        Bills49 = new ModelRenderer( this, 0, 0 );
        Bills49.setTextureSize( 256, 128 );
        Bills49.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills49.setRotationPoint( 0F, 3F, 0F );
        Bills50 = new ModelRenderer( this, 0, 0 );
        Bills50.setTextureSize( 256, 128 );
        Bills50.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills50.setRotationPoint( 12F, 3F, 0F );
        Bills51 = new ModelRenderer( this, 0, 0 );
        Bills51.setTextureSize( 256, 128 );
        Bills51.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills51.setRotationPoint( 24F, 3F, 0F );
        Bills52 = new ModelRenderer( this, 0, 0 );
        Bills52.setTextureSize( 256, 128 );
        Bills52.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills52.setRotationPoint( 0F, 3F, -24F );
        Bills53 = new ModelRenderer( this, 0, 0 );
        Bills53.setTextureSize( 256, 128 );
        Bills53.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills53.setRotationPoint( 24F, 3F, -24F );
        Bills54 = new ModelRenderer( this, 0, 0 );
        Bills54.setTextureSize( 256, 128 );
        Bills54.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills54.setRotationPoint( 36F, 3F, -24F );
        Bills55 = new ModelRenderer( this, 0, 0 );
        Bills55.setTextureSize( 256, 128 );
        Bills55.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills55.setRotationPoint( 0F, 0F, 0F );
        Bills56 = new ModelRenderer( this, 0, 0 );
        Bills56.setTextureSize( 256, 128 );
        Bills56.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills56.setRotationPoint( 12F, 0F, 0F );
        Bills57 = new ModelRenderer( this, 0, 0 );
        Bills57.setTextureSize( 256, 128 );
        Bills57.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills57.setRotationPoint( 24F, 0F, 0F );
        Bills58 = new ModelRenderer( this, 0, 0 );
        Bills58.setTextureSize( 256, 128 );
        Bills58.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills58.setRotationPoint( 0F, 0F, -24F );
        Bills59 = new ModelRenderer( this, 0, 0 );
        Bills59.setTextureSize( 256, 128 );
        Bills59.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills59.setRotationPoint( 24F, 0F, -24F );
        Bills60 = new ModelRenderer( this, 0, 0 );
        Bills60.setTextureSize( 256, 128 );
        Bills60.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills60.setRotationPoint( 36F, 0F, -24F );
        Bills61 = new ModelRenderer( this, 0, 0 );
        Bills61.setTextureSize( 256, 128 );
        Bills61.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills61.setRotationPoint( 0F, -3F, 0F );
        Bills62 = new ModelRenderer( this, 0, 0 );
        Bills62.setTextureSize( 256, 128 );
        Bills62.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills62.setRotationPoint( 24F, -3F, 0F );
        Bills63 = new ModelRenderer( this, 0, 0 );
        Bills63.setTextureSize( 256, 128 );
        Bills63.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills63.setRotationPoint( 0F, -6F, 0F );
        Bills64 = new ModelRenderer( this, 0, 0 );
        Bills64.setTextureSize( 256, 128 );
        Bills64.addBox( -6F, -1.5F, -12F, 12, 3, 24);
        Bills64.setRotationPoint( 0F, -9F, 0F );
        Platform = new ModelRenderer( this, 0, 77 );
        Platform.setTextureSize( 256, 128 );
        Platform.addBox( -24F, -1.5F, -24F, 48, 3, 48);
        Platform.setRotationPoint( 18F, 24F, -12F );
    }
    
   public void renderAll(TileEntityBlockBills te, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
   {
	   	this.te = te;
    	render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
   }

   @Override   
   public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
	   
	   Platform.rotateAngleX = 0F;
       Platform.rotateAngleY = 0F;
       Platform.rotateAngleZ = 0F;
       Platform.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
       
	   		if (te.getNumbBills() >= 1) 
	   		{
	        Bills01.rotateAngleX = 0F;
	        Bills01.rotateAngleY = 0F;
	        Bills01.rotateAngleZ = 0F;
	        Bills01.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	   		}
	        
	        if (te.getNumbBills() >= 2) 
	        {
	        Bills02.rotateAngleX = 0F;
	        Bills02.rotateAngleY = 0F;
	        Bills02.rotateAngleZ = 0F;
	        Bills02.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }


	        if (te.getNumbBills() >= 3) 
	        {
	        Bills03.rotateAngleX = 0F;
	        Bills03.rotateAngleY = 0F;
	        Bills03.rotateAngleZ = 0F;
	        Bills03.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 4) 
	        {
	        Bills04.rotateAngleX = 0F;
	        Bills04.rotateAngleY = 0F;
	        Bills04.rotateAngleZ = 0F;
	        Bills04.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 5) 
	        {
	        Bills05.rotateAngleX = 0F;
	        Bills05.rotateAngleY = 0F;
	        Bills05.rotateAngleZ = 0F;
	        Bills05.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 6) 
	        {
	        Bills06.rotateAngleX = 0F;
	        Bills06.rotateAngleY = 0F;
	        Bills06.rotateAngleZ = 0F;
	        Bills06.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 7) 
	        {
	        Bills07.rotateAngleX = 0F;
	        Bills07.rotateAngleY = 0F;
	        Bills07.rotateAngleZ = 0F;
	        Bills07.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 8) 
	        {
	        Bills08.rotateAngleX = 0F;
	        Bills08.rotateAngleY = 0F;
	        Bills08.rotateAngleZ = 0F;
	        Bills08.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 9) 
	        {
	        Bills09.rotateAngleX = 0F;
	        Bills09.rotateAngleY = 0F;
	        Bills09.rotateAngleZ = 0F;
	        Bills09.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 10) 
	        {
	        Bills10.rotateAngleX = 0F;
	        Bills10.rotateAngleY = 0F;
	        Bills10.rotateAngleZ = 0F;
	        Bills10.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 11) 
	        {
	        Bills11.rotateAngleX = 0F;
	        Bills11.rotateAngleY = 0F;
	        Bills11.rotateAngleZ = 0F;
	        Bills11.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 12) 
	        {
	        Bills12.rotateAngleX = 0F;
	        Bills12.rotateAngleY = 0F;
	        Bills12.rotateAngleZ = 0F;
	        Bills12.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 13) 
	        {
	        Bills13.rotateAngleX = 0F;
	        Bills13.rotateAngleY = 0F;
	        Bills13.rotateAngleZ = 0F;
	        Bills13.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 14) 
	        {
	        Bills14.rotateAngleX = 0F;
	        Bills14.rotateAngleY = 0F;
	        Bills14.rotateAngleZ = 0F;
	        Bills14.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 15) 
	        {
	        Bills15.rotateAngleX = 0F;
	        Bills15.rotateAngleY = 0F;
	        Bills15.rotateAngleZ = 0F;
	        Bills15.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 16) 
	        {
	        Bills16.rotateAngleX = 0F;
	        Bills16.rotateAngleY = 0F;
	        Bills16.rotateAngleZ = 0F;
	        Bills16.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 17) 
	        {
	        Bills17.rotateAngleX = 0F;
	        Bills17.rotateAngleY = 0F;
	        Bills17.rotateAngleZ = 0F;
	        Bills17.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 18) 
	        {
	        Bills18.rotateAngleX = 0F;
	        Bills18.rotateAngleY = 0F;
	        Bills18.rotateAngleZ = 0F;
	        Bills18.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 19) 
	        {
	        Bills19.rotateAngleX = 0F;
	        Bills19.rotateAngleY = 0F;
	        Bills19.rotateAngleZ = 0F;
	        Bills19.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 20) 
	        {
	        Bills20.rotateAngleX = 0F;
	        Bills20.rotateAngleY = 0F;
	        Bills20.rotateAngleZ = 0F;
	        Bills20.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 21) 
	        {
	        Bills21.rotateAngleX = 0F;
	        Bills21.rotateAngleY = 0F;
	        Bills21.rotateAngleZ = 0F;
	        Bills21.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 22)
	        {
	        Bills22.rotateAngleX = 0F;
	        Bills22.rotateAngleY = 0F;
	        Bills22.rotateAngleZ = 0F;
	        Bills22.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 23) 
	        {
	        Bills23.rotateAngleX = 0F;
	        Bills23.rotateAngleY = 0F;
	        Bills23.rotateAngleZ = 0F;
	        Bills23.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 24) 
	        {
	        Bills24.rotateAngleX = 0F;
	        Bills24.rotateAngleY = 0F;
	        Bills24.rotateAngleZ = 0F;
	        Bills24.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 25) 
	        {
	        Bills25.rotateAngleX = 0F;
	        Bills25.rotateAngleY = 0F;
	        Bills25.rotateAngleZ = 0F;
	        Bills25.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 26) 
	        {
	        Bills26.rotateAngleX = 0F;
	        Bills26.rotateAngleY = 0F;
	        Bills26.rotateAngleZ = 0F;
	        Bills26.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 27)
	        {
	        Bills27.rotateAngleX = 0F;
	        Bills27.rotateAngleY = 0F;
	        Bills27.rotateAngleZ = 0F;
	        Bills27.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 28) 
	        {
	        Bills28.rotateAngleX = 0F;
	        Bills28.rotateAngleY = 0F;
	        Bills28.rotateAngleZ = 0F;
	        Bills28.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 29) 
	        {
	        Bills29.rotateAngleX = 0F;
	        Bills29.rotateAngleY = 0F;
	        Bills29.rotateAngleZ = 0F;
	        Bills29.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 30) 
	        {
	        Bills30.rotateAngleX = 0F;
	        Bills30.rotateAngleY = 0F;
	        Bills30.rotateAngleZ = 0F;
	        Bills30.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 31) 
	        {
	        Bills31.rotateAngleX = 0F;
	        Bills31.rotateAngleY = 0F;
	        Bills31.rotateAngleZ = 0F;
	        Bills31.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 32) 
	        {
	        Bills32.rotateAngleX = 0F;
	        Bills32.rotateAngleY = 0F;
	        Bills32.rotateAngleZ = 0F;
	        Bills32.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 33) 
	        {
	        Bills33.rotateAngleX = 0F;
	        Bills33.rotateAngleY = 0F;
	        Bills33.rotateAngleZ = 0F;
	        Bills33.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 34) 
	        {
	        Bills34.rotateAngleX = 0F;
	        Bills34.rotateAngleY = 0F;
	        Bills34.rotateAngleZ = 0F;
	        Bills34.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 35) 
	        {
	        Bills35.rotateAngleX = 0F;
	        Bills35.rotateAngleY = 0F;
	        Bills35.rotateAngleZ = 0F;
	        Bills35.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 36) 
	        {
	        Bills36.rotateAngleX = 0F;
	        Bills36.rotateAngleY = 0F;
	        Bills36.rotateAngleZ = 0F;
	        Bills36.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 37) 
	        {
	        Bills37.rotateAngleX = 0F;
	        Bills37.rotateAngleY = 0F;
	        Bills37.rotateAngleZ = 0F;
	        Bills37.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 38) 
	        {
	        Bills38.rotateAngleX = 0F;
	        Bills38.rotateAngleY = 0F;
	        Bills38.rotateAngleZ = 0F;
	        Bills38.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 39) 
	        {
	        Bills39.rotateAngleX = 0F;
	        Bills39.rotateAngleY = 0F;
	        Bills39.rotateAngleZ = 0F;
	        Bills39.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 40) 
	        {
	        Bills40.rotateAngleX = 0F;
	        Bills40.rotateAngleY = 0F;
	        Bills40.rotateAngleZ = 0F;
	        Bills40.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 41) 
	        {
	        Bills41.rotateAngleX = 0F;
	        Bills41.rotateAngleY = 0F;
	        Bills41.rotateAngleZ = 0F;
	        Bills41.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 42) 
	        {
	        Bills42.rotateAngleX = 0F;
	        Bills42.rotateAngleY = 0F;
	        Bills42.rotateAngleZ = 0F;
	        Bills42.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 43) 
	        {
	        Bills43.rotateAngleX = 0F;
	        Bills43.rotateAngleY = 0F;
	        Bills43.rotateAngleZ = 0F;
	        Bills43.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 44)
	        {
	        Bills44.rotateAngleX = 0F;
	        Bills44.rotateAngleY = 0F;
	        Bills44.rotateAngleZ = 0F;
	        Bills44.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 45) 
	        {
	        Bills45.rotateAngleX = 0F;
	        Bills45.rotateAngleY = 0F;
	        Bills45.rotateAngleZ = 0F;
	        Bills45.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 46) 
	        {
	        Bills46.rotateAngleX = 0F;
	        Bills46.rotateAngleY = 0F;
	        Bills46.rotateAngleZ = 0F;
	        Bills46.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 47)
	        {
	        Bills47.rotateAngleX = 0F;
	        Bills47.rotateAngleY = 0F;
	        Bills47.rotateAngleZ = 0F;
	        Bills47.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 48) 
	        {
	        Bills48.rotateAngleX = 0F;
	        Bills48.rotateAngleY = 0F;
	        Bills48.rotateAngleZ = 0F;
	        Bills48.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 49) 
	        {
	        Bills49.rotateAngleX = 0F;
	        Bills49.rotateAngleY = 0F;
	        Bills49.rotateAngleZ = 0F;
	        Bills49.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 50) 
	        {
	        Bills50.rotateAngleX = 0F;
	        Bills50.rotateAngleY = 0F;
	        Bills50.rotateAngleZ = 0F;
	        Bills50.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 51) 
	        {
	        Bills51.rotateAngleX = 0F;
	        Bills51.rotateAngleY = 0F;
	        Bills51.rotateAngleZ = 0F;
	        Bills51.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 52) 
	        {
	        Bills52.rotateAngleX = 0F;
	        Bills52.rotateAngleY = 0F;
	        Bills52.rotateAngleZ = 0F;
	        Bills52.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 53) 
	        {
	        Bills53.rotateAngleX = 0F;
	        Bills53.rotateAngleY = 0F;
	        Bills53.rotateAngleZ = 0F;
	        Bills53.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 54) 
	        {
	        Bills54.rotateAngleX = 0F;
	        Bills54.rotateAngleY = 0F;
	        Bills54.rotateAngleZ = 0F;
	        Bills54.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 55)
	        {
	        Bills55.rotateAngleX = 0F;
	        Bills55.rotateAngleY = 0F;
	        Bills55.rotateAngleZ = 0F;
	        Bills55.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 56) 
	        {
	        Bills56.rotateAngleX = 0F;
	        Bills56.rotateAngleY = 0F;
	        Bills56.rotateAngleZ = 0F;
	        Bills56.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 57) 
	        {
	        Bills57.rotateAngleX = 0F;
	        Bills57.rotateAngleY = 0F;
	        Bills57.rotateAngleZ = 0F;
	        Bills57.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 58) 
	        {
	        Bills58.rotateAngleX = 0F;
	        Bills58.rotateAngleY = 0F;
	        Bills58.rotateAngleZ = 0F;
	        Bills58.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 59) 
	        {
	        Bills59.rotateAngleX = 0F;
	        Bills59.rotateAngleY = 0F;
	        Bills59.rotateAngleZ = 0F;
	        Bills59.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 60) 
	        {
	        Bills60.rotateAngleX = 0F;
	        Bills60.rotateAngleY = 0F;
	        Bills60.rotateAngleZ = 0F;
	        Bills60.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 61) 
	        {
	        Bills61.rotateAngleX = 0F;
	        Bills61.rotateAngleY = 0F;
	        Bills61.rotateAngleZ = 0F;
	        Bills61.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 62) 
	        {
	        Bills62.rotateAngleX = 0F;
	        Bills62.rotateAngleY = 0F;
	        Bills62.rotateAngleZ = 0F;
	        Bills62.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 63) 
	        {
	        Bills63.rotateAngleX = 0F;
	        Bills63.rotateAngleY = 0F;
	        Bills63.rotateAngleZ = 0F;
	        Bills63.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

	        if (te.getNumbBills() >= 64) 
	        {
	        Bills64.rotateAngleX = 0F;
	        Bills64.rotateAngleY = 0F;
	        Bills64.rotateAngleZ = 0F;
	        Bills64.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        }

   }



}