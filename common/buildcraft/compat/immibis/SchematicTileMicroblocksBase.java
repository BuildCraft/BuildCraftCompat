package buildcraft.compat.immibis;

import net.minecraft.item.ItemStack;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;

public class SchematicTileMicroblocksBase extends SchematicTile {
	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		storedRequirements = new ItemStack[0];
	}
}
