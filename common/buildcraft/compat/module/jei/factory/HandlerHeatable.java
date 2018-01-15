package buildcraft.compat.module.jei.factory;

import buildcraft.api.recipes.IRefineryRecipeManager;

import buildcraft.compat.module.jei.BCPluginJEI;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class HandlerHeatable implements IRecipeWrapperFactory<IRefineryRecipeManager.IHeatableRecipe> {
    @Override
    public IRecipeWrapper getRecipeWrapper(IRefineryRecipeManager.IHeatableRecipe recipe) {
        return new WrapperHeatable(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
    }
}
