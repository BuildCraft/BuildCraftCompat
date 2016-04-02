package buildcraft.compat;

import cpw.mods.fml.common.Loader;

import buildcraft.api.crops.CropManager;
import buildcraft.compat.magiccrops.CropHandlerMagicCrops;

public class CompatModuleMagicCrops extends CompatModuleBase {
	@Override
	public String name() {
		return "Magical Crops";
	}

	@Override
	public boolean canLoad() {
		return Loader.isModLoaded("magicalcrops");
	}

	@Override
	public void init() {
		CropManager.registerHandler(new CropHandlerMagicCrops());
	}
}

