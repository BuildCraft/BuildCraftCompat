package buildcraft.compat.nei;

import codechicken.nei.api.API;

import cpw.mods.fml.common.Loader;

public class NEIIntegrationBC {

    private void registerHandler(RecipeHandlerBase handler) {
        handler.prepare();
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
    }

    public void load() {
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
