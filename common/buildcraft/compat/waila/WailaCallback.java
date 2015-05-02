package buildcraft.compat.waila;

import mcp.mobius.waila.api.IWailaRegistrar;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import buildcraft.robotics.EntityRobot;

public final class WailaCallback {
	private WailaCallback() {

	}

	public static void callback(IWailaRegistrar registry) {
		if (Loader.isModLoaded("BuildCraft|Robotics")) {
			callbackRobotics(registry);
		}
	}

	@Optional.Method(modid = "BuildCraft|Robotics")
	private static void callbackRobotics(IWailaRegistrar registry) {
		registry.registerBodyProvider(new EntityRobotDataProvider(), EntityRobot.class);
		registry.registerNBTProvider(new EntityRobotDataProvider(), EntityRobot.class);
	}
}
