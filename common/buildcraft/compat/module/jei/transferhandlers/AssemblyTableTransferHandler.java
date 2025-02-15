package buildcraft.compat.module.jei.transferhandlers;

import buildcraft.api.recipes.IAssemblyRecipe;
import buildcraft.compat.module.jei.silicon.CategoryAssemblyTable;
import buildcraft.silicon.BCSiliconMenuTypes;
import buildcraft.silicon.container.ContainerAssemblyTable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;

import javax.annotation.Nullable;
import java.util.Optional;

// Calen: never used in 1.12.2?
public class AssemblyTableTransferHandler implements IRecipeTransferHandler<ContainerAssemblyTable, IAssemblyRecipe> {

    @Override
    public Class<ContainerAssemblyTable> getContainerClass() {
        return ContainerAssemblyTable.class;
    }

    @Override
    public Optional<MenuType<ContainerAssemblyTable>> getMenuType() {
        return Optional.of(BCSiliconMenuTypes.ASSEMBLY_TABLE);
    }

    @Override
    public RecipeType<IAssemblyRecipe> getRecipeType() {
        return CategoryAssemblyTable.RECIPE_TYPE;
    }

    @Override
    @Nullable
//    public IRecipeTransferError transferRecipe(ContainerAssemblyTable container, IRecipeLayout recipeLayout, Player player, boolean maxTransfer, boolean doTransfer)
    public IRecipeTransferError transferRecipe(ContainerAssemblyTable container, IAssemblyRecipe recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer) {
        return null;
    }
}
