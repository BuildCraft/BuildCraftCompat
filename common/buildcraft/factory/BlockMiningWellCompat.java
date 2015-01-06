package buildcraft.factory;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMiningWellCompat extends BlockMiningWell {
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileMiningWellCompat();
	}
}
