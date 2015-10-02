package buildcraft.compat.thermalexpansion;

import net.minecraft.nbt.NBTTagCompound;

public class SchematicTE4Cache extends SchematicTE4Base {
	@Override
	protected void fixDualNBT(NBTTagCompound tileNBT) {
		tileNBT.removeTag("Item");
	}
}
