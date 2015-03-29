package buildcraft.compat;

import forestry.api.storage.BackpackManager;
import forestry.api.storage.EnumBackpackType;
import forestry.storage.BackpackDefinition;

import cpw.mods.fml.common.Loader;
import buildcraft.compat.CompatModuleBase;

/**
 * Created by asie on 2/22/15.
 */
public class CompatModuleForestry extends CompatModuleBase {
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
		BackpackManager.backpackInterface.addBackpack(backpackPlumber, EnumBackpackType.T1);
		BackpackManager.backpackInterface.addBackpack(backpackArchitect, EnumBackpackType.T1);
		BackpackManager.backpackInterface.addBackpack(backpackPipeEngineer, EnumBackpackType.T1);
		BackpackManager.backpackInterface.addBackpack(backpackRobotEngineer, EnumBackpackType.T1);
		BackpackManager.backpackInterface.addBackpack(backpackPlumber, EnumBackpackType.T2);
		BackpackManager.backpackInterface.addBackpack(backpackArchitect, EnumBackpackType.T2);
		BackpackManager.backpackInterface.addBackpack(backpackPipeEngineer, EnumBackpackType.T2);
		BackpackManager.backpackInterface.addBackpack(backpackRobotEngineer, EnumBackpackType.T2);
	}
}
