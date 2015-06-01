package buildcraft.texture;

import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextureManager {

	private static TextureManager instance;

	public static TextureManager getInstance() {
		if (instance == null) {
			instance = new TextureManager();
		}

		return instance;
	}

	private final List<String> iconNames = Arrays.asList(
			"propolisPipe/anything", "propolisPipe/bee", "propolisPipe/cave", "propolisPipe/closed", "propolisPipe/drone",
			"propolisPipe/flyer", "propolisPipe/item", "propolisPipe/nocturnal", "propolisPipe/princess", "propolisPipe/pure_breed",
			"propolisPipe/pure_cave", "propolisPipe/pure_flyer", "propolisPipe/pure_nocturnal", "propolisPipe/queen"
	);

	private final Map<String, IIcon> icons = new HashMap<String, IIcon>();

	private TextureManager() {
	}

	public void initIcons(IIconRegister register) {
		for (String iconName : iconNames) {
			IIcon icon = register.registerIcon("buildcraftcompat:" + iconName);
			icons.put(iconName, icon);
		}
	}

	public IIcon getIcon(String iconName) {
		return icons.get(iconName);
	}
}
