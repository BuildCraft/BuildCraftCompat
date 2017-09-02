//package buildcraft.compat.jei;
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
//public class CategoryDistiller implements IRecipeCategory {
//    public static final String UID = "buildcraft:category_distiller";
//    public static final ResourceLocation distillerBackground = new ResourceLocation("buildcraftfactory:textures/gui/distiller.png");
//
//    private final IDrawable background, slot;
//
//    public CategoryDistiller(IGuiHelper helper) {
//        background = helper.createDrawable(distillerBackground, 61, 12, 36, 57);
//        slot = helper.createDrawable(distillerBackground, 7, 34, 18, 18);
//    }
//
//    @Override
//    public String getUid() {
//        return UID;
//    }
//
//    @Override
//    public String getTitle() {
//        return "Distillable Fluids";
//    }
//
//    @Override
//    public IDrawable getBackground() {
//        return background;
//    }
//
//    @Override
//    public void drawExtras(Minecraft minecraft) {
//        slot.draw(minecraft, -20, 21);
//        slot.draw(minecraft, 36, -4);
//        slot.draw(minecraft, 36, 41);
//    }
//
//    @Override
//    public void drawAnimations(Minecraft minecraft) {}
//
//    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
//        if (recipeWrapper instanceof WrapperDistiller) {
//            WrapperDistiller wrapper = (WrapperDistiller) recipeWrapper;
//            IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
//
//            guiFluidStacks.init(0, true, -19, 22, 16, 16, 10, false, null);
//            guiFluidStacks.set(0, wrapper.getFluidInputs());
//
//            guiFluidStacks.init(1, false, 37, -3, 16, 16, 10, false, null);
//            guiFluidStacks.set(1, wrapper.recipe.outGas());
//
//            guiFluidStacks.init(2, false, 37, 42, 16, 16, 10, false, null);
//            guiFluidStacks.set(2, wrapper.recipe.outLiquid());
//        }
//    }
//}
