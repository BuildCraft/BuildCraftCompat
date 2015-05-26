package buildcraft.compat.witchery;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import buildcraft.api.crops.ICropHandler;

public class CropHandlerWitchery implements ICropHandler {
	@Override
	public boolean isSeed(ItemStack stack) {
		return false;
	}

	@Override
	public boolean canSustainPlant(World world, ItemStack seed, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean isMature(IBlockAccess blockAccess, Block block, int metadata, int x, int y, int z) {
		if(block.getClass().getSimpleName().equals("BlockWitchCrop")) {
			final String name = block.getUnlocalizedName().replace("tile.witchery:", "");

			if (name.equals("garlicplant"))
				return metadata == 5;
			else if(name.equals("wolfsbane"))
				return metadata == 7;
			else
				return metadata == 4;
		}

		return false;
	}

	@Override
	public boolean harvestCrop(World world, int x, int y, int z, List<ItemStack> drops) {
		Block block = world.getBlock(x, y, z);
		drops.addAll(block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0));
		world.setBlockToAir(x, y, z);
		return true;
	}
}
