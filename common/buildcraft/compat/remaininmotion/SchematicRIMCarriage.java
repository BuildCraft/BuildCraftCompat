package buildcraft.compat.remaininmotion;

import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;
import buildcraft.compat.CompatUtils;

public class SchematicRIMCarriage extends SchematicTile {
	private static final byte[] shiftMatrix = {0, 1, 5, 4, 2, 3, 6, 7};

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null && tileNBT.hasKey("SideClosed0")) {
			byte[] sidesClosed = new byte[6];
			for (int i = 0; i < 6; i++) {
				sidesClosed[i] = tileNBT.getByte("SideClosed" + i);
			}
			for (int i = 0; i < 6; i++) {
				tileNBT.setByte("SideClosed" + shiftMatrix[i], sidesClosed[i]);
			}
		}
	}

	@Override
	public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
		if (this.block != context.world().getBlock(x, y, z) || this.meta != context.world().getBlockMetadata(x, y, z)) {
			return false;
		}

		NBTTagCompound tag = CompatUtils.getTileNBT(context.world(), x, y, z);
		if (tileNBT != null) {
			for (int i = 0; i < 6; i++) {
				if (tileNBT.getByte("SideClosed" + i) != tag.getByte("SideClosed" + i)) {
					return false;
				}
			}

			return true;
		} else {
			return true;
		}
	}
}
