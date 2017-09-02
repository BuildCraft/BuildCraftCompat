package buildcraft.compat.jei.factory;

import buildcraft.api.recipes.IRefineryRecipeManager;
import buildcraft.compat.jei.BCPluginJEI;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class HandlerCoolable implements IRecipeWrapperFactory<IRefineryRecipeManager.ICoolableRecipe> {
    @Override
    public IRecipeWrapper getRecipeWrapper(IRefineryRecipeManager.ICoolableRecipe recipe) {
        return new WrapperCoolable(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
    }
}
