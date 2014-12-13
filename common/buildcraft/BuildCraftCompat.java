package buildcraft;

import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.StatementManager;
import buildcraft.compat.RedLogicProvider;
import buildcraft.compat.nei.NEIIntegrationBC;
import buildcraft.core.triggers.ActionBundledOutput;
import buildcraft.core.triggers.TriggerBundledInput;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(name = "BuildCraft Compat", version = "1.0.0", useMetadata = false, modid = "BuildCraft|Compat", acceptedMinecraftVersions = "[1.7.10,1.8)", dependencies = "required-after:Forge@[10.13.0.1179,);required-after:BuildCraft|Core@[6.2.4,6.3.0)")
public class BuildCraftCompat extends BuildCraftMod {
	public static ITriggerExternal triggerBundledInputOff;
	public static ITriggerExternal triggerBundledInputOn;
	public static IActionExternal actionBundledOutput;
	
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

		if (Loader.isModLoaded("NotEnoughItems")) {
			new NEIIntegrationBC().load();
		}
	}
	
	@Mod.EventHandler
	public void postInitalize(FMLPostInitializationEvent evt) {
	}
}
