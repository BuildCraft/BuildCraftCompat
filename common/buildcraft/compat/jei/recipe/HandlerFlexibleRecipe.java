package buildcraft.compat.jei.recipe;

import buildcraft.api.recipes.IFlexibleRecipe;

import javax.annotation.Nonnull;
import mezz.jei.api.recipe.IRecipeHandler;

public abstract class HandlerFlexibleRecipe implements IRecipeHandler<IFlexibleRecipe> {
	@Nonnull
	@Override
	public Class<IFlexibleRecipe> getRecipeClass() {
		return IFlexibleRecipe.class;
	}
}
