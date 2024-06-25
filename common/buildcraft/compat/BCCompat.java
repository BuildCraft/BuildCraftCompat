package buildcraft.compat;

import buildcraft.api.BCModules;
import buildcraft.api.core.BCLog;
import buildcraft.compat.module.crafttweaker.CompatModuleCraftTweaker;
import buildcraft.compat.module.ic2.CompatModuleIndustrialCraft2;
import buildcraft.compat.module.theoneprobe.CompatModuleTheOneProbe;
import buildcraft.lib.config.BCConfig;
import buildcraft.lib.config.Configuration;
import buildcraft.lib.config.EnumRestartRequirement;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.util.HashMap;
import java.util.Map;

//@Mod(
//        modid = "buildcraftcompat",
//        name = "BuildCraft Compat",
//        version = "7.99.24.8",
//        updateJSON = "https://mod-buildcraft.com/version/versions-compat.json",
//        acceptedMinecraftVersions = "[1.12.2]",
//        dependencies = "required-after:forge@[14.23.0.2544,);required-after:buildcraftcore@[7.99.24.8,);after:buildcrafttransport;after:buildcraftbuilders;after:buildcraftsilicon;after:theoneprobe;after:forestry;after:crafttweaker;after:ic2"
//)
@Mod(BCCompat.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCCompat {
    static final String DEPENDENCIES = "required-after:forge@(gradle_replace_forgeversion,)"//
            + ";required-after:buildcraftcore@[$bc_version,)"//
            + ";after:buildcrafttransport"//
            + ";after:buildcraftbuilders"//
            + ";after:buildcraftsilicon"//
            + ";after:theoneprobe"//
            + ";after:forestry"//
            + ";after:crafttweaker"//
            + ";after:ic2"//
            ;
    public static final String MODID = "buildcraftcompat";
    public static final String VERSION = "$version";
    public static final String GIT_BRANCH = "${git_branch}";
    public static final String GIT_COMMIT_HASH = "${git_commit_hash}";
    public static final String GIT_COMMIT_MSG = "${git_commit_msg}";
    public static final String GIT_COMMIT_AUTHOR = "${git_commit_author}";

    // @Instance(MOD_ID)
    public static BCCompat instance;
    private static final Map<String, CompatModuleBase> modules = new HashMap<>();
    private static final Map<String, BooleanValue> moduleConfigs = new HashMap<>();

    public static Configuration config;

    public BCCompat() {
        instance = this;
    }

    private static void offerAndPreInitModule(CompatModuleBase module) {
        String cModId = module.compatModId();
        if (module.canLoad()) {
            String _modules = "modules";
            BooleanValue prop = config
                    .define(_modules,
                            "",
                            EnumRestartRequirement.NONE,
                            cModId, true);
            modules.put(cModId, module);
            moduleConfigs.put(cModId, prop);
//            if (prop.get()) {
//                modules.put(cModId, module);
//                BCLog.logger.info("[compat]   + " + cModId);
//                module.preInit();
//            } else {
//                BCLog.logger.info("[compat]   x " + cModId + " (It has been disabled in the config)");
//            }
        } else {
            BCLog.logger.info("[compat]   x " + cModId + " (It cannot load)");
        }
    }

    @SubscribeEvent
    public static void preInit(FMLConstructModEvent evt) {
        // Start config
        BCModules module = BCModules.COMPAT;
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        config = new Configuration(builder, module);

        // init
        BCLog.logger.info("");
        BCLog.logger.info("Starting BuildCraftCompat " + VERSION);
        BCLog.logger.info("Copyright (c) the BuildCraft team, 2011-2017");
        BCLog.logger.info("https://www.mod-buildcraft.com");
        if (!GIT_COMMIT_HASH.startsWith("${")) {
            BCLog.logger.info("Detailed Build Information:");
            BCLog.logger.info("  Branch " + GIT_BRANCH);
            BCLog.logger.info("  Commit " + GIT_COMMIT_HASH);
            BCLog.logger.info("    " + GIT_COMMIT_MSG);
            BCLog.logger.info("    committed by " + GIT_COMMIT_AUTHOR);
        }
        BCLog.logger.info("");

        BCLog.logger.info("[compat] Module list:");
        // List of all modules
        // TODO Calen Forestry?
//        offerAndPreInitModule(new CompatModuleForestry());
        offerAndPreInitModule(new CompatModuleTheOneProbe());
        offerAndPreInitModule(new CompatModuleCraftTweaker());
        offerAndPreInitModule(new CompatModuleIndustrialCraft2());
        // End of module list

        // Finalize config
        ForgeConfigSpec spec = config.build();
        ModContainer container = ModList.get().getModContainerById(module.getModId()).get();
        container.addConfig(new ModConfig(ModConfig.Type.COMMON, spec, container, config.getFileName()));
    }

    /** This is called after config loaded. */
    private static void loadModules() {
        modules.entrySet().forEach(entry ->
        {
            String cModId = entry.getKey();
            CompatModuleBase module = entry.getValue();
            if (moduleConfigs.get(cModId).get()) {
                BCLog.logger.info("[compat]   + " + cModId);
                module.preInit();
            } else {
                BCLog.logger.info("[compat]   x " + cModId + " (It has been disabled in the config)");
            }
        });
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent evt) {
        loadModules();
        // TODO Calen compat GUI???
//        NetworkRegistry.INSTANCE.registerGuiHandler(instance, CompatGui.guiHandlerProxy);
        for (CompatModuleBase m : modules.values()) {
            m.init();
        }
    }

    @SubscribeEvent
    public static void postInit(FMLLoadCompleteEvent evt) {
        for (CompatModuleBase m : modules.values()) {
            m.postInit();
        }
    }
}
