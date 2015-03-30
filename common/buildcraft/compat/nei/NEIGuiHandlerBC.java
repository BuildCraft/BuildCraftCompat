package buildcraft.compat.nei;

import codechicken.nei.api.INEIGuiAdapter;

import net.minecraft.client.gui.inventory.GuiContainer;
import buildcraft.core.lib.gui.CompatGuiUtils;
import buildcraft.core.lib.gui.GuiBuildCraft;

public class NEIGuiHandlerBC extends INEIGuiAdapter
{
	@Override
	public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h) {
		return gui instanceof GuiBuildCraft && CompatGuiUtils.ledgerOverlaps((GuiBuildCraft) gui, x, y, w, h);
	}
}
