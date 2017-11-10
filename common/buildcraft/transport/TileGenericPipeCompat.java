package buildcraft.transport;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.inventory.IInventoryConnection;
import cofh.api.transport.IItemDuct;
import buildcraft.BuildCraftCompat;
import buildcraft.compat.bluepower.BRProviderBluePower;
import buildcraft.core.lib.utils.MathUtils;
import mods.immibis.redlogic.api.wiring.IBareRedstoneWire;
import mods.immibis.redlogic.api.wiring.IBundledEmitter;
import mods.immibis.redlogic.api.wiring.IBundledUpdatable;
import mods.immibis.redlogic.api.wiring.IBundledWire;
import mods.immibis.redlogic.api.wiring.IConnectable;
import mods.immibis.redlogic.api.wiring.IRedstoneEmitter;
import mods.immibis.redlogic.api.wiring.IWire;
import mrtjp.projectred.api.IBundledTile;
import mrtjp.projectred.api.ProjectRedAPI;

@Optional.InterfaceList({
	@Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IBundledEmitter", modid = "RedLogic"),
	@Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IBundledUpdatable", modid = "RedLogic"),
	@Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IConnectable", modid = "RedLogic"),
	@Optional.Interface(iface = "mods.immibis.redlogic.api.wiring.IRedstoneEmitter", modid = "RedLogic"),
	@Optional.Interface(iface = "mrtjp.projectred.api.IBundledTile", modid = "ProjRed|Core")
})
public class TileGenericPipeCompat extends TileGenericPipe
	implements IItemDuct,
		IBundledEmitter, IBundledUpdatable, IConnectable, IRedstoneEmitter,
		IBundledTile {
	
	/* BUNDLED CABLE API */
	public byte[][] bundledCableReceived = new byte[6][16];
	public byte[][] bundledCableSent = new byte[6][16];
	public byte[][] bundledCableSentLast = new byte[6][16];
	public Object bluepowerWrapper;
	
	public void clearBundledCables() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 16; j++) {
				bundledCableSentLast[i][j] = bundledCableSent[i][j];
				bundledCableSent[i][j] = 0;
			}
		}
	}
	
	public void setBundledCable(int side, int position, boolean value) {
		if (side == -1) {
			for (int i = 0; i < 6; i++) {
				setBundledCable(i, position, value);
			}
		} else {
			bundledCableSent[side][position & 15] = (byte) (value ? -1 : 0);
		}
	}

	public boolean getBundledCable(int side, int position) {
		if (side == -1) {
			for (int i = 0; i < 6; i++) {
				if (getBundledCable(i, position)) {
					return true;
				}
			}
			return false;
		} else {
			return (bundledCableReceived[side][position & 15] != 0);
		}
	}

	/* OVERRIDES */
	@Override
	public void updateEntity() {
		if (!worldObj.isRemote && BuildCraftCompat.isLoaded("BundledRedstone")) {
			clearBundledCables();

			if (blockNeighborChange && Loader.isModLoaded("ProjRed|Core")) {
				updateProjectRedBundled();
			}
		}
		
		super.updateEntity();

		if (!worldObj.isRemote && BuildCraftCompat.isLoaded("BundledRedstone")) {
			for (int side = 0; side < 6; side++) {
				for (int position = 0; position < 16; position++) {
					if (bundledCableSent[side][position] != bundledCableSentLast[side][position]) {
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						ForgeDirection orientation = ForgeDirection.getOrientation(side);
						worldObj.notifyBlockOfNeighborChange(xCoord + orientation.offsetX, yCoord + orientation.offsetY, zCoord + orientation.offsetZ, this.blockType);
						break;
					}
				}
			}
		}
	}
	
	@Override
	protected boolean canPipeConnect_internal(TileEntity with, ForgeDirection side) {
		/*if (!ImmibisMicroblocks_isSideOpen(side.ordinal())) {
			return false;
		}

		if (with instanceof TileGenericPipeCompat) {
			if (!((TileGenericPipeCompat) with).ImmibisMicroblocks_isSideOpen(side.getOpposite().ordinal())) {
				return false;
			}
		}*/

		if (with instanceof IInventoryConnection && this.getPipeType() == PipeType.ITEM) {
			if (((IInventoryConnection) with).canConnectInventory(side.getOpposite()) == IInventoryConnection.ConnectionType.FORCE) {
				return true;
			} else if (((IInventoryConnection) with).canConnectInventory(side.getOpposite()) == IInventoryConnection.ConnectionType.DENY) {
				return false;
			}
		}

		if (BuildCraftCompat.isLoaded("BundledRedstone")) {
			if (Loader.isModLoaded("RedLogic")) {
				if (canPipeConnect_RedLogic(with, side)) {
					return true;
				}
			}
			if (Loader.isModLoaded("bluepower")) {
				if (canPipeConnect_BluePower(with, side)) {
					return true;
				}
			}
		}

		return super.canPipeConnect_internal(with, side);
	}
	
	/**
	 * MOD COMPATIBILITY SECTION
	 */
	
	/* IMMIBIS' MICROBLOCKS */
	
	//private boolean ImmibisMicroblocks_TransformableTileEntityMarker;

	//private void ImmibisMicroblocks_onMicroblocksChanged() {
	//	this.blockNeighborChange = true;
	//}

	//private boolean ImmibisMicroblocks_isSideOpen(int side) {
	//	return true;
	//}
	
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
		
		if (BuildCraftCompat.isLoaded("BundledRedstone") && wire instanceof IBundledWire) {
			return (blockFace == -1);
		} else if (wire instanceof IBareRedstoneWire) {
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
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 16; j++) {
				bundledCableReceived[i][j] = 0;
			}
		}

		for (int side = 0; side < 6; side++) {
			TileEntity tile = this.getTile(ForgeDirection.getOrientation(side));
			if (tile instanceof IBundledEmitter) {
				if (tile instanceof IBundledWire
						&& !((IBundledWire) tile).wireConnectsInDirection(-1, side ^ 1)) {
					continue;
				}

				byte[] data = ((IBundledEmitter) tile).getBundledCableStrength(-1, side ^ 1);
				if (data == null) {
					continue;
				}
				
				for (int position = 0; position < 16; position++) {
					if ((((int) data[position]) & 0xFF) > (((int) bundledCableReceived[side][position]) & 0xFF))
						bundledCableReceived[side][position] = data[position];
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
		
		return bundledCableSent[toDirection];
	}

	@Override
	@Optional.Method(modid = "RedLogic")
	public short getEmittedSignalStrength(int blockFace, int toDirection) {
		int signal = 0;
		if (pipe != null) {
			signal = MathUtils.clamp(pipe.isPoweringTo(toDirection), 0, 15);
		}
		return (short) ((signal << 4) | signal);
	}

	@Override
	public ItemStack insertItem(ForgeDirection from, ItemStack item) {
		int itemsUsed = injectItem(item, true, from);
		if (itemsUsed == item.stackSize) {
			return null;
		} else {
			ItemStack out = item.copy();
			out.stackSize -= itemsUsed;
			return out;
		}
	}

	@Override
	@Optional.Method(modid = "ProjRed|Core")
	public boolean canConnectBundled(int fromDirection) {
		ForgeDirection side = ForgeDirection.getOrientation(fromDirection);
		if (hasBlockingPluggable(side)) {
			return false;
		}

		return BuildCraftCompat.isLoaded("BundledRedstone");
	}

	@Override
	@Optional.Method(modid = "ProjRed|Core")
	public byte[] getBundledSignal(int dir) {
		return bundledCableSent[dir];
	}

	@Optional.Method(modid = "ProjRed|Core")
	private void updateProjectRedBundled() {
		if (ProjectRedAPI.transmissionAPI != null) {
			for (int i = 0; i < 6; i++) {
				byte[] data = ProjectRedAPI.transmissionAPI.getBundledInput(worldObj, xCoord, yCoord, zCoord, i);
				if (data != null && data.length == 16) {
					bundledCableReceived[i] = data;
				}
			}
		}
	}

	/* BLUEPOWER */
	@Optional.Method(modid = "bluepower")
	protected boolean canPipeConnect_BluePower(TileEntity with, ForgeDirection side) {
		return BRProviderBluePower.hasFreestandingBundledWire(with);
	}
}
