package buildcraft.compat.jei;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;

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

            guiFluidStacks.init(fuelSlot, true, 1, 15, 16, 16, 1000, false, null);
            guiFluidStacks.set(fuelSlot, wrapper.getFluidInputs());
        } else {
            wrapper = null;
        }
    }
}
