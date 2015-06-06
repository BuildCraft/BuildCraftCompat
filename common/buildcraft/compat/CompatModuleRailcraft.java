package buildcraft.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.compat.lib.SchematicTileDrops;
import buildcraft.compat.lib.SchematicTileStairs;
import buildcraft.compat.railcraft.SchematicRCDetector;
import buildcraft.compat.railcraft.SchematicRCEngine;
import buildcraft.compat.railcraft.SchematicRCTrack;
import buildcraft.compat.railcraft.SchematicRCTrackElevator;

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
        CompatUtils.registerSchematic("Railcraft:slab", SchematicTileDrops.class);
        CompatUtils.registerSchematic("Railcraft:stair", SchematicTileStairs.class);
        CompatUtils.registerSchematic("Railcraft:track.elevator", SchematicRCTrackElevator.class);
        CompatUtils.registerSchematic("Railcraft:track", SchematicRCTrack.class);
        CompatUtils.registerSchematic("Railcraft:detector", SchematicRCDetector.class);
        CompatUtils.registerSchematic("Railcraft:machine.beta", 7, 9, SchematicRCEngine.class);
    }
}
