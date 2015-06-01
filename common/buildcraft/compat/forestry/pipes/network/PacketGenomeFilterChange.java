package buildcraft.compat.forestry.pipes.network;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.common.network.ByteBufUtils;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;

import buildcraft.core.lib.network.PacketCoordinates;
import buildcraft.network.PacketIds;
import io.netty.buffer.ByteBuf;

public class PacketGenomeFilterChange extends PacketCoordinates {

	private int orientation;
	private int pattern;
	private int allele;
	private String species;

	public PacketGenomeFilterChange() {
	}

	public PacketGenomeFilterChange(TileEntity tile, ForgeDirection orientation, int pattern, int allele, IAllele species) {
		super(PacketIds.PROP_SEND_FILTER_CHANGE_GENOME, tile.xCoord, tile.yCoord, tile.zCoord);

		this.orientation = orientation.ordinal();
		this.pattern = pattern;
		this.allele = allele;
		if (species != null) {
			this.species = species.getUID();
		} else {
			this.species = "";
		}
	}

	@Override
	public void writeData(ByteBuf data) {
		super.writeData(data);
		data.writeShort(orientation);
		data.writeShort(pattern);
		data.writeShort(allele);
		ByteBufUtils.writeUTF8String(data, species);
	}

	@Override
	public void readData(ByteBuf data) {
		super.readData(data);
		orientation = data.readShort();
		pattern = data.readShort();
		allele = data.readShort();
		species = ByteBufUtils.readUTF8String(data);
	}

	public int getOrientation() {
		return orientation;
	}

	public int getPattern() {
		return pattern;
	}

	public int getAllele() {
		return allele;
	}

	public IAllele getSpecies() {
		if (species.length() == 0) {
			return null;
		}
		return AlleleManager.alleleRegistry.getAllele(species);
	}
}
