package buildcraft.compat.pamharvestcraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import buildcraft.api.core.BuildCraftAPI;
import buildcraft.core.crops.CropHandlerPlantable;

public class CropHandlerHarvestCraft extends CropHandlerPlantable {
	private static List<BlockCrops> pamCrops;

	public CropHandlerHarvestCraft() {
		if (pamCrops == null) {
			pamCrops = new ArrayList<BlockCrops>();
			for (String s : (Collection<String>) Block.blockRegistry.getKeys()) {
				Block b = Block.getBlockFromName(s);
				if (b instanceof BlockCrops && "com.pam.harvestcraft.BlockPamCrop".equals(b.getClass().getName())) {
					pamCrops.add((BlockCrops) b);
				}
			}
		}
	}

	@Override
	public boolean isMature(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
		return pamCrops.contains(block) && meta == 7;
	}

	@Override
	public boolean harvestCrop(World world, int x, int y, int z, List<ItemStack> list) {
		if (!world.isRemote) {
			Block b = world.getBlock(x, y, z);
			if (pamCrops.contains(b)) {
				if (world.getBlockMetadata(x, y, z) == 7) {
					b.onBlockActivated(world, x, y, z, BuildCraftAPI.proxy.getBuildCraftPlayer((WorldServer) world).get(), 0, 0.0F, 0.0F, 0.0F);
					return world.getBlockMetadata(x, y, z) == 0;
				}
			}
		}
		return false;
	}
}
