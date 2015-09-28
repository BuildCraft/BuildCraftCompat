package buildcraft.compat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;

import buildcraft.api.statements.StatementManager;
import buildcraft.compat.mfr.MFRTriggerProvider;
import buildcraft.robotics.EntityRobot;

public class CompatModuleMFR extends CompatModuleBase
{
    @Override
    public String name() {
        return "MineFactoryReloaded";
    }
    
    @Override
    public void init() {
		StatementManager.registerTriggerProvider(new MFRTriggerProvider());

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
