package buildcraft.compat;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class CompatUtils {
    private CompatUtils() {
    }

    public static NBTTagCompound getTileNBT(IBlockAccess a, BlockPos pos) {
        NBTTagCompound tag = new NBTTagCompound();
        TileEntity tile = a.getTileEntity(pos);
        if (tile != null) {
            tile.writeToNBT(tag);
        }
        return tag;
    }
}
