package buildcraft.compat.module.jei.silicon;

import java.awt.Color;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IntegrationRecipe;
import buildcraft.api.recipes.StackDefinition;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WrapperIntegrationTable implements IRecipeWrapper {
    private final IntegrationRecipe recipe;
    private final IDrawableAnimated progressBar;
    private final List<ItemStack> inputs, outputs;

    public WrapperIntegrationTable(IGuiHelper guiHelper, IntegrationRecipe recipe) {
        this.recipe = recipe;

        List<ItemStack> inputs = Lists.newArrayList();
//        inputs.addAll(Utils.getItemStacks(recipe.target));
//        for (StackDefinition definition : recipe.toIntegrate) {
//            inputs.addAll(Utils.getItemStacks(definition));
//        }
        this.inputs = ImmutableList.copyOf(inputs);
        this.outputs = ImmutableList.of(new ItemStack(Blocks.COBBLESTONE));

        ResourceLocation backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/integration_table.png");
        IDrawableStatic progressDrawable = guiHelper.createDrawable(backgroundLocation, 176, 17, 4, 69, 0, 0, 0, 0);
        this.progressBar = guiHelper.createAnimatedDrawable(progressDrawable, (int) (/*recipe.requiredMicroJoules / */ 720), IDrawableAnimated.StartDirection.BOTTOM, false);
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
        this.progressBar.draw(minecraft, 156, 1);
        minecraft.fontRenderer.drawString(MjAPI.formatMj(/*this.recipe.requiredMicroJoules*/0) + " MJ", 80, 52, Color.gray.getRGB());
    }
//
//    @Override
//    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
//        progressBar.draw(minecraft, 156, 1);
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
