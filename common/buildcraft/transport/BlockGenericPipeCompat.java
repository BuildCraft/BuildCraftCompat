package buildcraft.transport;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGenericPipeCompat extends BlockGenericPipe {
	/* IMMIBIS' MICROBLOCKS */
	//private boolean ImmibisMicroblocks_TransformableBlockMarker;
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileGenericPipeCompat();
	}
}
