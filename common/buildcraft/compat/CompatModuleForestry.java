package buildcraft.compat;

import forestry.storage.*;
import forestry.api.storage.*;

public class CompatModuleForestry extends CompatModuleBase
{
    private static BackpackDefinition backpackPlumber;
    private static BackpackDefinition backpackArchitect;
    private static BackpackDefinition backpackPipeEngineer;
    private static BackpackDefinition backpackRobotEngineer;
    
    @Override
    public String name() {
        return "Forestry";
    }
    
    @Override
    public void init() {
        BackpackManager.backpackInterface.addBackpack((IBackpackDefinition)CompatModuleForestry.backpackPlumber, EnumBackpackType.T1);
        BackpackManager.backpackInterface.addBackpack((IBackpackDefinition)CompatModuleForestry.backpackArchitect, EnumBackpackType.T1);
        BackpackManager.backpackInterface.addBackpack((IBackpackDefinition)CompatModuleForestry.backpackPipeEngineer, EnumBackpackType.T1);
        BackpackManager.backpackInterface.addBackpack((IBackpackDefinition)CompatModuleForestry.backpackRobotEngineer, EnumBackpackType.T1);
        BackpackManager.backpackInterface.addBackpack((IBackpackDefinition)CompatModuleForestry.backpackPlumber, EnumBackpackType.T2);
        BackpackManager.backpackInterface.addBackpack((IBackpackDefinition)CompatModuleForestry.backpackArchitect, EnumBackpackType.T2);
        BackpackManager.backpackInterface.addBackpack((IBackpackDefinition)CompatModuleForestry.backpackPipeEngineer, EnumBackpackType.T2);
        BackpackManager.backpackInterface.addBackpack((IBackpackDefinition)CompatModuleForestry.backpackRobotEngineer, EnumBackpackType.T2);
    }
}
