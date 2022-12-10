package sjkz1.com.esr.utils;

import boon4681.ColorUtils.ColorMixer;
import boon4681.ColorUtils.DeltaE;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.properties.Property;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import sjkz1.com.esr.EmissiveSkinRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

public class GlowSkinManager {
    private static final Minecraft client = Minecraft.getInstance();
    public static final File GLOW_SKIN_PATH = new File(Minecraft.getInstance().gameDirectory, "glow");

    public static void createGlowingSkinImageLessThan(Player player) {
        try {
            String url = getSkin(player);
            if (url.isBlank() || url.isEmpty()) {
                Minecraft.getInstance().gui.getChat().addMessage(Component.literal("Couldn't find your game profile!").withStyle(ChatFormatting.RED));
                return;
            }
            BufferedImage image = ImageIO.read(new URL(url).openStream());
            BufferedImage resizedImage = resize(image, 4, 4);
            ArrayList<Color> colors = new ArrayList<>();

            for (int y = 0; y < resizedImage.getHeight(); y++) {
                for (int x = 0; x < resizedImage.getWidth(); x++) {
                    colors.add(new Color(resizedImage.getRGB(x, y), false));
                }
            }
            ArrayList<Color> pallets = find(colors);
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    if (DeltaE.getDelta(new Color(image.getRGB(x, y)), pallets.get(0)) < EmissiveSkinRenderer.CONFIG.general.palletsRate) {
                        image.setRGB(x, y, Transparency.TRANSLUCENT);
                    }
                }
            }

            if (!GLOW_SKIN_PATH.exists()) {
                GLOW_SKIN_PATH.mkdirs();
            }

            ImageIO.write(image, "png", new File(GLOW_SKIN_PATH, player.getStringUUID() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void MoveToResourceLoc(String string) {
        try {
            File imageFile = new File(GLOW_SKIN_PATH, string + ".png");
            InputStream in = new FileInputStream(imageFile);
            NativeImage nativeImage = NativeImage.read(in);
            TextureManager textureManager = client.getTextureManager();
            textureManager.register(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/skin/" + string + ".png"), new DynamicTexture(nativeImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Color> find(ArrayList<Color> color) {

        ArrayList<Color> colors = new ArrayList<>();
        while (color.size() > 0) {
            color.remove(0);
            double min = 10000;
            Color save = null;
            for (Color element : color) {
                double calculate = DeltaE.getDelta(element, color.get(0));
                if (calculate < min) {
                    save = element;
                    min = calculate;
                }
            }
            if (save == null) {
                break;
            }
            colors.add(ColorMixer.mix(color.get(0), save));
            color.remove(save);
            color.remove(0);
        }
        return colors;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dim = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dim.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dim;
    }

    public static String getSkin(Player player) throws IOException {
        Collection<Property> textureData = player.getGameProfile().getProperties().get("textures");
        String skinUrl = "";
        for (Property p :
                textureData) {
            JsonObject props = JsonParser.parseString(new String(Base64.getDecoder().decode((p.getValue())))).getAsJsonObject();
            skinUrl = ((JsonObject) ((JsonObject) props.get("textures")).get("SKIN")).get("url").getAsString();
            break;
        }
        return skinUrl;
    }
}
