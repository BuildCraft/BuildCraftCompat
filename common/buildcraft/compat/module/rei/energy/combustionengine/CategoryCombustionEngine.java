package buildcraft.compat.module.rei.energy.combustionengine;

import buildcraft.api.BCModules;
import buildcraft.api.mj.MjAPI;
import buildcraft.compat.module.rei.ReiUtils;
import buildcraft.energy.BCEnergyBlocks;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.List;

public enum CategoryCombustionEngine implements DisplayCategory<DisplayCombustionEngine> {
    INSTANCE;

    public static final CategoryIdentifier<DisplayCombustionEngine> ID = CategoryIdentifier.of(new ResourceLocation(BCModules.ENERGY.getModId(), "category_engine_combustion"));
    public static final EntryStack<ItemStack> ICON = EntryStacks.of(new ItemStack(BCEnergyBlocks.engineIron.get()));
    public static final ResourceLocation BACKGROUND = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");

    @Override
    public CategoryIdentifier<? extends DisplayCombustionEngine> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("buildcraft.rei.title.combustion_engine_fuels");
    }

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public int getDisplayHeight() {
        return 32 + 2 + ReiUtils.PADDING * 2;
    }

    @Override
    public int getDisplayWidth(DisplayCombustionEngine display) {
        return 138 + 2 + ReiUtils.PADDING * 2;
    }

    @Override
    public List<Widget> setupDisplay(DisplayCombustionEngine display, Rectangle bounds) {
        List<Widget> ret = Lists.newArrayList();

        ret.add(Widgets.createRecipeBase(bounds));

        Point lu = new Point(bounds.getX() + ReiUtils.PADDING, bounds.getY() + ReiUtils.PADDING);

        // background
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 0, lu.getY() + 0, 55, 38, 18, 32));

        // slot
        if (!display.getOutputEntries().isEmpty()) {
            ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 114, lu.getY() + 14, 7, 22, 18, 18));
        }

        // animation
        ret.add(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, delta) ->
                ReiUtils.drawAnimation(
                        guiGraphics, lu, BACKGROUND,
                        display.totalBurningTime / 10,
                        2, 0,
                        176, 0,
                        14, 14,
                        ReiUtils.StartPosition.BOTTOM
                )
        ));

        // slot content
        ret.add(Widgets.createSlot(new Point(lu.getX() + 1, lu.getY() + 15)).markInput().entries(display.getInputEntries().get(0)).disableBackground());
        if (!display.getOutputEntries().isEmpty()) {
            ret.add(Widgets.createSlot(new Point(lu.getX() + 115, lu.getY() + 15)).markOutput().entries(display.getOutputEntries().get(0)).disableBackground());
        }

        // text
        ret.add(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, delta) ->
        {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(lu.getX(), lu.getY(), 0);
            Font font = Minecraft.getInstance().font;

            poseStack.pushPose();
            poseStack.translate(24.0F, 6.0F, 0.0F);
            guiGraphics.drawString(font, Component.translatable("buildcraft.jei.title.combustion_engine_fuels.burn_time", display.totalBurningTime / 20).getString(), 0, 0, Color.darkGray.getRGB(), false);
            guiGraphics.drawString(font, Component.translatable("buildcraft.jei.title.combustion_engine_fuels.burn_speed", MjAPI.formatMj(display.powerPerCycle)).getString(), 0, font.lineHeight, Color.darkGray.getRGB(), false);
            poseStack.translate(0.0F, (float) (font.lineHeight * 2), 0.0F);
            poseStack.scale(0.7F, 0.7F, 1.0F);
            guiGraphics.drawString(font, Component.translatable("buildcraft.jei.title.combustion_engine_fuels.burn_total", MjAPI.formatMj(display.powerPerCycle * (long) display.totalBurningTime)).getString(), 1, 2, Color.gray.getRGB(), false);
            poseStack.popPose();
            poseStack.popPose();
        }));

        return ret;
    }
}
