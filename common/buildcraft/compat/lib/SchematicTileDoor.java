package buildcraft.compat.lib;

import java.util.LinkedList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.MappingRegistry;
import buildcraft.api.blueprints.SchematicTile;

public class SchematicTileDoor extends SchematicTile {
	int upperMeta = 0;

	public void rotateLeft(IBuilderContext context) {
		this.meta = (((this.meta & 3) + 1) & 3) | (this.meta & (~3));
	}

	public boolean doNotBuild() {
		return (this.meta & 8) != 0;
	}

	public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
		return this.block == context.world().getBlock(x, y, z);
	}

	public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
		context.world().setBlock(x, y, z, this.block, this.meta, 3);
		context.world().setBlock(x, y + 1, z, this.block, this.upperMeta, 3);
		context.world().setBlockMetadataWithNotify(x, y + 1, z, this.upperMeta, 3);
		context.world().setBlockMetadataWithNotify(x, y, z, this.meta, 3);
	}

	public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		if((this.meta & 8) == 0) {
			this.upperMeta = context.world().getBlockMetadata(x, y + 1, z);
		}
	}

	public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
		super.writeSchematicToNBT(nbt, registry);
		nbt.setByte("upperMeta", (byte)this.upperMeta);
	}

	public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
		super.readSchematicFromNBT(nbt, registry);
		this.upperMeta = nbt.getByte("upperMeta");
	}
}
