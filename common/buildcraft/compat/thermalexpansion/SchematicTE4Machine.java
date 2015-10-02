package buildcraft.compat.thermalexpansion;

import net.minecraft.nbt.NBTTagCompound;

public class SchematicTE4Machine extends SchematicTE4Base {
	@Override
	protected void fixDualNBT(NBTTagCompound tileNBT) {
		if (meta == 4 || meta == 5 || meta == 6) {
			tileNBT.removeTag("FluidName");
			tileNBT.removeTag("Amount");
			tileNBT.setString("Empty", "");
		} else if (meta == 7) {
			NBTTagCompound emptyTag = new NBTTagCompound();
			emptyTag.setString("Empty", "");
			tileNBT.setTag("ColdTank", emptyTag);
			tileNBT.setTag("HotTank", emptyTag.copy());
		} else if (meta == 8 || meta == 11) {
			tileNBT.setInteger("Amount", 0);
			tileNBT.removeTag("Hell");
		}
	}
}
