package buildcraft.compat;

import buildcraft.compat.minetweaker.AssemblyTable;
import buildcraft.compat.minetweaker.Fuels;
import buildcraft.compat.minetweaker.Refinery;

import minetweaker.MineTweakerAPI;

public class CompatModuleMineTweaker3 extends CompatModuleBase
{
    @Override
    public String name() {
        return "MineTweaker3";
    }
    
    @Override
    public void postInit() {
        MineTweakerAPI.registerClass(AssemblyTable.class);
        MineTweakerAPI.registerClass(Fuels.class);
        MineTweakerAPI.registerClass(Refinery.class);
    }
}
