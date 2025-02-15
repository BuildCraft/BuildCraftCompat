package buildcraft.compat.module.rei.gui;

import buildcraft.lib.gui.GuiBC8;
import buildcraft.lib.gui.IGuiElement;
import buildcraft.lib.gui.pos.GuiRectangle;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZonesProvider;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GuiHandlerBuildCraft implements ExclusionZonesProvider<GuiBC8<?>> {
    @Override
    public Collection<Rectangle> provide(GuiBC8<?> guiDirty) {
        List<Rectangle> list = new ArrayList<>();

        for (IGuiElement element : guiDirty.mainGui.shownElements) {
            GuiRectangle rect = element.asImmutable();
            int x = (int) rect.x;
            int y = (int) rect.y;
            int endX = Mth.ceil(rect.getEndX());
            int endY = Mth.ceil(rect.getEndY());
            int width = endX - x;
            int height = endY - y;
            list.add(new Rectangle(x, y, width, height));
        }

        return list;
    }
}
