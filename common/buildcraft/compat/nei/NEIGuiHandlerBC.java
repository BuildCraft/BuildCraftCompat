package buildcraft.compat.nei;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import buildcraft.compat.forestry.pipes.gui.GuiPropolisPipe;
import buildcraft.core.lib.gui.CompatGuiUtils;
import buildcraft.core.lib.gui.GuiBuildCraft;
import codechicken.nei.VisiblityData;
import codechicken.nei.api.INEIGuiHandler;
import codechicken.nei.api.TaggedInventoryArea;

public class NEIGuiHandlerBC implements INEIGuiHandler {

    @Override
    public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData currentVisibility) {
        return null;
    }

    @Override
    public Iterable<Integer> getItemSpawnSlots(GuiContainer gui, ItemStack item) {
        return new ArrayList<Integer>();
    }

    @Override
    public List<TaggedInventoryArea> getInventoryAreas(GuiContainer gui) {
        return null;
    }

    @Override
    public boolean handleDragNDrop(GuiContainer gui, int mousex, int mousey, ItemStack draggedStack, int button) {
        if (gui instanceof GuiPropolisPipe)
            return ((GuiPropolisPipe) gui).handleDragNDrop(mousex, mousey, draggedStack, button);
        else {
            return false;
        }
    }

    @Override
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h) {
        return gui instanceof GuiBuildCraft && CompatGuiUtils.ledgerOverlaps((GuiBuildCraft) gui, x, y, w, h);
    }
}
