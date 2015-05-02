package buildcraft.compat;

import cpw.mods.fml.common.*;
import buildcraft.api.blueprints.*;
import buildcraft.compat.lib.*;
import buildcraft.compat.carpentersblocks.*;

public class CompatModuleCarpentersBlocks extends CompatModuleBase
{
    @Override
    public String name() {
        return "CarpentersBlocks";
    }
    
    @Override
    public boolean canLoad() {
        return super.canLoad() && Loader.isModLoaded("BuildCraft|Builders");
    }
    
    @Override
    public void init() {
        CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersBlock", (Class<? extends Schematic>)SchematicCBBlock.class, new Object[0]);
        CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersDaylightSensor", (Class<? extends Schematic>)SchematicTileDrops.class, new Object[0]);
        CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersGate", (Class<? extends Schematic>)SchematicCBGate.class, new Object[0]);
        CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersLadder", (Class<? extends Schematic>)SchematicCBRotatedTwo.class, new Object[0]);
        CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersPressurePlate", (Class<? extends Schematic>)SchematicTileDrops.class, new Object[0]);
        CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersSafe", (Class<? extends Schematic>)SchematicCBSafe.class, new Object[0]);
        CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersSlope", (Class<? extends Schematic>)SchematicCBRotated.class, new Object[0]);
        CompatUtils.registerSchematic("CarpentersBlocks:blockCarpentersStairs", (Class<? extends Schematic>)SchematicCBRotated.class, new Object[0]);
    }
}
