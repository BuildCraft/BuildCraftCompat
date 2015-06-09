package buildcraft.compat.carpentersblocks;

public class SchematicCBCollapsible extends SchematicCBRotated {
	@Override
	protected short fixMetadata(short m) {
		int data = (int) m & 0xFFFF;
		return (short) (((data << 4) & 0xF000) | ((data << 8) & 0x0F00) | ((data >> 4) & 0x000F) | ((data >> 8) & 0x00F0));
	}
}
