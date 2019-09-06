package fr.fifoube.blocks.models;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;

public class ModelVault2by2 extends Model
{
	RendererModel BASE;
	RendererModel WheelBar_3;
	RendererModel WheelBar_2;
	RendererModel WheelBar_2_Ball;
	RendererModel WheelBar_4_Thin;
	RendererModel WheelBar_3_Ball;
	RendererModel WheelBar_3_Thin;
	RendererModel WheelBar_4_Ball;
	RendererModel WheelBar_4;
	RendererModel WheelBar_1_Ball;
    RendererModel Wheel_1;
    RendererModel Panel_front;
    RendererModel Wheel_2;
    RendererModel WheelBar_1_Thin;
    RendererModel Wheel_3;
    RendererModel WheelBar_2_Thin;
    RendererModel Roof;
    RendererModel Panel_back;
    RendererModel Panel_right;
    RendererModel WheelBar_1;
    RendererModel Panel_left;
    RendererModel plank_support;
    RendererModel Bills_duplication;
    RendererModel Bills2;
    RendererModel Bills3;
    RendererModel Bills4;
    RendererModel Bills5;
    RendererModel Bills1;
    RendererModel Bills21;
    RendererModel Bills31;
    RendererModel Bills41;
    RendererModel Bills51;

    public ModelVault2by2()
    {
        this( 0.0f );
    }

    public ModelVault2by2( float par1 )
    {
        BASE = new RendererModel( this, 0, 3 );
        BASE.setTextureSize( 256, 128 );
        BASE.addBox( -8F, -0.5F, -16F, 16, 1, 32);
        BASE.setRotationPoint( -8F, 23.5F, 0F );
        WheelBar_3 = new RendererModel( this, 46, 70 );
        WheelBar_3.setTextureSize( 256, 128 );
        WheelBar_3.addBox( -1F, -1F, -3F, 2, 2, 6);
        WheelBar_3.setRotationPoint( -20F, 8F, 5F );
        WheelBar_2 = new RendererModel( this, 36, 70 );
        WheelBar_2.setTextureSize( 256, 128 );
        WheelBar_2.addBox( -1F, -3F, -1F, 2, 6, 2);
        WheelBar_2.setRotationPoint( -20F, 13F, 0F );
        WheelBar_2_Ball = new RendererModel( this, 64, 74 );
        WheelBar_2_Ball.setTextureSize( 256, 128 );
        WheelBar_2_Ball.addBox( -1F, -1F, -1F, 2, 2, 2);
        WheelBar_2_Ball.setRotationPoint( -20F, 18F, 0F );
        WheelBar_4_Thin = new RendererModel( this, 66, 70 );
        WheelBar_4_Thin.setTextureSize( 256, 128 );
        WheelBar_4_Thin.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
        WheelBar_4_Thin.setRotationPoint( -20F, 8F, -8.5F );
        WheelBar_3_Ball = new RendererModel( this, 64, 74 );
        WheelBar_3_Ball.setTextureSize( 256, 128 );
        WheelBar_3_Ball.addBox( -1F, -1F, -1F, 2, 2, 2);
        WheelBar_3_Ball.setRotationPoint( -20F, 8F, 10F );
        WheelBar_3_Thin = new RendererModel( this, 66, 70 );
        WheelBar_3_Thin.setTextureSize( 256, 128 );
        WheelBar_3_Thin.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
        WheelBar_3_Thin.setRotationPoint( -20F, 8F, 8.5F );
        WheelBar_4_Ball = new RendererModel( this, 64, 74 );
        WheelBar_4_Ball.setTextureSize( 256, 128 );
        WheelBar_4_Ball.addBox( -1F, -1F, -1F, 2, 2, 2);
        WheelBar_4_Ball.setRotationPoint( -20F, 8F, -10F );
        WheelBar_4 = new RendererModel( this, 46, 70 );
        WheelBar_4.setTextureSize( 256, 128 );
        WheelBar_4.addBox( -1F, -1F, -3F, 2, 2, 6);
        WheelBar_4.setRotationPoint( -20F, 8F, -5F );
        WheelBar_1_Ball = new RendererModel( this, 64, 74 );
        WheelBar_1_Ball.setTextureSize( 256, 128 );
        WheelBar_1_Ball.addBox( -1F, -1F, -1F, 2, 2, 2);
        WheelBar_1_Ball.setRotationPoint( -20F, -2F, 0F );
        Wheel_1 = new RendererModel( this, 13, 70 );
        Wheel_1.setTextureSize( 256, 128 );
        Wheel_1.addBox( -1F, -2F, -2F, 2, 4, 4);
        Wheel_1.setRotationPoint( -16F, 8F, 0F );
        Panel_front = new RendererModel( this, 4, 68 );
        Panel_front.setTextureSize( 256, 128 );
        Panel_front.addBox( -0.5F, -15F, -15F, 1, 30, 30);
        Panel_front.setRotationPoint( -14.5F, 8F, 0F );
        Wheel_2 = new RendererModel( this, 26, 74 );
        Wheel_2.setTextureSize( 256, 128 );
        Wheel_2.addBox( -1F, -1F, -1F, 2, 2, 2);
        Wheel_2.setRotationPoint( -18F, 8F, 0F );
        WheelBar_1_Thin = new RendererModel( this, 66, 70 );
        WheelBar_1_Thin.setTextureSize( 256, 128 );
        WheelBar_1_Thin.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
        WheelBar_1_Thin.setRotationPoint( -20F, -0.5F, 0F );
        Wheel_3 = new RendererModel( this, 13, 70 );
        Wheel_3.setTextureSize( 256, 128 );
        Wheel_3.addBox( -1F, -2F, -2F, 2, 4, 4);
        Wheel_3.setRotationPoint( -20F, 8F, 0F );
        WheelBar_2_Thin = new RendererModel( this, 66, 70 );
        WheelBar_2_Thin.setTextureSize( 256, 128 );
        WheelBar_2_Thin.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
        WheelBar_2_Thin.setRotationPoint( -20F, 16.5F, 0F );
        Roof = new RendererModel( this, 0, 3 );
        Roof.setTextureSize( 256, 128 );
        Roof.addBox( -8F, -0.5F, -16F, 16, 1, 32);
        Roof.setRotationPoint( -8F, -7.5F, 0F );
        Panel_back = new RendererModel( this, 67, 49 );
        Panel_back.setTextureSize( 256, 128 );
        Panel_back.addBox( -0.5F, -15F, -15F, 1, 30, 30);
        Panel_back.setRotationPoint( -0.5F, 8F, 0F );
        Panel_right = new RendererModel( this, 0, 37 );
        Panel_right.setTextureSize( 256, 128 );
        Panel_right.addBox( -8F, -15F, -0.5F, 16, 30, 1);
        Panel_right.setRotationPoint( -8F, 8F, -15.5F );
        WheelBar_1 = new RendererModel( this, 36, 70 );
        WheelBar_1.setTextureSize( 256, 128 );
        WheelBar_1.addBox( -1F, -3F, -1F, 2, 6, 2);
        WheelBar_1.setRotationPoint( -20F, 3F, 0F );
        Panel_left = new RendererModel( this, 0, 37 );
        Panel_left.setTextureSize( 256, 128 );
        Panel_left.addBox( -8F, -15F, -0.5F, 16, 30, 1);
        Panel_left.setRotationPoint( -8F, 8F, 15.5F );
        plank_support = new RendererModel( this, 100, 98 );
        plank_support.setTextureSize( 256, 128 );
        plank_support.addBox( -7F, -0.5F, -14.5F, 14, 1, 29);
        plank_support.setRotationPoint( -7.5F, 8F, 0F );
        Bills_duplication = new RendererModel( this, 68, 113 );
        Bills_duplication.setTextureSize( 256, 128 );
        Bills_duplication.addBox( -6F, -2F, -4F, 12, 4, 8);
        Bills_duplication.setRotationPoint( -8F, 21F, 11F );
        Bills2 = new RendererModel( this, 68, 113 );
        Bills2.setTextureSize( 256, 128 );
        Bills2.addBox( -6F, -2F, -4F, 12, 4, 8);
        Bills2.setRotationPoint( -8F, 17F, 11F );
        Bills3 = new RendererModel( this, 97, 22 );
        Bills3.setTextureSize( 256, 128 );
        Bills3.addBox( -6F, -2F, -4F, 12, 4, 8);
        Bills3.setRotationPoint( -8F, 21F, -11F );
        Bills4 = new RendererModel( this, 100, 40 );
        Bills4.setTextureSize( 256, 128 );
        Bills4.addBox( -6F, -2F, -4F, 12, 4, 8);
        Bills4.setRotationPoint( -8F, 21F, 1F );
        Bills5 = new RendererModel( this, 100, 53 );
        Bills5.setTextureSize( 256, 128 );
        Bills5.addBox( -6F, -2F, -4F, 12, 4, 8);
        Bills5.setRotationPoint( -7F, 17F, -11F );
        Bills1 = new RendererModel( this, 68, 113 );
        Bills1.setTextureSize( 256, 128 );
        Bills1.addBox( -6F, -2F, -4F, 12, 4, 8);
        Bills1.setRotationPoint( -8F, 5.5F, 11F );
        Bills21 = new RendererModel( this, 100, 66 );
        Bills21.setTextureSize( 256, 128 );
        Bills21.addBox( -6F, -2F, -4F, 12, 4, 8);
        Bills21.setRotationPoint( -7F, 1.5F, 11F );
        Bills31 = new RendererModel( this, 100, 53 );
        Bills31.setTextureSize( 256, 128 );
        Bills31.addBox( -6F, -2F, -4F, 12, 4, 8);
        Bills31.setRotationPoint( -8F, 5.5F, -11F );
        Bills41 = new RendererModel( this, 97, 22 );
        Bills41.setTextureSize( 256, 128 );
        Bills41.addBox( -6F, -2F, -4F, 12, 4, 8);
        Bills41.setRotationPoint( -7F, 5.5F, 1F );
        Bills51 = new RendererModel( this, 100, 40 );
        Bills51.setTextureSize( 256, 128 );
        Bills51.addBox( -6F, -2F, -4F, 12, 4, 8);
        Bills51.setRotationPoint( -7F, 1.5F, -11F );
    }

   public void renderAll()
   {
	    float par7 = 0.0625F;
        BASE.rotateAngleX = 0F;
        BASE.rotateAngleY = 0F;
        BASE.rotateAngleZ = 0F;
        BASE.renderWithRotation(par7);

        WheelBar_3.rotateAngleX = 0F;
        WheelBar_3.rotateAngleY = 0F;
        WheelBar_3.rotateAngleZ = 0F;
        WheelBar_3.renderWithRotation(par7);

        WheelBar_2.rotateAngleX = 0F;
        WheelBar_2.rotateAngleY = 0F;
        WheelBar_2.rotateAngleZ = 0F;
        WheelBar_2.renderWithRotation(par7);

        WheelBar_2_Ball.rotateAngleX = 0F;
        WheelBar_2_Ball.rotateAngleY = 0F;
        WheelBar_2_Ball.rotateAngleZ = 0F;
        WheelBar_2_Ball.renderWithRotation(par7);

        WheelBar_4_Thin.rotateAngleX = 0F;
        WheelBar_4_Thin.rotateAngleY = 0F;
        WheelBar_4_Thin.rotateAngleZ = 0F;
        WheelBar_4_Thin.renderWithRotation(par7);

        WheelBar_3_Ball.rotateAngleX = 0F;
        WheelBar_3_Ball.rotateAngleY = 0F;
        WheelBar_3_Ball.rotateAngleZ = 0F;
        WheelBar_3_Ball.renderWithRotation(par7);

        WheelBar_3_Thin.rotateAngleX = 0F;
        WheelBar_3_Thin.rotateAngleY = 0F;
        WheelBar_3_Thin.rotateAngleZ = 0F;
        WheelBar_3_Thin.renderWithRotation(par7);

        WheelBar_4_Ball.rotateAngleX = 0F;
        WheelBar_4_Ball.rotateAngleY = 0F;
        WheelBar_4_Ball.rotateAngleZ = 0F;
        WheelBar_4_Ball.renderWithRotation(par7);

        WheelBar_4.rotateAngleX = 0F;
        WheelBar_4.rotateAngleY = 0F;
        WheelBar_4.rotateAngleZ = 0F;
        WheelBar_4.renderWithRotation(par7);

        WheelBar_1_Ball.rotateAngleX = 0F;
        WheelBar_1_Ball.rotateAngleY = 0F;
        WheelBar_1_Ball.rotateAngleZ = 0F;
        WheelBar_1_Ball.renderWithRotation(par7);

        Wheel_1.rotateAngleX = 0F;
        Wheel_1.rotateAngleY = 0F;
        Wheel_1.rotateAngleZ = 0F;
        Wheel_1.renderWithRotation(par7);

        Panel_front.rotateAngleX = 0F;
        Panel_front.rotateAngleY = 0F;
        Panel_front.rotateAngleZ = 0F;
        Panel_front.renderWithRotation(par7);

        Wheel_2.rotateAngleX = 0F;
        Wheel_2.rotateAngleY = 0F;
        Wheel_2.rotateAngleZ = 0F;
        Wheel_2.renderWithRotation(par7);

        WheelBar_1_Thin.rotateAngleX = 0F;
        WheelBar_1_Thin.rotateAngleY = 0F;
        WheelBar_1_Thin.rotateAngleZ = 0F;
        WheelBar_1_Thin.renderWithRotation(par7);

        Wheel_3.rotateAngleX = 0F;
        Wheel_3.rotateAngleY = 0F;
        Wheel_3.rotateAngleZ = 0F;
        Wheel_3.renderWithRotation(par7);

        WheelBar_2_Thin.rotateAngleX = 0F;
        WheelBar_2_Thin.rotateAngleY = 0F;
        WheelBar_2_Thin.rotateAngleZ = 0F;
        WheelBar_2_Thin.renderWithRotation(par7);

        Roof.rotateAngleX = 0F;
        Roof.rotateAngleY = 0F;
        Roof.rotateAngleZ = 0F;
        Roof.renderWithRotation(par7);

        Panel_back.rotateAngleX = 0F;
        Panel_back.rotateAngleY = 0F;
        Panel_back.rotateAngleZ = 0F;
        Panel_back.renderWithRotation(par7);

        Panel_right.rotateAngleX = 0F;
        Panel_right.rotateAngleY = 0F;
        Panel_right.rotateAngleZ = 0F;
        Panel_right.renderWithRotation(par7);

        WheelBar_1.rotateAngleX = 0F;
        WheelBar_1.rotateAngleY = 0F;
        WheelBar_1.rotateAngleZ = 0F;
        WheelBar_1.renderWithRotation(par7);

        Panel_left.rotateAngleX = 0F;
        Panel_left.rotateAngleY = 0F;
        Panel_left.rotateAngleZ = 0F;
        Panel_left.renderWithRotation(par7);

        plank_support.rotateAngleX = 0F;
        plank_support.rotateAngleY = 0F;
        plank_support.rotateAngleZ = 0F;
        plank_support.renderWithRotation(par7);

        Bills_duplication.rotateAngleX = 0F;
        Bills_duplication.rotateAngleY = 0F;
        Bills_duplication.rotateAngleZ = 0F;
        Bills_duplication.renderWithRotation(par7);

        Bills2.rotateAngleX = 0F;
        Bills2.rotateAngleY = 0F;
        Bills2.rotateAngleZ = 0F;
        Bills2.renderWithRotation(par7);

        Bills3.rotateAngleX = 0F;
        Bills3.rotateAngleY = 0F;
        Bills3.rotateAngleZ = 0F;
        Bills3.renderWithRotation(par7);

        Bills4.rotateAngleX = 0F;
        Bills4.rotateAngleY = 0F;
        Bills4.rotateAngleZ = 0F;
        Bills4.renderWithRotation(par7);

        Bills5.rotateAngleX = 0F;
        Bills5.rotateAngleY = 0F;
        Bills5.rotateAngleZ = 0F;
        Bills5.renderWithRotation(par7);

        Bills1.rotateAngleX = 0F;
        Bills1.rotateAngleY = 0F;
        Bills1.rotateAngleZ = 0F;
        Bills1.renderWithRotation(par7);

        Bills21.rotateAngleX = 0F;
        Bills21.rotateAngleY = 0F;
        Bills21.rotateAngleZ = 0F;
        Bills21.renderWithRotation(par7);

        Bills31.rotateAngleX = 0F;
        Bills31.rotateAngleY = 0F;
        Bills31.rotateAngleZ = 0F;
        Bills31.renderWithRotation(par7);

        Bills41.rotateAngleX = 0F;
        Bills41.rotateAngleY = 0F;
        Bills41.rotateAngleZ = 0F;
        Bills41.renderWithRotation(par7);

        Bills51.rotateAngleX = 0F;
        Bills51.rotateAngleY = 0F;
        Bills51.rotateAngleZ = 0F;
        Bills51.renderWithRotation(par7);

    }

}
