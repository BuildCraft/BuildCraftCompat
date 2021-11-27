package buildcraft.compat.carpentersblocks;

public class SchematicCBTorch extends SchematicCBRotated {
	@Override
	protected int fixMetadata(int m) {
		return ((m & (~0x7)) | shiftMatrix[m & 0x7]);
	}
}
