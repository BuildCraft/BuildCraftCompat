package buildcraft.compat.jei.recipe;

import buildcraft.api.recipes.IIntegrationRecipe;
import buildcraft.compat.jei.BCPluginJEI;

import javax.annotation.Nonnull;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class HandlerIntegrationTable implements IRecipeHandler<IIntegrationRecipe> {

	@Nonnull
	@Override
	public Class<IIntegrationRecipe> getRecipeClass() {
		return IIntegrationRecipe.class;
	}

	@Override
    public String getRecipeCategoryUid() {
        return CategoryIntegrationTable.UID;
    }

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull IIntegrationRecipe recipe) {
		return new WrapperIntegrationTable(BCPluginJEI.registry.getJeiHelpers().getGuiHelper(), recipe);
	}

	@Override
	public boolean isRecipeValid(@Nonnull IIntegrationRecipe recipe) {
		return true;
	}
}
