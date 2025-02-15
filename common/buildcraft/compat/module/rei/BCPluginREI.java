package buildcraft.compat.module.rei;

import buildcraft.api.BCModules;
import buildcraft.api.core.BCLog;
import buildcraft.api.fuels.IFuel;
import buildcraft.api.recipes.IAssemblyRecipe;
import buildcraft.api.recipes.IRefineryRecipeManager;
import buildcraft.compat.BCCompatConfig;
import buildcraft.compat.module.rei.energy.combustionengine.CategoryCombustionEngine;
import buildcraft.compat.module.rei.energy.combustionengine.DisplayCombustionEngine;
import buildcraft.compat.module.rei.factory.*;
import buildcraft.compat.module.rei.gui.GuiGhostIngredientHandlerBuildCraft;
import buildcraft.compat.module.rei.gui.GuiHandlerBuildCraft;
import buildcraft.compat.module.rei.silicon.CategoryAssemblyTable;
import buildcraft.compat.module.rei.silicon.DisplayAssembly;
import buildcraft.core.BCCoreItems;
import buildcraft.factory.BCFactoryBlocks;
import buildcraft.lib.gui.GuiBC8;
import buildcraft.silicon.BCSiliconBlocks;
import buildcraft.silicon.BCSiliconItems;
import buildcraft.transport.item.ItemPipeHolder;
import com.google.common.collect.Lists;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZones;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.util.Arrays;
import java.util.List;

@REIPluginClient
public class BCPluginREI implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        boolean factory = ModList.get().isLoaded(BCModules.FACTORY.getModId());
        boolean energy = ModList.get().isLoaded(BCModules.ENERGY.getModId());
        boolean silicon = ModList.get().isLoaded(BCModules.SILICON.getModId());
        List<String> lst = Lists.newArrayList();
        if (factory) {
            lst.add("factory");
            registry.add(CategoryHeatable.INSTANCE);
            registry.addWorkstations(CategoryHeatable.ID, CategoryHeatable.ICON);
            registry.add(CategoryDistiller.INSTANCE);
            registry.addWorkstations(CategoryDistiller.ID, CategoryDistiller.ICON);
            registry.add(CategoryCoolable.INSTANCE);
            registry.addWorkstations(CategoryCoolable.ID, CategoryCoolable.ICON);

            registry.addWorkstations(BuiltinPlugin.CRAFTING, EntryStacks.of(new ItemStack(BCFactoryBlocks.autoWorkbenchItems.get())));
        }

        if (energy) {
            lst.add("energy");
            registry.add(CategoryCombustionEngine.INSTANCE);
            registry.addWorkstations(CategoryCombustionEngine.ID, CategoryCombustionEngine.ICON);
        }

        if (silicon) {
            lst.add("silicon");
            registry.add(CategoryAssemblyTable.INSTANCE);
            registry.addWorkstations(CategoryAssemblyTable.ID, CategoryAssemblyTable.ICON);

            registry.addWorkstations(BuiltinPlugin.CRAFTING, EntryStacks.of(new ItemStack(BCSiliconBlocks.advancedCraftingTable.get())));
        }

        BCLog.logger.info("Loaded REI mods: " + Arrays.toString(lst.toArray()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(IRefineryRecipeManager.IHeatableRecipe.class, IRefineryRecipeManager.IHeatableRecipe.TYPE, DisplayHeatable::new);
        registry.registerRecipeFiller(IRefineryRecipeManager.IDistillationRecipe.class, IRefineryRecipeManager.IDistillationRecipe.TYPE, DisplayDistillation::new);
        registry.registerRecipeFiller(IRefineryRecipeManager.ICoolableRecipe.class, IRefineryRecipeManager.ICoolableRecipe.TYPE, DisplayCoolable::new);
        registry.registerRecipeFiller(IFuel.class, IFuel.TYPE, DisplayCombustionEngine::new);
        registry.registerRecipeFiller(IAssemblyRecipe.class, IAssemblyRecipe.TYPE, DisplayAssembly::new);
    }

    @Override
    public void registerExclusionZones(ExclusionZones zones) {
        zones.register(GuiBC8.class, new GuiHandlerBuildCraft());
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        registry.removeEntryIf(entryStack -> {
            if (entryStack.getType() == VanillaEntryTypes.ITEM) {
                ItemStack itemStack = entryStack.castValue();
                Item item = itemStack.getItem();
                if (item == BCCoreItems.fragileFluidShard.get()) {
                    return true;
                } else if (item instanceof ItemPipeHolder pipe) {
                    if (!BCCompatConfig.coloredPipesVisible) {
                        if (pipe.getColour() != DyeColor.WHITE) {
                            return true;
                        }
                    }
                } else if (item == BCSiliconItems.plugFacade.get()) {
                    if (!BCCompatConfig.facadesVisible) {
                        return true;
                    }
                }
            }
            return false;
        });
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerDraggableStackVisitor(new GuiGhostIngredientHandlerBuildCraft());
    }
}
