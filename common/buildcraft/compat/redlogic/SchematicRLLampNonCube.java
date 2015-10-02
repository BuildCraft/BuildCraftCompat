package buildcraft.compat.redlogic;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;
import buildcraft.api.core.BlockIndex;

public class SchematicRLLampNonCube extends SchematicTile {
	private static final byte[] shiftMatrix = {0, 1, 5, 4, 2, 3, 6, 7};

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			int meta = tileNBT.getShort("lamp");
			int side = shiftMatrix[(meta >> 5) & 7];
			tileNBT.setShort("lamp", (short) (meta & ~(7 << 5) | (side << 5)));
		}
	}

	@Override
	public Set<BlockIndex> getPrerequisiteBlocks(IBuilderContext context) {
		if (tileNBT != null) {
			int meta = tileNBT.getShort("lamp");
			int side = (meta >> 5) & 7;
			return Sets.newHashSet(new BlockIndex[]{RELATIVE_INDEXES[side]});
		}
		return null;
	}

	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		if (this.block != null) {
			this.storedRequirements = new ItemStack[1];
			this.storedRequirements[0] = block.getPickBlock(
					new MovingObjectPosition(x, y, z, 0, Vec3.createVectorHelper(0, 0, 0)),
					context.world(), x, y, z
			);
		}
	}
}
