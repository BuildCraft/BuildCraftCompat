package buildcraft.compat.jei.silicon;

import buildcraft.api.BCModules;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class CategoryAssemblyTable extends BlankRecipeCategory<WrapperAssemblyTable> {
    public static final String UID = "buildcraft-compat:silicon.assembly";

	protected final ResourceLocation backgroundLocation;
    private final IDrawable background;

    public CategoryAssemblyTable(IGuiHelper guiHelper) {
		this.backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/assembly_table.png");
		this.background = guiHelper.createDrawable(backgroundLocation, 5, 34, 166, 76, 10, 0, 0, 0);
	}

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Assembly Table";
    }

    @Override
    public String getModName() {
        return BCModules.SILICON.name();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WrapperAssemblyTable recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        for (int i = 0; i < inputs.size(); i++) {
            guiItemStacks.init(i, true, 2 + (i % 3) * 18, 11 + (i / 3) * 18);

            guiItemStacks.set(i, inputs.get(i));
//            Object o = recipeWrapper.getInputs().get(i);
//            if (o instanceof ItemStack) {
//                guiItemStacks.set(i, (ItemStack) o);
//            } else if (o instanceof List) {
//                guiItemStacks.set(i, (List) o);
//            }
        }

        guiItemStacks.init(12, false, 110, 11);
        guiItemStacks.set(12, ingredients.getOutputs(ItemStack.class).get(0));
    }
}
