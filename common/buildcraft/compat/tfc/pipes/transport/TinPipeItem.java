/**
 *  Copyright (C) 2013  emris
 *  https://github.com/emris/BCTFCcrossover
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package buildcraft.compat.tfc.pipes.transport;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.core.IIconProvider;
import buildcraft.transport.pipes.PipeItemsWood;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import buildcraft.compat.tfc.TFCCompat;
import buildcraft.compat.tfc.PipeIconProvider;

public class TinPipeItem extends PipeItemsWood
{
	protected int standardIconIndex = PipeIconProvider.TYPE.PipeTin.ordinal();
	protected int solidIconIndex = PipeIconProvider.TYPE.PipeTin_Solid.ordinal();
	
	public TinPipeItem(Item item)
	{
		super(item);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIconProvider getIconProvider()
	{
		return TFCCompat.instance.pipeIconProvider;
	}

	@Override
	public int getIconIndex(ForgeDirection direction)
	{
		if (direction == ForgeDirection.UNKNOWN)
			return standardIconIndex;
		else
		{
			int metadata = container.getBlockMetadata();

			if (metadata == direction.ordinal())
				return solidIconIndex;
			else
				return standardIconIndex;
		}
	}
}
