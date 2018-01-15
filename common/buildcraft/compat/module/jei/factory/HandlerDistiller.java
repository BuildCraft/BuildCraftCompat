package buildcraft.compat.module.jei.factory;

import buildcraft.api.recipes.IRefineryRecipeManager;

import buildcraft.compat.module.jei.BCPluginJEI;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class HandlerDistiller implements IRecipeWrapperFactory<IRefineryRecipeManager.IDistillationRecipe> {
    @Override
    public IRecipeWrapper getRecipeWrapper(IRefineryRecipeManager.IDistillationRecipe recipe) {
        return new WrapperDistiller(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
    }
}
