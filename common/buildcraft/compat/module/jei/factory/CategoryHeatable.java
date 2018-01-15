package buildcraft.compat.module.jei.factory;

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

public class CategoryHeatable extends BlankRecipeCategory<WrapperHeatable> {
    public static final String UID = "buildcraft:category_heatable";
    public static final ResourceLocation energyHeaterBackground = new ResourceLocation("buildcraftfactory:textures/gui/energy_heater.png");

    private final IDrawable background, slotIn, slotOut;

    public CategoryHeatable(IGuiHelper helper) {
        this.background = helper.createDrawable(energyHeaterBackground, 176, 19, 54, 19, 0, 0, 18, 80);
        this.slotIn = helper.createDrawable(energyHeaterBackground, 7, 22, 18, 18, 0, 0, 0, 0);
        this.slotOut = helper.createDrawable(energyHeaterBackground, 7, 22, 18, 18, 0, 0, 72, 0);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Heatable Fluids";
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
        slotIn.draw(minecraft);
        slotOut.draw(minecraft);
    }

//    @Override
//    public void drawAnimations(Minecraft minecraft) {}

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WrapperHeatable recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiFluidStacks.init(0, true, 1, 1, 16, 16, 10, false, null);
        guiFluidStacks.set(0, ingredients.getInputs(FluidStack.class).get(0));

        guiFluidStacks.init(1, false, 73, 1, 16, 16, 10, false, null);
        guiFluidStacks.set(1, ingredients.getOutputs(FluidStack.class).get(0));
    }
}
