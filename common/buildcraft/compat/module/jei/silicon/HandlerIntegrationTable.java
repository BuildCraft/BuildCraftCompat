package buildcraft.compat.module.jei.silicon;

import buildcraft.api.recipes.IntegrationRecipe;

import buildcraft.compat.module.jei.BCPluginJEI;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class HandlerIntegrationTable implements IRecipeWrapperFactory<IntegrationRecipe> {
    @Override
    public IRecipeWrapper getRecipeWrapper(IntegrationRecipe recipe) {
        return new WrapperIntegrationTable(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
    }

    //
//	@Nonnull
//	@Override
//	public Class<IIntegrationRecipe> getRecipeClass() {
//		return IIntegrationRecipe.class;
//	}
//
//	@Override
//    public String getRecipeCategoryUid() {
//        return CategoryIntegrationTable.UID;
//    }
//
//	@Nonnull
//	@Override
//	public IRecipeWrapper getRecipeWrapper(@Nonnull IIntegrationRecipe recipe) {
//		return new WrapperIntegrationTable(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
//	}
//
//	@Override
//	public boolean isRecipeValid(@Nonnull IIntegrationRecipe recipe) {
//		return true;
//	}
}
