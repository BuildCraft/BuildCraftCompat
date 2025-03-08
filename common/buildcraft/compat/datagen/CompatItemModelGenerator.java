package buildcraft.compat.datagen;

import buildcraft.compat.BCCompat;
import buildcraft.compat.BCCompatBlocks;
import buildcraft.datagen.base.BCBaseItemModelGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CompatItemModelGenerator extends BCBaseItemModelGenerator {
    public CompatItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BCCompat.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // fe engine
        getBuilder(BCCompatBlocks.engineFe.get().getRegistryName().toString()).parent(BUILTIN_ENTITY);
    }
}
