package buildcraft.compat.mfr;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import buildcraft.core.robots.EntityRobot;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;

public class MFRIntegrationBC {

    public static void init() {
//        FMLInterModComms.sendMessage("MineFactoryReloaded", "registerSafariNetBlacklist", EntityRobotBase.class.getCanonicalName());
        try {
            Method m = FMLInterModComms.class.getDeclaredMethod("enqueueMessage", Object.class, String.class, IMCMessage.class);
            m.setAccessible(true);
            Constructor<IMCMessage> c = IMCMessage.class.getDeclaredConstructor(String.class, Object.class);
            c.setAccessible(true);
            m.invoke(null, Loader.instance().activeModContainer(), "MineFactoryReloaded", c.newInstance("registerSafariNetBlacklist", EntityRobot.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
