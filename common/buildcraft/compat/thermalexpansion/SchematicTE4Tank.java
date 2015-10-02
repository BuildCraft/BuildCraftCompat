package buildcraft.compat.thermalexpansion;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.core.lib.inventory.StackHelper;

public class SchematicTE4Tank extends SchematicTE4Base {
	@Override
	public boolean isItemMatchingRequirement(ItemStack suppliedStack, ItemStack requiredStack) {
		return StackHelper.isMatchingItem(suppliedStack, requiredStack, true, false);
	}

	@Override
	protected void fixDualNBT(NBTTagCompound tileNBT) {
		tileNBT.removeTag("FluidName");
		tileNBT.removeTag("Amount");
		tileNBT.setString("Empty", "");
	}

	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		super.storeRequirements(context, x, y, z);
		for (ItemStack s : storedRequirements) {
			if (s != null) {
				s.setTagCompound(null);
			}
		}
	}
}
