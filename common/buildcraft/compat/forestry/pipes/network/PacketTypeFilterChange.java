package buildcraft.compat.forestry.pipes.network;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.compat.forestry.pipes.EnumFilterType;
import buildcraft.core.lib.network.PacketCoordinates;
import buildcraft.network.PacketIds;
import io.netty.buffer.ByteBuf;

public class PacketTypeFilterChange extends PacketCoordinates {

	private int orientation;
	private int filter;

	public PacketTypeFilterChange(){
	}

	public PacketTypeFilterChange(TileEntity tile, ForgeDirection orientation, EnumFilterType filter) {
		super(PacketIds.PROP_SEND_FILTER_CHANGE_TYPE, tile.xCoord, tile.yCoord, tile.zCoord);
		this.orientation = orientation.ordinal();
		this.filter = filter.ordinal();
	}

	@Override
	public void writeData(ByteBuf data) {
		super.writeData(data);
		data.writeShort(orientation);
		data.writeShort(filter);
	}

	@Override
	public void readData(ByteBuf data) {
		super.readData(data);
		orientation = data.readShort();
		filter = data.readShort();
	}

	public int getOrientation() {
		return orientation;
	}

	public int getFilter() {
		return filter;
	}
}
