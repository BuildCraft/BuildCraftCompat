package mods.immibis.core.api.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;

public interface IPacket {
	/**
	 * Returns the packet type ID.
	 * Should always return a constant.
	 */
	public byte getID();
	
	/**
	 * Returns the packet's channel.
	 * Should always return a constant.
	 */
	public String getChannel();
	
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
	
	/** Source is the player who sent the packet, always null on the client */
	public void onReceived(EntityPlayer source);
	
	/**
	 * Marker interface for packets that should be decoded and processed (read() and onReceive()) asynchronously.
	 */
	@Deprecated
	public static interface Asynchronous extends IPacket {}
}
