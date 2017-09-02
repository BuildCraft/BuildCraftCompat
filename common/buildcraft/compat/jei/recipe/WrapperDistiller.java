//package buildcraft.compat.jei;
//
//import java.awt.Color;
//import java.util.Collections;
//import java.util.List;
//
//import javax.annotation.Nonnull;
//
//import com.google.common.collect.ImmutableList;
//
//import net.minecraft.client.Minecraft;
//
//import net.minecraftforge.fluids.FluidStack;
//
//import buildcraft.api.recipes.IComplexRefineryRecipeManager.IDistilationRecipe;
//
//import mezz.jei.api.IGuiHelper;
//import mezz.jei.api.gui.IDrawableAnimated;
//import mezz.jei.api.gui.IDrawableStatic;
//import mezz.jei.api.recipe.IRecipeWrapper;
//
//public class WrapperDistiller implements IRecipeWrapper {
//    public final IDistilationRecipe recipe;
//    private final ImmutableList<FluidStack> in, out;
//    private final IDrawableAnimated animated;
//
//    public WrapperDistiller(@Nonnull IGuiHelper guiHelper, IDistilationRecipe recipe) {
//        this.recipe = recipe;
//        in = ImmutableList.of(recipe.in());
//        out = ImmutableList.of(recipe.outGas(), recipe.outLiquid());
//
//        IDrawableStatic overComplete = guiHelper.createDrawable(CategoryDistiller.distillerBackground, 212, 0, 36, 57);
//        animated = guiHelper.createAnimatedDrawable(overComplete, recipe.ticks() * 20, IDrawableAnimated.StartDirection.LEFT, false);
//    }
//
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
//
//    @Override
//    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
//        minecraft.fontRendererObj.drawString("Takes " + (recipe.ticks() / 20.0) + "s", 38, 24, Color.gray.getRGB());
//    }
//
//    @Override
//    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
//        animated.draw(minecraft, 0, 0);
//    }
//
//    @Override
//    public List<String> getTooltipStrings(int mouseX, int mouseY) {
//        return null;
//    }
//
//    @Override
//    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
//        return false;
//    }
//}
