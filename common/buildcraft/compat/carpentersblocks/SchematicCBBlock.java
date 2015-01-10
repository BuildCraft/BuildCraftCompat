package buildcraft.compat.carpentersblocks;

import buildcraft.api.blueprints.IBuilderContext;

public class SchematicCBBlock extends SchematicCBRotated {
	private static final short[] blockMatrix = {
			0, 5, 6, 3, 4, 1, 2
	};

	@Override
	protected short fixMetadata(short m) {
		return blockMatrix[m];
	}
}
