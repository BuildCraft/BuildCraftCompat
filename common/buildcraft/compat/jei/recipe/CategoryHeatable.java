//package buildcraft.compat.jei.recipe;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.util.ResourceLocation;
//
//import mezz.jei.api.IGuiHelper;
//import mezz.jei.api.gui.IDrawable;
//import mezz.jei.api.gui.IGuiFluidStackGroup;
//import mezz.jei.api.gui.IRecipeLayout;
//import mezz.jei.api.recipe.IRecipeCategory;
//import mezz.jei.api.recipe.IRecipeWrapper;
//
//public class CategoryHeatable implements IRecipeCategory {
//    public static final String UID = "buildcraft:category_heatable";
//    public static final ResourceLocation energyHeaterBackground = new ResourceLocation("buildcraftfactory:textures/gui/energy_heater.png");
//
//    private final IDrawable background, slotIn, slotOut;
//
//    public CategoryHeatable(IGuiHelper helper) {
//        background = helper.createDrawable(energyHeaterBackground, 176, 19, 54, 19, 0, 0, 18, 80);
//        slotIn = helper.createDrawable(energyHeaterBackground, 7, 22, 18, 18, 0, 0, 0, 0);
//        slotOut = helper.createDrawable(energyHeaterBackground, 7, 22, 18, 18, 0, 0, 72, 0);
//    }
//
//    @Override
//    public String getUid() {
//        return UID;
//    }
//
//    @Override
//    public String getTitle() {
//        return "Heatable Fluids";
//    }
//
//    @Override
//    public IDrawable getBackground() {
//        return background;
//    }
//
//    @Override
//    public void drawExtras(Minecraft minecraft) {
//        slotIn.draw(minecraft);
//        slotOut.draw(minecraft);
//    }
//
//    @Override
//    public void drawAnimations(Minecraft minecraft) {}
//
//    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
//        if (recipeWrapper instanceof WrapperHeatableRecipe) {
//            WrapperHeatableRecipe wrapper = (WrapperHeatableRecipe) recipeWrapper;
//            IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
//
//            guiFluidStacks.init(0, true, 1, 1, 16, 16, 10, false, null);
//            guiFluidStacks.set(0, wrapper.getFluidInputs());
//
//            guiFluidStacks.init(1, false, 73, 1, 16, 16, 10, false, null);
//            guiFluidStacks.set(1, wrapper.getFluidOutputs());
//        }
//    }
//}
