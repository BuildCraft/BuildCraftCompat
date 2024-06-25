package buildcraft.compat.module.jei.silicon;

import buildcraft.api.BCModules;
import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IntegrationRecipe;
import buildcraft.silicon.BCSiliconBlocks;
import com.google.common.collect.ImmutableList;
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
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

//public class CategoryIntegrationTable implements IRecipeCategory<WrapperIntegrationTable>
public class CategoryIntegrationTable implements IRecipeCategory<IntegrationRecipe> {
    // public static final ResourceLocation UID = new ResourceLocation("buildcraft-compat:silicon.integration");
    public static final ResourceLocation UID = new ResourceLocation(BCModules.SILICON.getModId(), "integration");
    protected final ResourceLocation backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/integration_table.png");
    private final IDrawable background;

    private final IDrawable icon;

    private final IntegrationRecipe recipe;
    private final IDrawableAnimated progressBar;
    private final List<ItemStack> inputs;
    private final List<ItemStack> outputs;

    @OnlyIn(Dist.CLIENT)
    private FontRenderer font = Minecraft.getInstance().font;

    public CategoryIntegrationTable(IGuiHelper guiHelper, IntegrationRecipe recipe) {
//        this.background = guiHelper.createDrawable(this.backgroundLocation, 17, 22, 153, 71, 0, 0, 9, 0);
        this.background = guiHelper.drawableBuilder(this.backgroundLocation, 17, 22, 153, 71).addPadding(0, 0, 9, 0).build();

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BCSiliconBlocks.integrationTable.get()));

        this.recipe = recipe;
        // Calen: not impl in 1.12.2
        List<ItemStack> inputs = Lists.newArrayList();
        this.inputs = ImmutableList.copyOf(inputs);
        this.outputs = ImmutableList.of(new ItemStack(Blocks.COBBLESTONE));
        ResourceLocation backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/integration_table.png");
//        IDrawableStatic progressDrawable = guiHelper.createDrawable(backgroundLocation, 176, 17, 4, 69, 0, 0, 0, 0);
        IDrawableStatic progressDrawable = guiHelper.drawableBuilder(this.backgroundLocation, 176, 17, 4, 69).addPadding(0, 0, 0, 0).build();
        this.progressBar = guiHelper.createAnimatedDrawable(progressDrawable, 720, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends IntegrationRecipe> getRecipeClass() {
        return IntegrationRecipe.class;
    }

    public String getTitle() {
//        return new TextComponent("Integration Table");
        return new TranslationTextComponent("tile.integrationTableBlock.name").getString();
    }

    public String getModName() {
        return BCModules.SILICON.name();
    }

    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(IntegrationRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getRequirements().stream().map(is -> is.ingredient).collect(Collectors.toList()));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void draw(IntegrationRecipe recipe, MatrixStack stack, double mouseX, double mouseY) {
        this.progressBar.draw(stack, 156, 1);
        this.font.draw(stack, MjAPI.formatMj(0L) + " MJ", 80, 52, Color.gray.getRGB());

    }

    @Override
    // public void setRecipe(IRecipeLayout recipeLayout, WrapperIntegrationTable recipeWrapper, IIngredients ingredients)
    public void setRecipe(IRecipeLayout recipeLayout, IntegrationRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
//        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        int inventoryIndex = 0;

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int slotIndex = x == 1 && y == 1 ? 0 : x + y * 3 + 1;
                if (inputs.size() > slotIndex) {
                    guiItemStacks.init(inventoryIndex, true, 19 + x * 25, 24 + y * 25);
                    guiItemStacks.set(inventoryIndex, inputs.get(slotIndex));
                    inventoryIndex++;
                }
            }
        }

        guiItemStacks.init(inventoryIndex, false, 129, 26);
        guiItemStacks.set(inventoryIndex, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
