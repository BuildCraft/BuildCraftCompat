package buildcraft.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.compat.immersiveengineering.SchematicIEBase;
import buildcraft.compat.immersiveengineering.SchematicIESupported;

public class CompatModuleImmersiveEngineering extends CompatModuleBase
{
    @Override
    public String name() {
        return "ImmersiveEngineering";
    }

    @Override
    public boolean canLoad() {
        return Loader.isModLoaded("ImmersiveEngineering") && Loader.isModLoaded("BuildCraft|Builders");
    }

    @Override
    public void init() {
        if (Loader.isModLoaded("ImmersiveEngineering")) {
            initBuilders();
        }
    }

    @Optional.Method(modid = "BuildCraft|Builders")
    private void initBuilders() {
        CompatUtils.registerSchematic("ImmersiveEngineering:metalDecoration", 2, SchematicIESupported.class); // Lantern
        CompatUtils.registerSchematic("ImmersiveEngineering:metalDecoration", 3, SchematicIEBase.class); // Structural Arm
        //CompatUtils.registerSchematic("ImmersiveEngineering:metalDevice", 9, SchematicIEBase.class); // Kinetic Dynamo
        CompatUtils.registerSchematic("ImmersiveEngineering:metalDevice", 10, SchematicIEBase.class); // Thermoelectric
        CompatUtils.registerSchematic("ImmersiveEngineering:metalDevice", 11, SchematicIEBase.class); // Conveyor Belt
        CompatUtils.registerSchematic("ImmersiveEngineering:metalDevice2", 0, SchematicIESupported.class); // Breaker

        // Connectors
        CompatUtils.registerSchematic("ImmersiveEngineering:metalDevice", 0, SchematicIESupported.class);
        CompatUtils.registerSchematic("ImmersiveEngineering:metalDevice", 2, SchematicIESupported.class);
        CompatUtils.registerSchematic("ImmersiveEngineering:metalDevice", 6, SchematicIESupported.class);
    }
}
