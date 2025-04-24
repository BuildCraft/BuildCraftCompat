package buildcraft.compat.module.rei.silicon;

import buildcraft.api.BCModules;
import buildcraft.api.mj.MjAPI;
import buildcraft.compat.module.rei.ReiUtils;
import buildcraft.silicon.BCSiliconBlocks;
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

public enum CategoryProgrammingTable implements DisplayCategory<DisplayProgramming> {
    INSTANCE;

    public static final CategoryIdentifier<DisplayProgramming> ID = CategoryIdentifier.of(new ResourceLocation(BCModules.SILICON.getModId(), "category_programming"));
    public static final EntryStack<ItemStack> ICON = EntryStacks.of(new ItemStack(BCSiliconBlocks.programmingTable.get()));
    public static final ResourceLocation BACKGROUND = new ResourceLocation(BCModules.SILICON.getModId(), "textures/gui/programming_table.png");

    @Override
    public CategoryIdentifier<? extends DisplayProgramming> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("tile.programmingTableBlock.name");
    }

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public int getDisplayHeight() {
        return 72 + 2 + ReiUtils.PADDING * 2;
    }

    @Override
    public int getDisplayWidth(DisplayProgramming display) {
        return 72 + 2 + ReiUtils.PADDING * 2;
    }

    @Override
    public List<Widget> setupDisplay(DisplayProgramming display, Rectangle bounds) {
        List<Widget> ret = Lists.newArrayList();

        ret.add(Widgets.createRecipeBase(bounds));

        Point lu = new Point(bounds.getX() + ReiUtils.PADDING, bounds.getY() + ReiUtils.PADDING);

        // input slot
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 2, lu.getY() + 27, 7, 35, 18, 18));

        // arrow
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 2 + 18 + 5, lu.getY() + 27 + 5, 28, 40, 11, 8));

        // output slot
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 2 + 18 + 5 + 11 + 5, lu.getY() + 27, 7, 89, 18, 18));

        // animation background
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 66, lu.getY() + 0, 163, 35, 6, 72));

        // animation
        ret.add(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, delta) ->
                ReiUtils.drawAnimation(
                        guiGraphics, lu, BACKGROUND,
                        (int) Math.max(10L, display.requiredMicroJoules / MjAPI.MJ / 50L),
                        67, 1,
                        176, 18,
                        4, 70,
                        ReiUtils.StartPosition.BOTTOM
                )
        ));

        // text
        ret.add(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, delta) ->
        {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(lu.getX(), lu.getY(), 0);
            Font font = Minecraft.getInstance().font;
            long mj = display.requiredMicroJoules;
            guiGraphics.drawString(font, MjAPI.formatMj(mj) + " MJ", 10, 30 + 5 + 8 + 10, Color.gray.getRGB(), false);
            poseStack.popPose();
        }));

        // slot content
        ret.add(Widgets.createSlot(new Point(lu.getX() + 2 + 1, lu.getY() + 27 + 1)).markInput().entries(display.getInputEntries().get(0)).disableBackground());
        ret.add(Widgets.createSlot(new Point(lu.getX() + 2 + 18 + 5 + 11 + 5 + 1, lu.getY() + 27 + 1)).markOutput().entries(display.getOutputEntries().get(0)).disableBackground());

        return ret;
    }
}
