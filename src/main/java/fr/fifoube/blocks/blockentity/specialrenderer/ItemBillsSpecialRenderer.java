package fr.fifoube.blocks.blockentity.specialrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import fr.fifoube.blocks.models.ModelBills;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemBillsSpecialRenderer extends BlockEntityWithoutLevelRenderer {

    private final ModelBills<?> model;

    public ItemBillsSpecialRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet set) {
        super(dispatcher, set);
        model = new ModelBills<>(set.bakeLayer(ModelBills.LAYER_LOCATION));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType type, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        super.renderByItem(stack, type, poseStack, buffer, combinedLight, combinedOverlay);
        Item item = stack.getItem();
        if (item.equals(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ModEconomyInc.MOD_ID, "block_bills")).asItem())) {
            poseStack.pushPose();
            poseStack.translate(0.49, -1.25, 0.5);
            poseStack.mulPose(new Quaternion(Vector3f.YP, 270.0F, true));
            poseStack.mulPose(new Quaternion(Vector3f.YN, 270.0F, true));
            poseStack.scale(1f,1f,1f);
            VertexConsumer builder = buffer.getBuffer(RenderType.entitySolid(new ResourceLocation(ModEconomyInc.MOD_ID, "textures/blocks_models/block_bills_0.png")));
            this.model.renderToBuffer(poseStack, builder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }
}
