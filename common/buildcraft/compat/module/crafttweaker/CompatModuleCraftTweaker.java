package buildcraft.compat.module.crafttweaker;

import buildcraft.compat.CompatModuleBase;

public class CompatModuleCraftTweaker extends CompatModuleBase {
    public CompatModuleCraftTweaker() {
    }

    public String compatModId() {
        return "crafttweaker";
    }

    public void preInit() {
//        CraftTweakerAPI.registerClass(AssemblyTable.class);
//        CraftTweakerAPI.getRegistry()..registerClass(AssemblyTable.class);
//        CraftTweakerAPI.registerClass(CombustionEngine.class);
    }
}
