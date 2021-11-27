package buildcraft.compat;

import buildcraft.api.crops.CropManager;
import buildcraft.compat.forbiddenmagic.CropHandlerFMInkFlower;
import buildcraft.core.crops.CropHandlerPlantable;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;

public class CompatModuleForbiddenMagic extends CompatModuleBase {
	public static Block umbralBush, inkFlower;

	@Override
	public String name() {
		return "ForbiddenMagic";
	}

	@Override
	public boolean canLoad() {
		return Loader.isModLoaded("ForbiddenMagic");
	}

	@Override
	public void init() {
		umbralBush = (Block) Block.blockRegistry.getObject("ForbiddenMagic:UmbralBush");
		inkFlower = (Block) Block.blockRegistry.getObject("ForbiddenMagic:InkFlower");

		if (umbralBush != null && inkFlower != null) {
			CropManager.registerHandler(new CropHandlerFMInkFlower());
			CropHandlerPlantable.forbidBlock(umbralBush);
		}
	}
}
