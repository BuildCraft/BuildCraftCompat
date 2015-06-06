package mods.immibis.core.api.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public abstract class NullInventory implements IInventory {
	@Override public void setInventorySlotContents(int i, ItemStack itemstack) {}
	@Override public void openInventory() {}
	@Override public boolean isItemValidForSlot(int i, ItemStack itemstack) {return false;}
	@Override public ItemStack getStackInSlotOnClosing(int i) {return null;}
	@Override public ItemStack getStackInSlot(int i) {return null;}
	@Override public int getSizeInventory() {return 0;}
	@Override public int getInventoryStackLimit() {return 0;}
	@Override public String getInventoryName() {return null;}
	@Override public ItemStack decrStackSize(int i, int j) {return null;}
	@Override public void closeInventory() {}
	
	@Override public abstract boolean isUseableByPlayer(EntityPlayer entityplayer);
	@Override public boolean hasCustomInventoryName() { return false; }
	@Override public void markDirty() { }
}
