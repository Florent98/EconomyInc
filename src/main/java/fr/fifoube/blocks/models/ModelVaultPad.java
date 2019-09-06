package fr.fifoube.blocks.models;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;

public class ModelVaultPad extends Model
{

	RendererModel Vault_Pad;

    public ModelVaultPad()
    {
        this( 0.0f );
    }

    public ModelVaultPad( float par1 )
    {

        Vault_Pad = new RendererModel( this, 181, 22 );
        Vault_Pad.setTextureSize( 256, 128 );
        Vault_Pad.addBox( -2F, -4F, -3F, 4 , 16 , 16);
        Vault_Pad.setRotationPoint( -14.4F, 17.5F, 11F );
    }

   public void renderAll()
   {
	   float par7 = 0.0625F;

        Vault_Pad.rotateAngleX = 0F;
        Vault_Pad.rotateAngleY = 0F;
        Vault_Pad.rotateAngleZ = 0.2084309F;
        Vault_Pad.renderWithRotation(par7);
    }
}