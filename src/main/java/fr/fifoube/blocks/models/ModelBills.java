package fr.fifoube.blocks.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fr.fifoube.blocks.blockentity.BlockEntityBills;
import fr.fifoube.main.ModEconomyInc;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ModelBills<T extends Entity> extends Model
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ModEconomyInc.MOD_ID, "block_bills"), "main");
    private BlockEntityBills te;

    private final ModelPart root;
    private final ModelPart bills01;
    private final ModelPart bills02;
    private final ModelPart bills03;
    private final ModelPart bills04;
    private final ModelPart bills05;
    private final ModelPart bills06;
    private final ModelPart bills07;
    private final ModelPart bills08;
    private final ModelPart bills09;
    private final ModelPart bills10;
    private final ModelPart bills11;
    private final ModelPart bills12;
    private final ModelPart bills13;
    private final ModelPart bills14;
    private final ModelPart bills15;
    private final ModelPart bills16;
    private final ModelPart bills17;
    private final ModelPart bills18;
    private final ModelPart bills19;
    private final ModelPart bills20;
    private final ModelPart bills21;
    private final ModelPart bills22;
    private final ModelPart bills23;
    private final ModelPart bills24;
    private final ModelPart bills25;
    private final ModelPart bills26;
    private final ModelPart bills27;
    private final ModelPart bills28;
    private final ModelPart bills29;
    private final ModelPart bills30;
    private final ModelPart bills31;
    private final ModelPart bills32;
    private final ModelPart bills33;
    private final ModelPart bills34;
    private final ModelPart bills35;
    private final ModelPart bills36;
    private final ModelPart bills37;
    private final ModelPart bills38;
    private final ModelPart bills39;
    private final ModelPart bills40;
    private final ModelPart bills41;
    private final ModelPart bills42;
    private final ModelPart bills43;
    private final ModelPart bills44;
    private final ModelPart bills45;
    private final ModelPart bills46;
    private final ModelPart bills47;
    private final ModelPart bills48;
    private final ModelPart bills49;
    private final ModelPart bills50;
    private final ModelPart bills51;
    private final ModelPart bills52;
    private final ModelPart bills53;
    private final ModelPart bills54;
    private final ModelPart bills55;
    private final ModelPart bills56;
    private final ModelPart bills57;
    private final ModelPart bills58;
    private final ModelPart bills59;
    private final ModelPart bills60;
    private final ModelPart bills61;
    private final ModelPart bills62;
    private final ModelPart bills63;
    private final ModelPart bills64;
    private final ModelPart platform;


    public ModelBills(ModelPart root) {
        super(RenderType::entitySolid);
        this.root = root;
        this.bills01 = root.getChild("bills01");
        this.bills02 = root.getChild("bills02");
        this.bills03 = root.getChild("bills03");
        this.bills04 = root.getChild("bills04");
        this.bills05 = root.getChild("bills05");
        this.bills06 = root.getChild("bills06");
        this.bills07 = root.getChild("bills07");
        this.bills08 = root.getChild("bills08");
        this.bills09 = root.getChild("bills09");
        this.bills10 = root.getChild("bills10");
        this.bills11 = root.getChild("bills11");
        this.bills12 = root.getChild("bills12");
        this.bills13 = root.getChild("bills13");
        this.bills14 = root.getChild("bills14");
        this.bills15 = root.getChild("bills15");
        this.bills16 = root.getChild("bills16");
        this.bills17 = root.getChild("bills17");
        this.bills18 = root.getChild("bills18");
        this.bills19 = root.getChild("bills19");
        this.bills20 = root.getChild("bills20");
        this.bills21 = root.getChild("bills21");
        this.bills22 = root.getChild("bills22");
        this.bills23 = root.getChild("bills23");
        this.bills24 = root.getChild("bills24");
        this.bills25 = root.getChild("bills25");
        this.bills26 = root.getChild("bills26");
        this.bills27 = root.getChild("bills27");
        this.bills28 = root.getChild("bills28");
        this.bills29 = root.getChild("bills29");
        this.bills30 = root.getChild("bills30");
        this.bills31 = root.getChild("bills31");
        this.bills32 = root.getChild("bills32");
        this.bills33 = root.getChild("bills33");
        this.bills34 = root.getChild("bills34");
        this.bills35 = root.getChild("bills35");
        this.bills36 = root.getChild("bills36");
        this.bills37 = root.getChild("bills37");
        this.bills38 = root.getChild("bills38");
        this.bills39 = root.getChild("bills39");
        this.bills40 = root.getChild("bills40");
        this.bills41 = root.getChild("bills41");
        this.bills42 = root.getChild("bills42");
        this.bills43 = root.getChild("bills43");
        this.bills44 = root.getChild("bills44");
        this.bills45 = root.getChild("bills45");
        this.bills46 = root.getChild("bills46");
        this.bills47 = root.getChild("bills47");
        this.bills48 = root.getChild("bills48");
        this.bills49 = root.getChild("bills49");
        this.bills50 = root.getChild("bills50");
        this.bills51 = root.getChild("bills51");
        this.bills52 = root.getChild("bills52");
        this.bills53 = root.getChild("bills53");
        this.bills54 = root.getChild("bills54");
        this.bills55 = root.getChild("bills55");
        this.bills56 = root.getChild("bills56");
        this.bills57 = root.getChild("bills57");
        this.bills58 = root.getChild("bills58");
        this.bills59 = root.getChild("bills59");
        this.bills60 = root.getChild("bills60");
        this.bills61 = root.getChild("bills61");
        this.bills62 = root.getChild("bills62");
        this.bills63 = root.getChild("bills63");
        this.bills64 = root.getChild("bills64");
        this.platform = root.getChild("platform");
    }

    public static LayerDefinition createDefinition()
    {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition platform = partdefinition.addOrReplaceChild("platform", CubeListBuilder.create().texOffs(0, 15).mirror().addBox(-8.0F, 5.5F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 17.5F, 0.0F));

        PartDefinition bills64 = partdefinition.addOrReplaceChild("bills64", CubeListBuilder.create(), PartPose.offset(-6.0F, 14.5F, 4.0F));

        PartDefinition bills64_r1 = bills64.addOrReplaceChild("bills64_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills63 = partdefinition.addOrReplaceChild("bills63", CubeListBuilder.create(), PartPose.offset(-6.0F, 14.5F, 4.0F));

        PartDefinition bills63_r1 = bills63.addOrReplaceChild("bills63_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 1.0F, -8.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills62 = partdefinition.addOrReplaceChild("bills62", CubeListBuilder.create(), PartPose.offset(-2.0F, 15.5F, -4.0F));

        PartDefinition bills62_r1 = bills62.addOrReplaceChild("bills62_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills61 = partdefinition.addOrReplaceChild("bills61", CubeListBuilder.create(), PartPose.offset(-2.0F, 15.5F, -4.0F));

        PartDefinition bills61_r1 = bills61.addOrReplaceChild("bills61_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills60 = partdefinition.addOrReplaceChild("bills60", CubeListBuilder.create(), PartPose.offset(6.0F, 15.5F, 4.0F));

        PartDefinition bills60_r1 = bills60.addOrReplaceChild("bills60_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills59 = partdefinition.addOrReplaceChild("bills59", CubeListBuilder.create(), PartPose.offset(2.0F, 15.5F, 4.0F));

        PartDefinition bills59_r1 = bills59.addOrReplaceChild("bills59_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills58 = partdefinition.addOrReplaceChild("bills58", CubeListBuilder.create(), PartPose.offset(-2.0F, 15.5F, 4.0F));

        PartDefinition bills58_r1 = bills58.addOrReplaceChild("bills58_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills57 = partdefinition.addOrReplaceChild("bills57", CubeListBuilder.create(), PartPose.offset(-6.0F, 15.5F, 4.0F));

        PartDefinition bills57_r1 = bills57.addOrReplaceChild("bills57_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills56 = partdefinition.addOrReplaceChild("bills56", CubeListBuilder.create(), PartPose.offset(6.0F, 16.5F, -4.0F));

        PartDefinition bills56_r1 = bills56.addOrReplaceChild("bills56_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills55 = partdefinition.addOrReplaceChild("bills55", CubeListBuilder.create(), PartPose.offset(2.0F, 16.5F, -4.0F));

        PartDefinition bills55_r1 = bills55.addOrReplaceChild("bills55_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills54 = partdefinition.addOrReplaceChild("bills54", CubeListBuilder.create(), PartPose.offset(-2.0F, 16.5F, -4.0F));

        PartDefinition bills54_r1 = bills54.addOrReplaceChild("bills54_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills53 = partdefinition.addOrReplaceChild("bills53", CubeListBuilder.create(), PartPose.offset(-6.0F, 16.5F, -4.0F));

        PartDefinition bills53_r1 = bills53.addOrReplaceChild("bills53_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills52 = partdefinition.addOrReplaceChild("bills52", CubeListBuilder.create(), PartPose.offset(6.0F, 16.5F, 4.0F));

        PartDefinition bills52_r1 = bills52.addOrReplaceChild("bills52_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills51 = partdefinition.addOrReplaceChild("bills51", CubeListBuilder.create(), PartPose.offset(2.0F, 16.5F, 4.0F));

        PartDefinition bills51_r1 = bills51.addOrReplaceChild("bills51_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills50 = partdefinition.addOrReplaceChild("bills50", CubeListBuilder.create(), PartPose.offset(-2.0F, 16.5F, 4.0F));

        PartDefinition bills50_r1 = bills50.addOrReplaceChild("bills50_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills49 = partdefinition.addOrReplaceChild("bills49", CubeListBuilder.create(), PartPose.offset(-6.0F, 16.5F, 4.0F));

        PartDefinition bills49_r1 = bills49.addOrReplaceChild("bills49_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills48 = partdefinition.addOrReplaceChild("bills48", CubeListBuilder.create(), PartPose.offset(6.0F, 17.5F, -4.0F));

        PartDefinition bills48_r1 = bills48.addOrReplaceChild("bills48_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills47 = partdefinition.addOrReplaceChild("bills47", CubeListBuilder.create(), PartPose.offset(2.0F, 17.5F, -4.0F));

        PartDefinition bills47_r1 = bills47.addOrReplaceChild("bills47_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills46 = partdefinition.addOrReplaceChild("bills46", CubeListBuilder.create(), PartPose.offset(-2.0F, 17.5F, -4.0F));

        PartDefinition bills46_r1 = bills46.addOrReplaceChild("bills46_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills45 = partdefinition.addOrReplaceChild("bills45", CubeListBuilder.create(), PartPose.offset(-6.0F, 17.5F, -4.0F));

        PartDefinition bills45_r1 = bills45.addOrReplaceChild("bills45_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills44 = partdefinition.addOrReplaceChild("bills44", CubeListBuilder.create(), PartPose.offset(6.0F, 17.5F, 4.0F));

        PartDefinition bills44_r1 = bills44.addOrReplaceChild("bills44_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills43 = partdefinition.addOrReplaceChild("bills43", CubeListBuilder.create(), PartPose.offset(2.0F, 17.5F, 4.0F));

        PartDefinition bills43_r1 = bills43.addOrReplaceChild("bills43_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills42 = partdefinition.addOrReplaceChild("bills42", CubeListBuilder.create(), PartPose.offset(-2.0F, 17.5F, 4.0F));

        PartDefinition bills42_r1 = bills42.addOrReplaceChild("bills42_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills41 = partdefinition.addOrReplaceChild("bills41", CubeListBuilder.create(), PartPose.offset(-6.0F, 17.5F, 4.0F));

        PartDefinition bills41_r1 = bills41.addOrReplaceChild("bills41_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills40 = partdefinition.addOrReplaceChild("bills40", CubeListBuilder.create(), PartPose.offset(6.0F, 18.5F, -4.0F));

        PartDefinition bills40_r1 = bills40.addOrReplaceChild("bills40_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills39 = partdefinition.addOrReplaceChild("bills39", CubeListBuilder.create(), PartPose.offset(2.0F, 18.5F, -4.0F));

        PartDefinition bills39_r1 = bills39.addOrReplaceChild("bills39_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills38 = partdefinition.addOrReplaceChild("bills38", CubeListBuilder.create(), PartPose.offset(-2.0F, 18.5F, -4.0F));

        PartDefinition bills38_r1 = bills38.addOrReplaceChild("bills38_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills37 = partdefinition.addOrReplaceChild("bills37", CubeListBuilder.create(), PartPose.offset(-6.0F, 18.5F, -4.0F));

        PartDefinition bills37_r1 = bills37.addOrReplaceChild("bills37_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills36 = partdefinition.addOrReplaceChild("bills36", CubeListBuilder.create(), PartPose.offset(6.0F, 18.5F, 4.0F));

        PartDefinition bills36_r1 = bills36.addOrReplaceChild("bills36_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills35 = partdefinition.addOrReplaceChild("bills35", CubeListBuilder.create(), PartPose.offset(2.0F, 18.5F, 4.0F));

        PartDefinition bills35_r1 = bills35.addOrReplaceChild("bills35_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills34 = partdefinition.addOrReplaceChild("bills34", CubeListBuilder.create(), PartPose.offset(-2.0F, 18.5F, 4.0F));

        PartDefinition bills34_r1 = bills34.addOrReplaceChild("bills34_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills33 = partdefinition.addOrReplaceChild("bills33", CubeListBuilder.create(), PartPose.offset(-6.0F, 18.5F, 4.0F));

        PartDefinition bills33_r1 = bills33.addOrReplaceChild("bills33_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills32 = partdefinition.addOrReplaceChild("bills32", CubeListBuilder.create(), PartPose.offset(-6.0F, 18.5F, 4.0F));

        PartDefinition bills32_r1 = bills32.addOrReplaceChild("bills32_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 1.0F, -8.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills31 = partdefinition.addOrReplaceChild("bills31", CubeListBuilder.create(), PartPose.offset(-6.0F, 18.5F, 4.0F));

        PartDefinition bills31_r1 = bills31.addOrReplaceChild("bills31_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 1.0F, -8.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills30 = partdefinition.addOrReplaceChild("bills30", CubeListBuilder.create(), PartPose.offset(-2.0F, 19.5F, -4.0F));

        PartDefinition bills30_r1 = bills30.addOrReplaceChild("bills30_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills29 = partdefinition.addOrReplaceChild("bills29", CubeListBuilder.create(), PartPose.offset(-6.0F, 19.5F, -4.0F));

        PartDefinition bills29_r1 = bills29.addOrReplaceChild("bills29_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills28 = partdefinition.addOrReplaceChild("bills28", CubeListBuilder.create(), PartPose.offset(6.0F, 19.5F, 4.0F));

        PartDefinition bills28_r1 = bills28.addOrReplaceChild("bills28_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills27 = partdefinition.addOrReplaceChild("bills27", CubeListBuilder.create(), PartPose.offset(2.0F, 19.5F, 4.0F));

        PartDefinition bills27_r1 = bills27.addOrReplaceChild("bills27_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills26 = partdefinition.addOrReplaceChild("bills26", CubeListBuilder.create(), PartPose.offset(-2.0F, 19.5F, 4.0F));

        PartDefinition bills26_r1 = bills26.addOrReplaceChild("bills26_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills25 = partdefinition.addOrReplaceChild("bills25", CubeListBuilder.create(), PartPose.offset(-6.0F, 19.5F, 4.0F));

        PartDefinition bills25_r1 = bills25.addOrReplaceChild("bills25_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills24 = partdefinition.addOrReplaceChild("bills24", CubeListBuilder.create(), PartPose.offset(6.0F, 20.5F, -4.0F));

        PartDefinition bills24_r1 = bills24.addOrReplaceChild("bills24_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills23 = partdefinition.addOrReplaceChild("bills23", CubeListBuilder.create(), PartPose.offset(2.0F, 20.5F, -4.0F));

        PartDefinition bills23_r1 = bills23.addOrReplaceChild("bills23_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills22 = partdefinition.addOrReplaceChild("bills22", CubeListBuilder.create(), PartPose.offset(-2.0F, 20.5F, -4.0F));

        PartDefinition bills22_r1 = bills22.addOrReplaceChild("bills22_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills21 = partdefinition.addOrReplaceChild("bills21", CubeListBuilder.create(), PartPose.offset(-6.0F, 20.5F, -4.0F));

        PartDefinition bills21_r1 = bills21.addOrReplaceChild("bills21_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills20 = partdefinition.addOrReplaceChild("bills20", CubeListBuilder.create(), PartPose.offset(6.0F, 20.5F, 4.0F));

        PartDefinition bills20_r1 = bills20.addOrReplaceChild("bills20_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills19 = partdefinition.addOrReplaceChild("bills19", CubeListBuilder.create(), PartPose.offset(2.0F, 20.5F, 4.0F));

        PartDefinition bills19_r1 = bills19.addOrReplaceChild("bills19_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills18 = partdefinition.addOrReplaceChild("bills18", CubeListBuilder.create(), PartPose.offset(-2.0F, 20.5F, 4.0F));

        PartDefinition bills18_r1 = bills18.addOrReplaceChild("bills18_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills17 = partdefinition.addOrReplaceChild("bills17", CubeListBuilder.create(), PartPose.offset(-6.0F, 20.5F, 4.0F));

        PartDefinition bills17_r1 = bills17.addOrReplaceChild("bills17_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills16 = partdefinition.addOrReplaceChild("bills16", CubeListBuilder.create(), PartPose.offset(6.0F, 21.5F, -4.0F));

        PartDefinition bills16_r1 = bills16.addOrReplaceChild("bills16_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills15 = partdefinition.addOrReplaceChild("bills15", CubeListBuilder.create(), PartPose.offset(2.0F, 21.5F, -4.0F));

        PartDefinition bills15_r1 = bills15.addOrReplaceChild("bills15_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills14 = partdefinition.addOrReplaceChild("bills14", CubeListBuilder.create(), PartPose.offset(-2.0F, 21.5F, -4.0F));

        PartDefinition bills14_r1 = bills14.addOrReplaceChild("bills14_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills13 = partdefinition.addOrReplaceChild("bills13", CubeListBuilder.create(), PartPose.offset(-6.0F, 21.5F, -4.0F));

        PartDefinition bills13_r1 = bills13.addOrReplaceChild("bills13_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills12 = partdefinition.addOrReplaceChild("bills12", CubeListBuilder.create(), PartPose.offset(6.0F, 21.5F, 4.0F));

        PartDefinition bills12_r1 = bills12.addOrReplaceChild("bills12_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills11 = partdefinition.addOrReplaceChild("bills11", CubeListBuilder.create(), PartPose.offset(2.0F, 21.5F, 4.0F));

        PartDefinition bills11_r1 = bills11.addOrReplaceChild("bills11_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills10 = partdefinition.addOrReplaceChild("bills10", CubeListBuilder.create(), PartPose.offset(-2.0F, 21.5F, 4.0F));

        PartDefinition bills10_r1 = bills10.addOrReplaceChild("bills10_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills09 = partdefinition.addOrReplaceChild("bills09", CubeListBuilder.create(), PartPose.offset(-6.0F, 21.5F, 4.0F));

        PartDefinition bills09_r1 = bills09.addOrReplaceChild("bills09_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills08 = partdefinition.addOrReplaceChild("bills08", CubeListBuilder.create(), PartPose.offset(6.0F, 22.5F, -4.0F));

        PartDefinition bills08_r1 = bills08.addOrReplaceChild("bills08_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills07 = partdefinition.addOrReplaceChild("bills07", CubeListBuilder.create(), PartPose.offset(2.0F, 22.5F, -4.0F));

        PartDefinition bills07_r1 = bills07.addOrReplaceChild("bills07_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills06 = partdefinition.addOrReplaceChild("bills06", CubeListBuilder.create(), PartPose.offset(-2.0F, 22.5F, -4.0F));

        PartDefinition bills06_r1 = bills06.addOrReplaceChild("bills06_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills05 = partdefinition.addOrReplaceChild("bills05", CubeListBuilder.create(), PartPose.offset(-6.0F, 22.5F, -4.0F));

        PartDefinition bills05_r1 = bills05.addOrReplaceChild("bills05_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills04 = partdefinition.addOrReplaceChild("bills04", CubeListBuilder.create(), PartPose.offset(6.0F, 22.5F, 4.0F));

        PartDefinition bills04_r1 = bills04.addOrReplaceChild("bills04_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills03 = partdefinition.addOrReplaceChild("bills03", CubeListBuilder.create(), PartPose.offset(2.0F, 22.5F, 4.0F));

        PartDefinition bills03_r1 = bills03.addOrReplaceChild("bills03_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills02 = partdefinition.addOrReplaceChild("bills02", CubeListBuilder.create(), PartPose.offset(-2.0F, 22.5F, 4.0F));

        PartDefinition bills02_r1 = bills02.addOrReplaceChild("bills02_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bills01 = partdefinition.addOrReplaceChild("bills01", CubeListBuilder.create(), PartPose.offset(-2.0F, 22.5F, 4.0F));

        PartDefinition bills01_r1 = bills01.addOrReplaceChild("bills01_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public void renderAll(BlockEntityBills tile, PoseStack stack, VertexConsumer vertex, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.te = tile;
        renderToBuffer(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer vertex, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        platform.render(stack, vertex, packedLightIn, packedOverlayIn);
        if (this.te != null) {
            if (te.getNumbBills() >= 1) {
                bills01.xRot = 0F;
                bills01.yRot = 0F;
                bills01.zRot = 0F;
                bills01.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }


            if (te.getNumbBills() >= 2) {
                bills02.xRot = 0F;
                bills02.yRot = 0F;
                bills02.zRot = 0F;
                bills02.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }


            if (te.getNumbBills() >= 3) {
                bills03.xRot = 0F;
                bills03.yRot = 0F;
                bills03.zRot = 0F;
                bills03.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 4) {
                bills04.xRot = 0F;
                bills04.yRot = 0F;
                bills04.zRot = 0F;
                bills04.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 5) {
                bills05.xRot = 0F;
                bills05.yRot = 0F;
                bills05.zRot = 0F;
                bills05.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 6) {
                bills06.xRot = 0F;
                bills06.yRot = 0F;
                bills06.zRot = 0F;
                bills06.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 7) {
                bills07.xRot = 0F;
                bills07.yRot = 0F;
                bills07.zRot = 0F;
                bills07.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 8) {
                bills08.xRot = 0F;
                bills08.yRot = 0F;
                bills08.zRot = 0F;
                bills08.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 9) {
                bills09.xRot = 0F;
                bills09.yRot = 0F;
                bills09.zRot = 0F;
                bills09.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 10) {
                bills10.xRot = 0F;
                bills10.yRot = 0F;
                bills10.zRot = 0F;
                bills10.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 11) {
                bills11.xRot = 0F;
                bills11.yRot = 0F;
                bills11.zRot = 0F;
                bills11.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 12) {
                bills12.xRot = 0F;
                bills12.yRot = 0F;
                bills12.zRot = 0F;
                bills12.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 13) {
                bills13.xRot = 0F;
                bills13.yRot = 0F;
                bills13.zRot = 0F;
                bills13.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 14) {
                bills14.xRot = 0F;
                bills14.yRot = 0F;
                bills14.zRot = 0F;
                bills14.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 15) {
                bills15.xRot = 0F;
                bills15.yRot = 0F;
                bills15.zRot = 0F;
                bills15.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 16) {
                bills16.xRot = 0F;
                bills16.yRot = 0F;
                bills16.zRot = 0F;
                bills16.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 17) {
                bills17.xRot = 0F;
                bills17.yRot = 0F;
                bills17.zRot = 0F;
                bills17.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 18) {
                bills18.xRot = 0F;
                bills18.yRot = 0F;
                bills18.zRot = 0F;
                bills18.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 19) {
                bills19.xRot = 0F;
                bills19.yRot = 0F;
                bills19.zRot = 0F;
                bills19.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 20) {
                bills20.xRot = 0F;
                bills20.yRot = 0F;
                bills20.zRot = 0F;
                bills20.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 21) {
                bills21.xRot = 0F;
                bills21.yRot = 0F;
                bills21.zRot = 0F;
                bills21.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 22) {
                bills22.xRot = 0F;
                bills22.yRot = 0F;
                bills22.zRot = 0F;
                bills22.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 23) {
                bills23.xRot = 0F;
                bills23.yRot = 0F;
                bills23.zRot = 0F;
                bills23.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 24) {
                bills24.xRot = 0F;
                bills24.yRot = 0F;
                bills24.zRot = 0F;
                bills24.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 25) {
                bills25.xRot = 0F;
                bills25.yRot = 0F;
                bills25.zRot = 0F;
                bills25.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 26) {
                bills26.xRot = 0F;
                bills26.yRot = 0F;
                bills26.zRot = 0F;
                bills26.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 27) {
                bills27.xRot = 0F;
                bills27.yRot = 0F;
                bills27.zRot = 0F;
                bills27.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 28) {
                bills28.xRot = 0F;
                bills28.yRot = 0F;
                bills28.zRot = 0F;
                bills28.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 29) {
                bills29.xRot = 0F;
                bills29.yRot = 0F;
                bills29.zRot = 0F;
                bills29.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 30) {
                bills30.xRot = 0F;
                bills30.yRot = 0F;
                bills30.zRot = 0F;
                bills30.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 31) {
                bills31.xRot = 0F;
                bills31.yRot = 0F;
                bills31.zRot = 0F;
                bills31.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 32) {
                bills32.xRot = 0F;
                bills32.yRot = 0F;
                bills32.zRot = 0F;
                bills32.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 33) {
                bills33.xRot = 0F;
                bills33.yRot = 0F;
                bills33.zRot = 0F;
                bills33.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 34) {
                bills34.xRot = 0F;
                bills34.yRot = 0F;
                bills34.zRot = 0F;
                bills34.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 35) {
                bills35.xRot = 0F;
                bills35.yRot = 0F;
                bills35.zRot = 0F;
                bills35.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 36) {
                bills36.xRot = 0F;
                bills36.yRot = 0F;
                bills36.zRot = 0F;
                bills36.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 37) {
                bills37.xRot = 0F;
                bills37.yRot = 0F;
                bills37.zRot = 0F;
                bills37.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 38) {
                bills38.xRot = 0F;
                bills38.yRot = 0F;
                bills38.zRot = 0F;
                bills38.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 39) {
                bills39.xRot = 0F;
                bills39.yRot = 0F;
                bills39.zRot = 0F;
                bills39.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 40) {
                bills40.xRot = 0F;
                bills40.yRot = 0F;
                bills40.zRot = 0F;
                bills40.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 41) {
                bills41.xRot = 0F;
                bills41.yRot = 0F;
                bills41.zRot = 0F;
                bills41.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 42) {
                bills42.xRot = 0F;
                bills42.yRot = 0F;
                bills42.zRot = 0F;
                bills42.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 43) {
                bills43.xRot = 0F;
                bills43.yRot = 0F;
                bills43.zRot = 0F;
                bills43.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 44) {
                bills44.xRot = 0F;
                bills44.yRot = 0F;
                bills44.zRot = 0F;
                bills44.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 45) {
                bills45.xRot = 0F;
                bills45.yRot = 0F;
                bills45.zRot = 0F;
                bills45.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 46) {
                bills46.xRot = 0F;
                bills46.yRot = 0F;
                bills46.zRot = 0F;
                bills46.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 47) {
                bills47.xRot = 0F;
                bills47.yRot = 0F;
                bills47.zRot = 0F;
                bills47.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 48) {
                bills48.xRot = 0F;
                bills48.yRot = 0F;
                bills48.zRot = 0F;
                bills48.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 49) {
                bills49.xRot = 0F;
                bills49.yRot = 0F;
                bills49.zRot = 0F;
                bills49.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 50) {
                bills50.xRot = 0F;
                bills50.yRot = 0F;
                bills50.zRot = 0F;
                bills50.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 51) {
                bills51.xRot = 0F;
                bills51.yRot = 0F;
                bills51.zRot = 0F;
                bills51.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 52) {
                bills52.xRot = 0F;
                bills52.yRot = 0F;
                bills52.zRot = 0F;
                bills52.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 53) {
                bills53.xRot = 0F;
                bills53.yRot = 0F;
                bills53.zRot = 0F;
                bills53.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 54) {
                bills54.xRot = 0F;
                bills54.yRot = 0F;
                bills54.zRot = 0F;
                bills54.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 55) {
                bills55.xRot = 0F;
                bills55.yRot = 0F;
                bills55.zRot = 0F;
                bills55.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 56) {
                bills56.xRot = 0F;
                bills56.yRot = 0F;
                bills56.zRot = 0F;
                bills56.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 57) {
                bills57.xRot = 0F;
                bills57.yRot = 0F;
                bills57.zRot = 0F;
                bills57.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 58) {
                bills58.xRot = 0F;
                bills58.yRot = 0F;
                bills58.zRot = 0F;
                bills58.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 59) {
                bills59.xRot = 0F;
                bills59.yRot = 0F;
                bills59.zRot = 0F;
                bills59.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 60) {
                bills60.xRot = 0F;
                bills60.yRot = 0F;
                bills60.zRot = 0F;
                bills60.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 61) {
                bills61.xRot = 0F;
                bills61.yRot = 0F;
                bills61.zRot = 0F;
                bills61.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 62) {
                bills62.xRot = 0F;
                bills62.yRot = 0F;
                bills62.zRot = 0F;
                bills62.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 63) {
                bills63.xRot = 0F;
                bills63.yRot = 0F;
                bills63.zRot = 0F;
                bills63.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (te.getNumbBills() >= 64) {
                bills64.xRot = 0F;
                bills64.yRot = 0F;
                bills64.zRot = 0F;
                bills64.render(stack, vertex, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }
    }

}