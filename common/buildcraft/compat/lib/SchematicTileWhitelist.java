package buildcraft.compat.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.SchematicTile;

public class SchematicTileWhitelist extends SchematicTile {
	private final List<String> whitelistNBT = new ArrayList<String>();
	private final boolean ignoreDrops;

	public SchematicTileWhitelist(String[] whitelist, boolean ignoreDrops) {
		for (String s : whitelist) {
			whitelistNBT.add(s);
		}
		this.ignoreDrops = ignoreDrops;
	}

	public SchematicTileWhitelist() {
		this.ignoreDrops = false;
	}

	@Override
	public void storeRequirements(IBuilderContext context, int x, int y, int z) {
		if (ignoreDrops) {
			storedRequirements = new ItemStack[1];
			storedRequirements[0] = new ItemStack(this.block, 1, this.meta);
		} else {
			super.storeRequirements(context, x, y, z);
		}
	}

	@Override
	public void initializeFromObjectAt (IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		if (tileNBT != null && whitelistNBT.size() > 0) {
			NBTTagCompound source = tileNBT;
			tileNBT = new NBTTagCompound();

			tileNBT.setTag("id", source.getTag("id"));

			for (String s : whitelistNBT) {
				if (source.hasKey(s)) {
					tileNBT.setTag(s, source.getTag(s));
				}
			}
		}
	}
}
