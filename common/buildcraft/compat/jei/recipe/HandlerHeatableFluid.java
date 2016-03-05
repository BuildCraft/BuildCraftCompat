package buildcraft.compat.jei.recipe;

import buildcraft.api.recipes.IComplexRefineryRecipeManager.IHeatableRecipe;
import buildcraft.compat.jei.BCPluginJEI;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class HandlerHeatableFluid implements IRecipeHandler<IHeatableRecipe> {

    @Override
    public Class<IHeatableRecipe> getRecipeClass() {
        return IHeatableRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return CategoryHeatable.UID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(IHeatableRecipe recipe) {
        return new WrapperHeatableRecipe(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
    }

    @Override
    public boolean isRecipeValid(IHeatableRecipe recipe) {
        return true;
    }
}
