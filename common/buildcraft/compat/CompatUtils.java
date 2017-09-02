package buildcraft.compat;

import buildcraft.api.schematics.SchematicBlockFactoryRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class CompatUtils {
    private CompatUtils() {

    }

//    public static void registerSchematic(String blockName, Class<? extends Schematic> schematic, Object... params) {
//        Block b = Block.getBlockFromName(blockName);
//        if (b != null && b != Blocks.AIR) {
//            SchematicRegistry.INSTANCE.registerSchematicBlock(b, schematic, params);
//        }
//    }
//
//    public static void registerSchematic(String blockName, int meta, Class<? extends Schematic> schematic, Object... params) {
//        Block b = Block.getBlockFromName(blockName);
//        if (b != null && b != Blocks.AIR) {
//            SchematicRegistry.INSTANCE.registerSchematicBlock(b.getStateFromMeta(meta), schematic, params);
//        }
//    }
//
//    public static void registerSchematic(String blockName, int minMeta, int maxMeta, Class<? extends Schematic> schematic, Object... params) {
//        Block b = Block.getBlockFromName(blockName);
//        if (b != null && b != Blocks.AIR) {
//            for (int i = minMeta; i <= maxMeta; i++) {
//                SchematicRegistry.INSTANCE.registerSchematicBlock(b.getStateFromMeta(i), schematic, params);
//            }
//        }
//    }

    public static NBTTagCompound getTileNBT(IBlockAccess a, BlockPos pos) {
        NBTTagCompound tag = new NBTTagCompound();
        TileEntity tile = a.getTileEntity(pos);
        if (tile != null) {
            tile.writeToNBT(tag);
        }
        return tag;
    }
}
