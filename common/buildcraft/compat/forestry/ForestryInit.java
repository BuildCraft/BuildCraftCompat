package buildcraft.compat.forestry;

import forestry.api.storage.BackpackManager;
import forestry.api.storage.EnumBackpackType;
import forestry.api.storage.IBackpackDefinition;

/**
 * Created by asie on 2/22/15.
 */
public class ForestryInit {
	private static IBackpackDefinition backpackPlumber;
	private static IBackpackDefinition backpackArchitect;
	private static IBackpackDefinition backpackPipeEngineer;
	private static IBackpackDefinition backpackRobotEngineer;

	private ForestryInit() {

	}

	public static void init() {
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
