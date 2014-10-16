package buildcraft.compat;

import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DyeUtil {
	private static final String[] dyeOreDictNames = new String[] {
		"dyeBlack",
		"dyeRed",
		"dyeGreen",
		"dyeBrown",
		"dyeBlue",
		"dyePurple",
		"dyeCyan",
		"dyeLightGray",
		"dyeGray",
		"dyePink",
		"dyeLime",
		"dyeYellow",
		"dyeLightBlue",
		"dyeMagenta",
		"dyeOrange",
		"dyeWhite"
	};
	
	private static final int[] dyeOreDictIDs = new int[16];
	
	public static void initialize() {
		for (int i = 0; i < 16; i++) {
			dyeOreDictIDs[i] = OreDictionary.getOreID(dyeOreDictNames[i]);
		}
	}
	
	public static int getColor(Object object) {
		if (object instanceof ItemStack) {
			ItemStack stack = (ItemStack) object;
			if (stack.getItem() instanceof ItemDye) {
				return 15 - (stack.getItemDamage() & 15);
			}

			// Try Ore Dictionary lookup
			int[] oreIDs = OreDictionary.getOreIDs(stack);
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < oreIDs.length; j++) {
					if(oreIDs[j] == dyeOreDictIDs[i]) return i;
				}
			}
		}
		
		return -1;
	}
}
