package buildcraft.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.compat.lib.SchematicTileDrops;
import buildcraft.compat.lib.SchematicTileStairs;
import buildcraft.compat.railcraft.SchematicRCDetector;
import buildcraft.compat.railcraft.SchematicRCTrack;
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
        CompatUtils.registerSchematic("Railcraft:slab", SchematicTileDrops.class);
        CompatUtils.registerSchematic("Railcraft:stair", SchematicTileStairs.class);
        CompatUtils.registerSchematic("Railcraft:track.elevator", SchematicRotateMeta.class, new int[]{2, 5, 3, 4}, true);
        CompatUtils.registerSchematic("Railcraft:track", SchematicRCTrack.class);
        CompatUtils.registerSchematic("Railcraft:detector", SchematicRCDetector.class);
    }
}
