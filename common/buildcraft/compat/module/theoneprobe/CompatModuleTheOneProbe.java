package buildcraft.compat.module.theoneprobe;

import buildcraft.compat.CompatModuleBase;
import net.minecraftforge.fml.InterModComms;

public class CompatModuleTheOneProbe extends CompatModuleBase {
    public CompatModuleTheOneProbe() {
    }

    public String compatModId() {
        return "theoneprobe";
    }

    public void preInit() {
//        FMLInterModComms.sendFunctionMessage(this.compatModId(), "getTheOneProbe", "buildcraft.compat.module.theoneprobe.BCPluginTOP");
        InterModComms.sendTo(this.compatModId(), "getTheOneProbe", () -> BCPluginTOP.INSTANCE);
    }
}
