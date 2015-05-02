package buildcraft.compat.properties;

import buildcraft.BuildCraftCompat;
import cpw.mods.fml.common.Optional;
import net.minecraft.world.World;
import mods.defeatedcrow.api.plants.IRightClickHarvestable;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import buildcraft.core.properties.WorldPropertyIsHarvestable;

public class WorldPropertyIsHarvestableCompat extends WorldPropertyIsHarvestable
{
    private static final boolean APPLE_MILK_TEA;
    
    public boolean get(final IBlockAccess iBlockAccess, final Block block, final int i, final int x, final int y, final int z) {
        return (WorldPropertyIsHarvestableCompat.APPLE_MILK_TEA && isAMTBlock(block, iBlockAccess, x, y, z)) || super.get(iBlockAccess, block, i, x, y, z);
    }
    
    @Optional.Method(modid = "DCsAppleMilk")
    public static boolean isAMTBlock(final Block block, final IBlockAccess access, final int x, final int y, final int z) {
        return block instanceof IRightClickHarvestable && (!(access instanceof World) || ((IRightClickHarvestable)block).isHarvestable((World)access, x, y, z));
    }
    
    static {
        APPLE_MILK_TEA = BuildCraftCompat.hasModule("AppleMilkTea2");
    }
}
