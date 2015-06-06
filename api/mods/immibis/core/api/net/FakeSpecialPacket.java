package mods.immibis.core.api.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.io.IOException;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;

/**
 * If a FakeSpecialPacket goes through the networking pipeline,
 * its `send` method will be called at the first step in the pipeline, instead of
 * forwarding it. The `send` method can send multiple packets, or no packets, or do other
 * network-related processing.
 */
public abstract class FakeSpecialPacket extends Packet {
	
	public abstract void send(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception;

	@Override
	public final void readPacketData(PacketBuffer var1) throws IOException {
		throw new AssertionError("should not get here");
	}

	@Override
	public final void writePacketData(PacketBuffer var1) throws IOException {
		throw new AssertionError("should not get here");
	}

	@Override
	public final void processPacket(INetHandler var1) {
		throw new AssertionError("should not get here");
	}

}