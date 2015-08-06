package buildcraft.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.compat.bigreactors.SchematicBRCreativePart;
import buildcraft.compat.bigreactors.SchematicBRPart;

public class CompatModuleBigReactors extends CompatModuleBase
{
    @Override
    public String name() {
        return "BigReactors";
    }

    @Override
    public boolean canLoad() {
        return Loader.isModLoaded("BigReactors") && Loader.isModLoaded("BuildCraft|Builders");
    }

    @Override
    public void init() {
        if (Loader.isModLoaded("BigReactors")) {
            initBuilders();
        }
    }

    @Optional.Method(modid = "BuildCraft|Builders")
    private void initBuilders() {
        CompatUtils.registerSchematic("BigReactors:BRReactorPart", SchematicBRPart.class);
        CompatUtils.registerSchematic("BigReactors:YelloriumFuelRod", SchematicBRPart.class);
        CompatUtils.registerSchematic("BigReactors:BRReactorRedstonePort", SchematicBRPart.class);
        CompatUtils.registerSchematic("BigReactors:BRMultiblockGlass", SchematicBRPart.class);
        CompatUtils.registerSchematic("BigReactors:BRTurbinePart", SchematicBRPart.class);
        CompatUtils.registerSchematic("BigReactors:BRMultiblockCreativePart", SchematicBRCreativePart.class);
        CompatUtils.registerSchematic("BigReactors:BRTurbineRotorPart", SchematicBRPart.class);
    }
}
