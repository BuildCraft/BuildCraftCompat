package buildcraft.compat.carpentersblocks;

public class SchematicCBRotatedTwo extends SchematicCBRotated {
	@Override
	protected short fixMetadata(short m) {
		return (short) (m ^ 1);
	}
}
