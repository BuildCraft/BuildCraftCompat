package buildcraft;

import buildcraft.BuildCraftMod;
import buildcraft.api.gates.IAction;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.StatementManager;
import buildcraft.compat.DyeUtil;
import buildcraft.compat.RedLogicProvider;
import buildcraft.core.triggers.ActionBundledOutput;
import buildcraft.core.triggers.TriggerBundledInput;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(name = "BuildCraft Compat", version = "1.0.0", useMetadata = false, modid = "BuildCraft|Compat", acceptedMinecraftVersions = "[1.7.10,1.8)", dependencies = "required-after:Forge@[10.13.0.1179,);required-after:BuildCraft|Core@[6.1.1,6.2.0b)")
public class BuildCraftCompat extends BuildCraftMod {
	public static ITrigger triggerBundledInputOff;
	public static ITrigger triggerBundledInputOn;
	public static IAction actionBundledOutput;
	
	@Mod.EventHandler
	public void initalize(FMLInitializationEvent evt) {
		triggerBundledInputOff = new TriggerBundledInput(false);
		triggerBundledInputOn = new TriggerBundledInput(true);
		actionBundledOutput = new ActionBundledOutput();
		
		if (Loader.isModLoaded("RedLogic")) {
			RedLogicProvider p = new RedLogicProvider();
			StatementManager.registerActionProvider(p);
			StatementManager.registerTriggerProvider(p);
		}
	}
	
	@Mod.EventHandler
	public void postInitalize(FMLPostInitializationEvent evt) {
		DyeUtil.initialize();
	}
}
