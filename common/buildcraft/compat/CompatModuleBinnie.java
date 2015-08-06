package buildcraft.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.compat.binnie.SchematicTileCarpentry;
import buildcraft.compat.lib.SchematicTileDoor;
import buildcraft.compat.lib.SchematicTileDropsOnly;
import buildcraft.compat.lib.SchematicTileStairs;
import buildcraft.core.builders.schematics.SchematicRotateMeta;

public class CompatModuleBinnie extends CompatModuleBase
{
    @Override
    public String name() {
        return "BinnieMods";
    }

    @Override
    public boolean canLoad() {
        return Loader.isModLoaded("BinnieCore") && Loader.isModLoaded("BuildCraft|Builders");
    }

    @Override
    public void init() {
        if (Loader.isModLoaded("BuildCraft|Builders")) {
            initBuilders();
        }
    }

    @Optional.Method(modid = "BuildCraft|Builders")
    private void initBuilders() {
        CompatUtils.registerSchematic("ExtraTrees:slab", SchematicTileDropsOnly.class);
        CompatUtils.registerSchematic("ExtraTrees:fence", SchematicTileDropsOnly.class);
        CompatUtils.registerSchematic("ExtraTrees:multifence", SchematicTileDropsOnly.class);
        CompatUtils.registerSchematic("ExtraTrees:planks", SchematicTileDropsOnly.class);
        CompatUtils.registerSchematic("ExtraTrees:stairs", SchematicTileStairs.class);
        CompatUtils.registerSchematic("ExtraTrees:door", SchematicTileDoor.class);
        CompatUtils.registerSchematic("ExtraTrees:gate", SchematicRotateMeta.class, new int[]{0, 1, 2, 3}, true);
        CompatUtils.registerSchematic("ExtraTrees:log", SchematicRotateMeta.class, new int[]{8, 4, 8, 4}, true);
        CompatUtils.registerSchematic("ExtraTrees:stainedglass", SchematicTileCarpentry.class);
        CompatUtils.registerSchematic("ExtraTrees:carpentry", SchematicTileCarpentry.class);

        CompatUtils.registerSchematic("Botany:stained", SchematicTileDropsOnly.class);
        CompatUtils.registerSchematic("Botany:ceramic", SchematicTileDropsOnly.class);
        CompatUtils.registerSchematic("Botany:ceramicBrick", SchematicTileDropsOnly.class);
        CompatUtils.registerSchematic("Botany:ceramicPattern", SchematicTileCarpentry.class);
    }
}
