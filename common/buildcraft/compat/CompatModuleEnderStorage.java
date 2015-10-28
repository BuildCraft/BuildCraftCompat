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
    public boolean canLoad() {
        return Loader.isModLoaded("EnderStorage") && Loader.isModLoaded("BuildCraft|Builders");
    }

    @Override
    public void init() {
        if (Loader.isModLoaded("BuildCraft|Builders")) {
            initBuilders();
        }
    }

    @Optional.Method(modid = "BuildCraft|Builders")
    private void initBuilders() {
        CompatUtils.registerSchematic("EnderStorage:enderChest", 0, 1, SchematicTileEnderStorage.class);
    }
}
