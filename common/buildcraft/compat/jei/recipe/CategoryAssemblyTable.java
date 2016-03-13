package buildcraft.compat.jei.recipe;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CategoryAssemblyTable extends BlankRecipeCategory {
    public static final String UID = "buildcraft-compat:silicon.assembly";

	protected final ResourceLocation backgroundLocation;
    private final IDrawable background;
    private WrapperAssemblyTable wrapper = null;

    public CategoryAssemblyTable(IGuiHelper guiHelper) {
		backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/assembly_table.png");
		background = guiHelper.createDrawable(backgroundLocation, 5, 34, 166, 76, 10, 0, 0, 0);
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
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
		if (recipeWrapper instanceof WrapperAssemblyTable) {
			wrapper = (WrapperAssemblyTable) recipeWrapper;
			IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

			for (int i = 0; i < wrapper.getInputs().size(); i++) {
				guiItemStacks.init(i, true, 2 + (i % 3) * 18, 11 + (i / 3) * 18);

				Object o = wrapper.getInputs().get(i);
				if (o instanceof ItemStack) {
					guiItemStacks.set(i, (ItemStack) o);
				} else if (o instanceof List) {
					guiItemStacks.set(i, (List) o);
				}
			}

			guiItemStacks.init(12, false, 110, 11);
			guiItemStacks.set(12, (ItemStack) wrapper.getOutputs().get(0));
		} else {
			wrapper = null;
		}
    }
}
