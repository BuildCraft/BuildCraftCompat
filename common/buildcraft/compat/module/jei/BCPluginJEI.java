package buildcraft.compat.module.jei;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.Loader;

import buildcraft.api.BCBlocks;
import buildcraft.api.BCModules;
import buildcraft.api.core.BCLog;
import buildcraft.api.enums.EnumEngineType;
import buildcraft.api.fuels.IFuel;
import buildcraft.api.recipes.AssemblyRecipeBasic;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IRefineryRecipeManager;

import buildcraft.lib.fluid.FuelRegistry;
import buildcraft.lib.recipe.AssemblyRecipeRegistry;

import buildcraft.compat.module.jei.energy.combustionengine.CategoryCombustionEngine;
import buildcraft.compat.module.jei.energy.combustionengine.HandlerCombustionEngine;
import buildcraft.compat.module.jei.factory.CategoryCoolable;
import buildcraft.compat.module.jei.factory.CategoryDistiller;
import buildcraft.compat.module.jei.factory.CategoryHeatable;
import buildcraft.compat.module.jei.factory.HandlerCoolable;
import buildcraft.compat.module.jei.factory.HandlerDistiller;
import buildcraft.compat.module.jei.factory.HandlerHeatable;
import buildcraft.compat.module.jei.recipe.GuiHandlerBuildCraft;
import buildcraft.compat.module.jei.silicon.CategoryAssemblyTable;
import buildcraft.compat.module.jei.silicon.WrapperAssemblyTable;
import buildcraft.compat.module.jei.transferhandlers.AdvancedCraftingItemsTransferHandler;
import buildcraft.compat.module.jei.transferhandlers.AutoCraftItemsTransferHandler;
import buildcraft.core.BCCoreBlocks;
import buildcraft.silicon.container.ContainerAssemblyTable;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

@JEIPlugin
public class BCPluginJEI implements IModPlugin {
    //    public static boolean disableFacadeJEI;
    public static IModRegistry registry;

    @Override
    public void register(IModRegistry registry) {
        BCPluginJEI.registry = registry;
        registry.addAdvancedGuiHandlers(new GuiHandlerBuildCraft());
//        boolean transport = BCModules.TRANSPORT.isLoaded();
        boolean factory = BCModules.FACTORY.isLoaded();
        boolean energy = BCModules.ENERGY.isLoaded();
        boolean silicon = BCModules.SILICON.isLoaded();
//        boolean robotics = BCModules.ROBOTICS.isLoaded();

        if (factory) {
            registry.handleRecipes(IRefineryRecipeManager.ICoolableRecipe.class, new HandlerCoolable(), CategoryCoolable.UID);
            registry.handleRecipes(IRefineryRecipeManager.IDistillationRecipe.class, new HandlerDistiller(), CategoryDistiller.UID);
            registry.handleRecipes(IRefineryRecipeManager.IHeatableRecipe.class, new HandlerHeatable(), CategoryHeatable.UID);

            registry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.refineryRecipes.getCoolableRegistry().getAllRecipes()), CategoryCoolable.UID);
            registry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.refineryRecipes.getDistillationRegistry().getAllRecipes()), CategoryDistiller.UID);
            registry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.refineryRecipes.getHeatableRegistry().getAllRecipes()), CategoryHeatable.UID);
            if (BCBlocks.Factory.DISTILLER != null) {
                registry.addRecipeCatalyst(new ItemStack(BCBlocks.Factory.DISTILLER), CategoryDistiller.UID);
            }
            if (BCBlocks.Factory.HEAT_EXCHANGE != null) {
                registry.addRecipeCatalyst(new ItemStack(BCBlocks.Factory.HEAT_EXCHANGE), CategoryCoolable.UID);
                registry.addRecipeCatalyst(new ItemStack(BCBlocks.Factory.HEAT_EXCHANGE), CategoryHeatable.UID);
            }
        }
        if (energy) {
            registry.handleRecipes(IFuel.class, new HandlerCombustionEngine(), CategoryCombustionEngine.UID);
            registry.addRecipes(ImmutableList.copyOf(FuelRegistry.INSTANCE.getFuels()), CategoryCombustionEngine.UID);
            if (BCCoreBlocks.engine != null){
                if (BCCoreBlocks.engine.isRegistered(EnumEngineType.STONE)) {
                    registry.addRecipeCatalyst(BCCoreBlocks.engine.getStack(EnumEngineType.STONE), VanillaRecipeCategoryUid.FUEL);
                }
                if (BCCoreBlocks.engine.isRegistered(EnumEngineType.IRON)) {
                    registry.addRecipeCatalyst(BCCoreBlocks.engine.getStack(EnumEngineType.IRON), CategoryCombustionEngine.UID);
                }
            }
        }
        if (silicon) {
            registry.handleRecipes(AssemblyRecipeBasic.class, WrapperAssemblyTable::new, CategoryAssemblyTable.UID);
//            registry.handleRecipes(IntegrationRecipe.class, new HandlerIntegrationTable(), CategoryIntegrationTable.UID);

            registry.addRecipes(ImmutableList.copyOf(AssemblyRecipeRegistry.REGISTRY.values()), CategoryAssemblyTable.UID);
//            registry.addRecipes(ImmutableList.copyOf(IntegrationRecipeRegistry.INSTANCE.getAllRecipes()), CategoryIntegrationTable.UID);
            if (BCBlocks.Silicon.ASSEMBLY_TABLE != null) {
                registry.addRecipeCatalyst(new ItemStack(BCBlocks.Silicon.ASSEMBLY_TABLE), CategoryAssemblyTable.UID);
            }
            if (BCBlocks.Silicon.ADVANCED_CRAFTING_TABLE != null) {
                registry.addRecipeCatalyst(new ItemStack(BCBlocks.Silicon.ADVANCED_CRAFTING_TABLE), VanillaRecipeCategoryUid.CRAFTING);
            }
        }

        registry.getRecipeTransferRegistry().addRecipeTransferHandler(new AutoCraftItemsTransferHandler(), VanillaRecipeCategoryUid.CRAFTING);
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(new AdvancedCraftingItemsTransferHandler(), VanillaRecipeCategoryUid.CRAFTING);
        // registry.getRecipeTransferRegistry().addRecipeTransferHandler(new AssemblyTableTransferHandler(), CategoryAssemblyTable.UID);
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerAssemblyTable.class, CategoryAssemblyTable.UID,
                36, 12, 0, 36);
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
//            registry.addRecipeCategories(new CategoryIntegrationTable(helper));
        }

        BCLog.logger.info("Loaded JEI mods: " + Arrays.toString(lst.toArray()));
    }

//    private static void loadTransport(IModRegistry jeiRegistry) {
//        jeiRegistry.addAdvancedGuiHandlers(new GateGuiHandler());
//    }
}
