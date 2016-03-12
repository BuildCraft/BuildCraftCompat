package buildcraft.compat;

import buildcraft.compat.minetweaker.AssemblyTable;
import buildcraft.compat.minetweaker.Fuels;
import buildcraft.compat.minetweaker.ProgrammingTable;
import buildcraft.compat.minetweaker.Refinery;

import minetweaker.MineTweakerAPI;

public class CompatModuleMineTweaker3 extends CompatModuleBase
{
    @Override
    public String name() {
        return "crafttweaker";
    }
    
    @Override
    public void postInit() {
        MineTweakerAPI.registerClass(AssemblyTable.class);
        MineTweakerAPI.registerClass(Fuels.class);
		MineTweakerAPI.registerClass(ProgrammingTable.class);
        MineTweakerAPI.registerClass(Refinery.class);
    }
}
