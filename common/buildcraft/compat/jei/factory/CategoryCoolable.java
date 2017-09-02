package buildcraft.compat.jei.factory;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import buildcraft.api.BCModules;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;

public class CategoryCoolable extends BlankRecipeCategory<WrapperCoolable> {
    public static final String UID = "buildcraft:category_coolable";
    public static final ResourceLocation heatExchangerBackground = new ResourceLocation("buildcraftfactory:textures/gui/heat_exchanger.png");

    private final IDrawable background, slot;

    public CategoryCoolable(IGuiHelper helper) {
        this.background = helper.createDrawable(heatExchangerBackground, 61, 38, 54, 17, 0, 0, 18, 80);
        this.slot = helper.createDrawable(heatExchangerBackground, 7, 22, 18, 18);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Coolable Fluids";
    }

    @Override
    public String getModName() {
        return BCModules.FACTORY.name();
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        slot.draw(minecraft, 0, 0);
        slot.draw(minecraft, 72, 0);
    }
//
//    @Override
//    public void drawAnimations(Minecraft minecraft) {}

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WrapperCoolable recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiFluidStacks.init(0, true, 1, 1, 16, 16, 10, false, null);
        guiFluidStacks.set(0, ingredients.getInputs(FluidStack.class).get(0));

        guiFluidStacks.init(1, false, 73, 1, 16, 16, 10, false, null);
        guiFluidStacks.set(1, ingredients.getOutputs(FluidStack.class).get(0));
    }
}
