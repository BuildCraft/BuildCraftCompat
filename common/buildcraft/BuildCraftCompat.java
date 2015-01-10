package buildcraft;

import java.io.File;

import buildcraft.api.blueprints.SchematicTile;
import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.StatementManager;
import buildcraft.builders.schematics.SchematicRotateMeta;
import buildcraft.compat.CompatUtils;
import buildcraft.compat.SchematicTileDrops;
import buildcraft.compat.carpentersblocks.SchematicCBBlock;
import buildcraft.compat.carpentersblocks.SchematicCBGate;
import buildcraft.compat.carpentersblocks.SchematicCBRotated;
import buildcraft.compat.carpentersblocks.SchematicCBRotatedTwo;
import buildcraft.compat.minetweaker.MineTweakerInit;
import buildcraft.compat.multipart.MultipartSchematics;
import buildcraft.compat.redlogic.RedLogicProvider;
import buildcraft.compat.nei.NEIIntegrationBC;
import buildcraft.core.triggers.ActionBundledOutput;
import buildcraft.core.triggers.TriggerBundledInput;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.config.Configuration;

@Mod(name = "BuildCraft Compat", version = "6.3.0", useMetadata = false, modid = "BuildCraft|Compat", acceptedMinecraftVersions = "[1.7.10,1.8)", dependencies = "required-after:Forge@[10.13.0.1179,);required-after:BuildCraft|Core@[6.3.0,6.4.0)")
public class BuildCraftCompat extends BuildCraftMod {
	public static ITriggerExternal triggerBundledInputOff;
	public static ITriggerExternal triggerBundledInputOn;
	public static IActionExternal actionBundledOutput;

	public static boolean enableBundledRedstone;
    public static boolean enableNEI;
    public static boolean enableMultipart;

	private static Configuration config;

	private boolean getModBoolean(String modID, String name, String cat, boolean def, String comm) {
		if (Loader.isModLoaded(modID)) {
			return config.getBoolean(name, cat, def, comm);
		} else {
			return false;
		}
	}

	@Mod.EventHandler
	public void loadConfig(FMLPreInitializationEvent evt) {
		config = new Configuration(new File(new File(evt.getSuggestedConfigurationFile().getParentFile(), "buildcraft"), "compat.cfg"));
		config.load();

		if (Loader.isModLoaded("RedLogic")) {
			enableBundledRedstone = config.getBoolean("enableBundledRedstone", "compat", false, "RedLogic compatibility - bundled cables can be connected to pipes. WARNING: HIGHLY EXPERIMENTAL - MIGHT BE BROKEN");
		}
        enableNEI = getModBoolean("NotEnoughItems", "enableNEI", "compat", true, "NEI recipe and ledger integration.");
        enableMultipart = getModBoolean("ForgeMultipart", "enableMultipart", "compat", true, "ForgeMultipart schematic integration.");

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

		if (enableMultipart) {
			MultipartSchematics.init();
		}

		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			if (enableNEI) {
				new NEIIntegrationBC().load();
			}
		}

		// Register schematic compatibility
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersBlock", SchematicCBBlock.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersDaylightSensor", SchematicTileDrops.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersGate", SchematicCBGate.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersLadder", SchematicCBRotatedTwo.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersPressurePlate", SchematicTileDrops.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersSlope", SchematicCBRotated.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersStairs", SchematicCBRotated.class);
	}
	
	@Mod.EventHandler
	public void postInitalize(FMLPostInitializationEvent evt) {
		if (Loader.isModLoaded("MineTweaker3")) {
			MineTweakerInit.init();
		}
	}
}
