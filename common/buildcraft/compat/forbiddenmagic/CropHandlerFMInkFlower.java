package buildcraft.compat.forbiddenmagic;

import buildcraft.BuildCraftCompat;
import buildcraft.compat.CompatModuleForbiddenMagic;
import buildcraft.core.crops.CropHandlerPlantable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CropHandlerFMInkFlower extends CropHandlerPlantable {
	@Override
	public boolean isMature(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
		return block != null && block == CompatModuleForbiddenMagic.inkFlower;
	}

	@Override
	public boolean harvestCrop(World world, int x, int y, int z, List<ItemStack> list) {
		if (!world.isRemote) {
			Block b = world.getBlock(x, y, z);
			if (b != null && b == CompatModuleForbiddenMagic.inkFlower) {
				return super.harvestCrop(world, x, y, z, list);
			}
		}
		return false;
	}
}
