//package buildcraft.compat.jei.recipe;
//
//import java.util.List;
//
//import net.minecraft.util.ResourceLocation;
//
//import buildcraft.silicon.gui.ContainerIntegrationTable;
//
//import javax.annotation.Nonnull;
//import mezz.jei.api.IGuiHelper;
//import mezz.jei.api.gui.IDrawable;
//import mezz.jei.api.gui.IGuiItemStackGroup;
//import mezz.jei.api.gui.IRecipeLayout;
//import mezz.jei.api.recipe.BlankRecipeCategory;
//import mezz.jei.api.recipe.IRecipeWrapper;
//
//public class CategoryIntegrationTable extends BlankRecipeCategory {
//    public static final String UID = "buildcraft-compat:silicon.integration";
//
//	protected final ResourceLocation backgroundLocation;
//    private final IDrawable background;
//    private WrapperIntegrationTable wrapper = null;
//
//    public CategoryIntegrationTable(IGuiHelper guiHelper) {
//		backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/integration_table.png");
//		background = guiHelper.createDrawable(backgroundLocation, 17, 22, 153, 71, 0, 0, 9, 0);
//	}
//
//    @Override
//    public String getUid() {
//        return UID;
//    }
//
//    @Override
//    public String getTitle() {
//        return "Integration Table";
//    }
//
//    @Override
//    public IDrawable getBackground() {
//        return background;
//    }
//
//    @Override
//    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
//		if (recipeWrapper instanceof WrapperIntegrationTable) {
//			wrapper = (WrapperIntegrationTable) recipeWrapper;
//			IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
//
//			for (int i = 0; i < wrapper.getInputs().size(); i++) {
//				int x = ContainerIntegrationTable.SLOT_X[i] - 9;
//				int y = ContainerIntegrationTable.SLOT_Y[i] - 23;
//				guiItemStacks.init(i, true, x, y);
//				guiItemStacks.set(i, (List) wrapper.getInputs().get(i));
//			}
//
//			guiItemStacks.init(12, false, 129, 26);
//			guiItemStacks.set(12, (List) wrapper.getOutputs().get(0));
//		} else {
//			wrapper = null;
//		}
//    }
//}
