package sjkz1.com.esr.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sjkz1.com.esr.EmissiveSkinRenderer;
import sjkz1.com.esr.render.GlowingLayer;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRenderMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    @Shadow
    public abstract void render(AbstractClientPlayer arg, float f, float g, PoseStack arg2, MultiBufferSource arg3, int i);

    @Shadow
    public abstract ResourceLocation getTextureLocation(AbstractClientPlayer arg);

    PlayerRenderMixin() {
        super(null, null, 0);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(EntityRendererProvider.Context context, boolean bl, CallbackInfo ci) {
        this.addLayer(new GlowingLayer<>((PlayerRenderer) (Object) this));
    }


    @Inject(method = "renderHand", at = @At("TAIL"))
    private void renderArm(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, AbstractClientPlayer abstractClientPlayer, ModelPart modelPart, ModelPart modelPart2, CallbackInfo ci) {
        float time = (float) abstractClientPlayer.tickCount;
        for (var list : Minecraft.getInstance().getResourcePackRepository().getSelectedPacks()) {
            if (list.getDescription().getString().equals("Glow skin pack") && !abstractClientPlayer.isInvisible() && EmissiveSkinRenderer.CONFIG.general.glowSkin) {
                var resource = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/entity/skin/" + abstractClientPlayer.getName().getString().toLowerCase() + ".png"));
                if (resource.isPresent()) {
                    modelPart.xRot = 0.0f;
                    modelPart.render(poseStack, multiBufferSource.getBuffer(RenderType.dragonExplosionAlpha(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/entity/skin/" + abstractClientPlayer.getName().getString().toLowerCase() + ".png"))), i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
                    modelPart2.xRot = 0.0f;
                    modelPart2.render(poseStack, multiBufferSource.getBuffer(RenderType.dragonExplosionAlpha(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/entity/skin/" + abstractClientPlayer.getName().getString().toLowerCase() + ".png"))), i, OverlayTexture.NO_OVERLAY, GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), GlowingLayer.makeFade(time), 1.0F);
                }
            }
        }
    }
}
