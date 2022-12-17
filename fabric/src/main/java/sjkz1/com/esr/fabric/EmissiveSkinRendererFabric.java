package sjkz1.com.esr.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import sjkz1.com.esr.EmissiveSkinRenderer;

public class EmissiveSkinRendererFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EmissiveSkinRenderer.init();
        FabricLoader.getInstance().getModContainer(EmissiveSkinRenderer.MOD_ID).ifPresent(modContainer -> ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation("esr:esr_glow_pack"), modContainer, ResourcePackActivationType.NORMAL));
    }
}