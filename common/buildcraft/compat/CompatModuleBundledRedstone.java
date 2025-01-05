package buildcraft.compat;

import buildcraft.BuildCraftCompat;
import buildcraft.api.statements.ITriggerInternal;
import buildcraft.compat.bundledredstone.ActionBundledOutput;
import buildcraft.compat.bundledredstone.StatementProviderBundledRedstone;
import buildcraft.compat.bundledredstone.TriggerBundledInput;
import buildcraft.compat.projectred.BRProviderProjectRed;
import buildcraft.transport.TileGenericPipeCompat;
import com.bluepowermod.api.BPApi;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.StatementManager;
import buildcraft.compat.bluepower.BRProviderBluePower;
import buildcraft.compat.redlogic.BRProviderRedLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class CompatModuleBundledRedstone extends CompatModuleBase {
	public interface Detector {
		boolean hasBundledInput(TileEntity tile, ForgeDirection side);

		boolean hasBundledOutput(TileEntity tile, ForgeDirection side);
	}

	public static ITriggerInternal triggerBundledInputOff;
	public static ITriggerInternal triggerBundledInputOn;
	public static IActionExternal actionBundledOutput;
	private static final List<Detector> bundledDetectors = new ArrayList<>();
	public static boolean ENABLE_NON_FREESTANDING_WIRES = false;
	public static boolean ENABLE_CONNECTING_GATES = true;

	public static void addDetector(Detector d) {
		if (bundledDetectors.isEmpty()) {
			StatementProviderBundledRedstone provider = new StatementProviderBundledRedstone();
			StatementManager.registerActionProvider(provider);
			StatementManager.registerTriggerProvider(provider);
		}
		bundledDetectors.add(d);
	}

	public static boolean isBundledInputPresent(TileEntity tile, ForgeDirection side) {
		if (tile == null || tile instanceof TileGenericPipeCompat) {
			return false;
		}
		for (Detector d : bundledDetectors) {
			if (d.hasBundledInput(tile, side)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isBundledOutputPresent(TileEntity tile, ForgeDirection side) {
		if (tile == null || tile instanceof TileGenericPipeCompat) {
			return false;
		}
		for (Detector d : bundledDetectors) {
			if (d.hasBundledOutput(tile, side)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String name() {
		return "BundledRedstone";
	}

	@Override
	public boolean canLoad() {
		return Loader.isModLoaded("BuildCraft|Transport")
				&& (Loader.isModLoaded("RedLogic")
				|| Loader.isModLoaded("bluepower")
				|| Loader.isModLoaded("ProjRed|Core"));
	}

	@Override
	public void preInit() {
		ENABLE_NON_FREESTANDING_WIRES = BuildCraftCompat.instance.getConfig().getBoolean("connectNonFreestandingWiresToPipes", "bundledRedstone", false, "Should non-freestanding wires be allowed to connect to Pipes?");
		ENABLE_CONNECTING_GATES = BuildCraftCompat.instance.getConfig().getBoolean("connectWiresToGates", "bundledRedstone", false, "Should wires be allowed to connect on sides which have gates? This does not affect other types of pluggables.");
	}

	@Override
	public void init() {
		triggerBundledInputOff = new TriggerBundledInput(false);
		triggerBundledInputOn = new TriggerBundledInput(true);
		actionBundledOutput = new ActionBundledOutput();
		if (Loader.isModLoaded("RedLogic")) {
			initRedLogic();
		}
		if (Loader.isModLoaded("bluepower")) {
			initBluepower();
		}
		if (Loader.isModLoaded("ProjRed|Core")) {
			initProjectRed();
		}
	}

	@Optional.Method(modid = "bluepower")
	private void initBluepower() {
		final BRProviderBluePower p = new BRProviderBluePower();
		BPApi.getInstance().getRedstoneApi().registerRedstoneProvider(p);
		addDetector(p);
	}

	@Optional.Method(modid = "RedLogic")
	private void initRedLogic() {
		final BRProviderRedLogic p = new BRProviderRedLogic();
		addDetector(p);
	}

	@Optional.Method(modid = "ProjRed|Core")
	private void initProjectRed() {
		final BRProviderProjectRed p = new BRProviderProjectRed();
		addDetector(p);
	}
}
