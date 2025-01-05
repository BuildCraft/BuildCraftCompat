package buildcraft.compat.bluepower;

import com.bluepowermod.api.misc.IFace;
import com.bluepowermod.api.wire.redstone.IBundledDevice;
import com.bluepowermod.api.wire.redstone.IBundledDeviceWrapper;
import com.bluepowermod.api.wire.redstone.IRedstoneDevice;
import com.bluepowermod.api.wire.redstone.IRedstoneProvider;
import com.bluepowermod.api.wire.redstone.IRedwire;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.compat.CompatModuleBundledRedstone;
import buildcraft.transport.TileGenericPipeCompat;
import uk.co.qmunity.lib.part.IPart;
import uk.co.qmunity.lib.part.ITilePartHolder;
import uk.co.qmunity.lib.part.compat.MultipartCompatibility;

/**
 * Created by asie on 5/10/15.
 */
public class BRProviderBluePower implements IRedstoneProvider, CompatModuleBundledRedstone.Detector {
	public static boolean hasFreestandingBundledWire(TileEntity entity) {
		if (entity == null) {
			return false;
		}
		return hasFreestandingBundledWire(entity.getWorldObj(), entity.xCoord, entity.yCoord, entity.zCoord);
	}

	public static boolean hasFreestandingBundledWire(World world, int x, int y, int z) {
		ITilePartHolder holder = MultipartCompatibility.getPartHolder(world, x, y, z);
		if (holder != null) {
			for (IPart p : holder.getParts()) {
				if (p instanceof IRedwire && (p instanceof IBundledDevice || p instanceof IBundledDeviceWrapper)) {
					return CompatModuleBundledRedstone.ENABLE_NON_FREESTANDING_WIRES || !(p instanceof IFace);
				}
			}
		}
		return false;
	}

	@Override
	public IRedstoneDevice getRedstoneDeviceAt(World world, int i, int i1, int i2, ForgeDirection forgeDirection, ForgeDirection forgeDirection1) {
		return null;
	}

	@Override
	public IBundledDevice getBundledDeviceAt(World world, int x, int y, int z, ForgeDirection face, ForgeDirection side) {
		if (face != ForgeDirection.UNKNOWN || side == ForgeDirection.UNKNOWN) {
			return null;
		}

		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileGenericPipeCompat) {
			TileGenericPipeCompat tgpc = (TileGenericPipeCompat) te;
			if (tgpc.hasBlockingPluggable_bundledRedstoneCompat_internal(side)) {
				return null;
			}
			if (tgpc.bluepowerWrapper == null) {
				tgpc.bluepowerWrapper = new BundledDevicePipe(tgpc);
			}
			return (IBundledDevice) tgpc.bluepowerWrapper;
		}
		return null;
	}

	// TODO: These could support direct (no-wire) connections as well.

	@Override
	public boolean hasBundledInput(TileEntity tile, ForgeDirection side) {
		return hasFreestandingBundledWire(tile);
	}

	@Override
	public boolean hasBundledOutput(TileEntity tile, ForgeDirection side) {
		return hasFreestandingBundledWire(tile);
	}
}
