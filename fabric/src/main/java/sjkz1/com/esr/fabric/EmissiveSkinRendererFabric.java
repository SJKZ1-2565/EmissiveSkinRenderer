package sjkz1.com.esr.fabric;

import net.fabricmc.api.ModInitializer;
import sjkz1.com.esr.EmissiveSkinRenderer;

public class EmissiveSkinRendererFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EmissiveSkinRenderer.init();
    }
}