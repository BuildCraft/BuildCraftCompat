package buildcraft.compat.module.jei.energy.combustionengine;

import buildcraft.api.BCModules;
import buildcraft.api.fuels.IFuel;
import buildcraft.api.fuels.IFuelManager;
import buildcraft.api.fuels.IFuelManager.IDirtyFuel;
import buildcraft.api.mj.MjAPI;
import buildcraft.energy.BCEnergyBlocks;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//public class CategoryCombustionEngine extends BlankRecipeCategory<WrapperCombustionEngine>
public class CategoryCombustionEngine implements IRecipeCategory<IFuel> {
    // public static final ResourceLocation UID = new ResourceLocation("buildcraft-compat:engine.combustion");
    public static final ResourceLocation UID = new ResourceLocation(BCModules.ENERGY.getModId(), "engine_combustion");
    public static final ResourceLocation FURNACE = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");
    private final IDrawable background;

    private final IDrawable icon;

    @OnlyIn(Dist.CLIENT)
    private FontRenderer font = Minecraft.getInstance().font;
    private final Map<Integer, IDrawableAnimated> burnTimeDrabableMap = new HashMap<>();

    public CategoryCombustionEngine(IGuiHelper guiHelper, Collection<IFuel> fuels) {
        // Calen: 80->100
//        this.background = guiHelper.createDrawable(new ResourceLocation("minecraft", "textures/gui/container/furnace.png"), 55, 38, 18, 32, 0, 0, 0, 80);
//        this.background = guiHelper.drawableBuilder(FURNACE, 55, 38, 18, 32).addPadding(0, 0, 0, 80).build();
        this.background = guiHelper.drawableBuilder(FURNACE, 55, 38, 18, 32).addPadding(0, 0, 0, 120).build();
        // Calen: from 1.12.2 what is this doing???
        guiHelper.createDrawable(new ResourceLocation(BCModules.ENERGY.getModId(), ""), 0, 0, 16, 16);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BCEnergyBlocks.engineIron.get()));

//        ResourceLocation furnaceBackgroundLocation = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");
        IDrawableStatic flameDrawable = guiHelper.createDrawable(FURNACE, 176, 0, 14, 14);
//        this.flame = guiHelper.createAnimatedDrawable(flameDrawable, fuel.getTotalBurningTime() / 10, IDrawableAnimated.StartDirection.TOP, true);
        for (IFuel fuel : fuels) {
            if (!burnTimeDrabableMap.containsKey(fuel.getTotalBurningTime())) {
                burnTimeDrabableMap.put(fuel.getTotalBurningTime(), guiHelper.createAnimatedDrawable(flameDrawable, fuel.getTotalBurningTime() / 10, IDrawableAnimated.StartDirection.TOP, true));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends IFuel> getRecipeClass() {
        return IFuel.class;
    }

    @Override
    public String getTitle() {
//        return new TextComponent("Combustion Engine Fuels");
        return new TranslationTextComponent("buildcraft.jei.title.combustion_engine_fuels").getString();
    }

    public String getModName() {
        return BCModules.ENERGY.name();
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
    public void setIngredients(IFuel recipe, IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, recipe.getFluid());
        if (recipe instanceof IDirtyFuel) {
            ingredients.setOutput(VanillaTypes.FLUID, ((IDirtyFuel) recipe).getResidue());
        }
    }

    // Calen
    private int lastBurnTime = -1;

    @Override
    public void draw(IFuel fuel, MatrixStack stack, double mouseX, double mouseY) {
//        this.flame.draw(stack, 2, 0);
        this.burnTimeDrabableMap.get(fuel.getTotalBurningTime()).draw(stack, 2, 0);
//        GlStateManager.func_179094_E();
        stack.pushPose();
        // Calen: 8->6
//        GlStateManager.func_179109_b(24.0F, 8.0F, 0.0F);
//        stack.translate(24.0F, 8.0F, 0.0F);
        stack.translate(24.0F, 6.0F, 0.0F);
//        this.font.draw(stack, "Burns for " + fuel.getTotalBurningTime() / 20 + "s", 0, 0, Color.darkGray.getRGB());
        this.font.draw(stack, new TranslationTextComponent("buildcraft.jei.title.combustion_engine_fuels.burn_time", fuel.getTotalBurningTime() / 20).getString(), 0, 0, Color.darkGray.getRGB());
//        this.font.draw(stack, " at " + MjAPI.formatMj(fuel.getPowerPerCycle()) + " MJ/t", 0, font.lineHeight, Color.darkGray.getRGB());
        this.font.draw(stack, new TranslationTextComponent("buildcraft.jei.title.combustion_engine_fuels.burn_speed", MjAPI.formatMj(fuel.getPowerPerCycle())).getString(), 0, font.lineHeight, Color.darkGray.getRGB());
//        GlStateManager.func_179109_b(0.0F, (float)(minecraft.field_71466_p.field_78288_b * 2), 0.0F);
        stack.translate(0.0F, (float) (font.lineHeight * 2), 0.0F);
//        GlStateManager.func_179139_a(0.7, 0.7, 1.0);
        stack.scale(0.7F, 0.7F, 1.0F);
//        this.font.draw(stack, " total " + MjAPI.formatMj(fuel.getPowerPerCycle() * (long) fuel.getTotalBurningTime()) + " MJ", 1, 2, Color.gray.getRGB());
        this.font.draw(stack, new TranslationTextComponent("buildcraft.jei.title.combustion_engine_fuels.burn_total", MjAPI.formatMj(fuel.getPowerPerCycle() * (long) fuel.getTotalBurningTime())).getString(), 1, 2, Color.gray.getRGB());
//        GlStateManager.func_179121_F();
        stack.popPose();
    }

    @Override
    // public void setRecipe(IRecipeLayout recipeLayout, WrapperCombustionEngine recipeWrapper, IIngredients ingredients)
    public void setRecipe(IRecipeLayout recipeLayout, IFuel fuel, IIngredients ingredients) {
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
        guiFluidStacks.init(0, true, 1, 15, 16, 16, 1000, false, null);
        guiFluidStacks.set(0, new FluidStack(fuel.getFluid(), 1000));
        if (fuel instanceof IFuelManager.IDirtyFuel) {
            IFuelManager.IDirtyFuel dirtyFuel = (IFuelManager.IDirtyFuel) fuel;
            // Calen: move right 20 to not covered the text
//            guiFluidStacks.init(1, false, 95, 15, 16, 16, 1000, false, (IDrawable) null);
            guiFluidStacks.init(1, false, 115, 15, 16, 16, 1000, false, (IDrawable) null);
//            guiFluidStacks.set(1, (List) ingredients.getOutputs(FluidStack.class).get(0));
            guiFluidStacks.set(1, dirtyFuel.getResidue());
        }
    }

    @Override
    public List<ITextComponent> getTooltipStrings(IFuel recipe, double mouseX, double mouseY) {
        return Lists.newArrayList();
    }

    @Override
    public boolean handleClick(IFuel recipe, double mouseX, double mouseY, int mouseButton) {
        return false;
    }
}
