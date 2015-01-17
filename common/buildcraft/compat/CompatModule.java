package buildcraft.compat;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class CompatModule {
	public abstract String getModID();
	public abstract String getConfigDescription();

	public void preInit() {

	}

	public void init() {

	}

	@SideOnly(Side.CLIENT)
	public void initClient() {

	}

	public void postInit() {

	}

	@SideOnly(Side.CLIENT)
	public void postInitClient() {

	}
}
