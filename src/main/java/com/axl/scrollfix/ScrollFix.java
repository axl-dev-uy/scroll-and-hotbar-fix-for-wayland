package com.axl.scrollfix;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = ScrollFix.MODID, name = ScrollFix.NAME, version = ScrollFix.VERSION, clientSideOnly = true)
public class ScrollFix {
    public static final String MODID = "scroll-hotbar-fix";
    public static final String NAME = "Scroll and Hotbar fix";
    public static final String VERSION = "1.0";

    private static long lastScrollTime = 0;
    private static long lastHotbarScrollTime = 0;
    private static int lastHotbarSlot = 0;
    public static int scrollThreshold = 25;
    private static Configuration config;

    @SubscribeEvent
    void onMouseInput(GuiScreenEvent.MouseInputEvent.Pre event) {
        int delta = Mouse.getEventDWheel();
        if (delta != 0) {
            long now = System.currentTimeMillis();
            if (now - lastScrollTime < scrollThreshold) {
                event.setCanceled(true);
                return;
            }
            lastScrollTime = now;
        }
    }

    @SubscribeEvent
    void onHotbarScroll(InputEvent.MouseInputEvent event) {
        int delta = Mouse.getEventDWheel();
        if (delta != 0) {
            long now = System.currentTimeMillis();
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.player == null) return;

            if (now - lastHotbarScrollTime < scrollThreshold) {
                // Duplicate detected, revert to last slot
                mc.player.inventory.currentItem = lastHotbarSlot;
            } else {
                lastHotbarSlot = mc.player.inventory.currentItem;
            }
            lastHotbarScrollTime = now;
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        scrollThreshold = config.getInt(
                "scrollThreshold",           // name
                "general",                   // category
                25,                          // default
                1,                           // min
                500,                         // max
                "Time in milliseconds to suppress duplicate scroll events. Increase if double scrolling persists, decrease if legitimate fast scrolls are being skipped."
        );
        if (config.hasChanged()) config.save();
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new ScrollFix());
    }

}