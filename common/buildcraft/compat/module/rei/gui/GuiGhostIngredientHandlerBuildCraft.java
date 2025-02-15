package buildcraft.compat.module.rei.gui;

import buildcraft.core.list.GuiList;
import buildcraft.lib.gui.GuiBC8;
import buildcraft.lib.gui.slot.SlotPhantom;
import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.drag.DraggableStack;
import me.shedaniel.rei.api.client.gui.drag.DraggableStackVisitor;
import me.shedaniel.rei.api.client.gui.drag.DraggedAcceptorResult;
import me.shedaniel.rei.api.client.gui.drag.DraggingContext;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import net.minecraft.client.gui.screens.Screen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class GuiGhostIngredientHandlerBuildCraft implements DraggableStackVisitor<GuiBC8<?>> {
    @Override
    public <R extends Screen> boolean isHandingScreen(R screen) {
        return screen instanceof GuiBC8<?>;
    }

    @Override
    public Stream<BoundsProvider> getDraggableAcceptingBounds(DraggingContext<GuiBC8<?>> context, DraggableStack stack) {
        EntryStack<?> entryStack = stack.getStack();
        if (entryStack.getType() == VanillaEntryTypes.ITEM) {
            GuiBC8<?> gui = context.getScreen();
            if (gui instanceof GuiList guiList) {
                List<BoundsProvider> retList = Lists.newArrayList();
                for (int lineIndex = 0; lineIndex < guiList.container.lines.length; lineIndex++) {
                    if (guiList.container.lines[lineIndex].isOneStackMode()) {
                        retList.add(BoundsProvider.ofRectangle(new Rectangle(gui.getGuiLeft() + 8 + 0 * 18, gui.getGuiTop() + 32 + lineIndex * 34, 16, 16)));
                    } else {
                        Arrays.stream(guiList.container.slots[lineIndex])
                                .forEach(
                                        slot -> retList.add(
                                                BoundsProvider.ofRectangle(new Rectangle(gui.getGuiLeft() + 8 + slot.slotIndex * 18, gui.getGuiTop() + 32 + slot.lineIndex * 34))
                                        )
                                );
                    }
                }
                return retList.stream();
            } else {
                return gui.container.slots.stream()
                        .filter(slot -> slot instanceof SlotPhantom)
                        .map(slot -> BoundsProvider.ofRectangle(new Rectangle(gui.getGuiLeft() + slot.x, gui.getGuiTop() + slot.y, 16, 16)));
            }
        } else {
            return Stream.<BoundsProvider>builder().build();
        }
    }

    @Override
    public DraggedAcceptorResult acceptDraggedStack(DraggingContext<GuiBC8<?>> context, DraggableStack stack) {
        Point position = context.getCurrentPosition();
        GuiBC8<?> gui = context.getScreen();
        EntryStack<?> entryStack = stack.getStack();
        if (entryStack.getType() == VanillaEntryTypes.ITEM) {
            if (gui instanceof GuiList guiList) {
                Arrays.stream(guiList.container.slots)
                        .flatMap(Arrays::stream)
                        .filter(slot -> slot.slotIndex == 0 || !guiList.container.lines[slot.lineIndex].isOneStackMode())
                        .filter(slot -> new Rectangle(gui.getGuiLeft() + 8 + slot.slotIndex * 18, gui.getGuiTop() + 32 + slot.lineIndex * 34, 16, 16).contains(position))
                        .forEach(slot -> guiList.clientSetStackToServer(slot, entryStack.castValue()));
            } else {
                gui.container.slots.stream()
                        .filter(slot -> slot instanceof SlotPhantom)
                        .filter(slot -> new Rectangle(gui.getGuiLeft() + slot.x, gui.getGuiTop() + slot.y, 16, 16).contains(position))
                        .map(slot -> (SlotPhantom) slot)
                        .forEach(slotPhantom -> gui.container.sendSetPhantomSlot(slotPhantom, entryStack.castValue()));
            }
            return DraggedAcceptorResult.ACCEPTED;
        } else {
            return DraggedAcceptorResult.PASS;
        }
    }
}
