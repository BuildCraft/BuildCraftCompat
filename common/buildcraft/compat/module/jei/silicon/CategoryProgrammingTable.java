package buildcraft.compat.module.jei.silicon;

import buildcraft.api.BCModules;
import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IProgrammingRecipe;
import buildcraft.silicon.BCSiliconBlocks;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryProgrammingTable implements IRecipeCategory<IProgrammingRecipe> {
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
    private FontRenderer font = Minecraft.getInstance().font;

    public CategoryProgrammingTable(IGuiHelper guiHelper, Collection<IProgrammingRecipe> recipes) {
        this.background = guiHelper.createBlankDrawable(72, 72);
        this.inputSlot = guiHelper.drawableBuilder(this.backgroundLocation, 7, 35, 18, 18).build();
        this.outputSlot = guiHelper.drawableBuilder(this.backgroundLocation, 7, 89, 18, 18).build();
        this.arrow = guiHelper.drawableBuilder(this.backgroundLocation, 28, 40, 11, 8).build();
        this.progressBarBackground = guiHelper.drawableBuilder(this.backgroundLocation, 163, 35, 6, 72).build();

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BCSiliconBlocks.programmingTable.get()));

        IDrawableStatic progressDrawable = guiHelper.drawableBuilder(this.backgroundLocation, 176, 18, 4, 70).addPadding(0, 0, 0, 0).build();
        this.defaultProgressBar = guiHelper.createAnimatedDrawable(progressDrawable, 720, IDrawableAnimated.StartDirection.BOTTOM, false);
        for (IProgrammingRecipe recipe : recipes) {
            long mj = recipe.getEnergyCost();
            progressBarMap.put(mj, guiHelper.createAnimatedDrawable(progressDrawable, (int) Math.max(10L, mj / MjAPI.MJ / 50L), IDrawableAnimated.StartDirection.BOTTOM, false));
        }
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("tile.programmingTableBlock.name").getString();
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

    @Override
    public void setIngredients(IProgrammingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(Lists.newArrayList(recipe.getInput().ingredient));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void draw(IProgrammingRecipe recipe, MatrixStack stack, double mouseX, double mouseY) {
        this.inputSlot.draw(stack, 2, 27);
        this.arrow.draw(stack, 2 + 18 + 5, 27 + 5);
        this.outputSlot.draw(stack, 2 + 18 + 5 + 11 + 5, 27);
        this.progressBarBackground.draw(stack, 66, 0);
        this.progressBarMap.getOrDefault(recipe.getEnergyCost(), this.defaultProgressBar).draw(stack, 67, 1);
        long mj = recipe.getEnergyCost();
        this.font.draw(stack, MjAPI.formatMj(mj) + " MJ", 10, 30 + 5 + 8 + 10, Color.gray.getRGB());
    }

    @Override
    public void setRecipe(IRecipeLayout builder, IProgrammingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = builder.getItemStacks();
//        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);

        guiItemStacks.init(0, true, 2 + 1, 27 + 1);
        guiItemStacks.set(0, inputs.get(0));

        guiItemStacks.init(1, false, 2 + 18 + 5 + 11 + 5 + 1, 27 + 1);
        guiItemStacks.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
