package buildcraft.compat.lib;

import buildcraft.api.blueprints.IBuilderContext;

public class SchematicTileStairs extends SchematicTileDropsOnly {
	private static final int[] shiftMatrix = {2, 3, 1, 0};

	public SchematicTileStairs() {
	}

	public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
		return this.block == context.world().getBlock(x, y, z);
	}

	public void rotateLeft(IBuilderContext context) {
		this.meta = this.shiftMatrix[this.meta & 3];
	}

	public BuildingStage getBuildStage() {
		return BuildingStage.STANDALONE;
	}
}
