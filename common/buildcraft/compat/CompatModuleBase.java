package buildcraft.compat;

import cpw.mods.fml.common.*;

public abstract class CompatModuleBase
{
    public boolean canLoad() {
        return Loader.isModLoaded(this.name());
    }
    
    public abstract String name();
    
    public String comment() {
        return null;
    }

    public void preInit() {
    }

    public void init() {
    }
    
    public void postInit() {
    }
}
