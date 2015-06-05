package buildcraft.compat.forestry.schematics;

import net.minecraft.nbt.NBTTagCompound;

public class SchematicForestryFarmBlock extends SchematicTileForestry {
	@Override
	public boolean shouldClearNBT() {
		return true;
	}

	@Override
	public void processNBT(NBTTagCompound tileNBT, NBTTagCompound target) {
		super.processNBT(tileNBT, target);
		target.setTag("SchemataOrdinal", tileNBT.getTag("SchemataOrdinal"));
		target.setTag("FarmBlock", tileNBT.getTag("FarmBlock"));
	}
}
