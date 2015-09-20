package buildcraft.compat.redlogic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;
import buildcraft.api.core.BlockIndex;
import buildcraft.compat.CompatUtils;

public class SchematicRLWire extends SchematicTile {
	private static final byte[] shiftMatrix = {0, 1, 5, 4, 2, 3};

	private int rotateMask(int mask) {
		int newMask = mask & ~0x3C;
		for (int i = 2; i < 6; i++) {
			if ((mask & (1 << i)) != 0) {
				newMask |= 1 << shiftMatrix[i];
			}
		}
		return newMask;
	}

	@Override
	public void rotateLeft(IBuilderContext context) {
		super.rotateLeft(context);
		if (tileNBT != null) {
			tileNBT.setByte("mask", (byte) rotateMask(tileNBT.getByte("mask")));
			if (tileNBT.hasKey("jcmc")) {
				tileNBT.setByte("jcmc", (byte) rotateMask(tileNBT.getByte("jcmc")));
			}
		}
	}

	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		int maskSize = tileNBT.hasKey("jcmc") ? 1 : 0;
		byte mask = tileNBT.getByte("mask");
		for (; mask != 0; maskSize++) {
			mask &= mask - 1;
		}
		storedRequirements = new ItemStack[1];
		storedRequirements[0] = new ItemStack(this.block, maskSize, tileNBT.getByte("type"));
	}

	@Override
	public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		if (tileNBT != null) {
			tileNBT.removeTag("cmc");
			tileNBT.removeTag("ccc");
			tileNBT.removeTag("notifyQueued");
		}
	}

	@Override
	public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
		super.placeInWorld(context, x, y, z, stacks);
		for (int xo = -1; xo < 2; xo++) {
			for (int yo = -1; yo < 2; yo++) {
				for (int zo = -1; zo < 2; zo++) {
					if (xo == yo && xo == zo && yo == zo) {
						continue;
					}
					if ((xo == 1 || xo == -1) && (yo == 1 || yo == -1) && (zo == 1 || zo == -1)) {
						continue;
					}
					int xs = x + xo;
					int ys = y + yo;
					int zs = z + zo;
					if (context.world().getBlock(xs, ys, zs).equals(this.block)) {
						context.world().notifyBlockOfNeighborChange(xs, ys, zs, this.block);
					}
				}
			}
		}
	}

	@Override
	public Set<BlockIndex> getPrerequisiteBlocks(IBuilderContext context) {
		Set<BlockIndex> relativeIndexes = new HashSet<BlockIndex>();
		byte mask = tileNBT.getByte("mask");
		for (int i = 0; i < 6; i++) {
			if ((mask & (1 << i)) != 0) {
				relativeIndexes.add(RELATIVE_INDEXES[i]);
			}
		}
		return relativeIndexes;
	}

	@Override
	public void postProcessing(IBuilderContext context, int x, int y, int z) {
	}

	@Override
	public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
		if (this.block != context.world().getBlock(x, y, z)) {
			return false;
		}

		NBTTagCompound tag = CompatUtils.getTileNBT(context.world(), x, y, z);
		return tag.getByte("type") == tileNBT.getByte("type") && tag.hasKey("jcmc") == tileNBT.hasKey("jcmc");
	}
}
