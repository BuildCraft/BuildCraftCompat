package buildcraft.compat;

import buildcraft.api.BCModules;
import buildcraft.lib.config.BCConfig;
import buildcraft.lib.config.ConfigCategory;
import buildcraft.lib.config.Configuration;
import buildcraft.lib.config.EnumRestartRequirement;

public class BCCompatConfig {
    public static Configuration config;

    // Calen: remove the json file
    public static boolean coloredPipesVisible;
    public static boolean facadesVisible;
    private static ConfigCategory<Boolean> propColoredPipesVisible;
    private static ConfigCategory<Boolean> propFacadesVisible;

    public static void preInit() {
        // Start config
        BCModules module = BCModules.COMPAT;
        config = new Configuration(module);
        createProps();
        reloadConfig();
//        BCCoreConfig.addReloadListener(BCEnergyConfig::reloadConfig);
        BCConfig.registerReloadListener(module, BCCompatConfig::reloadConfig);
    }

    public static void createProps() {
        EnumRestartRequirement world = EnumRestartRequirement.WORLD;

        propColoredPipesVisible = config
                .define("client",
                        "Should all colored pipes be visible in JEI/REI?",
                        world,
                        "colored_pipes_visible", false);

        propFacadesVisible = config
                .define("client",
                        "Should all facades be visible in JEI/REI?",
                        world,
                        "facades_visible", false);
    }

    public static void reloadConfig() {
        coloredPipesVisible = propColoredPipesVisible.get();
        facadesVisible = propFacadesVisible.get();

        saveConfigs();
    }

    public static void saveConfigs() {
        if (config.hasChanged()) {
            config.save();
        }
    }
}
