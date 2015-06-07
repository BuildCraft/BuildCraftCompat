package buildcraft.compat.carpentersblocks;

import net.minecraft.nbt.NBTTagList;
import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.compat.lib.SchematicTileDrops;

public class SchematicCBRotated extends SchematicTileDrops {
	private static final int[] shiftMatrix = {0, 1, 5, 4, 2, 3, 6};

	protected short fixMetadata(short _m) {
		short m = _m;
		switch (m & 3) {
			case 0: {
				m &= ~3;
				m |= 3;
				break;
			}
			case 1: {
				m &= ~3;
				m |= 2;
				break;
			}
			case 2: {
				m &= ~3;
				// m |= 0;
				break;
			}
			case 3: {
				m &= ~3;
				m |= 1;
				break;
			}
		}
		return m;
	}

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			// Fix metadata
			short m = tileNBT.getShort("cbMetadata");
			m = fixMetadata(m);
			tileNBT.setShort("cbMetadata", m);
			// Fix attributes
			NBTTagList attrs = tileNBT.getTagList("cbAttrList", 10);
			for (int a = 0; a < attrs.tagCount(); a++) {
				int at = attrs.getCompoundTagAt(a).getByte("cbAttribute");
				int a2 = at - (at % 7) + shiftMatrix[at % 7];
				attrs.getCompoundTagAt(a).setByte("cbAttribute", (byte) (at < 21 ? a2 : at));
			}
			// Fix chisel designs
			String[] designs = new String[7];
			for (int a = 0; a < 7; a++) {
				designs[a] = tileNBT.getString("cbChiselDesign_" + a);
			}
			for (int a = 0; a < 7; a++) {
				tileNBT.setString("cbChiselDesign_" + shiftMatrix[a], designs[a]);
			}
		}
	}
}
