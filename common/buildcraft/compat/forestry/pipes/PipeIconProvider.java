package buildcraft.compat.forestry.pipes;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraft.api.core.IIconProvider;

public class PipeIconProvider implements IIconProvider {

	private static IIcon[] icons;

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int iconIndex) {
		return icons[iconIndex];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icons = new IIcon[8];
		icons[0] = iconRegister.registerIcon("buildcraftcompat:pipes/pipe_prop.empty");
		icons[1] = iconRegister.registerIcon("buildcraftcompat:pipes/pipe_prop.black");
		icons[2] = iconRegister.registerIcon("buildcraftcompat:pipes/pipe_prop.white");
		icons[3] = iconRegister.registerIcon("buildcraftcompat:pipes/pipe_prop.red");
		icons[4] = iconRegister.registerIcon("buildcraftcompat:pipes/pipe_prop.blue");
		icons[5] = iconRegister.registerIcon("buildcraftcompat:pipes/pipe_prop.green");
		icons[6] = iconRegister.registerIcon("buildcraftcompat:pipes/pipe_prop.yellow");
		icons[7] = iconRegister.registerIcon("buildcraftcompat:pipes/pipe_prop.item");
	}
}
