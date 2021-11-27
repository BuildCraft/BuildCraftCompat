package buildcraft.compat;

import buildcraft.api.crops.CropManager;
import buildcraft.compat.natura.CropHandlerNaturaBush;
import buildcraft.compat.pamharvestcraft.CropHandlerHarvestCraft;
import cpw.mods.fml.common.Loader;

public class CompatModuleNatura extends CompatModuleBase {
	@Override
	public String name() {
		return "Natura";
	}

	@Override
	public boolean canLoad() {
		return Loader.isModLoaded("Natura");
	}

	@Override
	public void init() {
		CropManager.registerHandler(new CropHandlerNaturaBush());
	}
}
