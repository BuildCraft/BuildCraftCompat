package mods.immibis.core.api.net;

import java.io.IOException;

import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;

/**
 * This is passed through the Netty pipeline after a custom packet is decoded.
 */
public class IPacketWrapper extends Packet {
	
	public IPacket packet;
	
	public IPacketWrapper(IPacket packet) {
		this.packet = packet;
	}
	
	@Override
	public void processPacket(INetHandler var1) {
		if(var1 instanceof NetHandlerPlayServer)
			packet.onReceived(((NetHandlerPlayServer)var1).playerEntity);
		else
			packet.onReceived(null);
	}
	
	@Override
	public void readPacketData(PacketBuffer var1) throws IOException {
		throw new IOException("not serializable");
	}
	
	@Override
	public void writePacketData(PacketBuffer var1) throws IOException {
		throw new IOException("not serializable");
	}
}
