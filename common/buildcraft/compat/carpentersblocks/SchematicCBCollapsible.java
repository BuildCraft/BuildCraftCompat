package buildcraft.compat.carpentersblocks;

public class SchematicCBCollapsible extends SchematicCBRotated {
	@Override
	protected int fixMetadata(int m) {
		return (((m << 5) & 0x7C0000) | ((m << 10) & 0x03E000) | ((m >> 5) & 0x0000F8) | ((m >> 10) & 0x001F00))
				| (m & 7);
	}
}
