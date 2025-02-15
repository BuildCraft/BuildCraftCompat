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

public enum CategoryAssemblyTable implements DisplayCategory<DisplayAssembly> {
    INSTANCE;

    public static final CategoryIdentifier<DisplayAssembly> ID = CategoryIdentifier.of(new ResourceLocation(BCModules.SILICON.getModId(), "category_assembly"));
    public static final EntryStack<ItemStack> ICON = EntryStacks.of(new ItemStack(BCSiliconBlocks.assemblyTable.get()));
    public static final ResourceLocation BACKGROUND = new ResourceLocation(BCModules.SILICON.getModId(), "textures/gui/assembly_table.png");

    @Override
    public CategoryIdentifier<? extends DisplayAssembly> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("tile.assemblyTableBlock.name");
    }

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public int getDisplayHeight() {
        return 86 + 2 + ReiUtils.PADDING * 2;
    }

    @Override
    public int getDisplayWidth(DisplayAssembly display) {
        return 166 + 2 + ReiUtils.PADDING * 2;
    }

    @Override
    public List<Widget> setupDisplay(DisplayAssembly display, Rectangle bounds) {
        List<Widget> ret = Lists.newArrayList();

        ret.add(Widgets.createRecipeBase(bounds));

        Point lu = new Point(bounds.getX() + ReiUtils.PADDING, bounds.getY() + ReiUtils.PADDING);

        // background
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 0, lu.getY() + 10, 5, 34, 166, 76));

        // animation
        ret.add(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, delta) ->
                ReiUtils.drawAnimation(
                        guiGraphics, lu, BACKGROUND,
                        (int) Math.max(10L, display.requiredMicroJoules / MjAPI.MJ / 50L),
                        81, 12,
                        176, 48,
                        4, 71,
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
            guiGraphics.drawString(font, MjAPI.formatMj(mj) + " MJ", 4, 0, Color.gray.getRGB(), false);
            poseStack.popPose();
        }));

        // slot content
        for (int i = 0; i < display.getInputEntries().size(); ++i) {
            ret.add(Widgets.createSlot(new Point(lu.getX() + 3 + i % 3 * 18, lu.getY() + 12 + i / 3 * 18)).markInput().entries(display.getInputEntries().get(i)).disableBackground());
        }
        ret.add(Widgets.createSlot(new Point(lu.getX() + 111, lu.getY() + 12)).markOutput().entries(display.getOutputEntries().get(0)).disableBackground());

        return ret;
    }
}
