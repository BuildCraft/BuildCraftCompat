package buildcraft.compat.railcraft;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;

public class SchematicRCEngine extends SchematicTile {
	private static final byte[] shiftMatrix = {0, 1, 5, 4, 2, 3};

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			tileNBT.setByte("direction", shiftMatrix[tileNBT.getByte("direction") % 6]);
		}
	}

	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		storedRequirements = new ItemStack[1];
		storedRequirements[0] = new ItemStack(this.block, 1, this.meta);
	}

	public void processNBT(NBTTagCompound source, NBTTagCompound target) {
		target.setTag("id", source.getTag("id"));

		if (source.hasKey("direction")) {
			target.setByte("direction", source.getByte("direction"));
		}
	}

	@Override
	public void initializeFromObjectAt (IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		if (tileNBT != null) {
			NBTTagCompound source = tileNBT;
			tileNBT = new NBTTagCompound();
			processNBT(source, tileNBT);
		}
	}
}
