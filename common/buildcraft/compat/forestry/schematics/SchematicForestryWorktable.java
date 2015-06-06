package buildcraft.compat.forestry.schematics;

import net.minecraft.nbt.NBTTagCompound;

public class SchematicForestryWorktable extends SchematicTileForestry {
	@Override
	public boolean shouldClearNBT() {
		return false;
	}

	@Override
	public boolean keepInventory() {
		return true;
	}

	@Override
	public void processNBT(NBTTagCompound tileNBT, NBTTagCompound target) {

	}
}
