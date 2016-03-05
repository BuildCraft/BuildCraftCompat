package buildcraft.compat.jei;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;

import net.minecraftforge.fluids.FluidStack;

import buildcraft.api.recipes.IComplexRefineryRecipeManager.ICoolableRecipe;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WrapperCoolable implements IRecipeWrapper {
    private final ICoolableRecipe recipe;
    private final ImmutableList<FluidStack> in, out;
    private final IDrawableAnimated animatedCooling, animatedHeating;

    public WrapperCoolable(@Nonnull IGuiHelper guiHelper, ICoolableRecipe recipe) {
        this.recipe = recipe;
        in = ImmutableList.of(recipe.in());
        out = ImmutableList.of(recipe.out());

        IDrawableStatic overComplete = guiHelper.createDrawable(CategoryCoolable.heatExchangerBackground, 52, 171, 54, 17);
        animatedCooling = guiHelper.createAnimatedDrawable(overComplete, recipe.ticks() * 20, IDrawableAnimated.StartDirection.LEFT, false);
        overComplete = guiHelper.createDrawable(CategoryCoolable.heatExchangerBackground, 52, 188, 54, 17);
        animatedHeating = guiHelper.createAnimatedDrawable(overComplete, recipe.ticks() * 20, IDrawableAnimated.StartDirection.RIGHT, false);
    }

    @Override
    public List getInputs() {
        return Collections.emptyList();
    }

    @Override
    public List getOutputs() {
        return Collections.emptyList();
    }

    @Override
    public List<FluidStack> getFluidInputs() {
        return in;
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return out;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight) {}

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRendererObj.drawString("Takes " + (recipe.ticks() / 20.0) + "s", 93, 0, Color.gray.getRGB());
    }

    @Override
    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
        animatedCooling.draw(minecraft, 18, 0);
        animatedHeating.draw(minecraft, 18, 0);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return null;
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
