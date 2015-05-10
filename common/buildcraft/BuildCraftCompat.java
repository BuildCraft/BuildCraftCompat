package buildcraft;

import java.io.File;
import java.util.HashSet;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

import buildcraft.api.core.BCLog;
import buildcraft.api.core.BuildCraftAPI;
import buildcraft.api.core.IWorldProperty;
import buildcraft.api.robots.RobotManager;
import buildcraft.compat.CompatModuleAMT;
import buildcraft.compat.CompatModuleBase;
import buildcraft.compat.CompatModuleBundledRedstone;
import buildcraft.compat.CompatModuleCarpentersBlocks;
import buildcraft.compat.CompatModuleFMP;
import buildcraft.compat.CompatModuleIronChest;
import buildcraft.compat.CompatModuleMFR;
import buildcraft.compat.CompatModuleMineTweaker3;
import buildcraft.compat.CompatModuleNEI;
import buildcraft.compat.CompatModuleWAILA;
import buildcraft.compat.properties.WorldPropertyIsHarvestableCompat;
import buildcraft.compat.robots.BoardRobotHarvesterCompat;

@Mod(name = "BuildCraft Compat", version = "7.0.2", useMetadata = false, modid = "BuildCraft|Compat", acceptedMinecraftVersions = "[1.7.10,1.8)", dependencies = "required-after:Forge@[10.13.0.1179,);required-after:BuildCraft|Core")
public class BuildCraftCompat extends BuildCraftMod {
    private static Configuration config;
    private static final HashSet<CompatModuleBase> modules;
    private static final HashSet<String> moduleNames;

    private void offerModule(final CompatModuleBase module) {
        if (module.canLoad() && BuildCraftCompat.config.getBoolean(module.name(), "modules", true, module.comment())) {
            BuildCraftCompat.modules.add(module);
            BuildCraftCompat.moduleNames.add(module.name());
        }
    }

    public static boolean isLoaded(String module) {
        return moduleNames.contains(module);
    }

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent evt) {
        (BuildCraftCompat.config = new Configuration(new File(new File(evt.getSuggestedConfigurationFile().getParentFile(), "buildcraft"), "compat.cfg"))).load();
        this.offerModule(new CompatModuleAMT());
        this.offerModule(new CompatModuleFMP());
        this.offerModule(new CompatModuleMFR());
        this.offerModule(new CompatModuleMineTweaker3());
        this.offerModule(new CompatModuleNEI());
        this.offerModule(new CompatModuleCarpentersBlocks());
        this.offerModule(new CompatModuleIronChest());
        this.offerModule(new CompatModuleWAILA());
        this.offerModule(new CompatModuleBundledRedstone());
        BuildCraftCompat.config.save();
    }
    
    @Mod.EventHandler
    public void initalize(final FMLInitializationEvent evt) {
        for (final CompatModuleBase m : BuildCraftCompat.modules) {
            BCLog.logger.info("Loading compat module " + m.name());
            m.init();
        }
    }
    
    @Mod.EventHandler
    public void postInitalize(final FMLPostInitializationEvent evt) {
        for (final CompatModuleBase m : BuildCraftCompat.modules) {
            m.postInit();
        }
        BuildCraftAPI.registerWorldProperty("harvestable", new WorldPropertyIsHarvestableCompat());
        if (Loader.isModLoaded("BuildCraft|Robotics")) {
            this.postInitRobotics();
        }
    }
    
    @Optional.Method(modid = "BuildCraft|Robotics")
    public void postInitRobotics() {
        RobotManager.registerAIRobot((Class)BoardRobotHarvesterCompat.class, "boardRobotHarvester");
    }
    
    public static boolean hasModule(final String module) {
        return BuildCraftCompat.moduleNames.contains(module);
    }
    
    static {
        modules = new HashSet<CompatModuleBase>();
        moduleNames = new HashSet<String>();
    }
}
