package buildcraft.compat;

import cpw.mods.fml.common.Loader;

import buildcraft.api.crops.CropManager;
import buildcraft.compat.pamharvestcraft.CropHandlerHarvestCraft;

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
