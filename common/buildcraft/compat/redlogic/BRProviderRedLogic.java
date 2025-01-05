package buildcraft.compat.redlogic;

import mods.immibis.redlogic.api.wiring.IBundledEmitter;
import mods.immibis.redlogic.api.wiring.IBundledUpdatable;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.compat.CompatModuleBundledRedstone;

public class BRProviderRedLogic implements CompatModuleBundledRedstone.Detector {
	@Override
	public boolean hasBundledInput(TileEntity tile, ForgeDirection side) {
		return tile instanceof IBundledUpdatable;
	}

	@Override
	public boolean hasBundledOutput(TileEntity tile, ForgeDirection side) {
		return tile instanceof IBundledEmitter;
	}
}
