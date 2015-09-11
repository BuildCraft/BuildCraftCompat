package buildcraft.compat;

import buildcraft.compat.enderstorage.SchematicTileEnderStorage;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

public class CompatModuleEnderStorage extends CompatModuleBase {
    @Override
    public String name() {
        return "EnderStorage";
    }

    @Override
    public void init() {
        if (Loader.isModLoaded("EnderStorage")) {
            initBuilders();
        }
    }

    @Optional.Method(modid = "BuildCraft|Builders")
    private void initBuilders() {
        CompatUtils.registerSchematic("EnderStorage:enderChest", 0, 1, SchematicTileEnderStorage.class);
    }
}
