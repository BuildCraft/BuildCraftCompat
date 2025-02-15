package buildcraft.compat.module.jei.gui;

import buildcraft.core.list.ContainerList;
import buildcraft.core.list.GuiList;
import buildcraft.lib.gui.GuiBC8;
import buildcraft.lib.gui.slot.SlotPhantom;
import com.google.common.collect.Lists;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class GuiGhostIngredientHandlerBuildCraft implements IGhostIngredientHandler<GuiBC8> {
    @Override
    public <I> List<Target<I>> getTargetsTyped(GuiBC8 gui, ITypedIngredient<I> ingredient, boolean doStart) {
        if (ingredient.getIngredient() instanceof ItemStack) {
            if (gui instanceof GuiList guiList) {
                List<Target<I>> ret = Lists.newArrayList();
                for (int lineIndex = 0; lineIndex < guiList.container.lines.length; lineIndex++) {
                    if (guiList.container.lines[lineIndex].isOneStackMode()) {
                        ContainerList.WidgetListSlot slot = guiList.container.slots[lineIndex][0];
                        ret.add((Target<I>) new SlotPhantomTarget(gui, 8 + 0 * 18, 32 + lineIndex * 34, stack -> guiList.clientSetStackToServer(slot, stack)));
                    } else {
                        Arrays.stream(guiList.container.slots[lineIndex])
                                .forEach(
                                        slot -> ret.add(
                                                (Target<I>) new SlotPhantomTarget(gui, 8 + slot.slotIndex * 18, 32 + slot.lineIndex * 34, stack -> guiList.clientSetStackToServer(slot, stack))
                                        )
                                );
                    }
                }
                return ret;
            } else {
                return gui.container.slots.stream()
                        .filter(s -> s instanceof SlotPhantom)
                        .map(s -> (SlotPhantom) s)
                        .map(slot -> (Target<I>) new SlotPhantomTarget(gui, slot.x, slot.y, stack -> gui.container.sendSetPhantomSlot(slot, stack)))
                        .toList();
            }
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public void onComplete() {
    }

    private static class SlotPhantomTarget implements Target<ItemStack> {
        private final Rect2i area;
        private final Consumer<ItemStack> stackConsumer;

        private SlotPhantomTarget(GuiBC8<?> gui, int slotX, int slotY, Consumer<ItemStack> stackConsumer) {
            this.area = new Rect2i(gui.getGuiLeft() + slotX, gui.getGuiTop() + slotY, 16, 16);
            this.stackConsumer = stackConsumer;
        }

        @Override
        public Rect2i getArea() {
            return area;
        }

        @Override
        public void accept(ItemStack stack) {
            stackConsumer.accept(stack);
        }
    }
}
