package buildcraft.compat;

import cpw.mods.fml.common.Loader;
import buildcraft.compat.carpentersblocks.SchematicCBBlock;
import buildcraft.compat.carpentersblocks.SchematicCBGate;
import buildcraft.compat.carpentersblocks.SchematicCBRotated;
import buildcraft.compat.carpentersblocks.SchematicCBRotatedTwo;
import buildcraft.compat.carpentersblocks.SchematicCBSafe;
import buildcraft.compat.lib.SchematicTileDrops;

public class CompatModuleCarpentersBlocks extends CompatModuleBase {
	@Override
	public String name() {
		return "CarpentersBlocks";
	}

	@Override
	public boolean canLoad() {
		return super.canLoad() && Loader.isModLoaded("BuildCraft|Builders");
	}

	@Override
	public void init() {
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersBlock", SchematicCBBlock.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersDaylightSensor", SchematicTileDrops.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersGate", SchematicCBGate.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersLadder", SchematicCBRotatedTwo.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersPressurePlate", SchematicTileDrops.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersSafe", SchematicCBSafe.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersSlope", SchematicCBRotated.class);
		CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersStairs", SchematicCBRotated.class);
	}
}
