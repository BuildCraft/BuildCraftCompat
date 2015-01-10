package buildcraft.compat.carpentersblocks;

public class SchematicCBGate extends SchematicCBRotated {
	@Override
	protected short fixMetadata(short m) {
		short newM = (short) (m & ~0x20);
		if ((m & 0x20) == 0) {
			newM |= 0x20;
		}
		return newM;
	}
}
