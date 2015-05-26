package buildcraft.compat;

import cpw.mods.fml.common.Loader;

import buildcraft.api.crops.CropManager;
import buildcraft.compat.witchery.CropHandlerWitchery;

public class CompatModuleWitchery extends CompatModuleBase
{
    @Override
    public String name() {
        return "Witchery";
    }
    
    @Override
    public boolean canLoad() {
        return Loader.isModLoaded("witchery");
    }
    
    @Override
    public void init() {
        CropManager.registerHandler(new CropHandlerWitchery());
    }
}
