package buildcraft.compat;

import com.bluepowermod.api.BPApi;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.StatementManager;
import buildcraft.compat.bluepower.BRProviderBluePower;
import buildcraft.compat.redlogic.BRProviderRedLogic;

public class CompatModuleBundledRedstone extends CompatModuleBase {
	public static ITriggerExternal triggerBundledInputOff;
	public static ITriggerExternal triggerBundledInputOn;
	public static IActionExternal actionBundledOutput;

	@Override
	public String name() {
		return "BundledRedstone";
	}

	@Override
	public boolean canLoad() {
		return Loader.isModLoaded("BuildCraft|Transport") && (Loader.isModLoaded("RedLogic") || Loader.isModLoaded("bluepower"));
	}

	@Override
	public void init() {
		triggerBundledInputOff = (ITriggerExternal)new TriggerBundledInput(false);
		triggerBundledInputOn = (ITriggerExternal)new TriggerBundledInput(true);
		actionBundledOutput = (IActionExternal)new ActionBundledOutput();
		if (Loader.isModLoaded("RedLogic")) {
			initRedLogic();
		}
		if (Loader.isModLoaded("bluepower")) {
			initBluepower();
		}
	}

	@Optional.Method(modid = "bluepower")
	private void initBluepower() {
		final BRProviderBluePower p = new BRProviderBluePower();
		BPApi.getInstance().getRedstoneApi().registerRedstoneProvider(p);
		StatementManager.registerActionProvider(p);
		StatementManager.registerTriggerProvider(p);
	}

	@Optional.Method(modid = "RedLogic")
	private void initRedLogic() {
		final BRProviderRedLogic p = new BRProviderRedLogic();
		StatementManager.registerActionProvider(p);
		StatementManager.registerTriggerProvider(p);
	}
}
