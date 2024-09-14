/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks.blockentity.specialrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import fr.fifoube.blocks.blockentity.BlockEntityBills;
import fr.fifoube.blocks.models.ModelBills;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

public class BlockEntityBillsSpecialRenderer implements BlockEntityRenderer<BlockEntityBills> {

    private static ResourceLocation texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_0.png");
    private final ModelBills<?> modelBlock;

    public BlockEntityBillsSpecialRenderer(BlockEntityRendererProvider.Context context) {
        modelBlock = new ModelBills<>(context.bakeLayer(ModelBills.LAYER_LOCATION));
    }

    @Override
    public void render(BlockEntityBills te, float partialTicks, PoseStack matrixStackIn, MultiBufferSource buffer, int light, int overlay) {

        checkBillRef(te);
        matrixStackIn.pushPose();
        switch (te.getDirection()) {
            case 0:
                matrixStackIn.translate(0.5F, 1.5F, 0.5F);
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(180f));
                break;
            case 1:
                matrixStackIn.translate(0.5F, 1.5F, 0.5F);
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(90f));
                break;
            case 2:
                matrixStackIn.translate(0.5F, 1.5F, 0.50F);
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(360f));
                break;
            case 3:
                matrixStackIn.translate(0.5F, 1.5F, 0.5F);
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(270f));
                break;
            default:
                break;
        }
        matrixStackIn.mulPose(Axis.ZP.rotationDegrees(180f));
        VertexConsumer renderBuffer = buffer.getBuffer(RenderType.entitySolid(texture));
        modelBlock.renderAll(te,matrixStackIn, renderBuffer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStackIn.popPose();

    }


    public void checkBillRef(BlockEntityBills tile)
    {
        switch (tile.getBillValue()) {
            case 1:
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_1.png");
                break;
            case 5:
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_5.png");
                break;
            case 10:
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_10.png");
                break;
            case 20:
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_20.png");
                break;
            case 50:
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_50.png");
                break;
            case 100:
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_100.png");
                break;
            case 200:
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_200.png");
                break;
            case 500:
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_500.png");
                break;
            default:
                break;
        }
    }
	
	
}
