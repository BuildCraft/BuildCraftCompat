package buildcraft.compat;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;

public class SchematicTileDrops extends SchematicTile {
	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		if (block != null) {
			ArrayList<ItemStack> rqs = block.getDrops(context.world(), x, y, z, meta, 0);
			storedRequirements = rqs.toArray(new ItemStack[rqs.size()]);
		}
	}
}
