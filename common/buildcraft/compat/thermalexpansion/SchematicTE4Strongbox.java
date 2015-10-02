package buildcraft.compat.thermalexpansion;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;

public class SchematicTE4Strongbox extends SchematicTE4Base {
	@Override
	protected void fixDualNBT(NBTTagCompound tileNBT) {
		tileNBT.removeTag("Inventory");
	}

	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		if (block != null) {
			ArrayList<ItemStack> rqs = block.getDrops(context.world(), x, y, z, meta, 0);
			storedRequirements = rqs.toArray(new ItemStack[rqs.size()]);
		}
	}
}
