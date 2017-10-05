package buildcraft.compat.jei.factory;

import java.util.List;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import buildcraft.api.recipes.IRefineryRecipeManager;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WrapperCoolable implements IRecipeWrapper {
    private final IRefineryRecipeManager.ICoolableRecipe recipe;
    private final ImmutableList<FluidStack> in, out;
    private final IDrawableAnimated animatedCooling, animatedHeating;

    WrapperCoolable(IGuiHelper guiHelper, IRefineryRecipeManager.ICoolableRecipe recipe) {
        this.recipe = recipe;
        this.in = ImmutableList.of(recipe.in());
        //noinspection ConstantConditions
        this.out = (recipe.out() != null) ? ImmutableList.of(recipe.out()) : ImmutableList.of();

        IDrawableStatic overComplete = guiHelper.createDrawable(CategoryCoolable.heatExchangerBackground, 52, 171, 54, 17);
        this.animatedCooling = guiHelper.createAnimatedDrawable(overComplete, /*recipe.ticks() * 20*/ 40, IDrawableAnimated.StartDirection.LEFT, false);
        overComplete = guiHelper.createDrawable(CategoryCoolable.heatExchangerBackground, 52, 188, 54, 17);
        this.animatedHeating = guiHelper.createAnimatedDrawable(overComplete, /*recipe.ticks() * 20*/ 40, IDrawableAnimated.StartDirection.RIGHT, false);
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
//        return out;
//    }
//
//    @Override
//    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight) {}

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(FluidStack.class, this.in);
        ingredients.setOutputs(FluidStack.class, this.out);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        this.animatedCooling.draw(minecraft, 18, 0);
        this.animatedHeating.draw(minecraft, 18, 0);
        // minecraft.fontRenderer.drawString("Takes " + (recipe.ticks() / 20.0) + "s", 93, 0, Color.gray.getRGB());
    }

//    @Override
//    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
//        animatedCooling.draw(minecraft, 18, 0);
//        animatedHeating.draw(minecraft, 18, 0);
//    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Lists.newArrayList();
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
