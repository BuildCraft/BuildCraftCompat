package buildcraft.compat.jei;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraft.api.fuels.IFuel;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WrapperCombustionEngine implements IRecipeWrapper {
    private final IFuel fuel;
    private final ImmutableList<FluidStack> in;
    @Nonnull
    private final IDrawableAnimated flame;

    public WrapperCombustionEngine(@Nonnull IGuiHelper guiHelper, IFuel fuel) {
        this.fuel = fuel;
        in = ImmutableList.of(new FluidStack(fuel.getFluid(), FluidContainerRegistry.BUCKET_VOLUME));

        ResourceLocation furnaceBackgroundLocation = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");
        IDrawableStatic flameDrawable = guiHelper.createDrawable(furnaceBackgroundLocation, 176, 0, 14, 14);
        this.flame = guiHelper.createAnimatedDrawable(flameDrawable, fuel.getTotalBurningTime() / 10, IDrawableAnimated.StartDirection.TOP, true);
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
        return null;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight) {}

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRendererObj.drawString("Burns for " + (fuel.getTotalBurningTime() / 20) + "s", 24, 12, Color.gray.getRGB());
        minecraft.fontRendererObj.drawString(" at " + fuel.getPowerPerCycle() + "RF/t", 24, 21, Color.gray.getRGB());
    }

    @Override
    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
        flame.draw(minecraft, 2, 0);
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
