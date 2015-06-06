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

import buildcraft.api.transport.IPipe;
import buildcraft.api.transport.IPipeTile;
import buildcraft.transport.Pipe;

public class PipeHelper {
	public static <T extends Pipe> T getPipe(World world, int x, int y, int z, Class<T> pipeClass) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!(tile instanceof IPipeTile)) {
			return null;
		}

		IPipe pipe = ((IPipeTile) tile).getPipe();
		if (!pipeClass.isInstance(pipe)) {
			return null;
		}

		return pipeClass.cast(pipe);
	}
}
