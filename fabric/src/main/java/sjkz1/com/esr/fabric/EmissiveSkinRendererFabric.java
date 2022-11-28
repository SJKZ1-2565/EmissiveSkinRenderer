package sjkz1.com.esr.fabric;

import sjkz1.com.esr.EmissiveSkinRenderer;
import net.fabricmc.api.ModInitializer;

public class EmissiveSkinRendererFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EmissiveSkinRenderer.init();
    }
}