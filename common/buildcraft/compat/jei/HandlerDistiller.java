package buildcraft.compat.jei;

import buildcraft.api.recipes.IComplexRefineryRecipeManager.IDistilationRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class HandlerDistiller implements IRecipeHandler<IDistilationRecipe> {

    @Override
    public Class<IDistilationRecipe> getRecipeClass() {
        return IDistilationRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return CategoryDistiller.UID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(IDistilationRecipe recipe) {
        return new WrapperDistiller(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
    }

    @Override
    public boolean isRecipeValid(IDistilationRecipe recipe) {
        return true;
    }
}
