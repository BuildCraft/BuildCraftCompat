package buildcraft.compat.module.jei.transferhandlers;

import buildcraft.lib.recipe.assembly.AssemblyRecipe;
import buildcraft.silicon.container.ContainerAdvancedCraftingTable;
import buildcraft.silicon.container.ContainerAssemblyTable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.CraftingRecipe;

import javax.annotation.Nullable;

// Calen: never used in 1.12.2?
public class AssemblyTableTransferHandler implements IRecipeTransferHandler<ContainerAssemblyTable, AssemblyRecipe> {

    @Override
    public Class<ContainerAssemblyTable> getContainerClass() {
        return ContainerAssemblyTable.class;
    }

    @Override
    public Class getRecipeClass() {
        return AssemblyRecipe.class;
    }

    @Nullable
//    public IRecipeTransferError transferRecipe(ContainerAssemblyTable container, IRecipeLayout recipeLayout, Player player, boolean maxTransfer, boolean doTransfer)
    public IRecipeTransferError transferRecipe(ContainerAdvancedCraftingTable container, CraftingRecipe recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer) {
        return null;
    }
}
