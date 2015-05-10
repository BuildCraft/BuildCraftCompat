package buildcraft.factory;

import com.bluepowermod.api.tube.ITubeConnection;
import com.bluepowermod.part.tube.TubeStack;

import cpw.mods.fml.common.Optional;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.inventory.IInventoryConnection;

@Optional.InterfaceList({
		@Optional.Interface(iface = "com.bluepowermod.api.tube.ITubeConnection", modid = "bluepowerAPI")
})
public class TileMiningWellCompat extends TileMiningWell implements IInventoryConnection, ITubeConnection {
	@Override
	public ConnectionType canConnectInventory(ForgeDirection from) {
		return ConnectionType.FORCE;
	}

	@Override
	public boolean isConnectedTo(ForgeDirection from) {
		return true;
	}

	@Override
	public TubeStack acceptItemFromTube(TubeStack tubeStack, ForgeDirection side, boolean simulate) {
		return tubeStack;
	}
}
