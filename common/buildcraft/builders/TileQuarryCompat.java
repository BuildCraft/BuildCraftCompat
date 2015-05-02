package buildcraft.builders;

import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.inventory.IInventoryConnection;
import buildcraft.builders.TileQuarry;

public class TileQuarryCompat extends TileQuarry implements IInventoryConnection {
	@Override
	public ConnectionType canConnectInventory(ForgeDirection from) {
		return ConnectionType.FORCE;
	}
}
