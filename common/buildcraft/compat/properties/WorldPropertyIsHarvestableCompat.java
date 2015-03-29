package buildcraft.compat.properties;

import mods.defeatedcrow.api.plants.IRightClickHarvestable;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.Optional;
import buildcraft.BuildCraftCompat;
import buildcraft.core.properties.WorldPropertyIsHarvestable;

public class WorldPropertyIsHarvestableCompat extends WorldPropertyIsHarvestable {
	private static final boolean APPLE_MILK_TEA = BuildCraftCompat.hasModule("AppleMilkTea2");

	@Override
	public boolean get(IBlockAccess iBlockAccess, Block block, int i, int x, int y, int z) {
		if (APPLE_MILK_TEA && isAMTBlock(block, iBlockAccess, x, y, z)) {
			return true;
		}
		return super.get(iBlockAccess, block, i, x, y, z);
	}

	@Optional.Method(modid = "DCsAppleMilk")
	public static boolean isAMTBlock(Block block, IBlockAccess access, int x, int y, int z) {
		if (block instanceof IRightClickHarvestable) {
			if (access instanceof World) {
				return ((IRightClickHarvestable) block).isHarvestable((World) access, x, y, z);
			} else {
				return true; // Lucky guess
			}
		} else {
			return false;
		}
	}
}
