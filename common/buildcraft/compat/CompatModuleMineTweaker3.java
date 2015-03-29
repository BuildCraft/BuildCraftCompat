package buildcraft.compat;

import minetweaker.MineTweakerAPI;

import buildcraft.compat.minetweaker.AssemblyTable;
import buildcraft.compat.minetweaker.Fuels;
import buildcraft.compat.minetweaker.IntegrationTable;
import buildcraft.compat.minetweaker.Refinery;

public class CompatModuleMineTweaker3 extends CompatModuleBase {
	public CompatModuleMineTweaker3() {

	}

	@Override
	public String name() {
		return "MineTweaker3";
	}

	@Override
	public void postInit() {
		MineTweakerAPI.registerClass(AssemblyTable.class);
		MineTweakerAPI.registerClass(Fuels.class);
		MineTweakerAPI.registerClass(IntegrationTable.class);
		MineTweakerAPI.registerClass(Refinery.class);
	}
}
