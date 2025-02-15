package buildcraft.compat.module.jei.silicon;

import buildcraft.api.BCModules;
import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IAssemblyRecipe;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.silicon.BCSiliconBlocks;
import com.google.common.collect.Lists;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;
import java.util.*;

public class CategoryAssemblyTable implements IRecipeCategory<IAssemblyRecipe> {
    // Calen
    public static final RecipeType<IAssemblyRecipe> RECIPE_TYPE =
            RecipeType.create(BCModules.SILICON.getModId(), "assembly", IAssemblyRecipe.class);
    // public static final ResourceLocation UID = new ResourceLocation("buildcraft-compat:silicon.assembly");
    public static final ResourceLocation UID = new ResourceLocation(BCModules.SILICON.getModId(), "assembly");
    protected final ResourceLocation backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/assembly_table.png");
    private final IDrawable background;

    private final IDrawable icon;

    private final Map<IAssemblyRecipe, IDrawableAnimated> progressBarMap = new HashMap<>();
    private final Map<IAssemblyRecipe, List<Ingredient>> inputsMap = new HashMap<>();
    private final Map<IAssemblyRecipe, Ingredient> outputsMap = new HashMap<>();

    @OnlyIn(Dist.CLIENT)
    private Font font = Minecraft.getInstance().font;

    // public CategoryAssemblyTable(IGuiHelper guiHelper, AssemblyRecipeBasic recipe)
    public CategoryAssemblyTable(IGuiHelper guiHelper, Collection<IAssemblyRecipe> recipes) {
//        this.background = guiHelper.createDrawable(this.backgroundLocation, 5, 34, 166, 76, 10, 0, 0, 0);
        this.background = guiHelper.drawableBuilder(this.backgroundLocation, 5, 34, 166, 76).addPadding(10, 0, 0, 0).build();

        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BCSiliconBlocks.assemblyTable.get()));

        ResourceLocation backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/assembly_table.png");
//        IDrawableStatic progressDrawable = guiHelper.createDrawable(backgroundLocation, 176, 48, 4, 71, 10, 0, 0, 0);
        IDrawableStatic progressDrawable = guiHelper.drawableBuilder(backgroundLocation, 176, 48, 4, 71).addPadding(10, 0, 0, 0).build();

        for (IAssemblyRecipe recipe : recipes) {
            List<Ingredient> _inputs = Lists.newArrayList();

            for (IngredientStack in : recipe.getInputsFor(ItemStack.EMPTY)) {
                List<ItemStack> inner = new ArrayList();

                for (ItemStack matching : in.ingredient.getItems()) {
                    matching = matching.copy();
                    matching.setCount(in.count);
                    inner.add(matching);
                }

                _inputs.add(Ingredient.of(inner.stream()));
            }

            this.inputsMap.put(recipe, _inputs);
            this.outputsMap.put(recipe, Ingredient.of(recipe.getOutputPreviews().stream()));

            long mj = recipe.getRequiredMicroJoulesFor(ItemStack.EMPTY);
//            this.progressBar = guiHelper.createAnimatedDrawable(progressDrawable, (int) Math.max(10L, mj / MjAPI.MJ / 50L), IDrawableAnimated.StartDirection.BOTTOM, false);
            progressBarMap.put(recipe, guiHelper.createAnimatedDrawable(progressDrawable, (int) Math.max(10L, mj / MjAPI.MJ / 50L), IDrawableAnimated.StartDirection.BOTTOM, false));
        }
    }

    @Override
    public RecipeType<IAssemblyRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
//        return new TextComponent("Assembly Table");
        return Component.translatable("tile.assemblyTableBlock.name");
    }

    public String getModName() {
        return BCModules.SILICON.name();
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
    public void draw(IAssemblyRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
//        this.progressBar.draw(stack, 81, 2);
        this.progressBarMap.get(recipe).draw(guiGraphics, 81, 2);
        long mj = recipe.getRequiredMicroJoulesFor(ItemStack.EMPTY);
        guiGraphics.drawString(this.font, MjAPI.formatMj(mj) + " MJ", 4, 0, Color.gray.getRGB(), false);
    }

    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, WrapperAssemblyTable recipeWrapper, IIngredients ingredients)
    public void setRecipe(IRecipeLayoutBuilder builder, IAssemblyRecipe recipe, IFocusGroup focuses) {
//        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        // Calen: the looks moved x-1 y-1 off the slot in 1.18.2 with the position used in 1.12.2
//        for (int i = 0; i < inputs.size(); ++i)
        List<Ingredient> inputs = inputsMap.get(recipe);
        for (int i = 0; i < inputs.size(); ++i) {
//            guiItemStacks.init(i, true, 2 + i % 3 * 18, 11 + i / 3 * 18);
//            guiItemStacks.set(i, (List) inputs.get(i));
            builder
//                    .addSlot(RecipeIngredientRole.INPUT, 2 + i % 3 * 18, 11 + i / 3 * 18)
                    .addSlot(RecipeIngredientRole.INPUT, 3 + i % 3 * 18, 12 + i / 3 * 18)
                    .addIngredients(inputs.get(i));
        }

//        guiItemStacks.init(12, false, 110, 11);
//        guiItemStacks.set(12, (List) ingredients.getOutputs(ItemStack.class).get(0));
        builder
//                .addSlot(RecipeIngredientRole.OUTPUT, 110, 11)
                .addSlot(RecipeIngredientRole.OUTPUT, 111, 12)
                .addIngredients(this.outputsMap.get(recipe));
    }
}
