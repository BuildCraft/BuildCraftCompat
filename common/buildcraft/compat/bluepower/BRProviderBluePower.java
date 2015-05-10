package buildcraft.compat.bluepower;

import java.util.Collection;
import java.util.HashSet;

import com.bluepowermod.api.misc.IFace;
import com.bluepowermod.api.wire.redstone.IBundledDevice;
import com.bluepowermod.api.wire.redstone.IBundledDeviceWrapper;
import com.bluepowermod.api.wire.redstone.IRedstoneDevice;
import com.bluepowermod.api.wire.redstone.IRedstoneProvider;
import com.bluepowermod.api.wire.redstone.IRedwire;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.IActionInternal;
import buildcraft.api.statements.IActionProvider;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.ITriggerInternal;
import buildcraft.api.statements.ITriggerProvider;
import buildcraft.compat.CompatModuleBundledRedstone;
import buildcraft.transport.TileGenericPipeCompat;
import uk.co.qmunity.lib.part.IPart;
import uk.co.qmunity.lib.part.ITilePartHolder;
import uk.co.qmunity.lib.part.compat.MultipartCompatibility;

/**
 * Created by asie on 5/10/15.
 */
public class BRProviderBluePower implements IRedstoneProvider, ITriggerProvider, IActionProvider {
	private final HashSet<ITriggerExternal> triggers = new HashSet<ITriggerExternal>();
	private final HashSet<IActionExternal> actions = new HashSet<IActionExternal>();

	public BRProviderBluePower() {
		triggers.add(CompatModuleBundledRedstone.triggerBundledInputOff);
		triggers.add(CompatModuleBundledRedstone.triggerBundledInputOn);
		actions.add(CompatModuleBundledRedstone.actionBundledOutput);
	}

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
				if (p instanceof IRedwire && !(p instanceof IFace) && (p instanceof IBundledDevice || p instanceof IBundledDeviceWrapper)) {
					return true;
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
			if (tgpc.hasBlockingPluggable(side)) {
				return null;
			}
			if (tgpc.bluepowerWrapper == null) {
				tgpc.bluepowerWrapper = new BundledDevicePipe(tgpc);
			}
			return (IBundledDevice) tgpc.bluepowerWrapper;
		}
		return null;
	}

	@Override
	public Collection<ITriggerExternal> getExternalTriggers(ForgeDirection side, TileEntity arg1) {
		return hasFreestandingBundledWire(arg1) ? triggers : null;
	}

	@Override
	public Collection<ITriggerInternal> getInternalTriggers(IStatementContainer c) {
		return null;
	}

	@Override
	public Collection<IActionExternal> getExternalActions(ForgeDirection side, TileEntity arg1) {
		return hasFreestandingBundledWire(arg1) ? actions : null;
	}

	@Override
	public Collection<IActionInternal> getInternalActions(IStatementContainer c) {
		return null;
	}
}
