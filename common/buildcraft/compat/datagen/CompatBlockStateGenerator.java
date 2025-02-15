package buildcraft.compat.datagen;

import buildcraft.compat.BCCompat;
import buildcraft.compat.BCCompatBlocks;
import buildcraft.datagen.base.BCBaseBlockStateGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CompatBlockStateGenerator extends BCBaseBlockStateGenerator {
    public CompatBlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BCCompat.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // fe engine
        builtinEntity(BCCompatBlocks.engineFe.get(), "buildcraftcompat:block/engine/fe/back");
    }
}
