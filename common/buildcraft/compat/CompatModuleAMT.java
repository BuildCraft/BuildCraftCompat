package buildcraft.compat;

import cpw.mods.fml.common.Loader;
import buildcraft.api.robots.RobotManager;
import buildcraft.compat.amt.AIRobotHarvestAMT;

public class CompatModuleAMT extends CompatModuleBase {
	@Override
	public String name() {
		return "AppleMilkTea2";
	}

	@Override
	public boolean canLoad() {
		return Loader.isModLoaded("DCsAppleMilk");
	}

	@Override
	public void init() {
		RobotManager.registerAIRobot(AIRobotHarvestAMT.class, "buildcraft.compat.aiRobotHarvestAMT");
	}
}
