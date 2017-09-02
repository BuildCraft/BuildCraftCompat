package buildcraft.compat.jei;

import java.util.Arrays;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.Loader;
import buildcraft.api.BCModules;
import buildcraft.api.core.BCLog;
import buildcraft.api.fuels.IFuel;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IRefineryRecipeManager;
import buildcraft.lib.fluid.FuelRegistry;
import buildcraft.compat.jei.energy.combustionengine.CategoryCombustionEngine;
import buildcraft.compat.jei.energy.combustionengine.HandlerCombusionEngine;
import buildcraft.compat.jei.factory.CategoryCoolable;
import buildcraft.compat.jei.factory.CategoryDistiller;
import buildcraft.compat.jei.factory.CategoryHeatable;
import buildcraft.compat.jei.factory.HandlerCoolable;
import buildcraft.compat.jei.factory.HandlerDistiller;
import buildcraft.compat.jei.factory.HandlerHeatable;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class BCPluginJEI extends BlankModPlugin {
//    public static boolean disableFacadeJEI;
    public static IModRegistry registry;
//    public static IJeiRuntime runtime;

    @Override
    public void register(IModRegistry registry) {
        BCPluginJEI.registry = registry;
//        boolean transport = Loader.isModLoaded(BCModules.TRANSPORT.getModId());
        boolean factory = Loader.isModLoaded(BCModules.FACTORY.getModId());
        boolean energy = Loader.isModLoaded(BCModules.ENERGY.getModId());
//        boolean silicon = Loader.isModLoaded(BCModules.SILICON.getModId());
//        boolean robotics = Loader.isModLoaded(BCModules.ROBOTICS.getModId());

        if (factory) {
            registry.handleRecipes(IRefineryRecipeManager.ICoolableRecipe.class, new HandlerCoolable(), CategoryCoolable.UID);
            registry.handleRecipes(IRefineryRecipeManager.IDistillationRecipe.class, new HandlerDistiller(), CategoryDistiller.UID);
            registry.handleRecipes(IRefineryRecipeManager.IHeatableRecipe.class, new HandlerHeatable(), CategoryHeatable.UID);

            registry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.refineryRecipes.getCoolableRegistry().getAllRecipes()), CategoryCoolable.UID);
            registry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.refineryRecipes.getDistillationRegistry().getAllRecipes()), CategoryDistiller.UID);
            registry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.refineryRecipes.getHeatableRegistry().getAllRecipes()), CategoryHeatable.UID);

//            registry.addRecipeClickArea(GuiEnergyHeater.class, 61, 18, 54, 23, CategoryHeatable.UID);
//            registry.addRecipeClickArea(GuiDistiller.class, 61, 12, 36, 57, CategoryDistiller.UID);
//            registry.addRecipeClickArea(GuiHeatExchanger.class, 61, 38, 54, 17, CategoryHeatable.UID, CategoryCoolable.UID);
        }
        if (energy) {
            registry.handleRecipes(IFuel.class, new HandlerCombusionEngine(), CategoryCombustionEngine.UID);
            registry.addRecipes(ImmutableList.copyOf(FuelRegistry.INSTANCE.getFuels()), CategoryCombustionEngine.UID);
//            registry.addRecipeClickArea(GuiCombustionEngine.class, 76, 41, 22, 15, CategoryCombustionEngine.UID);
//            registry.addRecipeClickArea(GuiStoneEngine.class, 80, 24, 16, 16,  VanillaRecipeCategoryUid.FUEL);
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
//        boolean transport = Loader.isModLoaded(BCModules.TRANSPORT.getModId());
        boolean factory = Loader.isModLoaded(BCModules.FACTORY.getModId());
        boolean energy = Loader.isModLoaded(BCModules.ENERGY.getModId());
//        boolean silicon = Loader.isModLoaded(BCModules.SILICON.getModId());
//        boolean robotics = Loader.isModLoaded(BCModules.ROBOTICS.getModId());

        List<String> lst = Lists.newArrayList();

//        jeiRegistry.addAdvancedGuiHandlers(new LedgerGuiHandler());
//        if (transport) {
//            lst.add("transport");
//            loadTransport(jeiRegistry);
//        }
        if (factory) {
            lst.add("factory");
            IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
            registry.addRecipeCategories(new CategoryHeatable(helper));
            registry.addRecipeCategories(new CategoryDistiller(helper));
            registry.addRecipeCategories(new CategoryCoolable(helper));
        }
        if (energy) {
            lst.add("energy");
            registry.addRecipeCategories(new CategoryCombustionEngine(registry.getJeiHelpers().getGuiHelper()));
        }
//        if (silicon) {
//            lst.add("silicon");
//            loadSilicon(jeiRegistry);
//        }

        BCLog.logger.info("Loaded JEI mods: " + Arrays.toString(lst.toArray()));
    }

//    private static void loadTransport(IModRegistry jeiRegistry) {
//        jeiRegistry.addAdvancedGuiHandlers(new GateGuiHandler());
//    }

//    private void loadFactoryEnergy(IModRegistry jeiRegistry) {
//        IGuiHelper helper = jeiRegistry.getJeiHelpers().getGuiHelper();
//        jeiRegistry.addRecipeCategories(new CategoryHeatable(helper), new CategoryDistiller(helper), new CategoryCoolable(helper));
//        jeiRegistry.addRecipeHandlers(new HandlerHeatable(), new HandlerDistiller(), new HandlerCoolable());
//
//        jeiRegistry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.complexRefinery.getCoolableRegistry().getAllRecipes()));
//        jeiRegistry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.complexRefinery.getHeatableRegistry().getAllRecipes()));
//        jeiRegistry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.complexRefinery.getDistilationRegistry().getAllRecipes()));
//
//        jeiRegistry.addRecipeClickArea(GuiEnergyHeater.class, 61, 18, 54, 23, CategoryHeatable.UID);
//        jeiRegistry.addRecipeClickArea(GuiDistiller.class, 61, 12, 36, 57, CategoryDistiller.UID);
//        jeiRegistry.addRecipeClickArea(GuiHeatExchanger.class, 61, 38, 54, 17, CategoryHeatable.UID, CategoryCoolable.UID);
//    }
//
//    private static void loadSilicon(IModRegistry jeiRegistry) {
//		IGuiHelper helper = jeiRegistry.getJeiHelpers().getGuiHelper();
//		jeiRegistry.addRecipeCategories(new CategoryAssemblyTable(helper), new CategoryIntegrationTable(helper));
//		jeiRegistry.addRecipeHandlers(new HandlerAssemblyTable(), new HandlerIntegrationTable());
//
//		jeiRegistry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.assemblyTable.getRecipes()));
//		jeiRegistry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.integrationTable.getRecipes()));
//
//        jeiRegistry.addRecipeClickArea(GuiAdvancedCraftingTable.class, 93, 34, 22, 15, VanillaRecipeCategoryUid.CRAFTING);
//    }

//    @Override
//    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {}
//
//    @Override
//    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
//        BCPluginJEI.runtime = jeiRuntime;
//    }
}
