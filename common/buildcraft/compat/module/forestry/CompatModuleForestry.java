package buildcraft.compat.module.forestry;

import net.minecraft.util.ResourceLocation;

import buildcraft.api.BCModules;
import buildcraft.api.core.BCLog;
import buildcraft.api.lists.ListRegistry;

import buildcraft.compat.CompatModuleBase;
import buildcraft.compat.module.forestry.list.ListMatchGenome;
import buildcraft.compat.module.forestry.pipe.ForestryPipes;

import forestry.api.core.ForestryAPI;
import forestry.modules.ForestryModuleUids;
import forestry.plugins.ForestryCompatPlugins;

public class CompatModuleForestry extends CompatModuleBase {
    @Override
    public String compatModId() {
        return "forestry";
    }

    @Override
    public void preInit() {
        ListRegistry.registerHandler(new ListMatchGenome());
        if (canLoadPropolisPipe()) {
            ForestryPipes.preInit();
        }
    }

    @Override
    public void init() {
        if (Boolean.getBoolean("buildcraft.temp_fix_old_forestry.remove_transport_module")) {
            // TEMPORARY TO FIX BUG IN OLDER FORESTRY!!!!
            /*
             * InventoryUtil
        @Optional.Method(modid = "BuildCraftAPI|transport")
             */
            ForestryAPI.enabledModules.remove(new ResourceLocation(ForestryCompatPlugins.ID, ForestryModuleUids.BUILDCRAFT_TRANSPORT));
        }
    }

    private static boolean canLoadPropolisPipe() {
        if (!BCModules.TRANSPORT.isLoaded()) {
            return false;
        }
        try {
            // Ensure that forestry is up-to-date
            Class.forName("forestry.sorting.tiles.IFilterContainer");
            return true;
        } catch (ClassNotFoundException ignored) {
            BCLog.logger.warn(
                "[compat.forestry] IFilterContainer not found -- forestry must be updated to add the propolis pipe!");
            return false;
        }
    }
}
