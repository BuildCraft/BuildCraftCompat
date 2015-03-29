package buildcraft;

import java.io.File;
import java.util.HashSet;

import buildcraft.api.core.BCLog;
import buildcraft.api.core.BuildCraftAPI;
import buildcraft.api.robots.RobotManager;
import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.StatementManager;
import buildcraft.compat.CompatModuleAMT;
import buildcraft.compat.CompatModuleBase;
import buildcraft.compat.CompatModuleCarpentersBlocks;
import buildcraft.compat.CompatModuleFMP;
import buildcraft.compat.CompatModuleIronChest;
import buildcraft.compat.CompatModuleNEI;
import buildcraft.compat.CompatUtils;
import buildcraft.compat.lib.SchematicTileDrops;
import buildcraft.compat.carpentersblocks.SchematicCBBlock;
import buildcraft.compat.carpentersblocks.SchematicCBGate;
import buildcraft.compat.carpentersblocks.SchematicCBRotated;
import buildcraft.compat.carpentersblocks.SchematicCBRotatedTwo;
import buildcraft.compat.carpentersblocks.SchematicCBSafe;
import buildcraft.compat.ironchests.SchematicIronChest;
import buildcraft.compat.CompatModuleMFR;
import buildcraft.compat.CompatModuleMineTweaker3;
import buildcraft.compat.multipart.MultipartSchematics;
import buildcraft.compat.properties.WorldPropertyIsHarvestableCompat;
import buildcraft.compat.redlogic.RedLogicProvider;
import buildcraft.compat.nei.NEIIntegrationBC;
import buildcraft.compat.robots.BoardRobotHarvesterCompat;
import buildcraft.core.triggers.ActionBundledOutput;
import buildcraft.core.triggers.TriggerBundledInput;
import buildcraft.robotics.boards.BoardRobotHarvester;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.config.Configuration;

@Mod(name = "BuildCraft Compat", version = "6.4.0", useMetadata = false, modid = "BuildCraft|Compat", acceptedMinecraftVersions = "[1.7.10,1.8)", dependencies = "required-after:Forge@[10.13.0.1179,);required-after:BuildCraft|Core")
public class BuildCraftCompat extends BuildCraftMod {
	public static ITriggerExternal triggerBundledInputOff;
	public static ITriggerExternal triggerBundledInputOn;
	public static IActionExternal actionBundledOutput;

	public static boolean enableBundledRedstone;

	private static Configuration config;
	private static final HashSet<CompatModuleBase> modules = new HashSet<CompatModuleBase>();
	private static final HashSet<String> moduleNames = new HashSet<String>();

	private boolean getModBoolean(String modID, String name, String cat, boolean def, String comm) {
		if (Loader.isModLoaded(modID)) {
			return config.getBoolean(name, cat, def, comm);
		} else {
			return false;
		}
	}

	private void offerModule(CompatModuleBase module) {
		if (module.canLoad()) {
			if (config.getBoolean(module.name(), "modules", true, module.comment())) {
				modules.add(module);
				moduleNames.add(module.name());
			}
		}
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		config = new Configuration(new File(new File(evt.getSuggestedConfigurationFile().getParentFile(), "buildcraft"), "compat.cfg"));
		config.load();

		offerModule(new CompatModuleAMT());
		offerModule(new CompatModuleFMP());
		offerModule(new CompatModuleMFR());
		offerModule(new CompatModuleMineTweaker3());
		offerModule(new CompatModuleNEI());

		// Builder
		offerModule(new CompatModuleCarpentersBlocks());
		offerModule(new CompatModuleIronChest());

		if (Loader.isModLoaded("RedLogic")) {
			enableBundledRedstone = config.getBoolean("enableBundledRedstone", "compat", false, "RedLogic compatibility - bundled cables can be connected to pipes. WARNING: HIGHLY EXPERIMENTAL - MIGHT BE BROKEN");
		}

		config.save();
	}

	@Mod.EventHandler
	public void initalize(FMLInitializationEvent evt) {
		if (enableBundledRedstone) {
			triggerBundledInputOff = new TriggerBundledInput(false);
			triggerBundledInputOn = new TriggerBundledInput(true);
			actionBundledOutput = new ActionBundledOutput();

			if (Loader.isModLoaded("RedLogic")) {
				RedLogicProvider p = new RedLogicProvider();
				StatementManager.registerActionProvider(p);
				StatementManager.registerTriggerProvider(p);
			}
		}

		for (CompatModuleBase m : modules) {
			BCLog.logger.info("Loading compat module " + m.name());
			m.init();
		}
	}
	
	@Mod.EventHandler
	public void postInitalize(FMLPostInitializationEvent evt) {
		for (CompatModuleBase m : modules) {
			m.postInit();
		}

		BuildCraftAPI.registerWorldProperty("harvestable", new WorldPropertyIsHarvestableCompat());

		if (Loader.isModLoaded("BuildCraft|Robotics")) {
			postInitRobotics();
		}
	}

	@Optional.Method(modid = "BuildCraft|Robotics")
	public void postInitRobotics() {
		RobotManager.registerAIRobot(BoardRobotHarvesterCompat.class, "boardRobotHarvester");
	}

	public static boolean hasModule(String module) {
		return moduleNames.contains(module);
	}
}
