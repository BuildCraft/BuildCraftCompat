package buildcraft.compat.module.rei.factory;

import buildcraft.api.BCModules;
import buildcraft.api.mj.MjAPI;
import buildcraft.compat.module.rei.ReiUtils;
import buildcraft.factory.BCFactoryBlocks;
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

public enum CategoryDistiller implements DisplayCategory<DisplayDistillation> {
    INSTANCE;

    public static final CategoryIdentifier<DisplayDistillation> ID = CategoryIdentifier.of(new ResourceLocation(BCModules.FACTORY.getModId(), "category_distiller"));
    public static final EntryStack<ItemStack> ICON = EntryStacks.of(new ItemStack(BCFactoryBlocks.distiller.get()));
    public static final ResourceLocation BACKGROUND = new ResourceLocation(BCModules.FACTORY.getModId(), "textures/gui/distiller.png");

    @Override
    public CategoryIdentifier<? extends DisplayDistillation> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("buildcraft.rei.title.distillable_fluids");
    }

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public int getDisplayHeight() {
        return 65 + 2 + ReiUtils.PADDING * 2;
    }

    @Override
    public int getDisplayWidth(DisplayDistillation display) {
        return 90 + 2 + ReiUtils.PADDING * 2;
    }

    @Override
    public List<Widget> setupDisplay(DisplayDistillation display, Rectangle bounds) {
        List<Widget> ret = Lists.newArrayList();

        ret.add(Widgets.createRecipeBase(bounds));

        Point lu = new Point(bounds.getX() + ReiUtils.PADDING, bounds.getY() + ReiUtils.PADDING);

        // background
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 20, lu.getY() + 4, 61, 12, 36, 57));

        // slot
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 0, lu.getY() + 25, 7, 34, 18, 18));
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 56, lu.getY() + 0, 7, 34, 18, 18));
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 56, lu.getY() + 45, 7, 34, 18, 18));

        // animation
        ret.add(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, delta) ->
                ReiUtils.drawAnimation(
                        guiGraphics, lu, BACKGROUND,
                        40,
                        20, 4,
                        212, 0,
                        36, 57,
                        ReiUtils.StartPosition.LEFT
                )
        ));

        // text
        ret.add(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, delta) ->
        {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(lu.getX(), lu.getY(), 0);
            Font font = Minecraft.getInstance().font;
            guiGraphics.drawString(font, MjAPI.formatMj(display.powerRequired) + " MJ", 58, 28, Color.CYAN.getRGB());
            poseStack.popPose();
        }));

        // slot content
        ret.add(Widgets.createSlot(new Point(lu.getX() + 1, lu.getY() + 26)).markInput().entries(display.getInputEntries().get(0)).disableBackground());
        ret.add(Widgets.createSlot(new Point(lu.getX() + 57, lu.getY() + 1)).markOutput().entries(display.getOutputEntries().get(0)).disableBackground());
        ret.add(Widgets.createSlot(new Point(lu.getX() + 57, lu.getY() + 46)).markOutput().entries(display.getOutputEntries().get(1)).disableBackground());

        return ret;
    }
}
