package buildcraft.compat.module.jei.factory;

import buildcraft.api.BCModules;
import buildcraft.api.recipes.IRefineryRecipeManager;
import buildcraft.api.recipes.IRefineryRecipeManager.ICoolableRecipe;
import buildcraft.factory.BCFactoryBlocks;
import buildcraft.lib.misc.StackUtil;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

//public class CategoryCoolable extends BlankRecipeCategory<WrapperCoolable>
public class CategoryCoolable implements IRecipeCategory<IRefineryRecipeManager.ICoolableRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(BCModules.FACTORY.getModId(), "category_coolable");
    public static final ResourceLocation heatExchangerBackground = new ResourceLocation("buildcraftfactory:textures/gui/heat_exchanger.png");
    private final IDrawable background;
    private final IDrawable slot;

    private final IDrawable icon;

    private final IDrawableAnimated animatedCooling;
    private final IDrawableAnimated animatedHeating;

    public CategoryCoolable(IGuiHelper guiHelper) {
        // Calen: top padding add 2 to be same height as heatable
//        this.background = helper.createDrawable(heatExchangerBackground, 61, 38, 54, 17, 0, 0, 18, 80);
//        this.background = guiHelper.drawableBuilder(heatExchangerBackground, 61, 38, 54, 17).addPadding(0, 0, 18, 80).build();
        this.background = guiHelper.drawableBuilder(heatExchangerBackground, 61, 38, 54, 17).addPadding(2, 0, 18, 80).build();
        this.slot = guiHelper.createDrawable(heatExchangerBackground, 7, 22, 18, 18);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BCFactoryBlocks.heatExchange.get()));

        IDrawableStatic overComplete = guiHelper.createDrawable(CategoryCoolable.heatExchangerBackground, 52, 171, 54, 17);
        this.animatedCooling = guiHelper.createAnimatedDrawable(overComplete, 40, IDrawableAnimated.StartDirection.LEFT, false);
        overComplete = guiHelper.createDrawable(CategoryCoolable.heatExchangerBackground, 52, 188, 54, 17);
        this.animatedHeating = guiHelper.createAnimatedDrawable(overComplete, 40, IDrawableAnimated.StartDirection.RIGHT, false);
    }

    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends IRefineryRecipeManager.ICoolableRecipe> getRecipeClass() {
        return IRefineryRecipeManager.ICoolableRecipe.class;
    }

    public String getTitle() {
//        return new TextComponent("Coolable Fluids");
        return new TranslationTextComponent("buildcraft.jei.title.coolable_fluids").getString();
    }

    public String getModName() {
        return BCModules.FACTORY.name();
    }

    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(ICoolableRecipe recipe, IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, recipe.in());
        ingredients.setOutput(VanillaTypes.FLUID, recipe.out() == null ? StackUtil.EMPTY_FLUID : recipe.out());
    }

    @Override
//    public void drawExtras(Minecraft minecraft)
    public void draw(IRefineryRecipeManager.ICoolableRecipe recipe, MatrixStack stack, double mouseX, double mouseY) {
        // Calen: y+1 to be at the same height as tubes of background
        // in 1.18.2 additionally +1
//        this.slot.draw(stack, 0, 0);
        this.slot.draw(stack, 0, 1);
//        this.slot.draw(stack, 72, 0);
        this.slot.draw(stack, 72, 1);

        // Calen: top padding add 2 to be same height as heatable
//        this.animatedCooling.draw(stack, 18, 0);
        this.animatedCooling.draw(stack, 18, 2);
//        this.animatedHeating.draw(stack, 18, 0);
        this.animatedHeating.draw(stack, 18, 2);
    }

    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, WrapperCoolable recipeWrapper, IIngredients ingredients)
    public void setRecipe(IRecipeLayout recipeLayout, ICoolableRecipe recipe, IIngredients ingredients) {
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
//        guiFluidStacks.init(0, true, 1, 1, 16, 16, 10, false, (IDrawable) null);
        guiFluidStacks.init(0, true, 1, 2, 16, 16, 10, false, (IDrawable) null);
        guiFluidStacks.set(0, recipe.in());
//        guiFluidStacks.init(1, false, 73, 1, 16, 16, 10, false, (IDrawable) null);
        guiFluidStacks.init(1, false, 73, 2, 16, 16, 10, false, (IDrawable) null);
//        guiFluidStacks.set(1, (List) ingredients.getOutputs(FluidStack.class).get(0));
        guiFluidStacks.set(1, recipe.out() == null ? StackUtil.EMPTY_FLUID : recipe.out());
    }

    @Override
    public List<ITextComponent> getTooltipStrings(ICoolableRecipe recipe, double mouseX, double mouseY) {
        return Lists.newArrayList();
    }

    @Override
    public boolean handleClick(IRefineryRecipeManager.ICoolableRecipe recipe, double mouseX, double mouseY, int mouseButton) {
        return false;
    }
}
