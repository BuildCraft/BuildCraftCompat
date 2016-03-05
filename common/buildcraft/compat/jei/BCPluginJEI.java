package buildcraft.compat.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraftforge.fml.common.Loader;

import buildcraft.api.core.BCLog;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.compat.jei.recipe.CategoryCombustionEngine;
import buildcraft.compat.jei.recipe.CategoryHeatable;
import buildcraft.compat.jei.recipe.HandlerCombusionEngine;
import buildcraft.compat.jei.recipe.HandlerHeatableFluid;
import buildcraft.energy.fuels.FuelManager;
import buildcraft.energy.gui.GuiCombustionEngine;
import buildcraft.energy.gui.GuiStoneEngine;
import buildcraft.factory.gui.GuiDistiller;
import buildcraft.factory.gui.GuiEnergyHeater;
import buildcraft.factory.gui.GuiHeatExchanger;
import buildcraft.silicon.gui.GuiAdvancedCraftingTable;

import mezz.jei.api.*;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

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

        jeiRegistry.addAdvancedGuiHandlers(new LedgerGuiHandler());
        if (transport) {
            lst.add("transport");
            loadTransport(jeiRegistry);
        }
        if (factory) {
            lst.add("factory");
            if (energy) {
                loadFactoryEnergy(jeiRegistry);
            }
        }
        if (energy) {
            lst.add("energy");
            loadEnergy(jeiRegistry);
        }
        if (silicon) {
            lst.add("silicon");
            loadSilicon(jeiRegistry);
        }
        BCLog.logger.info("Loaded JEI mods: " + Arrays.toString(lst.toArray()));
    }

    private static void loadTransport(IModRegistry jeiRegistry) {
        jeiRegistry.addAdvancedGuiHandlers(new GateGuiHandler());
    }

    private static void loadEnergy(IModRegistry jeiRegistry) {
        jeiRegistry.addRecipeCategories(new CategoryCombustionEngine(jeiRegistry.getJeiHelpers().getGuiHelper()));
        jeiRegistry.addRecipeHandlers(new HandlerCombusionEngine());
        jeiRegistry.addRecipes(ImmutableList.copyOf(FuelManager.INSTANCE.getFuels()));

        jeiRegistry.addRecipeClickArea(GuiCombustionEngine.class, 76, 41, 22, 15, CategoryCombustionEngine.UID);
        jeiRegistry.addRecipeClickArea(GuiStoneEngine.class, 80, 24, 16, 16, VanillaRecipeCategoryUid.FUEL);
    }

    private void loadFactoryEnergy(IModRegistry jeiRegistry) {
        IGuiHelper helper = jeiRegistry.getJeiHelpers().getGuiHelper();
        jeiRegistry.addRecipeCategories(new CategoryHeatable(helper), new CategoryDistiller(helper), new CategoryCoolable(helper));
        jeiRegistry.addRecipeHandlers(new HandlerHeatableFluid(), new HandlerDistiller(), new HandlerCoolable());

        jeiRegistry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.complexRefinery.getCoolableRegistry().getAllRecipes()));
        jeiRegistry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.complexRefinery.getHeatableRegistry().getAllRecipes()));
        jeiRegistry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.complexRefinery.getDistilationRegistry().getAllRecipes()));

        jeiRegistry.addRecipeClickArea(GuiEnergyHeater.class, 61, 18, 54, 23, CategoryHeatable.UID);
        jeiRegistry.addRecipeClickArea(GuiDistiller.class, 61, 12, 36, 57, CategoryDistiller.UID);
        jeiRegistry.addRecipeClickArea(GuiHeatExchanger.class, 61, 38, 54, 17, CategoryHeatable.UID, CategoryCoolable.UID);
    }

    private static void loadSilicon(IModRegistry jeiRegistry) {
        jeiRegistry.addRecipeClickArea(GuiAdvancedCraftingTable.class, 93, 34, 22, 15, VanillaRecipeCategoryUid.CRAFTING);
    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {}

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
    }
}
