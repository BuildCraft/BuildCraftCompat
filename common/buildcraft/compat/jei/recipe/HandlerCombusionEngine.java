package buildcraft.compat.jei.recipe;

import net.minecraftforge.fluids.FluidRegistry;

import buildcraft.api.fuels.IFuel;
import buildcraft.api.fuels.IFuelManager.IDirtyFuel;
import buildcraft.compat.jei.BCPluginJEI;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class HandlerCombusionEngine implements IRecipeHandler<IFuel> {

    @Override
    public Class<IFuel> getRecipeClass() {
        return IFuel.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return CategoryCombustionEngine.UID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(IFuel recipe) {
        if (recipe instanceof IDirtyFuel) {
            return new WrapperCombustionEngine.Dirty(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), (IDirtyFuel) recipe);
        }
        return new WrapperCombustionEngine(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
    }

    @Override
    public boolean isRecipeValid(IFuel fuel) {
        return FluidRegistry.isFluidRegistered(fuel.getFluid());
    }
}
