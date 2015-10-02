package buildcraft.compat.thermalexpansion;

import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.compat.CompatUtils;

public class SchematicTE4Light extends SchematicTE4Base {
	protected static final int[] shiftMatrixLight = {
			8, 9, 5, 4, 2, 3, 6, 7,
			0, 1, 13, 12, 10, 11, 14, 15
	};

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			if (tileNBT.hasKey("Align")) {
				tileNBT.setByte("Align", (byte) (shiftMatrixLight[tileNBT.getByte("Align") & 15] & (tileNBT.getByte("Style") == 4 ? 15 : 7)));
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
			return tag.getByte("Align") == tileNBT.getByte("Align")
					&& tag.getByte("Style") == tileNBT.getByte("Style")
					&& tag.getByte("Mode") == tileNBT.getByte("Mode")
					&& tag.getInteger("Color") == tileNBT.getInteger("Color");
		} else {
			return true;
		}
	}
}
