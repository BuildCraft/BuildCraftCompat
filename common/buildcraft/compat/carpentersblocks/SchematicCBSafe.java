package buildcraft.compat.carpentersblocks;

public class SchematicCBSafe extends SchematicCBRotated {
	private static final short[] rotMatrix = {1, 2, 3, 0};

	@Override
	protected int fixMetadata(int m) {
		return (m & ~0x3) | rotMatrix[m & 0x3];
	}
}
