package buildcraft.compat.minetweaker;

import minetweaker.MineTweakerAPI;

public class MineTweakerInit {
	private MineTweakerInit() {

	}

	public static void init() {
		MineTweakerAPI.registerClass(AssemblyTable.class);
		MineTweakerAPI.registerClass(Fuels.class);
		MineTweakerAPI.registerClass(IntegrationTable.class);
		MineTweakerAPI.registerClass(Refinery.class);
	}
}
