package sjkz1.com.esr.forge;

import dev.architectury.platform.forge.EventBuses;
import sjkz1.com.esr.EmissiveSkinRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EmissiveSkinRenderer.MOD_ID)
public class EmissiveSkinRendererForge {
    public EmissiveSkinRendererForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(EmissiveSkinRenderer.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        EmissiveSkinRenderer.init();
    }
}