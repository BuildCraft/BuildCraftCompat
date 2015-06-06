package mods.immibis.core.api.porting;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class PortableTileEntity extends TileEntity {
	
	public void onDataPacket(S35PacketUpdateTileEntity packet) {}
	
	@Override public final void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity p) {
		onDataPacket(p);
	}
}
