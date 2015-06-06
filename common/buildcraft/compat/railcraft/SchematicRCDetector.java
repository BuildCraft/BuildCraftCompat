package buildcraft.compat.railcraft;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;

public class SchematicRCDetector extends SchematicTile {
	private static final byte[] shiftMatrix = {0, 1, 5, 4, 2, 3};

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			tileNBT.setByte("direction", shiftMatrix[tileNBT.getByte("direction") % 6]);
		}
	}
}
