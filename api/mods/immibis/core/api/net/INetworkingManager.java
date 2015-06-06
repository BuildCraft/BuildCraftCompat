package mods.immibis.core.api.net;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;

public interface INetworkingManager {
	public Packet wrap(IPacket packet, boolean isFromServer);
	
	public void sendToServer(IPacket packet);
	public void sendToClient(IPacket packet, EntityPlayer target);
	
	public void sendToServer(ISimplePacket packet);
	public void sendToClient(ISimplePacket packet, EntityPlayerMP target);
	
	public void send(IPacket packet, NetworkManager conn, boolean isFromServer);

	public void listen(IPacketMap handler);
	
	/**
	 * After this is called, all raw ByteBufs that would be sent on the wire
	 * will be added to the capture (instead of being sent) until stopCapturingPackets
	 * is called.
	 * 
	 * NOT IMPLEMENTED.
	 */
	public void startCapturingPackets(PacketCapture cap);
	public void stopCapturingPackets(PacketCapture cap);
}
