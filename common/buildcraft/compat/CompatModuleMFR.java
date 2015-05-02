package buildcraft.compat;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.*;
import buildcraft.robotics.*;
import java.lang.reflect.*;

public class CompatModuleMFR extends CompatModuleBase
{
    @Override
    public String name() {
        return "MineFactoryReloaded";
    }
    
    @Override
    public void init() {
        if (Loader.isModLoaded("BuildCraft|Robotics")) {
            initRobotics();
        }
    }

    @Optional.Method(modid = "BuildCraft|Robotics")
    private void initRobotics() {
        try {
            final Method m = FMLInterModComms.class.getDeclaredMethod("enqueueMessage", Object.class, String.class, FMLInterModComms.IMCMessage.class);
            m.setAccessible(true);
            final Constructor<FMLInterModComms.IMCMessage> c = FMLInterModComms.IMCMessage.class.getDeclaredConstructor(String.class, Object.class);
            c.setAccessible(true);
            m.invoke(null, Loader.instance().activeModContainer(), "MineFactoryReloaded", c.newInstance("registerSafariNetBlacklist", EntityRobot.class));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
