package buildcraft.compat.module.jei.silicon;

import buildcraft.api.BCModules;
import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IProgrammingRecipe;
import buildcraft.silicon.BCSiliconBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CategoryProgrammingTable implements IRecipeCategory<IProgrammingRecipe> {
    public static final RecipeType<IProgrammingRecipe> RECIPE_TYPE =
            RecipeType.create(BCModules.SILICON.getModId(), "programming", IProgrammingRecipe.class);
    public static final ResourceLocation UID = new ResourceLocation(BCModules.SILICON.getModId(), "programming");
    protected final ResourceLocation backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/programming_table.png");
    private final IDrawable background;
    private final IDrawable inputSlot;
    private final IDrawable outputSlot;
    private final IDrawable arrow;
    private final IDrawable progressBarBackground;
    private final IDrawableAnimated defaultProgressBar;
    private final Map<Long, IDrawableAnimated> progressBarMap = new HashMap<>();
    private final IDrawable icon;
    @OnlyIn(Dist.CLIENT)
    private Font font = Minecraft.getInstance().font;

    public CategoryProgrammingTable(IGuiHelper guiHelper, Collection<IProgrammingRecipe> recipes) {
        this.background = guiHelper.createBlankDrawable(72, 72);
        this.inputSlot = guiHelper.drawableBuilder(this.backgroundLocation, 7, 35, 18, 18).build();
        this.outputSlot = guiHelper.drawableBuilder(this.backgroundLocation, 7, 89, 18, 18).build();
        this.arrow = guiHelper.drawableBuilder(this.backgroundLocation, 28, 40, 11, 8).build();
        this.progressBarBackground = guiHelper.drawableBuilder(this.backgroundLocation, 163, 35, 6, 72).build();

        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BCSiliconBlocks.programmingTable.get()));

        IDrawableStatic progressDrawable = guiHelper.drawableBuilder(this.backgroundLocation, 176, 18, 4, 70).addPadding(0, 0, 0, 0).build();
        this.defaultProgressBar = guiHelper.createAnimatedDrawable(progressDrawable, 720, IDrawableAnimated.StartDirection.BOTTOM, false);
        for (IProgrammingRecipe recipe : recipes) {
            long mj = recipe.getEnergyCost();
            progressBarMap.put(mj, guiHelper.createAnimatedDrawable(progressDrawable, (int) Math.max(10L, mj / MjAPI.MJ / 50L), IDrawableAnimated.StartDirection.BOTTOM, false));
        }
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("tile.programmingTableBlock.name");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends IProgrammingRecipe> getRecipeClass() {
        return IProgrammingRecipe.class;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void draw(IProgrammingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        this.inputSlot.draw(stack, 2, 27);
        this.arrow.draw(stack, 2 + 18 + 5, 27 + 5);
        this.outputSlot.draw(stack, 2 + 18 + 5 + 11 + 5, 27);
        this.progressBarBackground.draw(stack, 66, 0);
        this.progressBarMap.getOrDefault(recipe.getEnergyCost(), this.defaultProgressBar).draw(stack, 67, 1);
        long mj = recipe.getEnergyCost();
        this.font.draw(stack, MjAPI.formatMj(mj) + " MJ", 10, 30 + 5 + 8 + 10, Color.gray.getRGB());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IProgrammingRecipe recipe, IFocusGroup focuses) {
        builder
                .addSlot(RecipeIngredientRole.INPUT, 2 + 1, 27 + 1)
                .addIngredients(recipe.getInput().ingredient);

        builder
                .addSlot(RecipeIngredientRole.OUTPUT, 2 + 18 + 5 + 11 + 5 + 1, 27 + 1)
                .addIngredients(Ingredient.of(recipe.getOutput()));
    }
}
