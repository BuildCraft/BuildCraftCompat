package buildcraft.compat.bluepower;

import com.bluepowermod.api.BPApi;
import com.bluepowermod.api.connect.ConnectionType;
import com.bluepowermod.api.connect.IConnectionCache;
import com.bluepowermod.api.misc.MinecraftColor;
import com.bluepowermod.api.wire.redstone.IBundledDevice;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.transport.TileGenericPipeCompat;

public class BundledDevicePipe implements IBundledDevice {
	private final TileGenericPipeCompat tile;
	private final IConnectionCache<? extends IBundledDevice> cache;

	public BundledDevicePipe(TileGenericPipeCompat tile) {
		this.tile = tile;
		this.cache = BPApi.getInstance().getRedstoneApi().createBundledConnectionCache(this);
	}

	@Override
	public boolean canConnect(ForgeDirection side, IBundledDevice dev, ConnectionType type) {
		return type == ConnectionType.STRAIGHT && side != ForgeDirection.UNKNOWN && !tile.hasBlockingPluggable(side);
	}

	@Override
	public IConnectionCache<? extends IBundledDevice> getBundledConnectionCache() {
		return cache;
	}

	@Override
	public byte[] getBundledOutput(ForgeDirection side) {
		return tile.bundledCableSent[side.ordinal()].clone();
	}

	@Override
	public void setBundledPower(ForgeDirection side, byte[] power) {
		if (power.length == 16) {
			tile.bundledCableReceived[side.ordinal()] = power.clone();
		}
	}

	@Override
	public byte[] getBundledPower(ForgeDirection side) {
		return tile.bundledCableReceived[side.ordinal()].clone();
	}

	@Override
	public void onBundledUpdate() {
		tile.scheduleNeighborChange();
	}

	@Override
	public MinecraftColor getBundledColor(ForgeDirection side) {
		return MinecraftColor.NONE;
	}

	@Override
	public boolean isNormalFace(ForgeDirection side) {
		return true;
	}

	@Override
	public World getWorld() {
		return tile.getWorldObj();
	}

	@Override
	public int getX() {
		return tile.xCoord;
	}

	@Override
	public int getY() {
		return tile.yCoord;
	}

	@Override
	public int getZ() {
		return tile.zCoord;
	}
}
