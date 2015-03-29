package buildcraft.compat;

import cpw.mods.fml.common.Loader;

public abstract class CompatModuleBase {
	public boolean canLoad() {
		return Loader.isModLoaded(name());
	}

	public abstract String name();

	public String comment() {
		return null;
	}

	public void init() {

	}

	public void postInit() {

	}
}
