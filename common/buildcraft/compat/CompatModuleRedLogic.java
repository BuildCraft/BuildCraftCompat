package buildcraft.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.compat.redlogic.SchematicRLButton;
import buildcraft.compat.redlogic.SchematicRLGate;
import buildcraft.compat.redlogic.SchematicRLLampNonCube;
import buildcraft.compat.redlogic.SchematicRLWire;

public class CompatModuleRedLogic extends CompatModuleBase
{
    @Override
    public String name() {
        return "RedLogic";
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
        CompatUtils.registerSchematic("RedLogic:redlogic.wire", SchematicRLWire.class);
        CompatUtils.registerSchematic("RedLogic:redlogic.gates", SchematicRLGate.class);
        CompatUtils.registerSchematic("RedLogic:redlogic.arrayCells", SchematicRLGate.class);
        CompatUtils.registerSchematic("RedLogic:redlogic.lampNonCube", SchematicRLLampNonCube.class);
        CompatUtils.registerSchematic("RedLogic:redlogic.button", SchematicRLButton.class);
    }
}
