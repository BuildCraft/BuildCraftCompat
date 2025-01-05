package buildcraft.compat.projectred;

import buildcraft.compat.CompatModuleBundledRedstone;
import mrtjp.projectred.api.IBundledEmitter;
import mrtjp.projectred.api.IBundledTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class BRProviderProjectRed implements CompatModuleBundledRedstone.Detector {
	@Override
	public boolean hasBundledInput(TileEntity tile, ForgeDirection side) {
		if (side != null && side != ForgeDirection.UNKNOWN && tile instanceof IBundledTile) {
			return ((IBundledTile) tile).canConnectBundled(side.ordinal());
		}
		return tile instanceof IBundledTile;
	}

	@Override
	public boolean hasBundledOutput(TileEntity tile, ForgeDirection side) {
		return tile instanceof IBundledEmitter;
	}
}
