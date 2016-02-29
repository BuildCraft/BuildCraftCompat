package buildcraft.compat.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraftforge.fml.common.Loader;

import buildcraft.api.core.BCLog;
import buildcraft.energy.fuels.FuelManager;

import mezz.jei.api.*;

@JEIPlugin
public class BCPluginJEI implements IModPlugin {
    public static boolean disableFacadeJEI;
    public static IModRegistry registry;
    public static IJeiRuntime runtime;

    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {}

    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) {}

    @Override
    public void register(IModRegistry jeiRegistry) {
        registry = jeiRegistry;
        boolean transport = Loader.isModLoaded("BuildCraft|Transport");
        boolean factory = Loader.isModLoaded("BuildCraft|Factory");
        boolean energy = Loader.isModLoaded("BuildCraft|Energy");
        boolean silicon = Loader.isModLoaded("BuildCraft|Silicon");
        boolean robotics = Loader.isModLoaded("BuildCraft|Robotics");
        List<String> lst = new ArrayList<>();

        if (transport) {
            lst.add("transport");

            jeiRegistry.addAdvancedGuiHandlers(new GateGuiHandler());
            jeiRegistry.addAdvancedGuiHandlers(new LedgerGuiHandler());
        }
        if (factory) {
            lst.add("factory");
            // if (energy) {

            // }
        }
        if (energy) {
            lst.add("energy");

            jeiRegistry.addRecipeCategories(new CategoryCombustionEngine(jeiRegistry.getJeiHelpers().getGuiHelper()));
            jeiRegistry.addRecipeHandlers(new HandlerCombusionEngine());
            jeiRegistry.addRecipes(ImmutableList.copyOf(FuelManager.INSTANCE.getFuels()));
        }
        BCLog.logger.info("Loaded JEI mods: " + Arrays.toString(lst.toArray()));
    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {}

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
    }
}
