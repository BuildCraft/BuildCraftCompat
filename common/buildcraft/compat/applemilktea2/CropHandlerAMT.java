package buildcraft.compat.applemilktea2;

import java.util.List;

import mods.defeatedcrow.api.plants.IRightClickHarvestable;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import buildcraft.api.crops.ICropHandler;
import buildcraft.core.lib.inventory.SimpleInventory;

public class CropHandlerAMT implements ICropHandler {
	private static final IInventory fakeInventory = new SimpleInventory(9, "RightClickCapture", 64);

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
	public boolean isMature(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
		if (!(blockAccess instanceof World)) {
			return false;
		}
		return block instanceof IRightClickHarvestable
				&& ((IRightClickHarvestable) block).isHarvestable((World) blockAccess, x, y, z);
	}

	@Override
	public boolean harvestCrop(World world, int x, int y, int z, List<ItemStack> drops) {
		Block block = world.getBlock(x, y, z);
		if (block instanceof IRightClickHarvestable) {
			IRightClickHarvestable rch = (IRightClickHarvestable) block;
			rch.onHarvest(world, x, y, z, fakeInventory, null);

			for (int i = 0; i < 9; i++) {
				ItemStack stack = fakeInventory.getStackInSlot(i);
				if (stack != null) {
					drops.add(stack);
					fakeInventory.setInventorySlotContents(i, null);
				}
			}

			return true;
		}
		return false;
	}
}
