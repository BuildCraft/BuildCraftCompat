package buildcraft.compat.jei.recipe;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.api.recipes.IFlexibleRecipeViewable;
import buildcraft.compat.jei.BCPluginJEI;

import javax.annotation.Nonnull;
import mezz.jei.api.recipe.IRecipeWrapper;

/**
 * Created by asie on 3/12/16.
 */
public class HandlerAssemblyRecipe extends HandlerFlexibleRecipe {
	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return CategoryAssemblyRecipe.UID;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull IFlexibleRecipe recipe) {
		return new WrapperAssemblyRecipe(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
	}

	@Override
	public boolean isRecipeValid(@Nonnull IFlexibleRecipe recipe) {
		return recipe instanceof IFlexibleRecipeViewable && BuildcraftRecipeRegistry.assemblyTable.getRecipes().contains(recipe);
	}
}
