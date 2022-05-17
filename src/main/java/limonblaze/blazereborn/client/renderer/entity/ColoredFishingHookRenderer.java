package limonblaze.blazereborn.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import limonblaze.blazereborn.util.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ColoredFishingHookRenderer<T extends FishingHook> extends EntityRenderer<T> {
    private final ResourceLocation spriteLocation;
    private final int red;
    private final int green;
    private final int blue;

    public ColoredFishingHookRenderer(EntityRendererProvider.Context context, int stringColor, ResourceLocation spriteLocation) {
        super(context);
        Vec3i rgb = MiscUtils.unwrapRGB(stringColor);
        this.red = rgb.getX();
        this.green = rgb.getY();
        this.blue = rgb.getZ();
        this.spriteLocation = spriteLocation;
    }

    public void render(T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Player player = entity.getPlayerOwner();
        if (player != null) {
            poseStack.pushPose();
            //render bobber
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            PoseStack.Pose bobberPose = poseStack.last();
            VertexConsumer bobberBuffer = buffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(entity)));
            spriteVertex(bobberBuffer, bobberPose, packedLight, 0, 0, 0, 1);
            spriteVertex(bobberBuffer, bobberPose, packedLight, 1, 0, 1, 1);
            spriteVertex(bobberBuffer, bobberPose, packedLight, 1, 1, 1, 0);
            spriteVertex(bobberBuffer, bobberPose, packedLight, 0, 1, 0, 0);
            poseStack.popPose();
            //render string
            int armFlag = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
            ItemStack itemstack = player.getMainHandItem();
            if (!itemstack.is(Items.FISHING_ROD)) {
                armFlag = -armFlag;
            }

            float swingRaw = player.getAttackAnim(partialTicks);
            float swing = Mth.sin(Mth.sqrt(swingRaw) * (float)Math.PI);
            float yRot = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * ((float)Math.PI / 180F);
            double vecX = Mth.sin(yRot);
            double vecZ = Mth.cos(yRot);
            double armOffset = armFlag * 0.35D;
            double cameraX;
            double cameraY;
            double cameraZ;
            float eyeHeight;

            if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
                double fov = 960.0D / this.entityRenderDispatcher.options.fov;
                Vec3 cameraView = this.entityRenderDispatcher.camera
                    .getNearPlane()
                    .getPointOnPlane(armFlag * 0.525F, -0.1F)
                    .scale(fov)
                    .yRot(swing * 0.5F)
                    .xRot(-swing * 0.7F);
                cameraX = Mth.lerp(partialTicks, player.xo, player.getX()) + cameraView.x;
                cameraY = Mth.lerp(partialTicks, player.yo, player.getY()) + cameraView.y;
                cameraZ = Mth.lerp(partialTicks, player.zo, player.getZ()) + cameraView.z;
                eyeHeight = player.getEyeHeight();
            } else {
                cameraX = Mth.lerp(partialTicks, player.xo, player.getX()) - vecZ * armOffset - vecX * 0.8D;
                cameraY = player.yo + player.getEyeHeight() + (player.getY() - player.yo) * partialTicks - 0.45D;
                cameraZ = Mth.lerp(partialTicks, player.zo, player.getZ()) - vecX * armOffset + vecZ * 0.8D;
                eyeHeight = player.isCrouching() ? -0.1875F : 0.0F;
            }

            double bobberX = Mth.lerp(partialTicks, entity.xo, entity.getX());
            double bobberY = Mth.lerp(partialTicks, entity.yo, entity.getY()) + 0.25D;
            double bobberZ = Mth.lerp(partialTicks, entity.zo, entity.getZ());
            float relativeX = (float)(cameraX - bobberX);
            float relativeY = (float)(cameraY - bobberY) + eyeHeight;
            float relativeZ = (float)(cameraZ - bobberZ);
            VertexConsumer lineBuffer = buffer.getBuffer(RenderType.lineStrip());
            PoseStack.Pose linePose = poseStack.last();

            int maxProgress = 16;

            for(int progress = 0; progress <= maxProgress; ++progress) {
                float maxProressF = (float) maxProgress;
                stringVertex(
                    relativeX, relativeY, relativeZ,
                    this.red, this.green, this.blue,
                    lineBuffer, linePose,
                    progress / maxProressF, (progress + 1) / maxProressF
                );
            }

            poseStack.popPose();
            super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
        }
    }

    private static void spriteVertex(VertexConsumer buffer, PoseStack.Pose pose, int light,
                                     int x, int y, int u, int v) {
        buffer
            .vertex(pose.pose(), x - 0.5F, y - 0.5F, 0.0F)
            .color(255, 255, 255, 255)
            .uv(u, v)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(light)
            .normal(pose.normal(), 0.0F, 1.0F, 0.0F)
            .endVertex();
    }

    private static void stringVertex(float dx, float dy, float dz,
                                     int red, int green, int blue,
                                     VertexConsumer buffer, PoseStack.Pose pose,
                                     float start, float end) {
        float startX = dx * start;
        float startY = dy * (start * start + start) * 0.5F + 0.25F;
        float startZ = dz * start;
        float rx = dx * end - startX;
        float ry = dy * (end * end + end) * 0.5F + 0.25F - startY;
        float rz = dz * end - startZ;
        float length = Mth.sqrt(rx * rx + ry * ry + rz * rz);
        rx /= length;
        ry /= length;
        rz /= length;
        buffer
            .vertex(pose.pose(), startX, startY, startZ)
            .color(red, green, blue, 255)
            .normal(pose.normal(), rx, ry, rz)
            .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(T fishingHook) {
        return this.spriteLocation;
    }

}
