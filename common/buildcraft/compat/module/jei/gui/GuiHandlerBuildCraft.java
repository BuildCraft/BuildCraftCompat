package buildcraft.compat.module.jei.gui;

import buildcraft.lib.gui.GuiBC8;
import buildcraft.lib.gui.IGuiElement;
import buildcraft.lib.gui.pos.GuiRectangle;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class GuiHandlerBuildCraft implements IGuiContainerHandler<GuiBC8<?>> {
    @Nonnull
    @Override
    public List<Rectangle2d> getGuiExtraAreas(GuiBC8<?> guiDirty) {
        List<Rectangle2d> list = new ArrayList<>();

        for (IGuiElement element : guiDirty.mainGui.shownElements) {
            GuiRectangle rect = element.asImmutable();
            int x = (int) rect.x;
            int y = (int) rect.y;
            int endX = MathHelper.ceil(rect.getEndX());
            int endY = MathHelper.ceil(rect.getEndY());
            int width = endX - x;
            int height = endY - y;
            list.add(new Rectangle2d(x, y, width, height));
        }

        return list;
    }


//    @Nullable
//    @Override
//    public Object getIngredientUnderMouse(GuiBC8<?> screen, double mouseX, double mouseY) {
//        return screen.getIngredientUnderMouse(mouseX, mouseY);
//    }

//    @NotNull
//    @Override
//    public Collection<IGuiClickableArea> getGuiClickableAreas(GuiBC8<?> screen, double mouseX, double mouseY) {
//        return Collections.emptyList();
//    }
}
