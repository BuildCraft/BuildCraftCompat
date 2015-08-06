package buildcraft.compat;

import cpw.mods.fml.common.event.FMLInterModComms;

public class CompatModuleWAILA extends CompatModuleBase {
    @Override
    public String name() {
        return "Waila";
    }

    @Override
    public void init() {
        FMLInterModComms.sendMessage("Waila", "register", "buildcraft.compat.waila.WailaCallback.callback");
    }
}
