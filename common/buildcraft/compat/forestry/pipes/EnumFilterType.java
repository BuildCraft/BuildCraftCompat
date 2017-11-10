package buildcraft.compat.forestry.pipes;

import java.util.Locale;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraft.compat.CompatModuleForestry;
import buildcraft.texture.TextureManager;

public enum EnumFilterType {

	CLOSED, ANYTHING, ITEM, BEE, DRONE, PRINCESS, QUEEN, PURE_BREED, NOCTURNAL, PURE_NOCTURNAL, FLYER, PURE_FLYER, CAVE, PURE_CAVE, NATURAL;

	public static EnumFilterType getType(ItemStack stack) {
		if (CompatModuleForestry.beeRoot.isMember(stack)) {
			if (CompatModuleForestry.beeRoot.isDrone(stack)) {
				return DRONE;
			} else if (CompatModuleForestry.beeRoot.isMated(stack)) {
				return QUEEN;
			} else {
				return PRINCESS;
			}
		}

		return ITEM;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon() {
		return TextureManager.getInstance().getIcon("propolisPipe/" + this.toString().toLowerCase(Locale.ENGLISH));
	}
}
