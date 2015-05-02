package buildcraft.compat;

import minetweaker.*;
import buildcraft.compat.minetweaker.*;

public class CompatModuleMineTweaker3 extends CompatModuleBase
{
    @Override
    public String name() {
        return "MineTweaker3";
    }
    
    @Override
    public void postInit() {
        MineTweakerAPI.registerClass((Class)AssemblyTable.class);
        MineTweakerAPI.registerClass((Class)Fuels.class);
        MineTweakerAPI.registerClass((Class)IntegrationTable.class);
        MineTweakerAPI.registerClass((Class)Refinery.class);
    }
}
