package buildcraft.compat.datagen;

import buildcraft.compat.BCCompat;
import buildcraft.compat.BCCompatBlocks;
import buildcraft.datagen.base.BCBaseBlockStateGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CompatBlockStateGenerator extends BCBaseBlockStateGenerator {
    public CompatBlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, BCCompat.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // fe engine
        builtinEntity(BCCompatBlocks.engineFe.get(), "buildcraftcompat:block/engine/fe/back");
    }
}
