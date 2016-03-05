package buildcraft.compat.jei.recipe;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;

import net.minecraftforge.fluids.FluidStack;

import buildcraft.BuildCraftFactory;
import buildcraft.api.recipes.IComplexRefineryRecipeManager.IHeatableRecipe;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WrapperHeatableRecipe implements IRecipeWrapper {
    private final IHeatableRecipe heatable;
    private final ImmutableList<FluidStack> in, out;
    private final IDrawableAnimated animated;

    public WrapperHeatableRecipe(@Nonnull IGuiHelper guiHelper, IHeatableRecipe recipe) {
        this.heatable = recipe;
        in = ImmutableList.of(recipe.in());
        out = ImmutableList.of(recipe.out());

        IDrawableStatic overComplete = guiHelper.createDrawable(CategoryHeatable.energyHeaterBackground, 176, 152, 54, 19);
        animated = guiHelper.createAnimatedDrawable(overComplete, recipe.ticks() * 20, IDrawableAnimated.StartDirection.LEFT, false);
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
        minecraft.fontRendererObj.drawString("Takes " + (heatable.ticks() / 20.0) + "s", 93, 0, Color.gray.getRGB());
        int rftick = Math.abs(heatable.heatFrom() - heatable.heatTo()) * BuildCraftFactory.rfPerHeatPerMB * heatable.in().amount;
        minecraft.fontRendererObj.drawString(" at " + rftick + "RF/t", 93, 11, Color.gray.getRGB());
    }

    @Override
    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
        animated.draw(minecraft, 18, 0);
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
