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
package buildcraft.compat.tfc;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import buildcraft.api.core.IIconProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PipeIconProvider implements IIconProvider
{
	public enum TYPE
	{
		PipeStructureLead("PipeStructureLead"),
		//
		PipeTin("PipeTin"),
		PipeTin_Solid("PipeTin_Solid"),
		//
		PipeLead("PipeLead"),
		//
		PipeBronze("PipeBronze"),
		//
		PipeBrass("PipeBrass"),
		//
		PipeSterlingSilver("PipeSterlingSilver"),
		//
		PipeWroughtIron("PipeWroughtIron"),
		PipeWroughtIron_Solid("PipeWroughtIron_Solid"),
		//
		PipeSteel("PipeSteel"),
		//
		PipeBlueSteel("PipeBlueSteel"),
		PipeBlueSteel_down("PipeBlueSteel_down"),
		PipeBlueSteel_east("PipeBlueSteel_east"),
		PipeBlueSteel_north("PipeBlueSteel_north"),
		PipeBlueSteel_south("PipeBlueSteel_south"),
		PipeBlueSteel_up("PipeBlueSteel_up"),
		PipeBlueSteel_west("PipeBlueSteel_west"),
		//
		PipeRedSteel("PipeRedSteel"),
		PipeRedSteel_Solid("PipeRedSteel_Solid"),
		//
		PipeBlackBronze("PipeBlackBronze"),
		PipeBlackBronze_Solid("PipeBlackBronze_Solid"),
		//
		PipeRoseGold("PipeRoseGold"),
		//
		PipeBlackSteel("PipeBlackSteel"),
		//
		PipeFluidsTin("PipeFluidsTin"),
		PipeFluidsLead("PipeFluidsLead"),
		PipeFluidsBronze("PipeFluidsBronze"),
		PipeFluidsWroughtIron("PipeFluidsWroughtIron"),
		PipeFluidsSteel("PipeFluidsSteel"),
		PipeFluidsRedSteel("PipeFluidsRedSteel"),
		PipeFluidsBlackBronze("PipeFluidsBlackBronze"),
		PipeFluidsRoseGold("PipeFluidsRoseGold"),
		//
		PipePowerCopper("PipePowerCopper"),
		PipePowerRoseGold("PipePowerRoseGold"),
		PipePowerBlackBronze("PipePowerBlackBronze"),
		PipePowerWroughtIron("PipePowerWroughtIron"),
		PipePowerSteel("PipePowerSteel"),
		PipePowerPlatinum("PipePowerPlatinum"),
		PipePowerSilver("PipePowerSilver");
		public static final TYPE[] VALUES = values();
		private final String iconTag;
		private IIcon icon;

		private TYPE(String iconTag) { this.iconTag = iconTag; }
		private void registerIcon(IIconRegister iconRegister)
		{
			icon = iconRegister.registerIcon("BuildCraft|Compat:tfc/" + iconTag);
		}
		public IIcon getIcon() { return icon; }
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int pipeIconIndex)
	{
		if (pipeIconIndex == -1)
			return null;
		return TYPE.VALUES[pipeIconIndex].icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		for (TYPE type : TYPE.VALUES)
			type.registerIcon(iconRegister);
	}

}
