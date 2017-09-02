package buildcraft.compat.jei.recipe;

import java.awt.Color;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import buildcraft.api.fuels.IFuel;
import buildcraft.api.fuels.IFuelManager.IDirtyFuel;
import buildcraft.api.mj.MjAPI;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WrapperCombustionEngine implements IRecipeWrapper {
    private final IFuel fuel;
    private final ImmutableList<FluidStack> in;
    private final IDrawableAnimated flame;

    WrapperCombustionEngine(IGuiHelper guiHelper, IFuel fuel) {
        this.fuel = fuel;
        in = ImmutableList.of(new FluidStack(fuel.getFluid(), Fluid.BUCKET_VOLUME));

        ResourceLocation furnaceBackgroundLocation = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");
        IDrawableStatic flameDrawable = guiHelper.createDrawable(furnaceBackgroundLocation, 176, 0, 14, 14);
        this.flame = guiHelper.createAnimatedDrawable(flameDrawable, fuel.getTotalBurningTime() / 10, IDrawableAnimated.StartDirection.TOP, true);
    }

//    @Override
//    public List getInputs() {
//        return Collections.emptyList();
//    }
//
//    @Override
//    public List getOutputs() {
//        return Collections.emptyList();
//    }
//
//    @Override
//    public List<FluidStack> getFluidInputs() {
//        return in;
//    }
//
//    @Override
//    public List<FluidStack> getFluidOutputs() {
//        return null;
//    }
//
//    @Override
//    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight) {}

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(FluidStack.class, this.in);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(24, 8, 0);
        // GlStateManager.scale(.7, .7, 1.0);
        minecraft.fontRenderer.drawString("Burns for " + (fuel.getTotalBurningTime() / 20) + "s", 0, 0, Color.darkGray.getRGB());
        minecraft.fontRenderer.drawString(" at " + MjAPI.formatMj(fuel.getPowerPerCycle()) + " MJ/t", 0, minecraft.fontRenderer.FONT_HEIGHT, Color.darkGray.getRGB());
        GlStateManager.translate(0, minecraft.fontRenderer.FONT_HEIGHT * 2, 0);
        GlStateManager.scale(.7, .7, 1.0);
        minecraft.fontRenderer.drawString(" total " + MjAPI.formatMj(fuel.getPowerPerCycle() * fuel.getTotalBurningTime()) + " MJ", 1, 2, Color.gray.getRGB());
        GlStateManager.popMatrix();
        
        this.flame.draw(minecraft, 2, 0);
    }

//    @Override
//    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
//        flame.draw(minecraft, 2, 0);
//    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Lists.newArrayList();
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    public static class Dirty extends WrapperCombustionEngine {
        final IDirtyFuel dirty;

        Dirty(IGuiHelper guiHelper, IDirtyFuel fuel) {
            super(guiHelper, fuel);
            this.dirty = fuel;
        }

        @Override
        public void getIngredients(IIngredients ingredients) {
            ingredients.setOutput(FluidStack.class, this.dirty.getResidue());
        }
    }
}
