/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.blocks.tileentity.specialrenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.fifoube.blocks.models.ModelBills;
import fr.fifoube.blocks.tileentity.TileEntityBlockBills;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class TileEntityBlockBillsSpecialRenderer extends TileEntityRenderer<TileEntityBlockBills> {

    private static ResourceLocation texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_0.png");
    private static ModelBills modelBlock = new ModelBills(RenderType::getEntitySolid);

    public TileEntityBlockBillsSpecialRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileEntityBlockBills te, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        checkBillRef(te);
        matrixStackIn.push();
        switch (te.getDirection()) {
            case 0:
                matrixStackIn.translate(0.125F, 0.530F, 0.250F);
                matrixStackIn.rotate(new Quaternion(new Vector3f(0, 1.0f, 0), 180f, true));
                break;
            case 1:
                matrixStackIn.translate(0.750F, 0.530F, 0.125F);
                matrixStackIn.rotate(new Quaternion(new Vector3f(0, 1.0f, 0), 90f, true));
                break;
            case 2:
                matrixStackIn.translate(0.875F, 0.530F, 0.750F);
                matrixStackIn.rotate(new Quaternion(new Vector3f(0, 1.0f, 0), 360f, true));
                break;
            case 3:
                matrixStackIn.translate(0.250F, 0.530F, 0.875F);
                matrixStackIn.rotate(new Quaternion(new Vector3f(0, 1.0f, 0), 270f, true));
                break;
            default:
                break;
        }
        matrixStackIn.scale(0.3335f, 0.3335f, 0.3335f);
        matrixStackIn.rotate(new Quaternion(new Vector3f(0, 0, 1.0f), 180f, true));
        IVertexBuilder renderBuffer = bufferIn.getBuffer(modelBlock.getRenderType(texture));
        modelBlock.renderAll(te, matrixStackIn, renderBuffer, combinedLightIn, combinedOverlayIn, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStackIn.pop();
    }

    public void checkBillRef(TileEntityBlockBills tile) {
        switch (tile.getBillRef()) {
            case "item.economyinc.item_oneb":
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_1.png");
                break;
            case "item.economyinc.item_fiveb":
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_5.png");
                break;
            case "item.economyinc.item_tenb":
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_10.png");
                break;
            case "item.economyinc.item_twentyb":
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_20.png");
                break;
            case "item.economyinc.item_fiftybe":
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_50.png");
                break;
            case "item.economyinc.item_hundreedb":
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_100.png");
                break;
            case "item.economyinc.item_twohundreedb":
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_200.png");
                break;
            case "item.economyinc.item_fivehundreedb":
                texture = new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_500.png");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isGlobalRenderer(TileEntityBlockBills te) {

        return false;
    }


}
