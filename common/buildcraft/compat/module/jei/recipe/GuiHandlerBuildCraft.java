package buildcraft.compat.module.jei.recipe;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.util.math.MathHelper;

import buildcraft.lib.gui.GuiBC8;
import buildcraft.lib.gui.IGuiElement;
import buildcraft.lib.gui.pos.GuiRectangle;

import mezz.jei.api.gui.IAdvancedGuiHandler;

public class GuiHandlerBuildCraft implements IAdvancedGuiHandler<GuiBC8> {

    @Override
    public Class<GuiBC8> getGuiContainerClass() {
        return GuiBC8.class;
    }

    @Override
    @Nullable
    public List<Rectangle> getGuiExtraAreas(GuiBC8 guiDirty) {
        GuiBC8<?> gui = guiDirty;
        // Get the rectangles of everything that is *outside* the main gui area
        List<Rectangle> list = new ArrayList<>();
        for (IGuiElement element : gui.shownElements) {
            // Ignore children: all ledger style elements are top level
            GuiRectangle rect = element.asImmutable();
//            if (!gui.rootElement.contains(rect)) {
                // Round down x and y
                int x = (int) rect.x;
                int y = (int) rect.y;
                // Round up width and height
                int endX = MathHelper.ceil(rect.getEndX());
                int endY = MathHelper.ceil(rect.getEndY());
                int width = endX - x;
                int height = endY - y;
                list.add(new Rectangle(x, y, width, height));
//            }
        }
        if (list.isEmpty()) {
            // Cheapen JEI checks a tiny bit. Possibly.
            return null;
        }
        return list;
    }
}
