package buildcraft.compat.redlogic;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;

public class SchematicRLGate extends SchematicTile {
	private static final byte[] shiftMatrix = {0, 1, 5, 4, 2, 3, 6};

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			if (tileNBT.hasKey("front")) {
				tileNBT.setByte("front", shiftMatrix[tileNBT.getByte("front")]);
			}
			if (tileNBT.hasKey("side")) {
				tileNBT.setByte("side", shiftMatrix[tileNBT.getByte("side")]);
			}
		}
	}

	@Override
	public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		if (tileNBT != null) {
			tileNBT.removeTag("inputs");
			tileNBT.removeTag("outputs");
			tileNBT.removeTag("prevOutputs");
			tileNBT.removeTag("updatePending");
			tileNBT.removeTag("renderState");
			tileNBT.removeTag("prevRenderState");
		}
	}

	@Override
	public BuildingStage getBuildStage() {
		return BuildingStage.SUPPORTED;
	}
}
