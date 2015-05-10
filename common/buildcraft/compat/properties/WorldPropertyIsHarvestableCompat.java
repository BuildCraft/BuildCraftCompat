package buildcraft.compat.properties;

import buildcraft.BuildCraftCompat;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import net.minecraft.world.World;
import mods.defeatedcrow.api.plants.IRightClickHarvestable;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import buildcraft.core.properties.WorldPropertyIsHarvestable;

public class WorldPropertyIsHarvestableCompat extends WorldPropertyIsHarvestable
{
    private static final boolean APPLE_MILK_TEA;
    private static final boolean WITCHERY;
    
    public boolean get(final IBlockAccess iBlockAccess, final Block block, final int i, final int x, final int y, final int z) {
        return (APPLE_MILK_TEA && isAMTBlock(block, iBlockAccess, x, y, z)) || (WITCHERY && this.isWitchCrop(block, i, x, y, z)) || super.get(iBlockAccess, block, i, x, y, z);
    }

    private boolean isWitchCrop(Block block, int metadata, int x, int y, int z) {
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

    @Optional.Method(modid = "DCsAppleMilk")
    public static boolean isAMTBlock(final Block block, final IBlockAccess access, final int x, final int y, final int z) {
        return block instanceof IRightClickHarvestable && (!(access instanceof World) || ((IRightClickHarvestable)block).isHarvestable((World)access, x, y, z));
    }
    
    static {
        APPLE_MILK_TEA = BuildCraftCompat.hasModule("AppleMilkTea2");
        WITCHERY = Loader.isModLoaded("witchery");
    }
}
