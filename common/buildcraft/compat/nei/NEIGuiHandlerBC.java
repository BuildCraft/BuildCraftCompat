package buildcraft.compat.nei;

import codechicken.nei.VisiblityData;
import codechicken.nei.api.INEIGuiHandler;
import codechicken.nei.api.TaggedInventoryArea;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import buildcraft.core.lib.gui.CompatGuiUtils;
import buildcraft.core.lib.gui.GuiBuildCraft;

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
		return false;
	}
	@Override
	public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h) {
		return gui instanceof GuiBuildCraft && CompatGuiUtils.ledgerOverlaps((GuiBuildCraft) gui, x, y, w, h);
	}
}
