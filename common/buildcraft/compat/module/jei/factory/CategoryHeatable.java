package buildcraft.compat.module.jei.factory;

import buildcraft.api.BCModules;
import buildcraft.api.recipes.IRefineryRecipeManager;
import buildcraft.factory.BCFactoryBlocks;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

//public class CategoryHeatable extends BlankRecipeCategory
public class CategoryHeatable implements IRecipeCategory<IRefineryRecipeManager.IHeatableRecipe> {
    // Calen
    public static final RecipeType<IRefineryRecipeManager.IHeatableRecipe> RECIPE_TYPE =
            RecipeType.create(BCModules.FACTORY.getModId(), "category_heatable", IRefineryRecipeManager.IHeatableRecipe.class);
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

        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BCFactoryBlocks.heatExchange.get()));

        IDrawableStatic overComplete = guiHelper.createDrawable(CategoryHeatable.energyHeaterBackground, 176, 152, 54, 19);
        this.animated = guiHelper.createAnimatedDrawable(overComplete, 40, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public RecipeType<IRefineryRecipeManager.IHeatableRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
//        return new TextComponent("Heatable Fluids");
        return Component.translatable("buildcraft.jei.title.heatable_fluids");
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

    @OnlyIn(Dist.CLIENT)
    @Override
//    public void drawExtras(Minecraft minecraft)
    public void draw(IRefineryRecipeManager.IHeatableRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.slotIn.draw(guiGraphics);
        this.slotOut.draw(guiGraphics);

        this.animated.draw(guiGraphics, 18, 0);
    }

    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, WrapperHeatable recipeWrapper, IIngredients ingredients)
    public void setRecipe(IRecipeLayoutBuilder builder, IRefineryRecipeManager.IHeatableRecipe recipe, IFocusGroup focuses) {
//        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
//        guiFluidStacks.init(0, true, 1, 1, 16, 16, 10, false, (IDrawable) null);
//        guiFluidStacks.set(0, (List) ingredients.getInputs(FluidStack.class).get(0));
        builder
//                .addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .addSlot(RecipeIngredientRole.INPUT, 1, 2)
                .setFluidRenderer(10, false, 16, 16)
                .addIngredient(ForgeTypes.FLUID_STACK, recipe.in());
//        guiFluidStacks.init(1, false, 73, 1, 16, 16, 10, false, (IDrawable) null);
//        guiFluidStacks.set(1, (List) ingredients.getOutputs(FluidStack.class).get(0));
        IRecipeSlotBuilder slotBuilder = builder
//                .addSlot(RecipeIngredientRole.OUTPUT, 73, 1)
                .addSlot(RecipeIngredientRole.OUTPUT, 73, 2)
                .setFluidRenderer(10, false, 16, 16);
        FluidStack out = recipe.out();
        if (out != null && !out.isEmpty()) {
            slotBuilder.addIngredient(ForgeTypes.FLUID_STACK, recipe.out());
        }
    }

    @Override
    public List<Component> getTooltipStrings(IRefineryRecipeManager.IHeatableRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        return Lists.newArrayList();
    }

    @Override
    public boolean handleInput(IRefineryRecipeManager.IHeatableRecipe recipe, double mouseX, double mouseY, InputConstants.Key input) {
        return false;
    }
}
