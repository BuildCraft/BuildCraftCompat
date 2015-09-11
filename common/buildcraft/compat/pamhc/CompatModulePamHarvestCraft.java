package buildcraft.compat.pamhc;

import cpw.mods.fml.common.Loader;

import buildcraft.api.crops.CropManager;
import buildcraft.compat.CompatModuleBase;

public class CompatModulePamHarvestCraft extends CompatModuleBase {
	@Override
	public String name() {
		return "PamHarvestCraft";
	}

	@Override
	public boolean canLoad() {
		return Loader.isModLoaded("harvestcraft");
	}

	@Override
	public void init() {
		CropManager.registerHandler(new CropHandlerHarvestCraft());
	}
}
