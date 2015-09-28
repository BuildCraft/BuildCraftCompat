package buildcraft.compat.lib;

import java.util.LinkedList;

import net.minecraft.item.ItemStack;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;

public class SchematicTileIgnoreNBT extends SchematicTile {
	@Override
	public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {

	}

	@Override
	public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
		setBlockInWorld(context, x, y, z);
	}
}
