package buildcraft.compat.module.rei.factory;

import buildcraft.api.BCModules;
import buildcraft.compat.module.rei.ReiUtils;
import buildcraft.factory.BCFactoryBlocks;
import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public enum CategoryHeatable implements DisplayCategory<DisplayHeatable> {
    INSTANCE;

    public static final CategoryIdentifier<DisplayHeatable> ID = CategoryIdentifier.of(new ResourceLocation(BCModules.FACTORY.getModId(), "category_heatable"));
    public static final EntryStack<ItemStack> ICON = EntryStacks.of(new ItemStack(BCFactoryBlocks.heatExchange.get()));
    public static final ResourceLocation BACKGROUND = new ResourceLocation(BCModules.FACTORY.getModId(), "textures/gui/energy_heater.png");

    @Override
    public CategoryIdentifier<? extends DisplayHeatable> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("buildcraft.rei.title.heatable_fluids");
    }

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public int getDisplayHeight() {
        return 17 + 2 + ReiUtils.PADDING * 2;
    }

    @Override
    public int getDisplayWidth(DisplayHeatable display) {
        return 90 + 2 + ReiUtils.PADDING * 2;
    }

    @Override
    public List<Widget> setupDisplay(DisplayHeatable display, Rectangle bounds) {
        List<Widget> ret = Lists.newArrayList();

        ret.add(Widgets.createRecipeBase(bounds));

        Point lu = new Point(bounds.getX() + ReiUtils.PADDING, bounds.getY() + ReiUtils.PADDING);

        // background
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 18, lu.getY() + 0, 176, 19, 54, 19));

        // slot
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 0, lu.getY() + 1, 7, 22, 18, 18));
        ret.add(Widgets.createTexturedWidget(BACKGROUND, lu.getX() + 72, lu.getY() + 1, 7, 22, 18, 18));

        // animation
        ret.add(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, delta) ->
                ReiUtils.drawAnimation(
                        guiGraphics, lu, BACKGROUND,
                        40,
                        18, 0,
                        176, 152,
                        54, 19,
                        ReiUtils.StartPosition.LEFT
                )
        ));

        // slot content
        ret.add(Widgets.createSlot(new Point(lu.getX() + 1, lu.getY() + 2)).markInput().entries(display.getInputEntries().get(0)).disableBackground());
        ret.add(Widgets.createSlot(new Point(lu.getX() + 73, lu.getY() + 2)).markOutput().entries(display.getOutputEntries().get(0)).disableBackground());

        return ret;
    }
}
