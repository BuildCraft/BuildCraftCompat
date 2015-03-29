package buildcraft.compat;

import cpw.mods.fml.common.Loader;
import buildcraft.compat.carpentersblocks.SchematicCBBlock;
import buildcraft.compat.carpentersblocks.SchematicCBGate;
import buildcraft.compat.carpentersblocks.SchematicCBRotated;
import buildcraft.compat.carpentersblocks.SchematicCBRotatedTwo;
import buildcraft.compat.carpentersblocks.SchematicCBSafe;
import buildcraft.compat.ironchests.SchematicIronChest;
import buildcraft.compat.lib.SchematicTileDrops;

public class CompatModuleIronChest extends CompatModuleBase {
	@Override
	public String name() {
		return "IronChest";
	}

	@Override
	public boolean canLoad() {
		return super.canLoad() && Loader.isModLoaded("BuildCraft|Builders");
	}

	@Override
	public void init() {
		CompatUtils.registerSchematic("IronChest:BlockIronChest", SchematicIronChest.class);
	}
}
