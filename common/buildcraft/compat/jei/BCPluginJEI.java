package buildcraft.compat.jei;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.common.Loader;
import buildcraft.api.BCModules;
import buildcraft.api.fuels.IFuel;
import buildcraft.lib.fluid.FuelRegistry;
import buildcraft.compat.jei.recipe.CategoryCombustionEngine;
import buildcraft.compat.jei.recipe.HandlerCombusionEngine;
import mezz.jei.api.BlankModPlugin;
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
//        boolean factory = Loader.isModLoaded(BCModules.FACTORY.getModId());
        boolean energy = Loader.isModLoaded(BCModules.ENERGY.getModId());
//        boolean silicon = Loader.isModLoaded(BCModules.SILICON.getModId());
//        boolean robotics = Loader.isModLoaded(BCModules.ROBOTICS.getModId());

        if (energy) {
            registry.handleRecipes(IFuel.class, new HandlerCombusionEngine(), CategoryCombustionEngine.UID);
            registry.addRecipes(ImmutableList.copyOf(FuelRegistry.INSTANCE.getFuels()), CategoryCombustionEngine.UID);
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
//        boolean transport = Loader.isModLoaded(BCModules.TRANSPORT.getModId());
//        boolean factory = Loader.isModLoaded(BCModules.FACTORY.getModId());
        boolean energy = Loader.isModLoaded(BCModules.ENERGY.getModId());
//        boolean silicon = Loader.isModLoaded(BCModules.SILICON.getModId());
//        boolean robotics = Loader.isModLoaded(BCModules.ROBOTICS.getModId());

//        List<String> lst = new ArrayList<>();

//        jeiRegistry.addAdvancedGuiHandlers(new LedgerGuiHandler());
//        if (transport) {
//            lst.add("transport");
//            loadTransport(jeiRegistry);
//        }
//        if (factory) {
//            lst.add("factory");
//            if (energy) {
//                loadFactoryEnergy(jeiRegistry);
//            }
//        }
        if (energy) {
//            lst.add("energy");
//            loadEnergy(registry);
            registry.addRecipeCategories(new CategoryCombustionEngine(registry.getJeiHelpers().getGuiHelper()));
        }
//        if (silicon) {
//            lst.add("silicon");
//            loadSilicon(jeiRegistry);
//        }
//        BCLog.logger.info("Loaded JEI mods: " + Arrays.toString(lst.toArray()));
    }

//    private static void loadTransport(IModRegistry jeiRegistry) {
//        jeiRegistry.addAdvancedGuiHandlers(new GateGuiHandler());
//    }

//    private static void loadEnergy(IRecipeCategoryRegistration registry) {
//        registry.addRecipeCategories(new CategoryCombustionEngine(BCPluginJEI.registry.getJeiHelpers().getGuiHelper()));
//        BCPluginJEI.registry.handleRecipes(IFuel.class, new HandlerCombusionEngine(), CategoryCombustionEngine.UID);
//        BCPluginJEI.registry.addRecipes(ImmutableList.copyOf(FuelRegistry.INSTANCE.getFuels()), CategoryCombustionEngine.UID);
//
////        BCPluginJEI.registry.addRecipeHandlers(new HandlerCombusionEngine());
////        BCPluginJEI.registry.addRecipes(ImmutableList.copyOf(FuelManager.INSTANCE.getFuels()));
//
////        BCPluginJEI.registry.addRecipeClickArea(GuiCombustionEngine.class, 76, 41, 22, 15, CategoryCombustionEngine.UID);
////        BCPluginJEI.registry.addRecipeClickArea(GuiStoneEngine.class, 80, 24, 16, 16, VanillaRecipeCategoryUid.FUEL);
//    }

//    private void loadFactoryEnergy(IModRegistry jeiRegistry) {
//        IGuiHelper helper = jeiRegistry.getJeiHelpers().getGuiHelper();
//        jeiRegistry.addRecipeCategories(new CategoryHeatable(helper), new CategoryDistiller(helper), new CategoryCoolable(helper));
//        jeiRegistry.addRecipeHandlers(new HandlerHeatableFluid(), new HandlerDistiller(), new HandlerCoolable());
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
