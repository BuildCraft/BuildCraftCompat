package buildcraft.compat;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.api.core.BCLog;
import buildcraft.compat.immibis.FakeWorldIMTesting;
import buildcraft.compat.immibis.SchematicTileMicroblocks;
import buildcraft.compat.immibis.SchematicTileMicroblocksBase;
import buildcraft.core.blueprints.SchematicRegistry;
import mods.immibis.core.api.multipart.ICoverableTile;

public class CompatModuleImmibisMicroblocks extends CompatModuleBase
{
    @Override
    public String name() {
        return "ImmibisMicroblocks";
    }

    @Override
    public boolean canLoad() {
        return super.canLoad() && Loader.isModLoaded("BuildCraft|Builders");
    }

    @Override
    public void init() {
        if (Loader.isModLoaded("BuildCraft|Builders")) {
            CompatUtils.registerSchematic("ImmibisMicroblocks:MicroblockContainer", SchematicTileMicroblocksBase.class);
        }
    }

    @Override
    public void postInit() {
        if (Loader.isModLoaded("BuildCraft|Builders")) {
            patchBuilders();
        }
    }

    @Optional.Method(modid = "BuildCraft|Builders")
    private void patchBuilders() {
        World test = new FakeWorldIMTesting();
        Set<String> ss = SchematicRegistry.INSTANCE.schematicBlocks.keySet();

        for (String s : ss.toArray(new String[ss.size()])) {
            String name = s.substring(0, s.lastIndexOf(":"));
            int meta = new Integer(s.substring(s.lastIndexOf(":") + 1));
            Block b = Block.getBlockFromName(name);
            if (b == null || meta < 0 || meta >= 16) {
                continue;
            }

            boolean isImmibis = false;

            if (b.hasTileEntity(meta)) {
                try {
                    isImmibis = b.createTileEntity(test, meta) instanceof ICoverableTile;
                } catch (Exception e) {

                }
            }

            if (isImmibis) {
                BCLog.logger.info("[BCCompat|ImmibisMicroblocks] Patching " + s);
                SchematicRegistry.SchematicConstructor c = SchematicRegistry.INSTANCE.schematicBlocks.get(s);
                SchematicRegistry.INSTANCE.schematicBlocks.remove(s);
                SchematicRegistry.INSTANCE.registerSchematicBlock(b, meta, SchematicTileMicroblocks.class, c);
            }
        }
    }
}
