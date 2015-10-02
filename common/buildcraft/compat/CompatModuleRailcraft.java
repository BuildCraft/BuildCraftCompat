package buildcraft.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.compat.lib.SchematicTileDropsOnly;
import buildcraft.compat.lib.SchematicTileIgnoreNBT;
import buildcraft.compat.lib.SchematicTileStairs;
import buildcraft.compat.railcraft.SchematicRCDirectional;
import buildcraft.compat.railcraft.SchematicRCTrack;
import buildcraft.compat.railcraft.SchematicRCTrackElevator;
import buildcraft.core.builders.schematics.SchematicIgnore;
import buildcraft.core.builders.schematics.SchematicRotateMeta;

public class CompatModuleRailcraft extends CompatModuleBase
{
    @Override
    public String name() {
        return "Railcraft";
    }

    @Override
    public boolean canLoad() {
        return super.canLoad() && Loader.isModLoaded("BuildCraft|Builders");
    }

    @Override
    public void init() {
        if (Loader.isModLoaded("BuildCraft|Builders")) {
            initBuilders();
        }
    }

    @Optional.Method(modid = "BuildCraft|Builders")
    private void initBuilders() {
		CompatUtils.registerSchematic("Railcraft:residual.heat", SchematicIgnore.class);

		CompatUtils.registerSchematic("Railcraft:anvil", SchematicRotateMeta.class, new Object[]{new int[]{0, 1, 2, 3}, Boolean.valueOf(true)});
        CompatUtils.registerSchematic("Railcraft:slab", SchematicTileDropsOnly.class);
        CompatUtils.registerSchematic("Railcraft:stair", SchematicTileStairs.class);
        CompatUtils.registerSchematic("Railcraft:track.elevator", SchematicRCTrackElevator.class);
        CompatUtils.registerSchematic("Railcraft:track", SchematicRCTrack.class);
        CompatUtils.registerSchematic("Railcraft:detector", SchematicRCDirectional.class);

		CompatUtils.registerSchematic("Railcraft:machine.alpha", 0, SchematicTileIgnoreNBT.class); // World Anchor
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 1, SchematicTileIgnoreNBT.class); // Steam Turbine
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 2, SchematicTileIgnoreNBT.class); // Personal Anchor
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 3, SchematicTileIgnoreNBT.class); // Steam Oven
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 4, SchematicTileIgnoreNBT.class); // Admin Anchor
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 6, SchematicRCDirectional.class, new String[]{"direction", "profession"}, true); // Trade Station
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 7, SchematicTileIgnoreNBT.class); // Coke Oven
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 8, SchematicTileIgnoreNBT.class); // Rolling Machine
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 9, SchematicRCDirectional.class, new String[]{"direction"}, true); // Steam Trap
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 10, SchematicRCDirectional.class, new String[]{"direction"}, true); // Steam Trap
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 11, SchematicTileIgnoreNBT.class); // Feed Station
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 12, SchematicTileIgnoreNBT.class); // Blast Furnace
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 13, SchematicTileIgnoreNBT.class); // Passive Anchor
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 14, SchematicTileIgnoreNBT.class); // Water Tank Siding
		CompatUtils.registerSchematic("Railcraft:machine.alpha", 15, SchematicTileIgnoreNBT.class); // Rock Crusher

		CompatUtils.registerSchematic("Railcraft:machine.beta", 7, 9, SchematicRCDirectional.class, new String[]{"direction"}, true); // Engines
		CompatUtils.registerSchematic("Railcraft:machine.beta", 11, 12, SchematicRCDirectional.class, new String[]{"facing"}, true); // Void/Metals Chest
    }
}
