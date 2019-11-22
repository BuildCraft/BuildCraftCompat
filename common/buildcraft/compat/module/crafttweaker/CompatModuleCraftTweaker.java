package buildcraft.compat.module.crafttweaker;

import buildcraft.compat.CompatModuleBase;

import crafttweaker.CraftTweakerAPI;

public class CompatModuleCraftTweaker extends CompatModuleBase {
    @Override
    public String compatModId() {
        return "crafttweaker";
    }

    @Override
    public void preInit() {
        CraftTweakerAPI.registerClass(AssemblyTable.class);
        CraftTweakerAPI.registerClass(CombustionEngine.class);
    }
}
