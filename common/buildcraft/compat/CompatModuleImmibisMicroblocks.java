package buildcraft.compat;

import java.util.HashSet;
import java.util.Set;

import buildcraft.BuildCraftCompat;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

import buildcraft.api.blueprints.SchematicTile;
import buildcraft.api.core.BCLog;
import buildcraft.compat.immibis.FakeWorldIMTesting;
import buildcraft.compat.immibis.SchematicTileMicroblocks;
import buildcraft.compat.immibis.SchematicTileMicroblocksBase;
import buildcraft.core.blueprints.SchematicRegistry;
import mods.immibis.core.api.multipart.ICoverableTile;

public class CompatModuleImmibisMicroblocks extends CompatModuleBase {
    private Set<String> fcSet = new HashSet<>();
    private String[] forbiddenClasses = new String[] {
            "ic2.core.block.BlockMultiID"
    };

    @Override
    public String name() {
        return "ImmibisMicroblocks";
    }

    @Override
    public boolean canLoad() {
        return super.canLoad() && Loader.isModLoaded("BuildCraft|Builders");
    }

    @Override
    public void preInit() {
        forbiddenClasses = BuildCraftCompat.instance.getConfig().getStringList("forbiddenClasses", "immibismicroblocks", forbiddenClasses, "A list of block classes which should be ignored in the Immibis' Microblocks heuristics due to crashes.");

        fcSet.clear();
        for (String s : forbiddenClasses) {
            fcSet.add(s);
        }
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
            Class clazz = SchematicRegistry.INSTANCE.schematicBlocks.get(s).clazz;
            if (clazz == SchematicTileMicroblocks.class || !SchematicTile.class.isAssignableFrom(clazz)) {
                continue;
            }

            String name = s.substring(0, s.lastIndexOf(":"));
            int meta = new Integer(s.substring(s.lastIndexOf(":") + 1));
            Block b = Block.getBlockFromName(name);
            if (b == null || meta < 0 || meta >= 16 || fcSet.contains(b.getClass().getName())) {
                continue;
            }

            boolean isImmibis = false;

            if (b.hasTileEntity(meta)) {
                try {
					TileEntity t = b.createTileEntity(test, meta);
                    isImmibis = t instanceof ICoverableTile;
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
