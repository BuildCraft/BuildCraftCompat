package buildcraft.compat;

import buildcraft.BuildCraftCompat;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;

import buildcraft.compat.nei.NEIGuiHandlerBC;
import buildcraft.compat.nei.RecipeHandlerAssemblyTable;
import buildcraft.compat.nei.RecipeHandlerBase;
import buildcraft.compat.nei.RecipeHandlerIntegrationTable;
import buildcraft.compat.nei.RecipeHandlerRefinery;
import codechicken.nei.api.API;
import codechicken.nei.api.INEIGuiHandler;

public class CompatModuleNEI extends CompatModuleBase {
    public static boolean disableFacadeNEI;

    @Override
    public String name() {
        return "NotEnoughItems";
    }
    
    @Override
    public boolean canLoad() {
        return super.canLoad() && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
    }

    @Override
    public void preInit() {
        disableFacadeNEI = BuildCraftCompat.instance.getConfig().getBoolean("hideFacadeRecipes", "client", false, "Should NEI facade recipes be hidden?");
    }

    @Optional.Method(modid = "NotEnoughItems")
    private void registerHandler(final RecipeHandlerBase handler) {
        handler.prepare();
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
    }
    
    @Override
    @Optional.Method(modid = "NotEnoughItems")
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
