package buildcraft.compat.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraftforge.fml.common.Loader;

import buildcraft.api.core.BCLog;

import mezz.jei.api.*;

@JEIPlugin
public class BCPluginJEI implements IModPlugin {
    public static boolean disableFacadeJEI;

    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {}

    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) {}

    @Override
    public void register(IModRegistry registry) {
        boolean transport = Loader.isModLoaded("BuildCraft|Transport");
        boolean factory = Loader.isModLoaded("BuildCraft|Factory");
        boolean energy = Loader.isModLoaded("BuildCraft|Energy");
        boolean silicon = Loader.isModLoaded("BuildCraft|Silicon");
        boolean robotics = Loader.isModLoaded("BuildCraft|Robotics");
        List<String> lst = new ArrayList<>();

        if (transport) {
            lst.add("transport");

            registry.addAdvancedGuiHandlers(new GateGuiHandler());
        }
        if (factory) {
            lst.add("factory");
            // if (energy && BuildCraftFactory.NEW_REFINERY_TESTING) {

            // }
        }
        if (energy) {
            lst.add("energy");

            // registry.addRecipeCategories(new CategoryCombustionEngine());
            // registry.addRecipeHandlers(new HandlerCombusionEngine());
            // registry.addRecipes(ImmutableList.copyOf(FuelManager.INSTANCE.getFuels()));
        }
        BCLog.logger.info("Loaded JEI mods: " + Arrays.toString(lst.toArray()));
    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {}

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {}
}
