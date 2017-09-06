package buildcraft.compat.jei.silicon;

import buildcraft.api.recipes.AssemblyRecipe;
import buildcraft.compat.jei.BCPluginJEI;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

/**
 * Created by asie on 3/12/16.
 */
public class HandlerAssemblyTable implements IRecipeWrapperFactory<AssemblyRecipe> {
    @Override
    public IRecipeWrapper getRecipeWrapper(AssemblyRecipe recipe) {
        return new WrapperAssemblyTable(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
    }

//	@Nonnull
//	@Override
//	public String getRecipeCategoryUid() {
//		return CategoryAssemblyTable.UID;
//	}
//
//	@Nonnull
//	@Override
//	public IRecipeWrapper getRecipeWrapper(@Nonnull IFlexibleRecipe recipe) {
//		return new WrapperAssemblyTable(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
//	}
//
//	@Override
//	public boolean isRecipeValid(@Nonnull IFlexibleRecipe recipe) {
//		return recipe.getId() != null && recipe instanceof IFlexibleRecipeViewable && AssemblyRecipeManager.INSTANCE.getRecipe(recipe.getId()) == recipe;
//	}
}
