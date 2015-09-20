package buildcraft.compat.railcraft;

import java.util.LinkedList;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;
import buildcraft.api.core.BlockIndex;

public class SchematicRCTrack extends SchematicTile {
	private static final int[] shiftMatrix = {1, 0, 5, 4, 2, 3, 7, 8, 9, 6};

	@Override
	public void rotateLeft(IBuilderContext context) {
		this.meta = shiftMatrix[this.meta % shiftMatrix.length];
		if (tileNBT != null && tileNBT.hasKey("reversed")) {
			boolean reversed = tileNBT.getBoolean("reversed");
			if (meta == 0) {
				tileNBT.setBoolean("reversed", !reversed);
			}
		}
	}

	@Override
	public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		if (tileNBT != null && tileNBT.hasKey("powered")) {
			tileNBT.removeTag("powered");
		}
	}

	@Override
	public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
		context.world().setBlock(x, y, z, this.block, 0, 3);

		if (this.block.hasTileEntity(this.meta)) {
			this.tileNBT.setInteger("x", x);
			this.tileNBT.setInteger("y", y);
			this.tileNBT.setInteger("z", z);
			context.world().setTileEntity(x, y, z, TileEntity.createAndLoadEntity(this.tileNBT));
		}
	}

	@Override
	public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
		return this.block == context.world().getBlock(x, y, z);
	}

	@Override
	public void postProcessing(IBuilderContext context, int x, int y, int z) {
		context.world().setBlockMetadataWithNotify(x, y, z, this.meta, 3);
	}

	@Override
	public Set<BlockIndex> getPrerequisiteBlocks(IBuilderContext context) {
		return Sets.newHashSet(new BlockIndex[]{RELATIVE_INDEXES[ForgeDirection.DOWN.ordinal()]});
	}
}
