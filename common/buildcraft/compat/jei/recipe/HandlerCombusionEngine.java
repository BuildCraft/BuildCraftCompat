package buildcraft.compat.jei.recipe;

import buildcraft.api.fuels.IFuel;
import buildcraft.api.fuels.IFuelManager.IDirtyFuel;
import buildcraft.compat.jei.BCPluginJEI;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class HandlerCombusionEngine implements IRecipeWrapperFactory<IFuel> {
    @Override
    public IRecipeWrapper getRecipeWrapper(IFuel recipe) {
        if (recipe instanceof IDirtyFuel) {
            return new WrapperCombustionEngine.Dirty(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), (IDirtyFuel) recipe);
        }
        return new WrapperCombustionEngine(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
    }
}
