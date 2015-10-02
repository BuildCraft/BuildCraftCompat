package buildcraft.compat.thermalexpansion;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;

public class SchematicTE4Dynamo extends SchematicTE4Base {
	@Override
	protected void fixDualNBT(NBTTagCompound tileNBT) {
		tileNBT.removeTag("Inventory");

		if (meta == 0) {
			NBTTagCompound emptyTag = new NBTTagCompound();
			emptyTag.setString("Empty", "");
			tileNBT.setTag("WaterTank", emptyTag);
			tileNBT.setTag("SteamTank", emptyTag.copy());
		} else if (meta == 1 || meta == 3) {
			tileNBT.removeTag("FluidName");
			tileNBT.removeTag("Amount");
			tileNBT.setString("Empty", "");

			if (tileNBT.hasKey("React")) {
				tileNBT.setInteger("React", 0);
				tileNBT.setInteger("ReactMax", 0);
			}
		} else if (meta == 2) {
			NBTTagCompound emptyTag = new NBTTagCompound();
			emptyTag.setString("Empty", "");
			tileNBT.setTag("FuelTank", emptyTag);
			tileNBT.setTag("CoolantTank", emptyTag.copy());
			tileNBT.setInteger("Coolant", 0);
		}
	}

	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		if (block != null) {
			ArrayList<ItemStack> rqs = block.getDrops(context.world(), x, y, z, meta, 0);
			storedRequirements = rqs.toArray(new ItemStack[rqs.size()]);
		}
	}
}
