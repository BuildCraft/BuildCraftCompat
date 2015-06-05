package buildcraft.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.compat.lib.SchematicTileDrops;
import buildcraft.compat.lib.SchematicTileStairs;

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
    }
}
