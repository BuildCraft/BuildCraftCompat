package buildcraft.compat.jei;

import java.util.Arrays;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.Loader;
import buildcraft.api.BCModules;
import buildcraft.api.core.BCLog;
import buildcraft.api.fuels.IFuel;
import buildcraft.api.recipes.AssemblyRecipe;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IRefineryRecipeManager;
import buildcraft.api.recipes.IntegrationRecipe;
import buildcraft.lib.fluid.FuelRegistry;
import buildcraft.lib.recipe.AssemblyRecipeRegistry;
import buildcraft.lib.recipe.IntegrationRecipeRegistry;
import buildcraft.compat.jei.energy.combustionengine.CategoryCombustionEngine;
import buildcraft.compat.jei.energy.combustionengine.HandlerCombusionEngine;
import buildcraft.compat.jei.factory.CategoryCoolable;
import buildcraft.compat.jei.factory.CategoryDistiller;
import buildcraft.compat.jei.factory.CategoryHeatable;
import buildcraft.compat.jei.factory.HandlerCoolable;
import buildcraft.compat.jei.factory.HandlerDistiller;
import buildcraft.compat.jei.factory.HandlerHeatable;
import buildcraft.compat.jei.silicon.CategoryAssemblyTable;
import buildcraft.compat.jei.silicon.CategoryIntegrationTable;
import buildcraft.compat.jei.silicon.HandlerAssemblyTable;
import buildcraft.compat.jei.silicon.HandlerIntegrationTable;
import buildcraft.compat.jei.transferhandlers.AdvancedCraftingItemsTransferHandler;
import buildcraft.compat.jei.transferhandlers.AutoCraftItemsTransferHandler;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

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
        boolean silicon = Loader.isModLoaded(BCModules.SILICON.getModId());
//        boolean robotics = Loader.isModLoaded(BCModules.ROBOTICS.getModId());

        if (factory) {
            registry.handleRecipes(IRefineryRecipeManager.ICoolableRecipe.class, new HandlerCoolable(), CategoryCoolable.UID);
            registry.handleRecipes(IRefineryRecipeManager.IDistillationRecipe.class, new HandlerDistiller(), CategoryDistiller.UID);
            registry.handleRecipes(IRefineryRecipeManager.IHeatableRecipe.class, new HandlerHeatable(), CategoryHeatable.UID);

            registry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.refineryRecipes.getCoolableRegistry().getAllRecipes()), CategoryCoolable.UID);
            registry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.refineryRecipes.getDistillationRegistry().getAllRecipes()), CategoryDistiller.UID);
            registry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.refineryRecipes.getHeatableRegistry().getAllRecipes()), CategoryHeatable.UID);
        }
        if (energy) {
            registry.handleRecipes(IFuel.class, new HandlerCombusionEngine(), CategoryCombustionEngine.UID);
            registry.addRecipes(ImmutableList.copyOf(FuelRegistry.INSTANCE.getFuels()), CategoryCombustionEngine.UID);
        }
        if (silicon) {
            registry.handleRecipes(AssemblyRecipe.class, new HandlerAssemblyTable(), CategoryAssemblyTable.UID);
            registry.handleRecipes(IntegrationRecipe.class, new HandlerIntegrationTable(), CategoryIntegrationTable.UID);

            registry.addRecipes(ImmutableList.copyOf(AssemblyRecipeRegistry.INSTANCE.getAllRecipes()), CategoryAssemblyTable.UID);
            registry.addRecipes(ImmutableList.copyOf(IntegrationRecipeRegistry.INSTANCE.getAllRecipes()), CategoryIntegrationTable.UID);
        }

//        registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerAutoCraftItems.class, VanillaRecipeCategoryUid.CRAFTING,
//                45, 9, 0, 36);
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(new AutoCraftItemsTransferHandler(), VanillaRecipeCategoryUid.CRAFTING);
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(new AdvancedCraftingItemsTransferHandler(), VanillaRecipeCategoryUid.CRAFTING);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
//        boolean transport = Loader.isModLoaded(BCModules.TRANSPORT.getModId());
        boolean factory = Loader.isModLoaded(BCModules.FACTORY.getModId());
        boolean energy = Loader.isModLoaded(BCModules.ENERGY.getModId());
        boolean silicon = Loader.isModLoaded(BCModules.SILICON.getModId());
//        boolean robotics = Loader.isModLoaded(BCModules.ROBOTICS.getModId());

        List<String> lst = Lists.newArrayList();
        IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();

//        jeiRegistry.addAdvancedGuiHandlers(new LedgerGuiHandler());
//        if (transport) {
//            lst.add("transport");
//            loadTransport(jeiRegistry);
//        }
        if (factory) {
            lst.add("factory");
            registry.addRecipeCategories(new CategoryHeatable(helper));
            registry.addRecipeCategories(new CategoryDistiller(helper));
            registry.addRecipeCategories(new CategoryCoolable(helper));
        }
        if (energy) {
            lst.add("energy");
            registry.addRecipeCategories(new CategoryCombustionEngine(helper));
        }
        if (silicon) {
            lst.add("silicon");
            registry.addRecipeCategories(new CategoryAssemblyTable(helper));
            registry.addRecipeCategories(new CategoryIntegrationTable(helper));
        }

        BCLog.logger.info("Loaded JEI mods: " + Arrays.toString(lst.toArray()));
    }

//    private static void loadTransport(IModRegistry jeiRegistry) {
//        jeiRegistry.addAdvancedGuiHandlers(new GateGuiHandler());
//    }
}
