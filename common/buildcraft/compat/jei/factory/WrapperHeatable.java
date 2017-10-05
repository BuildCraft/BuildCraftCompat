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

public class WrapperHeatable implements IRecipeWrapper {
    private final IRefineryRecipeManager.IHeatableRecipe heatable;
    private final ImmutableList<FluidStack> in, out;
    private final IDrawableAnimated animated;

    public WrapperHeatable(IGuiHelper guiHelper, IRefineryRecipeManager.IHeatableRecipe recipe) {
        this.heatable = recipe;
        this.in = ImmutableList.of(recipe.in());
        //noinspection ConstantConditions
        this.out = (recipe.out() != null) ? ImmutableList.of(recipe.out()) : ImmutableList.of();

        IDrawableStatic overComplete = guiHelper.createDrawable(CategoryHeatable.energyHeaterBackground, 176, 152, 54, 19);
        animated = guiHelper.createAnimatedDrawable(overComplete, /*recipe.ticks() * 20*/ 40, IDrawableAnimated.StartDirection.LEFT, false);
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
        this.animated.draw(minecraft, 18, 0);
//        minecraft.fontRenderer.drawString("Takes " + (heatable.ticks() / 20.0) + "s", 93, 0, Color.gray.getRGB());
//        int rftick = Math.abs(heatable.heatFrom() - heatable.heatTo()) * BuildCraftFactory.rfPerHeatPerMB * heatable.in().amount;
//        minecraft.fontRenderer.drawString(" at " + rftick + "RF/t", 93, 11, Color.gray.getRGB());
    }

//    @Override
//    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
//        animated.draw(minecraft, 18, 0);
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
