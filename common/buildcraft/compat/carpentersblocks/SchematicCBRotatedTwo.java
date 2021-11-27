package buildcraft.compat.carpentersblocks;

public class SchematicCBRotatedTwo extends SchematicCBRotated {
	@Override
	protected int fixMetadata(int m) {
		return (m ^ 1);
	}
}
