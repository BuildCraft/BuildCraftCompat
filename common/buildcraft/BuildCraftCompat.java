package buildcraft;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import buildcraft.api.blueprints.SchematicTile;
import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.StatementManager;
import buildcraft.builders.schematics.SchematicRotateMeta;
import buildcraft.compat.CompatModule;
import buildcraft.compat.CompatUtils;
import buildcraft.compat.SchematicTileDrops;
import buildcraft.compat.carpentersblocks.SchematicCBBlock;
import buildcraft.compat.carpentersblocks.SchematicCBGate;
import buildcraft.compat.carpentersblocks.SchematicCBRotated;
import buildcraft.compat.carpentersblocks.SchematicCBRotatedTwo;
import buildcraft.compat.carpentersblocks.SchematicCBSafe;
import buildcraft.compat.ironchests.SchematicIronChest;
import buildcraft.compat.minetweaker.MineTweakerInit;
import buildcraft.compat.multipart.MultipartSchematics;
import buildcraft.compat.redlogic.RedLogicProvider;
import buildcraft.compat.nei.NEIIntegrationBC;
import buildcraft.compat.tfc.TFCCompat;
import buildcraft.core.triggers.ActionBundledOutput;
import buildcraft.core.triggers.TriggerBundledInput;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.config.Configuration;

@Mod(name = "BuildCraft Compat", version = "@VERSION@", useMetadata = false, modid = "BuildCraft|Compat", acceptedMinecraftVersions = "[1.7.10,1.8)", dependencies = "required-after:Forge@[10.13.0.1179,);required-after:BuildCraft|Core@[6.3.0,6.4.0);after:BuildCraft|Builders;after:BuildCraft|Factory;after:BuildCraft|Silicon;after:BuildCraft|Transport;after:BuildCraft|Energy")
public class BuildCraftCompat extends BuildCraftMod {
	public static ITriggerExternal triggerBundledInputOff;
	public static ITriggerExternal triggerBundledInputOn;
	public static IActionExternal actionBundledOutput;

	public static boolean enableRedLogic;
	public static boolean enableProjectRed;
	public static boolean enableBundledRedstone;
    public static boolean enableNEI;
    public static boolean enableMultipart;

	private static final CompatModule[] modules = {
			new TFCCompat()
	};

	private static Configuration config;
	private static Logger logger;

	private final List<CompatModule> loadedModules = new ArrayList<CompatModule>();

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

		logger = evt.getModLog();

		enableRedLogic = getModBoolean("RedLogic", "RedLogic", "modules", true, "RedLogic integration for bundled redstone in gates.");
        enableNEI = getModBoolean("NotEnoughItems", "NotEnoughItems", "modules", true, "NEI recipe and ledger integration.");
        enableMultipart = getModBoolean("ForgeMultipart", "ForgeMultipart", "modules", true, "ForgeMultipart schematic integration.");

		for (CompatModule m : modules) {
			if (Loader.isModLoaded(m.getModID())) {
				if (config.getBoolean(m.getModID(), "modules", true, m.getConfigDescription())) {
					loadedModules.add(m);
					m.preInit();
				}
			}
		}

		config.save();

		enableBundledRedstone = enableRedLogic || enableProjectRed;
	}

	@Mod.EventHandler
	public void initalize(FMLInitializationEvent evt) {
		triggerBundledInputOff = new TriggerBundledInput(false);
		triggerBundledInputOn = new TriggerBundledInput(true);
		actionBundledOutput = new ActionBundledOutput();

		if (enableRedLogic) {
			RedLogicProvider p = new RedLogicProvider();
			StatementManager.registerActionProvider(p);
			StatementManager.registerTriggerProvider(p);
		}

		if (enableMultipart) {
			MultipartSchematics.init();
		}

		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			if (enableNEI) {
				new NEIIntegrationBC().load();
			}
		}

		for (CompatModule m : loadedModules) {
			m.init();

			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
				m.initClient();
			}
		}

		// Register schematic compatibility
		CompatUtils.registerSchematic("IronChest:BlockIronChest", SchematicIronChest.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersBlock", SchematicCBBlock.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersDaylightSensor", SchematicTileDrops.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersGate", SchematicCBGate.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersLadder", SchematicCBRotatedTwo.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersPressurePlate", SchematicTileDrops.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersSafe", SchematicCBSafe.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersSlope", SchematicCBRotated.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersStairs", SchematicCBRotated.class);
	}
	
	@Mod.EventHandler
	public void postInitalize(FMLPostInitializationEvent evt) {
		if (Loader.isModLoaded("MineTweaker3")) {
			MineTweakerInit.init();
		}

		for (CompatModule m : loadedModules) {
			m.postInit();

			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
				m.postInitClient();
			}
		}
	}

	@Mod.EventHandler
	public void remapBlocks(FMLMissingMappingsEvent evt) {
		for (FMLMissingMappingsEvent.MissingMapping m : evt.getAll()) {
			if (m.name.startsWith("bctfccrossover")) {
				if (m.type == GameRegistry.Type.BLOCK) {
					m.remap(Block.getBlockFromName(m.name.replaceAll("bctfccrossover", "BuildCraft|Compat")));
				} else if (m.type == GameRegistry.Type.ITEM) {
					m.remap((Item) Item.itemRegistry.getObject(m.name.replaceAll("bctfccrossover", "BuildCraft|Compat")));
				} else {
					m.warn();
				}
			}
		}
	}
}
