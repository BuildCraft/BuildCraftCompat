package buildcraft.transport;

import cpw.mods.fml.common.Loader;
import mods.immibis.redlogic.api.wiring.IBareRedstoneWire;
import mods.immibis.redlogic.api.wiring.IBundledEmitter;
import mods.immibis.redlogic.api.wiring.IBundledUpdatable;
import mods.immibis.redlogic.api.wiring.IBundledWire;
import mods.immibis.redlogic.api.wiring.IConnectable;
import mods.immibis.redlogic.api.wiring.IWire;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.Optional;

@Optional.InterfaceList({
	@Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IBundledEmitter", modid = "RedLogic"),
	@Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IBundledUpdatable", modid = "RedLogic"),
	@Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IConnectable", modid = "RedLogic")
})
public class TileGenericPipeCompat extends TileGenericPipe
	implements IBundledEmitter, IBundledUpdatable, IConnectable {
	
	/* BUNDLED CABLE API */
	private byte[] bundledCableReceived = new byte[16];
	private byte[] bundledCableSent = new byte[16];
	
	public void setBundledCable(int position, boolean value) {
		bundledCableSent[position & 15] = (byte) (value ? -1 : 0);
	}
	
	public boolean getBundledCable(int position) {
		return (bundledCableReceived[position & 15] != 0);
	}

	/* CONNECTION OVERRIDES */
	
	@Override
	protected boolean canPipeConnect_internal(TileEntity with, ForgeDirection side) {
		if (!ImmibisMicroblocks_isSideOpen(side.ordinal())) {
			return false;
		}

		if (with instanceof TileGenericPipeCompat) {
			if (!((TileGenericPipeCompat) with).ImmibisMicroblocks_isSideOpen(side.getOpposite().ordinal())) {
				return false;
			}
		}
		
		if (Loader.isModLoaded("RedLogic")) {
			if (canPipeConnect_RedLogic(with, side)) {
				return true;
			}
		}

		return super.canPipeConnect_internal(with, side);
	}
	
	/**
	 * MOD COMPATIBILITY SECTION
	 */
	
	/* IMMIBIS' MICROBLOCKS */
	
	private boolean ImmibisMicroblocks_TransformableTileEntityMarker;

	private void ImmibisMicroblocks_onMicroblocksChanged() {
		this.blockNeighborChange = true;
	}

	private boolean ImmibisMicroblocks_isSideOpen(int side) {
		return true;
	}
	
	/* IMMIBIS' REDLOGIC */
	
	@Optional.Method(modid = "RedLogic")
	protected boolean canPipeConnect_RedLogic(TileEntity with, ForgeDirection side) {
		if (with instanceof IBundledWire) {
			if (((IBundledWire) with).wireConnectsInDirection(-1, side.getOpposite().ordinal())) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	@Optional.Method(modid = "RedLogic")
	public boolean connects(IWire wire, int blockFace, int fromDirection) {
		ForgeDirection side = ForgeDirection.getOrientation(fromDirection);
		if (hasBlockingPluggable(side)) {
			return false;
		}
		
		if(wire instanceof IBundledWire) {
			return (blockFace == -1);
		} else if(wire instanceof IBareRedstoneWire) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Optional.Method(modid = "RedLogic")
	public boolean connectsAroundCorner(IWire wire, int blockFace,
			int fromDirection) {
		return false;
	}

	@Override
	@Optional.Method(modid = "RedLogic")
	public void onBundledInputChanged() {
		for (int position = 0; position < 16; position++) {
			bundledCableReceived[position] = 0;
		}
		
		for (int side = 0; side < 6; side++) {
			TileEntity tile = this.getTile(ForgeDirection.getOrientation(side));
			if (tile instanceof IBundledWire
					&& ((IBundledWire) tile).wireConnectsInDirection(-1, side ^ 1)) {
				byte[] data = ((IBundledWire) tile).getBundledCableStrength(-1, side ^ 1);
				if (data == null) {
					continue;
				}
				
				for (int position = 0; position < 16; position++) {
					if ((((int) data[position]) & 0xFF) > (((int) bundledCableReceived[position]) & 0xFF))
						bundledCableReceived[position] = data[position];
				}
			}
		}
	}

	@Override
	@Optional.Method(modid = "RedLogic")
	public byte[] getBundledCableStrength(int blockFace, int toDirection) {
		if (blockFace != -1) {
			return null;
		}
		
		return bundledCableSent;
	}
}
