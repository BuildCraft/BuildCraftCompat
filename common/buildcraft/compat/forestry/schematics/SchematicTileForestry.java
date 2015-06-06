package buildcraft.compat.forestry.schematics;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;

public class SchematicTileForestry extends SchematicTile {
	private static final Integer[] shiftMatrix = {0, 1, 5, 4, 2, 3};

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			tileNBT.setInteger("Orientation", shiftMatrix[tileNBT.getInteger("Orientation") % 6]);
		}
	}

	public boolean keepInventory() {
		return false;
	}

	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		if (keepInventory()) {
			super.storeRequirements(context, x, y, z);
			return;
		}
		storedRequirements = new ItemStack[1];
		storedRequirements[0] = new ItemStack(this.block, 1, this.meta);
	}

	public boolean shouldClearNBT() {
		return true;
	}

	public void processNBT(NBTTagCompound source, NBTTagCompound target) {
		target.setTag("id", source.getTag("id"));

		if (source.hasKey("Orientation")) {
			target.setInteger("Orientation", source.getInteger("Orientation"));
		}
	}

	@Override
	public void initializeFromObjectAt (IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		if (tileNBT != null) {
			if (shouldClearNBT()) {
				NBTTagCompound source = tileNBT;
				tileNBT = new NBTTagCompound();
				processNBT(source, tileNBT);
			} else {
				processNBT(tileNBT, tileNBT);
			}
		}
	}
}
