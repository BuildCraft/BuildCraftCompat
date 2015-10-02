package buildcraft.compat.waila;

import net.minecraft.block.Block;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.compat.CompatModuleWAILA;
import buildcraft.robotics.EntityRobot;

import mcp.mobius.waila.api.IWailaRegistrar;

public final class WailaCallback {
	private WailaCallback() {

	}

	public static void callback(IWailaRegistrar registry) {
		if (Loader.isModLoaded("BuildCraft|Robotics")) {
			callbackRobotics(registry);
		}
		if (CompatModuleWAILA.ENABLE_BUILDER_DEBUG) {
			registry.registerTailProvider(new BuilderDebugDataProvider(), Block.class);
		}
	}

	@Optional.Method(modid = "BuildCraft|Robotics")
	private static void callbackRobotics(IWailaRegistrar registry) {
		registry.registerBodyProvider(new EntityRobotDataProvider(), EntityRobot.class);
		registry.registerNBTProvider(new EntityRobotDataProvider(), EntityRobot.class);
	}
}
