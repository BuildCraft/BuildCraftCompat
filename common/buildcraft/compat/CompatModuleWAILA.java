package buildcraft.compat;

import cpw.mods.fml.common.event.FMLInterModComms;

import buildcraft.BuildCraftCompat;

public class CompatModuleWAILA extends CompatModuleBase {
	public static boolean ENABLE_BUILDER_DEBUG = false;

    @Override
    public String name() {
        return "Waila";
    }

	@Override
	public void preInit() {
		ENABLE_BUILDER_DEBUG = BuildCraftCompat.instance.getConfig().getBoolean("showBuilderSupportDebug", "waila", false, "Should WAILA tooltips show whether a block is supported by Builders or not?");
	}

    @Override
    public void init() {
        FMLInterModComms.sendMessage("Waila", "register", "buildcraft.compat.waila.WailaCallback.callback");
    }
}
