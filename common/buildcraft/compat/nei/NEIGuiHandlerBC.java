package buildcraft.compat.nei;

import codechicken.nei.api.INEIGuiAdapter;

import net.minecraft.client.gui.inventory.GuiContainer;
import buildcraft.core.gui.GuiBuildCraft;
import buildcraft.core.gui.CompatGuiUtils;

public class NEIGuiHandlerBC extends INEIGuiAdapter
{
	@Override
	public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h) {
		return gui instanceof GuiBuildCraft && CompatGuiUtils.ledgerOverlaps((GuiBuildCraft) gui, x, y, w, h);
	}
}
