package buildcraft.compat.factorization;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;
import buildcraft.compat.CompatUtils;
import factorization.api.FzOrientation;
import factorization.weird.TileEntityDayBarrel;

public class SchematicFZBarrel extends SchematicTile {
	protected static final int[] shiftMatrix = {0, 1, 5, 4, 2, 3, 6, 7};

	@Override
	public void rotateLeft(IBuilderContext context) {
		if (tileNBT != null) {
			FzOrientation orientation = FzOrientation.getOrientation(tileNBT.getByte("dir"));
			tileNBT.setByte("dir", (byte) orientation.rotateOnTop(1).ordinal());
		}
	}

	@Override
	public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		if (tileNBT != null) {
			if (tileNBT.getInteger("count") > 64) {
				tileNBT.setInteger("count", 64);
			}
		}
	}

	@Override
	public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
		if (super.isAlreadyBuilt(context, x, y, z)) {
			NBTTagCompound targetNBT = CompatUtils.getTileNBT(context.world(), x, y, z);
			return targetNBT.getByte("dir") == tileNBT.getByte("dir");
		} else {
			return false;
		}
	}

	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		if (tileNBT != null) {
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();

			ItemStack log = ItemStack.loadItemStackFromNBT(tileNBT.getCompoundTag("log"));
			ItemStack slab = ItemStack.loadItemStackFromNBT(tileNBT.getCompoundTag("slab"));
			items.add(TileEntityDayBarrel.makeBarrel(TileEntityDayBarrel.Type.values()[tileNBT.getByte("type")], log, slab));

			ItemStack item = ItemStack.loadItemStackFromNBT(tileNBT.getCompoundTag("item"));
			if (item != null && item.getItem() != null) {
				item.stackSize = tileNBT.getInteger("count");
				items.add(item);
			}

			this.storedRequirements = new ItemStack[items.size()];
			items.toArray(this.storedRequirements);
		}
	}
}
