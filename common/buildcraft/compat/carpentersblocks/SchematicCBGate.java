package buildcraft.compat.carpentersblocks;

public class SchematicCBGate extends SchematicCBRotated {
	@Override
	protected int fixMetadata(int m) {
		int newM = m & ~0x20;
		if ((m & 0x20) == 0) {
			newM |= 0x20;
		}
		return newM;
	}
}
