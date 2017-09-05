package buildcraft.compat;

import net.minecraftforge.fml.common.Loader;

public abstract class CompatModuleBase {
    public boolean canLoad() {
        return Loader.isModLoaded(this.compatModId());
    }

    public abstract String compatModId();

    public String comment() {
        return null;
    }

    public void preInit() {}

    public void init() {}

    public void postInit() {}
}
