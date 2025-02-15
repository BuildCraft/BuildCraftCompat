package buildcraft.compat.datagen;

import buildcraft.compat.BCCompat;
import buildcraft.compat.BCCompatBlocks;
import buildcraft.datagen.base.BCBaseItemModelGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CompatItemModelGenerator extends BCBaseItemModelGenerator {
    public CompatItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BCCompat.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // fe engine
        getBuilder(BCCompatBlocks.engineFe.get().getRegistryName().toString()).parent(BUILTIN_ENTITY);
    }
}
