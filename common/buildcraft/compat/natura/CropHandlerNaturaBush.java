package buildcraft.compat.natura;

import buildcraft.BuildCraftCompat;
import buildcraft.api.core.BuildCraftAPI;
import buildcraft.core.crops.CropHandlerPlantable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CropHandlerNaturaBush extends CropHandlerPlantable {
	private static List<BlockLeavesBase> naturaCrops;

	public CropHandlerNaturaBush() {
		if (naturaCrops == null) {
			naturaCrops = new ArrayList<BlockLeavesBase>();
			for (String s : (Collection<String>) Block.blockRegistry.getKeys()) {
				Block b = Block.getBlockFromName(s);
				if (b instanceof BlockLeavesBase && (
						"mods.natura.blocks.crops.BerryBush".equals(b.getClass().getName())
						|| "mods.natura.blocks.crops.NetherBerryBush".equals(b.getClass().getName())
				)) {
					naturaCrops.add((BlockLeavesBase) b);
					CropHandlerPlantable.forbidBlock(b);
				}
			}
		}
	}

	@Override
	public boolean isMature(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
		return naturaCrops.contains(block) && meta >= 12;
	}

	@Override
	public boolean harvestCrop(World world, int x, int y, int z, List<ItemStack> list) {
		if (!world.isRemote) {
			Block b = world.getBlock(x, y, z);
			if (naturaCrops.contains(b)) {
				if (world.getBlockMetadata(x, y, z) >= 12) {
					return b.onBlockActivated(world, x, y, z, BuildCraftCompat.getFakePlayerAbove(world, x, y, z), 0, 0.0F, 0.0F, 0.0F);
				}
			}
		}
		return false;
	}
}
