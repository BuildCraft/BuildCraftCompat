package buildcraft.compat.binnie;

import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.compat.CompatUtils;
import buildcraft.compat.lib.SchematicTileDropsOnly;

public class SchematicTileCarpentry extends SchematicTileDropsOnly {
	private static final int[] shiftMatrix = {0, 1, 5, 4, 2, 3};

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null && tileNBT.hasKey("meta")) {
			int realMeta = tileNBT.getInteger("meta");
			int rot = (realMeta >> 26) & 0x3;
			int axis = (realMeta >> 28) & 0x7;
			if (axis < 2) {
				rot = (rot + 1) & 3;
			} else {
				axis = shiftMatrix[axis];
			}
			realMeta = realMeta & 0x3FFFFFF | (rot << 26) | (axis << 28);
			tileNBT.setInteger("meta", realMeta);
		}
	}

	@Override
	public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
		if (super.isAlreadyBuilt(context, x, y, z)) {
			NBTTagCompound targetNBT = CompatUtils.getTileNBT(context.world(), x, y, z);
			return targetNBT.getInteger("meta") == tileNBT.getInteger("meta");
		} else {
			return false;
		}
	}
}
