package buildcraft.compat.jei.recipe;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.plugins.vanilla.furnace.FurnaceRecipeCategory;

public class CategoryCombustionEngine extends FurnaceRecipeCategory {
    public static final String UID = "buildcraft-compat:engine.combustion";

    @Nonnull
    private final IDrawable background;
    private WrapperCombustionEngine wrapper = null;

    public CategoryCombustionEngine(IGuiHelper guiHelper) {
        super(guiHelper);
        background = guiHelper.createDrawable(backgroundLocation, 55, 38, 18, 32, 0, 0, 0, 80);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Combustion Engine Fuels";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        if (recipeWrapper instanceof WrapperCombustionEngine) {
            wrapper = (WrapperCombustionEngine) recipeWrapper;
            IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

            guiFluidStacks.init(0, true, 1, 15, 16, 16, 1000, false, null);
            guiFluidStacks.set(0, wrapper.getFluidInputs());

            if (wrapper instanceof WrapperCombustionEngine.Dirty) {
                WrapperCombustionEngine.Dirty dirty = (WrapperCombustionEngine.Dirty) wrapper;

                guiFluidStacks.init(1, false, 95, 15, 16, 16, 1000, false, null);
                guiFluidStacks.set(1, wrapper.getFluidOutputs());
            }
        } else {
            wrapper = null;
        }
    }
}
