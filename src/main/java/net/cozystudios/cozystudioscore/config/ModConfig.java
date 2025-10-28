package net.cozystudios.cozystudioscore.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import java.util.ArrayList;
import java.util.List;

@Config(name = "cozystudioscore")
public class ModConfig implements ConfigData {

    // ======================
    // General
    // ======================

    @ConfigEntry.Category("general")
    @ConfigEntry.BoundedDiscrete(min = 5, max = 100)
    @ConfigEntry.Gui.Tooltip
    public int tranquilLanternRadius = 35;

    @ConfigEntry.Category("general")
    public boolean tranquilLanternBump = true;
    @ConfigEntry.Category("general")
    public boolean tranquilLanternBurn = false;

    // ======================
    // Kiln
    // ======================
    @ConfigEntry.Category("kiln")
    @ConfigEntry.Gui.Tooltip(count = 2)
    public List<String> extraKilnRecipes = new ArrayList<>(List.of(
            "minecraft:cobblestone -> minecraft:stone",
            "minecraft:sand -> minecraft:glass"
    ));

    @ConfigEntry.Category("kiln")
    @ConfigEntry.Gui.Tooltip
    public double kilnConfigDefaultXp = 0.1;

    @ConfigEntry.Category("kiln")
    @ConfigEntry.Gui.Tooltip
    public int kilnConfigDefaultTime = 100;

    // ======================
    // Gameplay
    // ======================
    @ConfigEntry.Category("gameplay")
    @ConfigEntry.Gui.Tooltip
    public boolean peacefulHunger = true;

    @ConfigEntry.Category("gameplay")
    @ConfigEntry.Gui.Tooltip
    public boolean enableMasonTrades = true;

    @ConfigEntry.Category("gameplay")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 16, max = 64) // makes it a slider
    public int maxStackSizeOverride = 64;

    // ======================
    // Recolor-on-use
    // ======================
    @ConfigEntry.Category("recolor_on_use")
    @ConfigEntry.Gui.Tooltip
    public boolean enableRightClickRecolor = true;

    @ConfigEntry.Category("recolor_on_use")
    @ConfigEntry.Gui.Tooltip
    public boolean recolorWool = true;

    @ConfigEntry.Category("recolor_on_use")
    @ConfigEntry.Gui.Tooltip
    public boolean recolorBeds = true;

    @ConfigEntry.Category("recolor_on_use")
    @ConfigEntry.Gui.Tooltip
    public boolean recolorBanners = true;

    // ======================
    // Fernling
    // ======================
    @ConfigEntry.Category("fernling")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 8)
    public int fernlingBonemealRadius = 3;

    @ConfigEntry.Category("fernling")
    @ConfigEntry.Gui.Tooltip
    public int fernlingBonemealCooldownMin = 600; // 30s

    @ConfigEntry.Category("fernling")
    @ConfigEntry.Gui.Tooltip
    public int fernlingBonemealCooldownMax = 2400; // 2 mins

    @ConfigEntry.Category("fernling")
    @ConfigEntry.Gui.Tooltip
    public double fernlingBonemealChance = 0.15; // 15%



    // ======================
    // Helpers
    // ======================
    public static void register() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
    }

    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}