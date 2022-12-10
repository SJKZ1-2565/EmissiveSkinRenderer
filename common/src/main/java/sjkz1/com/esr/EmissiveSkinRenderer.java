package sjkz1.com.esr;

import com.google.common.collect.Lists;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import sjkz1.com.esr.config.ESRConfig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EmissiveSkinRenderer {
    public static final String MOD_ID = "esr";
    public static ESRConfig CONFIG;
    public static final List<String> SPECIAL_MEMBER = Lists.newCopyOnWriteArrayList();

    public static void init() {
        AutoConfig.register(ESRConfig.class, GsonConfigSerializer::new);
        EmissiveSkinRenderer.CONFIG = AutoConfig.getConfigHolder(ESRConfig.class).getConfig();
        try {
            InputStream is = (new URL("https://raw.githubusercontent.com/SJKZ1-2565/modJSON-URL/master/donate.txt")).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            SPECIAL_MEMBER.addAll(rd.lines().toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}