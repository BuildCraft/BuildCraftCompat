package mods.immibis.core.api.traits;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Classes using this trait must implement IInventoryTraitUser.
 */
public interface IInventoryTrait extends ITrait, ISidedInventory {
	public void readFromNBT(NBTTagCompound tag);
	public void writeToNBT(NBTTagCompound tag);
}
