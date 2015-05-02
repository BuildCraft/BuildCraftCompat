package buildcraft.compat;

import cpw.mods.fml.relauncher.*;

import codechicken.nei.api.API;
import codechicken.nei.recipe.*;
import cpw.mods.fml.common.*;
import buildcraft.compat.nei.*;
import codechicken.nei.api.*;

public class CompatModuleNEI extends CompatModuleBase
{
    @Override
    public String name() {
        return "NotEnoughItems";
    }
    
    @Override
    public boolean canLoad() {
        return super.canLoad() && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
    }
    
    private void registerHandler(final RecipeHandlerBase handler) {
        handler.prepare();
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
    }
    
    @Override
    public void init() {
        if (Loader.isModLoaded("BuildCraft|Factory")) {
            this.registerHandler(new RecipeHandlerRefinery());
        }
        if (Loader.isModLoaded("BuildCraft|Silicon")) {
            this.registerHandler(new RecipeHandlerAssemblyTable());
            this.registerHandler(new RecipeHandlerIntegrationTable());
        }
        API.registerNEIGuiHandler((INEIGuiHandler)new NEIGuiHandlerBC());
    }
}
