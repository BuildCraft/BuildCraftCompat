package buildcraft.compat.jei;

import net.minecraftforge.fluids.FluidRegistry;

import buildcraft.api.fuels.IFuel;

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
        return new WrapperCombustionEngine(recipe);
    }

    @Override
    public boolean isRecipeValid(IFuel fuel) {
        return FluidRegistry.isFluidRegistered(fuel.getFluid());
    }
}
