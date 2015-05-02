package buildcraft.compat;

import cpw.mods.fml.common.*;
import buildcraft.compat.amt.*;
import buildcraft.api.robots.*;

public class CompatModuleAMT extends CompatModuleBase
{
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
        RobotManager.registerAIRobot((Class)AIRobotHarvestAMT.class, "buildcraft.compat.aiRobotHarvestAMT");
    }
}
