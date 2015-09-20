package buildcraft.compat.redlogic;

import java.lang.reflect.Method;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;
import buildcraft.api.core.BlockIndex;

public class SchematicRLButton extends SchematicTile {
	private static final byte[] shiftMatrix = {0, 1, 5, 4, 2, 3, 6, 7};

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			tileNBT.setByte("side", shiftMatrix[tileNBT.getByte("side") & 7]);
		}
	}

	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		TileEntity tile = context.world().getTileEntity(x, y, z);
		if(tile != null) {
			// HACK!
			try {
				Method m = tile.getClass().getMethod("getItemDropped");
				if (m != null) {
					Object o = m.invoke(tile);
					if (o instanceof ItemStack) {
						this.storedRequirements = new ItemStack[1];
						this.storedRequirements[0] = (ItemStack) o;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public Set<BlockIndex> getPrerequisiteBlocks(IBuilderContext context) {
		if (tileNBT.hasKey("side")) {
			return Sets.newHashSet(new BlockIndex[]{RELATIVE_INDEXES[tileNBT.getByte("side") & 7]});
		}
		return null;
	}
}
