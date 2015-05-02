package buildcraft.builders;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockQuarryCompat extends BlockQuarry {
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileQuarryCompat();
	}
}
