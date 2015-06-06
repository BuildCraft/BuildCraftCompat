package buildcraft.compat;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import buildcraft.api.blueprints.Schematic;
import buildcraft.core.blueprints.SchematicRegistry;

public class CompatUtils {
	private CompatUtils() {

	}

	public static void registerSchematic(String blockName, Class<? extends Schematic> schematic, Object... params) {
		Block b = Block.getBlockFromName(blockName);
		if (b != null && b != Blocks.air) {
			SchematicRegistry.INSTANCE.registerSchematicBlock(b, schematic, params);
		}
	}

	public static void registerSchematic(String blockName, int meta, Class<? extends Schematic> schematic, Object... params) {
		Block b = Block.getBlockFromName(blockName);
		if (b != null && b != Blocks.air) {
			SchematicRegistry.INSTANCE.registerSchematicBlock(b, meta, schematic, params);
		}
	}

	public static void registerSchematic(String blockName, int minMeta, int maxMeta, Class<? extends Schematic> schematic, Object... params) {
		Block b = Block.getBlockFromName(blockName);
		if (b != null && b != Blocks.air) {
			for (int i = minMeta; i <= maxMeta; i++) {
				SchematicRegistry.INSTANCE.registerSchematicBlock(b, i, schematic, params);
			}
		}
	}

	public static NBTTagCompound getTileNBT(IBlockAccess a, int x, int y, int z) {
		NBTTagCompound tag = new NBTTagCompound();
		TileEntity tile = a.getTileEntity(x, y, z);
		if (tile != null) {
			tile.writeToNBT(tag);
		}
		return tag;
	}
}
