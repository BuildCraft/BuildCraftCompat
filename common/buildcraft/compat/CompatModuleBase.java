package buildcraft.compat;

import net.minecraftforge.fml.common.Loader;

public abstract class CompatModuleBase {
    public boolean canLoad() {
        return Loader.isModLoaded(this.name());
    }

    public abstract String name();

    public String comment() {
        return null;
    }

    public void preInit() {}

    public void init() {}

    public void postInit() {}
}
