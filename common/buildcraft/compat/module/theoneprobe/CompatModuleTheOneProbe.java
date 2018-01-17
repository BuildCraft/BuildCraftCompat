package buildcraft.compat.module.theoneprobe;

import net.minecraftforge.fml.common.event.FMLInterModComms;

import buildcraft.compat.CompatModuleBase;

public class CompatModuleTheOneProbe extends CompatModuleBase {

    @Override
    public String compatModId() {
        return "theoneprobe";
    }

    @Override
    public void preInit() {
        FMLInterModComms.sendFunctionMessage(compatModId(), "getTheOneProbe",
            "buildcraft.compat.module.theoneprobe.BCPluginTOP");
    }
}
