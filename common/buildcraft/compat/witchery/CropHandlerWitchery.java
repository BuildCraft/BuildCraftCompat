package buildcraft.compat.witchery;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import buildcraft.api.crops.CropManager;
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
	public boolean plantCrop(World world, EntityPlayer player, ItemStack seed, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean isMature(IBlockAccess blockAccess, Block block, int metadata, int x, int y, int z) {
		if (block.getClass().getSimpleName().equals("BlockWitchCrop")) {
			final String name = block.getUnlocalizedName().replace("tile.witchery:", "");

			if (name.equals("garlicplant"))
				return metadata == 5;
			else if (name.equals("wolfsbane"))
				return metadata == 7;
			else
				return metadata == 4;
		}

		return false;
	}

	@Override
	public boolean harvestCrop(World world, int x, int y, int z, List<ItemStack> drops) {
		return CropManager.getDefaultHandler().harvestCrop(world, x, y, z, drops);
	}
}
