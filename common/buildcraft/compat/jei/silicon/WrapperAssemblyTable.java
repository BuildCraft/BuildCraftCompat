package buildcraft.compat.jei.silicon;

import java.awt.Color;
import java.util.List;
import javax.annotation.Nonnull;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.AssemblyRecipe;
import buildcraft.api.recipes.StackDefinition;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WrapperAssemblyTable implements IRecipeWrapper {
    private final AssemblyRecipe recipe;
    @Nonnull
    private final IDrawableAnimated progressBar;
    private final List<ItemStack> inputs, outputs;

    public WrapperAssemblyTable(@Nonnull IGuiHelper guiHelper, AssemblyRecipe recipe) {
        this.recipe = recipe;
        List<ItemStack> inputs = Lists.newArrayList();
        for (StackDefinition definition : recipe.requiredStacks) {
//		    for(ItemStack es: definition.filter.getExamples()) {
//		        ItemStack s = es.copy();
//		        s.setCount(sd.count);
//		        inputs.add(s);
//            }
            inputs.addAll(Utils.getItemStacks(definition));
        }
        this.inputs = ImmutableList.copyOf(inputs);
        this.outputs = ImmutableList.of(recipe.output);

        ResourceLocation backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/assembly_table.png");
        IDrawableStatic progressDrawable = guiHelper.createDrawable(backgroundLocation, 176, 17, 4, 71, 10, 0, 0, 0);
        progressBar = guiHelper.createAnimatedDrawable(progressDrawable, (int) (recipe.requiredMicroJoules / 720), IDrawableAnimated.StartDirection.BOTTOM, false);
    }

//    @Override
//    public List getInputs() {
//        return inputs;
//    }
//
//    @Override
//    public List getOutputs() {
//        return outputs;
//    }
//
//    @Override
//    public List<FluidStack> getFluidInputs() {
//        return null;
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
        ingredients.setInputs(ItemStack.class, this.inputs);
        ingredients.setOutputs(ItemStack.class, this.outputs);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        this.progressBar.draw(minecraft, 81, 1);
        minecraft.fontRenderer.drawString(MjAPI.formatMj(this.recipe.requiredMicroJoules) + " MJ", 4, 0, Color.gray.getRGB());
    }

//    @Override
//    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
//        progressBar.draw(minecraft, 81, 1);
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
