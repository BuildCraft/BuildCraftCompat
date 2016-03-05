package buildcraft.compat.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CategoryCoolable implements IRecipeCategory {
    public static final String UID = "buildcraft:category_coolable";
    public static final ResourceLocation heatExchangerBackground = new ResourceLocation("buildcraftfactory:textures/gui/heat_exchanger.png");

    private final IDrawable background, slot;

    public CategoryCoolable(IGuiHelper helper) {
        background = helper.createDrawable(heatExchangerBackground, 61, 38, 54, 17, 0, 0, 18, 80);
        slot = helper.createDrawable(heatExchangerBackground, 7, 22, 18, 18);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Coolable Fluids";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        slot.draw(minecraft, 0, 0);
        slot.draw(minecraft, 72, 0);
    }

    @Override
    public void drawAnimations(Minecraft minecraft) {}

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
        if (recipeWrapper instanceof WrapperCoolable) {
            WrapperCoolable wrapper = (WrapperCoolable) recipeWrapper;
            IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

            guiFluidStacks.init(0, true, 1, 1, 16, 16, 10, false, null);
            guiFluidStacks.set(0, wrapper.getFluidInputs());

            guiFluidStacks.init(1, false, 73, 1, 16, 16, 10, false, null);
            guiFluidStacks.set(1, wrapper.getFluidOutputs());
        }
    }
}
