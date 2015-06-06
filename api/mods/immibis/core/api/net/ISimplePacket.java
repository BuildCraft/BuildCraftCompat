package mods.immibis.core.api.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;

/**
 * Like IPacket, but packet type is identified by class name, instead of packet ID and channel name.
 * 
 * Classes implementing ISimplePacket must have a nullary constructor.
 */
public interface ISimplePacket {
	/**
	 * Reads the packet's contents from the given stream.
	 * Must be able to be safely called on any thread.
	 */
	public void read(DataInputStream in) throws IOException;
	
	/**
	 * Writes the packet's contents to the given stream.
	 * Must be able to be safely called on any thread.
	 */
	public void write(DataOutputStream out) throws IOException;
	
	/** Source is the player who sent the packet, or null on the client */
	public void onReceived(EntityPlayer source, NetworkManager connection);
}
