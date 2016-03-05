package buildcraft.compat.jei;

import buildcraft.api.recipes.IComplexRefineryRecipeManager.ICoolableRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class HandlerCoolable implements IRecipeHandler<ICoolableRecipe> {

    @Override
    public Class<ICoolableRecipe> getRecipeClass() {
        return ICoolableRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return CategoryCoolable.UID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(ICoolableRecipe recipe) {
        return new WrapperCoolable(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
    }

    @Override
    public boolean isRecipeValid(ICoolableRecipe recipe) {
        return true;
    }
}
