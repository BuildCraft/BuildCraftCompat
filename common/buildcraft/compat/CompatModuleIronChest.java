package buildcraft.compat;

import cpw.mods.fml.common.*;
import buildcraft.compat.ironchests.*;
import buildcraft.api.blueprints.*;

public class CompatModuleIronChest extends CompatModuleBase
{
    @Override
    public String name() {
        return "IronChest";
    }
    
    @Override
    public boolean canLoad() {
        return super.canLoad() && Loader.isModLoaded("BuildCraft|Builders");
    }
    
    @Override
    public void init() {
        CompatUtils.registerSchematic("IronChest:BlockIronChest", (Class<? extends Schematic>)SchematicIronChest.class, new Object[0]);
    }
}
