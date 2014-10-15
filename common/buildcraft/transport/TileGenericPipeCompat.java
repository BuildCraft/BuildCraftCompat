package buildcraft.transport;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileGenericPipeCompat extends TileGenericPipe {
	/* IMMIBIS' MICROBLOCKS */
	private boolean ImmibisMicroblocks_TransformableTileEntityMarker;

	private void ImmibisMicroblocks_onMicroblocksChanged() {
		this.blockNeighborChange = true;
	}

	private boolean ImmibisMicroblocks_isSideOpen(int side) {
		return true;
	}

	@Override
	protected boolean canPipeConnect(TileEntity with, ForgeDirection side) {
		if (!ImmibisMicroblocks_isSideOpen(side.ordinal())) return false;

		if (with instanceof TileGenericPipeCompat) {
			if (!((TileGenericPipeCompat) with).ImmibisMicroblocks_isSideOpen(side.getOpposite().ordinal()))
				return false;
		}

		return super.canPipeConnect(with, side);
	}
}
