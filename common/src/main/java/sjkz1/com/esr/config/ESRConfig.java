package sjkz1.com.esr.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import sjkz1.com.esr.EmissiveSkinRenderer;

@Config(name = EmissiveSkinRenderer.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/calcite.png")
public final class ESRConfig implements ConfigData {

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public General general;

    public ESRConfig() {
        this.general = new General();
    }

    public static class General {
        public boolean glowSkin = true;
        public boolean glowHorseArmor = true;
        public boolean fadingSkin = true;
        public boolean renderPlayerNameInThirdPerson = false;

    }
}
