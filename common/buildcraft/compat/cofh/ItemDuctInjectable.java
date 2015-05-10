package buildcraft.compat.cofh;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.transport.IItemDuct;
import buildcraft.api.core.EnumColor;
import buildcraft.api.transport.IInjectable;

public class ItemDuctInjectable implements IInjectable {
	public final IItemDuct duct;

	public ItemDuctInjectable(IItemDuct duct) {
		this.duct = duct;
	}

	@Override
	public boolean canInjectItems(ForgeDirection forgeDirection) {
		return true;
	}

	@Override
	public int injectItem(ItemStack itemStack, boolean doAdd, ForgeDirection from, EnumColor enumColor) {
		if (!doAdd) {
			return itemStack.stackSize; // We don't ACTUALLY know, though. Eh.
		}

		ItemStack outputStack = duct.insertItem(from, itemStack);
		if (outputStack == null) {
			return itemStack.stackSize;
		} else {
			return itemStack.stackSize - outputStack.stackSize;
		}
	}
}
