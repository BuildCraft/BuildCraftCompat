package buildcraft.compat;

import cpw.mods.fml.common.Loader;

import buildcraft.compat.thermalexpansion.SchematicTE4Base;
import buildcraft.compat.thermalexpansion.SchematicTE4Cache;
import buildcraft.compat.thermalexpansion.SchematicTE4Dynamo;
import buildcraft.compat.thermalexpansion.SchematicTE4Light;
import buildcraft.compat.thermalexpansion.SchematicTE4Machine;
import buildcraft.compat.thermalexpansion.SchematicTE4Strongbox;
import buildcraft.compat.thermalexpansion.SchematicTE4Tank;

public class CompatModuleThermalExpansion extends CompatModuleBase
{
    @Override
    public String name() {
        return "ThermalExpansion";
    }
    
    @Override
    public boolean canLoad() {
        return super.canLoad() && Loader.isModLoaded("BuildCraft|Builders");
    }
    
    @Override
    public void init() {
        CompatUtils.registerSchematic("ThermalExpansion:Machine", 0, 8, SchematicTE4Machine.class);
		CompatUtils.registerSchematic("ThermalExpansion:Machine", 10, 11, SchematicTE4Machine.class);
		CompatUtils.registerSchematic("ThermalExpansion:Device", 2, SchematicTE4Base.class);
		CompatUtils.registerSchematic("ThermalExpansion:Device", 3, SchematicTE4Base.class);
		CompatUtils.registerSchematic("ThermalExpansion:Device", 5, SchematicTE4Base.class);
		CompatUtils.registerSchematic("ThermalExpansion:Dynamo", 0, 4, SchematicTE4Dynamo.class);
		CompatUtils.registerSchematic("ThermalExpansion:Cell", SchematicTE4Base.class);
		CompatUtils.registerSchematic("ThermalExpansion:Tank", SchematicTE4Tank.class);
		CompatUtils.registerSchematic("ThermalExpansion:Cache", SchematicTE4Cache.class);
		CompatUtils.registerSchematic("ThermalExpansion:Strongbox", SchematicTE4Strongbox.class);
		CompatUtils.registerSchematic("ThermalExpansion:Light", SchematicTE4Light.class);
    }
}
