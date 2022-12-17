package sjkz1.com.esr.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import sjkz1.com.esr.EmissiveSkinRenderer;

@Environment(EnvType.CLIENT)
public class GlowingLayer<T extends Entity, M extends EntityModel<T>> extends RenderLayer<T, M> {


    public GlowingLayer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    public static float makeFade(float alpha) {
        return Math.min(0.7F, (Mth.sin(alpha / 24) + 1F) / 2F + 0.15F);
    }


    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        float time = h + entity.tickCount;
        for (var list : Minecraft.getInstance().getResourcePackRepository().getSelectedPacks()) {
            if (list.getDescription().getString().equals("Glow skin pack") && ResourceLocation.isValidResourceLocation(EmissiveSkinRenderer.MOD_ID + ":textures/entity/skin/" + entity.getName().getString().toLowerCase() + ".png")) {
                if (!entity.isInvisible() && EmissiveSkinRenderer.CONFIG.general.glowSkin) {
                    VertexConsumer inveterate = multiBufferSource.getBuffer(RenderType.eyes(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/entity/skin/" + entity.getName().getString().toLowerCase() + ".png")));
                    this.getParentModel().renderToBuffer(poseStack, inveterate, 0xF00000, OverlayTexture.NO_OVERLAY, makeFade(time), makeFade(time), makeFade(time), 1.0F);
                }
            }
        }

    }
}
