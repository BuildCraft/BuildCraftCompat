/**
 * Copyright (c) 2011-2014, SpaceToad and the BuildCraft Team
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package buildcraft.compat.forestry.pipes;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraft.transport.Pipe;
import buildcraft.transport.TileGenericPipe;

public class PipeHelper {
	public static <T extends Pipe> T getPipe(World world, int x, int y, int z, Class<T> pipeClass) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!(tile instanceof TileGenericPipe)) {
			return null;
		}

		Pipe pipe = ((TileGenericPipe) tile).pipe;
		if (!pipeClass.isInstance(pipe)) {
			return null;
		}

		return pipeClass.cast(pipe);
	}
}
