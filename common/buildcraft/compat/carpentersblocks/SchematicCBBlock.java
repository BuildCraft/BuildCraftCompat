package buildcraft.compat.carpentersblocks;

public class SchematicCBBlock extends SchematicCBRotated {
	private static final short[] blockMatrix = {
			0, 5, 6, 3, 4, 1, 2
	};

	@Override
	protected short fixMetadata(short m) {
		return blockMatrix[m];
	}
}
