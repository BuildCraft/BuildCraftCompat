package mods.immibis.core.api.crossmod;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface ICrossModBC {
	/**
	 * Returns the BC wrench interface, or null.
	 */
	public Class<?> getWrenchInterface();
	
	/**
	 * Returns the Pipe class name if te is a TileGenericPipe, otherwise null.
	 */
	public String getPipeClass(TileEntity te);

	/**
	 * Emits an item into a pipe. Returns true if successful.
	 */
	public boolean emitItem(ItemStack stack, TileEntity pipe, TileEntity from);
}
