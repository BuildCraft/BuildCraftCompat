package buildcraft.compat.forestry.pipes.network;

import buildcraft.core.lib.network.PacketCoordinates;
import buildcraft.network.PacketIds;

public class PacketRequestFilterSet extends PacketCoordinates {

	public PacketRequestFilterSet() {

	}

	public PacketRequestFilterSet(int x, int y, int z) {
		super(PacketIds.PROP_REQUEST_FILTER_SET, x, y, z);
	}
}
