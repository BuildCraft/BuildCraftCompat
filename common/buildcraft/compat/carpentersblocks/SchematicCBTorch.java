package buildcraft.compat.carpentersblocks;

public class SchematicCBTorch extends SchematicCBRotated {
	@Override
	protected short fixMetadata(short m) {
		return (short) ((m & (~0x7)) | shiftMatrix[m & 0x7]);
	}
}
