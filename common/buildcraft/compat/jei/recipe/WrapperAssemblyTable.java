package buildcraft.compat.jei.recipe;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.api.recipes.IFlexibleRecipeViewable;

import javax.annotation.Nonnull;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WrapperAssemblyTable implements IRecipeWrapper {
    private final IFlexibleRecipe<ItemStack> recipe;
	private final IFlexibleRecipeViewable viewable;
    @Nonnull
    private final IDrawableAnimated progressBar;
	private final List inputs, outputs;

    public WrapperAssemblyTable(@Nonnull IGuiHelper guiHelper, IFlexibleRecipe recipe) {
        this.recipe = (IFlexibleRecipe<ItemStack>) recipe;
		this.viewable = (IFlexibleRecipeViewable) recipe;
		this.inputs = ImmutableList.copyOf(viewable.getInputs());
		this.outputs = ImmutableList.of(viewable.getOutput());

		ResourceLocation backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/assembly_table.png");
		IDrawableStatic progressDrawable = guiHelper.createDrawable(backgroundLocation, 176, 17, 4, 71, 10, 0, 0, 0);
		progressBar = guiHelper.createAnimatedDrawable(progressDrawable, viewable.getEnergyCost() / 720, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public List getInputs() {
        return inputs;
    }

    @Override
    public List getOutputs() {
        return outputs;
    }

    @Override
    public List<FluidStack> getFluidInputs() {
        return null;
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return null;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight) {}

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRendererObj.drawString(viewable.getEnergyCost() + " RF", 4, 0, Color.gray.getRGB());
    }

    @Override
    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
        progressBar.draw(minecraft, 81, 1);
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
