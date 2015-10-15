package buildcraft.compat.waila;

import net.minecraft.block.Block;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.compat.CompatModuleWAILA;
import buildcraft.core.BlockEngine;
import buildcraft.robotics.EntityRobot;
import buildcraft.transport.BlockGenericPipe;

import mcp.mobius.waila.api.IWailaRegistrar;

public final class WailaCallback {
	private WailaCallback() {

	}

	public static void callback(IWailaRegistrar registry) {
		if (Loader.isModLoaded("BuildCraft|Energy")) {
			callbackEnergy(registry);
		}
		if (Loader.isModLoaded("BuildCraft|Robotics")) {
			callbackRobotics(registry);
		}
		if (Loader.isModLoaded("BuildCraft|Transport")) {
			callbackTransport(registry);
		}

		if (CompatModuleWAILA.ENABLE_BUILDER_DEBUG) {
			registry.registerTailProvider(new BuilderDebugDataProvider(), Block.class);
		}
	}

	@Optional.Method(modid = "BuildCraft|Energy")
	private static void callbackEnergy(IWailaRegistrar registry) {
		registry.registerBodyProvider(new PowerModeEnergyDataProvider(), BlockEngine.class);
	}

	@Optional.Method(modid = "BuildCraft|Transport")
	private static void callbackTransport(IWailaRegistrar registry) {
		registry.registerBodyProvider(new PowerModeTransportDataProvider(), BlockGenericPipe.class);
	}

	@Optional.Method(modid = "BuildCraft|Robotics")
	private static void callbackRobotics(IWailaRegistrar registry) {
		registry.registerBodyProvider(new EntityRobotDataProvider(), EntityRobot.class);
		registry.registerNBTProvider(new EntityRobotDataProvider(), EntityRobot.class);
	}
}
