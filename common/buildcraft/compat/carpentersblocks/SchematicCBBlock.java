package buildcraft.compat.carpentersblocks;

public class SchematicCBBlock extends SchematicCBRotated {
	protected static final short[] blockMatrix = {
			0, 5, 6, 3, 4, 1, 2
	};

	@Override
	protected int fixMetadata(int m) {
		return blockMatrix[m];
	}
}
