package sjkz1.com.esr.forge;

import dev.architectury.platform.forge.EventBuses;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sjkz1.com.esr.EmissiveSkinRenderer;
import sjkz1.com.esr.config.ESRConfig;

@Mod(EmissiveSkinRenderer.MOD_ID)
public class EmissiveSkinRendererForge {
    public EmissiveSkinRendererForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(EmissiveSkinRenderer.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> AutoConfig.getConfigScreen(ESRConfig.class, screen).get()));
        EmissiveSkinRenderer.init();
    }
}