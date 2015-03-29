package buildcraft.compat;

import codechicken.nei.api.API;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import buildcraft.api.recipes.IFlexibleRecipeViewable;
import buildcraft.compat.nei.NEIGuiHandlerBC;
import buildcraft.compat.nei.RecipeHandlerAssemblyTable;
import buildcraft.compat.nei.RecipeHandlerBase;
import buildcraft.compat.nei.RecipeHandlerIntegrationTable;
import buildcraft.compat.nei.RecipeHandlerRefinery;

public class CompatModuleNEI extends CompatModuleBase {
	@Override
	public String name() {
		return "NotEnoughItems";
	}

	@Override
	public boolean canLoad() {
		return super.canLoad() && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
	}

	private void registerHandler(RecipeHandlerBase handler) {
		handler.prepare();
		API.registerRecipeHandler(handler);
		API.registerUsageHandler(handler);
	}

	@Override
	public void init() {
		if (Loader.isModLoaded("BuildCraft|Factory")) {
			registerHandler(new RecipeHandlerRefinery());
		}
		if (Loader.isModLoaded("BuildCraft|Silicon")) {
			registerHandler(new RecipeHandlerAssemblyTable());
			registerHandler(new RecipeHandlerIntegrationTable());
		}

		API.registerNEIGuiHandler(new NEIGuiHandlerBC());
	}
}
