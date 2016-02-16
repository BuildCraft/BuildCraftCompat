package buildcraft.compat.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.gui.DrawableResource;

public class CategoryCombustionEngine implements IRecipeCategory {
    public static final String UID = "buildcraft-compat:engine.combustion";
    private static final DrawableResource BACKGROUND = new DrawableResource(new ResourceLocation(
            "buildcraftcompat:textures/gui/jei/combustion_engine"), 0, 0, 10, 10);

    private WrapperCombustionEngine wrapper = null;

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Combustion Engine Fuels";
    }

    @Override
    public IDrawable getBackground() {
        return BACKGROUND;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drawAnimations(Minecraft minecraft) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
        if (recipeWrapper instanceof WrapperCombustionEngine) {
            wrapper = (WrapperCombustionEngine) recipeWrapper;
        } else wrapper = null;
    }

}
