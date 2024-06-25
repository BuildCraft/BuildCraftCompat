package buildcraft.compat.module.jei.factory;

import buildcraft.api.BCModules;
import buildcraft.api.recipes.IRefineryRecipeManager;
import buildcraft.api.recipes.IRefineryRecipeManager.IHeatableRecipe;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

//public class CategoryHeatable extends BlankRecipeCategory
public class CategoryHeatable implements IRecipeCategory<IRefineryRecipeManager.IHeatableRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(BCModules.FACTORY.getModId(), "category_heatable");
    public static final ResourceLocation energyHeaterBackground = new ResourceLocation("buildcraftfactory:textures/gui/energy_heater.png");
    private final IDrawable background;
    private final IDrawable slotIn;
    private final IDrawable slotOut;

    private final IDrawable icon;

    private final IDrawableAnimated animated;


    public CategoryHeatable(IGuiHelper guiHelper) {
//        this.background = helper.createDrawable(energyHeaterBackground, 176, 19, 54, 19, 0, 0, 18, 80);
        this.background = guiHelper.drawableBuilder(energyHeaterBackground, 176, 19, 54, 19).addPadding(0, 0, 18, 80).build();
        // Calen: top+1 to be at the same height as tubes of background
//        this.slotIn = helper.createDrawable(energyHeaterBackground, 7, 22, 18, 18, 0, 0, 0, 0);
//        this.slotIn = guiHelper.drawableBuilder(energyHeaterBackground, 7, 22, 18, 18).addPadding(0, 0, 0, 0).build();
        this.slotIn = guiHelper.drawableBuilder(energyHeaterBackground, 7, 22, 18, 18).addPadding(1, 0, 0, 0).build();
//        this.slotOut = helper.createDrawable(energyHeaterBackground, 7, 22, 18, 18, 0, 0, 72, 0);
//        this.slotOut = guiHelper.drawableBuilder(energyHeaterBackground, 7, 22, 18, 18).addPadding(0, 0, 72, 0).build();
        this.slotOut = guiHelper.drawableBuilder(energyHeaterBackground, 7, 22, 18, 18).addPadding(1, 0, 72, 0).build();

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BCFactoryBlocks.heatExchange.get()));

        IDrawableStatic overComplete = guiHelper.createDrawable(CategoryHeatable.energyHeaterBackground, 176, 152, 54, 19);
        this.animated = guiHelper.createAnimatedDrawable(overComplete, 40, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends IRefineryRecipeManager.IHeatableRecipe> getRecipeClass() {
        return IRefineryRecipeManager.IHeatableRecipe.class;
    }

    @Override
    public String getTitle() {
//        return new TextComponent("Heatable Fluids");
        return new TranslationTextComponent("buildcraft.jei.title.heatable_fluids").getString();
    }

    public String getModName() {
        return BCModules.FACTORY.name();
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(IHeatableRecipe recipe, IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, recipe.in());
        ingredients.setOutput(VanillaTypes.FLUID, recipe.out() == null ? StackUtil.EMPTY_FLUID : recipe.out());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
//    public void drawExtras(Minecraft minecraft)
    public void draw(IRefineryRecipeManager.IHeatableRecipe recipe, MatrixStack stack, double mouseX, double mouseY) {
        this.slotIn.draw(stack);
        this.slotOut.draw(stack);

        this.animated.draw(stack, 18, 0);
    }

    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, WrapperHeatable recipeWrapper, IIngredients ingredients)
    public void setRecipe(IRecipeLayout recipeLayout, IHeatableRecipe recipe, IIngredients ingredients) {
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
//        guiFluidStacks.init(0, true, 1, 1, 16, 16, 10, false, (IDrawable) null);
        guiFluidStacks.init(0, true, 1, 2, 16, 16, 10, false, null);
//        guiFluidStacks.set(0, (List) ingredients.getInputs(FluidStack.class).get(0));
        guiFluidStacks.set(0, recipe.in());
//        guiFluidStacks.init(1, false, 73, 1, 16, 16, 10, false, (IDrawable) null);
        guiFluidStacks.init(1, false, 73, 2, 16, 16, 10, false, (IDrawable) null);
//        guiFluidStacks.set(1, (List) ingredients.getOutputs(FluidStack.class).get(0));
        guiFluidStacks.set(1, recipe.out() == null ? StackUtil.EMPTY_FLUID : recipe.out());
    }

    @Override
    public List<ITextComponent> getTooltipStrings(IHeatableRecipe recipe, double mouseX, double mouseY) {
        return Lists.newArrayList();
    }

    @Override
    public boolean handleClick(IRefineryRecipeManager.IHeatableRecipe recipe, double mouseX, double mouseY, int mouseButton) {
        return false;
    }
}
