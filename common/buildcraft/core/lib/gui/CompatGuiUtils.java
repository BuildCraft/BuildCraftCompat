package buildcraft.core.lib.gui;

public class CompatGuiUtils {
	public static boolean ledgerOverlaps(GuiBuildCraft gui, int x, int y, int width, int height) {
		return gui.ledgerManager.getAtPosition(x + width, y + height) != null
				|| gui.ledgerManager.getAtPosition(x + width, y) != null
				|| gui.ledgerManager.getAtPosition(x, y + height) != null
				|| gui.ledgerManager.getAtPosition(x, y) != null;
	}
}
