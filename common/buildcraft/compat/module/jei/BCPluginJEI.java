package buildcraft.compat.module.jei;

import buildcraft.api.BCModules;
import buildcraft.api.core.BCLog;
import buildcraft.api.enums.EnumEngineType;
import buildcraft.api.fuels.IFuel;
import buildcraft.api.recipes.IRefineryRecipeManager;
import buildcraft.compat.module.jei.energy.combustionengine.CategoryCombustionEngine;
import buildcraft.compat.module.jei.factory.CategoryCoolable;
import buildcraft.compat.module.jei.factory.CategoryDistiller;
import buildcraft.compat.module.jei.factory.CategoryHeatable;
import buildcraft.compat.module.jei.gui.GuiHandlerBuildCraft;
import buildcraft.compat.module.jei.silicon.CategoryAssemblyTable;
import buildcraft.compat.module.jei.transferhandlers.AdvancedCraftingItemsTransferHandler;
import buildcraft.compat.module.jei.transferhandlers.AutoCraftItemsTransferHandler;
import buildcraft.core.BCCoreBlocks;
import buildcraft.factory.BCFactoryBlocks;
import buildcraft.lib.gui.GuiBC8;
import buildcraft.lib.recipe.assembly.AssemblyRecipe;
import buildcraft.lib.recipe.assembly.AssemblyRecipeRegistry;
import buildcraft.lib.recipe.fuel.FuelRegistry;
import buildcraft.silicon.BCSiliconBlocks;
import buildcraft.silicon.container.ContainerAssemblyTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;

import java.util.Arrays;
import java.util.List;

@JeiPlugin
public class BCPluginJEI implements IModPlugin {
    //    public static IModRegistry registry;
    public static IRecipeRegistration registryRecipe;
    public static IGuiHandlerRegistration registryGui;
    public static IRecipeTransferRegistration registryRecipeTransfer;
    public static IRecipeCatalystRegistration registryRecipeCatalyst;
    public static IJeiRuntime jeiRuntime;

    private static final ResourceLocation UID = new ResourceLocation("buildcraft:jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }


    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registry) {
        BCPluginJEI.registryGui = registry;
        registry.addGenericGuiContainerHandler(GuiBC8.class, new GuiHandlerBuildCraft());

    }

    // Calen: IRecipeWrapper combined into IRecipeCategory
    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        BCPluginJEI.registryRecipe = registry;
        boolean factory = BCModules.FACTORY.isLoaded();
        boolean energy = BCModules.ENERGY.isLoaded();
        boolean silicon = BCModules.SILICON.isLoaded();
        if (factory) {
//            registry.handleRecipes(IRefineryRecipeManager.ICoolableRecipe.class, new HandlerCoolable(), "buildcraft:category_coolable");
//            registry.handleRecipes(IRefineryRecipeManager.IDistillationRecipe.class, new HandlerDistiller(), "buildcraft:category_distiller");
//            registry.handleRecipes(IRefineryRecipeManager.IHeatableRecipe.class, new HandlerHeatable(), "buildcraft:category_heatable");
//            registry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.refineryRecipes.getCoolableRegistry().getAllRecipes()), new ResourceLocation("buildcraft:category_coolable"));
            registry.addRecipes(ImmutableList.copyOf(Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(IRefineryRecipeManager.ICoolableRecipe.TYPE)), CategoryCoolable.UID);
//            registry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.refineryRecipes.getDistillationRegistry().getAllRecipes()), new ResourceLocation("buildcraft:category_distiller"));
            registry.addRecipes(ImmutableList.copyOf(Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(IRefineryRecipeManager.IDistillationRecipe.TYPE)), CategoryDistiller.UID);
//            registry.addRecipes(ImmutableList.copyOf(BuildcraftRecipeRegistry.refineryRecipes.getHeatableRegistry().getAllRecipes()), new ResourceLocation("buildcraft:category_heatable"));
            registry.addRecipes(ImmutableList.copyOf(Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(IRefineryRecipeManager.IHeatableRecipe.TYPE)), CategoryHeatable.UID);
        }

        if (energy) {
//            registry.handleRecipes(IFuel.class, new HandlerCombustionEngine(), "buildcraft-compat:engine.combustion");
//            registry.addRecipes(ImmutableList.copyOf(FuelRegistry.INSTANCE.getFuels()), new ResourceLocation("buildcraft-compat:engine.combustion"));
            registry.addRecipes(ImmutableList.copyOf(Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(IFuel.TYPE)), CategoryCombustionEngine.UID);
        }

        if (silicon) {
//            registry.handleRecipes(AssemblyRecipeBasic.class, WrapperAssemblyTable::new, "buildcraft-compat:silicon.assembly");
//            registry.addRecipes(ImmutableList.copyOf(AssemblyRecipeRegistry.REGISTRY.values()), new ResourceLocation("buildcraft-compat:silicon.assembly"));
            registry.addRecipes(ImmutableList.copyOf(Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(AssemblyRecipe.TYPE)), CategoryAssemblyTable.UID);
        }
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
        BCPluginJEI.registryRecipeTransfer = registry;

//        registry.addRecipeTransferHandler(new AutoCraftItemsTransferHandler(), "minecraft.crafting");
        registry.addRecipeTransferHandler(new AutoCraftItemsTransferHandler(), VanillaRecipeCategoryUid.CRAFTING);
//        registry.addRecipeTransferHandler(new AdvancedCraftingItemsTransferHandler(), "minecraft.crafting");
        registry.addRecipeTransferHandler(new AdvancedCraftingItemsTransferHandler(), VanillaRecipeCategoryUid.CRAFTING);
//        registry.addRecipeTransferHandler(ContainerAssemblyTable.class, "buildcraft-compat:silicon.assembly", 36, 12, 0, 36);
        registry.addRecipeTransferHandler(ContainerAssemblyTable.class, CategoryAssemblyTable.UID, 36, 12, 0, 36);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
//        boolean factory = Loader.isModLoaded(BCModules.FACTORY.getModId());
        boolean factory = ModList.get().isLoaded(BCModules.FACTORY.getModId());
//        boolean energy = Loader.isModLoaded(BCModules.ENERGY.getModId());
        boolean energy = ModList.get().isLoaded(BCModules.ENERGY.getModId());
//        boolean silicon = Loader.isModLoaded(BCModules.SILICON.getModId());
        boolean silicon = ModList.get().isLoaded(BCModules.SILICON.getModId());
        List<String> lst = Lists.newArrayList();
        IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
        if (factory) {
            lst.add("factory");
            registry.addRecipeCategories(new IRecipeCategory[] { new CategoryHeatable(helper) });
            registry.addRecipeCategories(new IRecipeCategory[] { new CategoryDistiller(helper) });
            registry.addRecipeCategories(new IRecipeCategory[] { new CategoryCoolable(helper) });
        }

        if (energy) {
            lst.add("energy");
//            registry.addRecipeCategories(new IRecipeCategory[]{new CategoryCombustionEngine(helper)});
            registry.addRecipeCategories(new IRecipeCategory[] { new CategoryCombustionEngine(helper, FuelRegistry.INSTANCE.getFuels(Minecraft.getInstance().level)) });
        }

        if (silicon) {
            lst.add("silicon");
//            registry.addRecipeCategories(new IRecipeCategory[]{new CategoryAssemblyTable(helper)});
            registry.addRecipeCategories(new IRecipeCategory[] { new CategoryAssemblyTable(helper, AssemblyRecipeRegistry.getAll(Minecraft.getInstance().level)) });
        }

        BCLog.logger.info("Loaded JEI mods: " + Arrays.toString(lst.toArray()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        BCPluginJEI.registryRecipeCatalyst = registry;

        boolean factory = BCModules.FACTORY.isLoaded();
        boolean energy = BCModules.ENERGY.isLoaded();
        boolean silicon = BCModules.SILICON.isLoaded();
        if (factory) {
            if (BCFactoryBlocks.distiller != null) {
//                registry.addRecipeCatalyst(new ItemStack(BCFactoryBlocks.distiller.get()), new String[]{"buildcraft:category_distiller"});
                registry.addRecipeCatalyst(new ItemStack(BCFactoryBlocks.distiller.get()), CategoryDistiller.UID);
            }

            if (BCFactoryBlocks.heatExchange != null) {
//                registry.addRecipeCatalyst(new ItemStack(BCFactoryBlocks.heatExchange.get()), new String[]{"buildcraft:category_coolable"});
                registry.addRecipeCatalyst(new ItemStack(BCFactoryBlocks.heatExchange.get()), CategoryCoolable.UID);
//                registry.addRecipeCatalyst(new ItemStack(BCFactoryBlocks.heatExchange.get()), new String[]{"buildcraft:category_heatable"});
                registry.addRecipeCatalyst(new ItemStack(BCFactoryBlocks.heatExchange.get()), CategoryHeatable.UID);
            }

            if (BCFactoryBlocks.autoWorkbenchItems != null) {
                registry.addRecipeCatalyst(new ItemStack(BCFactoryBlocks.autoWorkbenchItems.get()), VanillaRecipeCategoryUid.CRAFTING);
            }
        }

        if (energy) {
//            if (BCCoreBlocks.engine != null)
            if (!BCCoreBlocks.engineBlockMap.isEmpty()) {
//                if (BCCoreBlocks.engine.isRegistered(EnumEngineType.STONE))
                if (BCCoreBlocks.engineBlockMap.containsKey(EnumEngineType.STONE)) {
//                    registry.addRecipeCatalyst(BCCoreBlocks.engine.getStack(EnumEngineType.STONE), new String[]{"minecraft.fuel"});
                    registry.addRecipeCatalyst(new ItemStack(BCCoreBlocks.engineBlockMap.get(EnumEngineType.STONE).get()), VanillaRecipeCategoryUid.FUEL);
                }

//                if (BCCoreBlocks.engine.isRegistered(EnumEngineType.IRON))
                if (BCCoreBlocks.engineBlockMap.containsKey(EnumEngineType.IRON)) {
////                    registry.addRecipeCatalyst(BCCoreBlocks.engine.getStack(EnumEngineType.IRON), new String[]{"buildcraft-compat:engine.combustion"});
//                    registry.addRecipeCatalyst(new ItemStack(BCCoreBlocks.engineBlockMap.get(EnumEngineType.IRON).get()), new String[]{"buildcraft-compat:engine.combustion"});
                    registry.addRecipeCatalyst(new ItemStack(BCCoreBlocks.engineBlockMap.get(EnumEngineType.IRON).get()), CategoryCombustionEngine.UID);
                }
            }
        }

        if (silicon) {
            if (BCSiliconBlocks.assemblyTable != null) {
//                registry.addRecipeCatalyst(new ItemStack(BCSiliconBlocks.assemblyTable.get()), new String[]{"buildcraft-compat:silicon.assembly"});
                registry.addRecipeCatalyst(new ItemStack(BCSiliconBlocks.assemblyTable.get()), CategoryAssemblyTable.UID);
            }

            if (BCSiliconBlocks.advancedCraftingTable != null) {
//                registry.addRecipeCatalyst(new ItemStack(BCSiliconBlocks.advancedCraftingTable.get()), new String[]{"minecraft.crafting"});
                registry.addRecipeCatalyst(new ItemStack(BCSiliconBlocks.advancedCraftingTable.get()), VanillaRecipeCategoryUid.CRAFTING);
            }
        }
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        BCPluginJEI.jeiRuntime = jeiRuntime;
    }
}
