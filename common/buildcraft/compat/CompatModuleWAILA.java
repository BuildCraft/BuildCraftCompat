package buildcraft.compat;

import mcp.mobius.waila.api.IWailaRegistrar;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;
import buildcraft.api.blueprints.Schematic;
import buildcraft.compat.carpentersblocks.SchematicCBBlock;
import buildcraft.compat.carpentersblocks.SchematicCBGate;
import buildcraft.compat.carpentersblocks.SchematicCBRotated;
import buildcraft.compat.carpentersblocks.SchematicCBRotatedTwo;
import buildcraft.compat.carpentersblocks.SchematicCBSafe;
import buildcraft.compat.lib.SchematicTileDrops;
import buildcraft.compat.waila.EntityRobotDataProvider;
import buildcraft.robotics.EntityRobot;

public class CompatModuleWAILA extends CompatModuleBase {
    @Override
    public String name() {
        return "Waila";
    }

    @Override
    public void init() {
        FMLInterModComms.sendMessage("Waila", "register", "buildcraft.compat.waila.WailaCallback.callback");
    }
}
