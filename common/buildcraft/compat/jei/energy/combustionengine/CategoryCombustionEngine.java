package buildcraft.compat.jei.energy.combustionengine;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import buildcraft.api.BCModules;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;

public class CategoryCombustionEngine extends BlankRecipeCategory<WrapperCombustionEngine> {
    public static final String UID = "buildcraft-compat:engine.combustion";

    private final IDrawable background;
//    private WrapperCombustionEngine wrapper = null;

    public CategoryCombustionEngine(IGuiHelper guiHelper) {
        super();
        this.background = guiHelper.createDrawable(
                new ResourceLocation("minecraft", "textures/gui/container/furnace.png"),
                55, 38, 18, 32, 0, 0, 0, 80);
        guiHelper.createDrawable(new ResourceLocation(BCModules.ENERGY.getModId(), ""), 0, 0, 16, 16);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Combustion Engine Fuels";
    }

    @Override
    public String getModName() {
        return BCModules.ENERGY.name();
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WrapperCombustionEngine recipeWrapper, IIngredients ingredients) {
//        WrapperCombustionEngine wrapper = (WrapperCombustionEngine) recipeWrapper;
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiFluidStacks.init(0, true, 1, 15, 16, 16, 1000, false, null);
        guiFluidStacks.set(0, ingredients.getInputs(FluidStack.class).get(0));

        if (recipeWrapper instanceof WrapperCombustionEngine.Dirty) {
//            WrapperCombustionEngine.Dirty dirty = (WrapperCombustionEngine.Dirty)recipeWrapper;

            guiFluidStacks.init(1, false, 95, 15, 16, 16, 1000, false, null);
            guiFluidStacks.set(1, ingredients.getOutputs(FluidStack.class).get(0));
        }
    }
//
//    @Override
//    public void drawExtras(Minecraft minecraft) {
//        super.drawExtras(minecraft);
//
//        if (this.wrapper != null) {
//            this.wrapper.flame.draw(minecraft, 2, 0);
//        }
//    }
}
