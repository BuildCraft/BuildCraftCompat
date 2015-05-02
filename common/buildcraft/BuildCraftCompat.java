package buildcraft;

import cpw.mods.fml.common.Optional;
import buildcraft.api.robots.RobotManager;
import buildcraft.compat.CompatModuleWAILA;
import buildcraft.compat.robots.BoardRobotHarvesterCompat;
import buildcraft.api.core.IWorldProperty;
import buildcraft.api.core.BuildCraftAPI;
import buildcraft.compat.properties.WorldPropertyIsHarvestableCompat;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import java.util.Iterator;
import buildcraft.api.core.BCLog;
import buildcraft.api.statements.ITriggerProvider;
import buildcraft.api.statements.IActionProvider;
import buildcraft.api.statements.StatementManager;
import buildcraft.compat.redlogic.RedLogicProvider;
import buildcraft.core.triggers.ActionBundledOutput;
import buildcraft.core.triggers.TriggerBundledInput;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import buildcraft.compat.CompatModuleIronChest;
import buildcraft.compat.CompatModuleCarpentersBlocks;
import buildcraft.compat.CompatModuleNEI;
import buildcraft.compat.CompatModuleMineTweaker3;
import buildcraft.compat.CompatModuleMFR;
import buildcraft.compat.CompatModuleFMP;
import buildcraft.compat.CompatModuleAMT;
import java.io.File;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Loader;
import buildcraft.compat.CompatModuleBase;
import java.util.HashSet;
import net.minecraftforge.common.config.Configuration;
import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.ITriggerExternal;
import cpw.mods.fml.common.Mod;

@Mod(name = "BuildCraft Compat", version = "6.4.0", useMetadata = false, modid = "BuildCraft|Compat", acceptedMinecraftVersions = "[1.7.10,1.8)", dependencies = "required-after:Forge@[10.13.0.1179,);required-after:BuildCraft|Core")
public class BuildCraftCompat extends BuildCraftMod
{
    public static ITriggerExternal triggerBundledInputOff;
    public static ITriggerExternal triggerBundledInputOn;
    public static IActionExternal actionBundledOutput;
    public static boolean enableBundledRedstone;
    private static Configuration config;
    private static final HashSet<CompatModuleBase> modules;
    private static final HashSet<String> moduleNames;
    
    private boolean getModBoolean(final String modID, final String name, final String cat, final boolean def, final String comm) {
        return Loader.isModLoaded(modID) && BuildCraftCompat.config.getBoolean(name, cat, def, comm);
    }
    
    private void offerModule(final CompatModuleBase module) {
        if (module.canLoad() && BuildCraftCompat.config.getBoolean(module.name(), "modules", true, module.comment())) {
            BuildCraftCompat.modules.add(module);
            BuildCraftCompat.moduleNames.add(module.name());
        }
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
        if (Loader.isModLoaded("RedLogic")) {
            BuildCraftCompat.enableBundledRedstone = BuildCraftCompat.config.getBoolean("enableBundledRedstone", "compat", false, "RedLogic compatibility - bundled cables can be connected to pipes. WARNING: HIGHLY EXPERIMENTAL - MIGHT BE BROKEN");
        }
        BuildCraftCompat.config.save();
    }
    
    @Mod.EventHandler
    public void initalize(final FMLInitializationEvent evt) {
        if (BuildCraftCompat.enableBundledRedstone) {
            BuildCraftCompat.triggerBundledInputOff = (ITriggerExternal)new TriggerBundledInput(false);
            BuildCraftCompat.triggerBundledInputOn = (ITriggerExternal)new TriggerBundledInput(true);
            BuildCraftCompat.actionBundledOutput = (IActionExternal)new ActionBundledOutput();
            if (Loader.isModLoaded("RedLogic")) {
                final RedLogicProvider p = new RedLogicProvider();
                StatementManager.registerActionProvider((IActionProvider)p);
                StatementManager.registerTriggerProvider((ITriggerProvider)p);
            }
        }
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
        BuildCraftAPI.registerWorldProperty("harvestable", (IWorldProperty) new WorldPropertyIsHarvestableCompat());
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
