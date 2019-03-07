package buildcraft.compat.module.jei.silicon;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.AssemblyRecipeBasic;
import buildcraft.api.recipes.IngredientStack;

import buildcraft.compat.module.jei.BCPluginJEI;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WrapperAssemblyTable implements IRecipeWrapper {
    private final AssemblyRecipeBasic recipe;
    private final IDrawableAnimated progressBar;
    private final List<List<ItemStack>> inputs;
    private final List<ItemStack> outputs;

    public WrapperAssemblyTable(AssemblyRecipeBasic recipe) {
        this.recipe = recipe;
        List<List<ItemStack>> _inputs = Lists.newArrayList();
        for (IngredientStack in : recipe.getInputsFor(ItemStack.EMPTY)) {
            List<ItemStack> inner = new ArrayList<>();
            for (ItemStack matching : in.ingredient.getMatchingStacks()) {
                matching = matching.copy();
                matching.setCount(in.count);
                inner.add(matching);
            }
            _inputs.add(inner);
        }
        this.inputs = ImmutableList.copyOf(_inputs);
        this.outputs = ImmutableList.copyOf(recipe.getOutputPreviews());

        IGuiHelper guiHelper = BCPluginJEI.registry.getJeiHelpers().getGuiHelper();

        ResourceLocation backgroundLocation =
            new ResourceLocation("buildcraftsilicon", "textures/gui/assembly_table.png");
        IDrawableStatic progressDrawable = guiHelper.createDrawable(backgroundLocation, 176, 48, 4, 71, 10, 0, 0, 0);
        long mj = this.recipe.getRequiredMicroJoulesFor(ItemStack.EMPTY);
        progressBar = guiHelper.createAnimatedDrawable(progressDrawable, (int) (mj / MjAPI.MJ / 50),
            IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, this.inputs);
        ingredients.setOutputs(ItemStack.class, this.outputs);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        this.progressBar.draw(minecraft, 81, 2);
        long mj = this.recipe.getRequiredMicroJoulesFor(ItemStack.EMPTY);
        minecraft.fontRenderer.drawString(MjAPI.formatMj(mj) + " MJ", 4, 0, Color.gray.getRGB());
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Lists.newArrayList();
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
