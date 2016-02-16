package buildcraft.compat.jei;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraft.api.fuels.IFuel;

import mezz.jei.api.recipe.IRecipeWrapper;

public class WrapperCombustionEngine implements IRecipeWrapper {
    private final IFuel fuel;
    private final ImmutableList<FluidStack> in;

    public WrapperCombustionEngine(IFuel fuel) {
        this.fuel = fuel;
        in = ImmutableList.of(new FluidStack(fuel.getFluid(), FluidContainerRegistry.BUCKET_VOLUME));
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
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {}

    @Override
    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {}

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return null;
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
