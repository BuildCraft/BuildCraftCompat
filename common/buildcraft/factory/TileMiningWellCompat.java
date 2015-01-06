package buildcraft.factory;

import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.inventory.IInventoryConnection;

public class TileMiningWellCompat extends TileMiningWell implements IInventoryConnection {
	@Override
	public ConnectionType canConnectInventory(ForgeDirection from) {
		return ConnectionType.FORCE;
	}
}
