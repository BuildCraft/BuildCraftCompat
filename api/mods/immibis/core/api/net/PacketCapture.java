package mods.immibis.core.api.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;

/** NOT USED */
public class PacketCapture {
	public List<ByteBuf> rawBuffers = new ArrayList<>();
	public Channel channel;
}
