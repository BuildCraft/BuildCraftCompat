package buildcraft.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetHandler;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

import buildcraft.compat.forestry.pipes.PipeHelper;
import buildcraft.compat.forestry.pipes.PipeItemsPropolis;
import buildcraft.compat.forestry.pipes.PipeLogicPropolis;
import buildcraft.compat.forestry.pipes.gui.ContainerPropolisPipe;
import buildcraft.compat.forestry.pipes.network.PacketGenomeFilterChange;
import buildcraft.compat.forestry.pipes.network.PacketRequestFilterSet;
import buildcraft.compat.forestry.pipes.network.PacketTypeFilterChange;
import buildcraft.core.lib.network.Packet;
import buildcraft.core.lib.network.PacketHandler;
import buildcraft.core.lib.network.PacketNBT;
import buildcraft.core.proxy.CoreProxy;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;

@Sharable
public class PacketHandlerCompat extends PacketHandler {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
		super.channelRead0(ctx, packet);
		INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
		EntityPlayer player = CoreProxy.proxy.getPlayerFromNetHandler(netHandler);

		switch (packet.getID()) {
			// CLIENT
			case PacketIds.PROP_SEND_FILTER_SET: {
				onFilterSet((PacketNBT) packet);
				break;
			}

			// SERVER
			case PacketIds.PROP_REQUEST_FILTER_SET: {
				onRequestFilterSet(player, (PacketRequestFilterSet) packet);
				break;
			}
			case PacketIds.PROP_SEND_FILTER_CHANGE_TYPE: {
				onTypeFilterChange(player, (PacketTypeFilterChange) packet);
				break;
			}
			case PacketIds.PROP_SEND_FILTER_CHANGE_GENOME: {
				onGenomeFilterChange(player, (PacketGenomeFilterChange) packet);
				break;
			}
		}
	}

	private static void onFilterSet(PacketNBT packet) {

		Container container = FMLClientHandler.instance().getClient().thePlayer.openContainer;

		if (container instanceof ContainerPropolisPipe) {
			PipeLogicPropolis pipeLogic = ((ContainerPropolisPipe) container).pipeLogic;
			pipeLogic.handleFilterSet(packet);
		}
	}

	private static void onTypeFilterChange(EntityPlayer player, PacketTypeFilterChange packet) {

		PipeItemsPropolis pipe = PipeHelper.getPipe(player.worldObj, packet.posX, packet.posY, packet.posZ, PipeItemsPropolis.class);
		if (pipe == null) {
			return;
		}

		pipe.pipeLogic.handleTypeFilterChange(packet);
	}

	private static void onGenomeFilterChange(EntityPlayer player, PacketGenomeFilterChange packet) {

		PipeItemsPropolis pipe = PipeHelper.getPipe(player.worldObj, packet.posX, packet.posY, packet.posZ, PipeItemsPropolis.class);
		if (pipe == null) {
			return;
		}

		pipe.pipeLogic.handleGenomeFilterChange(packet);
	}

	private static void onRequestFilterSet(EntityPlayer player, PacketRequestFilterSet packet) {

		PipeItemsPropolis pipe = PipeHelper.getPipe(player.worldObj, packet.posX, packet.posY, packet.posZ, PipeItemsPropolis.class);
		if (pipe == null) {
			return;
		}

		pipe.pipeLogic.sendFilterSet(player);
	}
}
