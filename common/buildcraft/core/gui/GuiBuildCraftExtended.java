package buildcraft.core.gui;

import java.util.List;

import codechicken.nei.INEIGuiHandler;
import codechicken.nei.TaggedInventoryArea;
import codechicken.nei.VisiblityData;
import cpw.mods.fml.common.Optional;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Optional.InterfaceList({
	@Optional.Interface(iface = "codechicken.nei.INEIGuiHandler", modid = "NotEnoughItems")
})
public class GuiBuildCraftExtended extends GuiBuildCraft implements INEIGuiHandler {
	public GuiBuildCraftExtended(BuildCraftContainer container,
			IInventory inventory, ResourceLocation texture) {
		super(container, inventory, texture);
	}

	private boolean ledgerOverlaps(int x, int y, int width, int height) {
		return this.ledgerManager.getAtPosition(x + width, y + height) != null
				|| this.ledgerManager.getAtPosition(x + width, y) != null
				|| this.ledgerManager.getAtPosition(x, y + height) != null
				|| this.ledgerManager.getAtPosition(x, y) != null;
	}

	/* INEIGUIHANDLER */
	@Override
	@Optional.Method(modid = "NotEnoughItems")
	public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData currentVisibility) {
		return null;
	}
	@Override
	@Optional.Method(modid = "NotEnoughItems")
	public Iterable<Integer> getItemSpawnSlots(GuiContainer gui, ItemStack item) {
		return null;
	}
	@Override
	@Optional.Method(modid = "NotEnoughItems")
	public List<TaggedInventoryArea> getInventoryAreas(GuiContainer gui) {
		return null;
	}
	@Override
	@Optional.Method(modid = "NotEnoughItems")
	public boolean handleDragNDrop(GuiContainer gui, int mousex, int mousey, ItemStack draggedStack, int button) {
		return false;
	}
	@Override
	@Optional.Method(modid = "NotEnoughItems")
	public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h) {
		return gui instanceof GuiBuildCraftExtended && ((GuiBuildCraftExtended) gui).ledgerOverlaps(x, y, w, h);
	}
}
