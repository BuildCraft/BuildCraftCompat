package buildcraft.compat.forestry.schematics;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.compat.lib.SchematicTileStairs;
import buildcraft.core.lib.utils.NBTUtils;

public class SchematicTileStairsForestry extends SchematicTileStairs {
	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		storedRequirements = new ItemStack[1];
		storedRequirements[0] = new ItemStack(Block.getBlockFromName("Forestry:stairs"));
		NBTUtils.getItemData(storedRequirements[0]).setInteger("WoodType", tileNBT.getShort("WT"));
	}
}
