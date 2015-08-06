package buildcraft.compat.immersiveengineering;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;

public class SchematicIEBase extends SchematicTile {
	protected static final int[] shiftMatrix = {0, 1, 5, 4, 2, 3, 6, 7};

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			if (tileNBT.hasKey("facing")) {
				tileNBT.setByte("facing", (byte) shiftMatrix[tileNBT.getByte("facing") & 7]);
			}
		}
	}

	@Override
	public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		if (tileNBT != null) {
			// Connector
			tileNBT.removeTag("limitType");
			tileNBT.removeTag("prevPos");

			// Breaker Switch
			tileNBT.removeTag("wires");
			tileNBT.removeTag("sideAttached");
		}
	}
}
