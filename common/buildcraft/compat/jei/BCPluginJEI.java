package buildcraft.compat.jei;

import net.minecraftforge.fml.common.Loader;

import mezz.jei.api.*;

@JEIPlugin
public class BCPluginJEI implements IModPlugin {
    public static boolean disableFacadeJEI;

    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {

    }

    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) {

    }

    @Override
    public void register(IModRegistry registry) {
        if (Loader.isModLoaded("BuildCraft|Transport")) {
            registry.addAdvancedGuiHandlers(new GateGuiHandler());
        }
    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {

    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }
}
