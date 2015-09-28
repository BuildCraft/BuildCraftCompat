package buildcraft.compat.railcraft;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.compat.lib.SchematicTileWhitelist;

public class SchematicRCDirectional extends SchematicTileWhitelist {
	private static final byte[] shiftMatrix = {0, 1, 5, 4, 2, 3};

	public SchematicRCDirectional(String[] whitelist, boolean ignoreDrops) {
		super(whitelist, ignoreDrops);
	}

	public SchematicRCDirectional() {
		super();
	}

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			if (tileNBT.hasKey("direction")) {
				tileNBT.setByte("direction", shiftMatrix[tileNBT.getByte("direction") % 6]);
			} else if (tileNBT.hasKey("facing")) {
				tileNBT.setByte("facing", shiftMatrix[tileNBT.getByte("facing") % 6]);
			}
		}
	}

	@Override
	public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		if (tileNBT != null) {
			tileNBT.removeTag("owner");
			tileNBT.removeTag("ownerId");
		}
	}
}
