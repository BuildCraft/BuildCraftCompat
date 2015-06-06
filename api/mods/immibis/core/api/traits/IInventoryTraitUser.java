package mods.immibis.core.api.traits;

import net.minecraft.item.ItemStack;

public interface IInventoryTraitUser {
	/**
	 * Returns the slots accessible through a given side.
	 * @param side The side number (ForgeDirection ordinal).
	 * @return An array of accessible slot indices. Cannot be modified by the caller.
	 */
	public int[] getAccessibleSlots(int side);
	
	/**
	 * Checks whether a given item can be inserted into a given slot from a given side.
	 * {@link #canInsert(int, ItemStack)} must also return true for the item to be inserted.
	 * 
	 * @param slot The slot index.
	 * @param side The side number (ForgeDirection ordinal).
	 * @param stack The item type - ignore stackSize.
	 * @return True if the item type can be inserted into this slot.
	 */
	public boolean canInsert(int slot, int side, ItemStack stack);
	
	/**
	 * Checks whether a given item can be inserted into a given slot.
	 * @param slot The slot index.
	 * @param stack The item type - ignore stackSize.
	 * @return True if the item can be inserted into this slot.
	 */
	public boolean canInsert(int slot, ItemStack stack);
	
	/**
	 * Checks whether a given item can be extracted from a given slot and a given side.
	 * @param slot The slot index.
	 * @param side The side number (ForgeDirection ordinal).
	 * @param stack The item type - ignore stackSize.
	 * @return True if the item can be extracted.
	 */
	public boolean canExtract(int slot, int side, ItemStack stack);
	
	/**
	 * Called only once.
	 */
	public int getInventorySize();
}
