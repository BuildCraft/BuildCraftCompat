package buildcraft.compat.module.forge;

import buildcraft.api.core.IEngineType;
import net.minecraft.util.StringRepresentable;

public enum EnumEnergyCompatEngineTypes implements StringRepresentable, IEngineType {
    FE("compat", "fe");

    public static final EnumEnergyCompatEngineTypes[] VALUES = values();

    public final String unlocalizedTag;

    EnumEnergyCompatEngineTypes(String mod, String loc) {
        unlocalizedTag = loc;
    }

    @Override
    public String getSerializedName() {
        return unlocalizedTag;
    }
}
