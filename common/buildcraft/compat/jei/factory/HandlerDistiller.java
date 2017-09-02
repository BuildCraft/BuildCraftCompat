package buildcraft.compat.jei.factory;

import buildcraft.api.recipes.IRefineryRecipeManager;
import buildcraft.compat.jei.BCPluginJEI;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class HandlerDistiller implements IRecipeWrapperFactory<IRefineryRecipeManager.IDistillationRecipe> {
    @Override
    public IRecipeWrapper getRecipeWrapper(IRefineryRecipeManager.IDistillationRecipe recipe) {
        return new WrapperDistiller(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
    }
}
