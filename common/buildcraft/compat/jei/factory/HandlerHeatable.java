package buildcraft.compat.jei.factory;

import buildcraft.api.recipes.IRefineryRecipeManager;
import buildcraft.compat.jei.BCPluginJEI;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class HandlerHeatable implements IRecipeWrapperFactory<IRefineryRecipeManager.IHeatableRecipe> {
    @Override
    public IRecipeWrapper getRecipeWrapper(IRefineryRecipeManager.IHeatableRecipe recipe) {
        return new WrapperHeatable(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
    }
}
