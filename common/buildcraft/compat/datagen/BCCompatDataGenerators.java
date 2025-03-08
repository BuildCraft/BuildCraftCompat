package buildcraft.compat.datagen;

import buildcraft.core.BCCore;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BCCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCCompatDataGenerators {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // BlockState and Block Model
        generator.addProvider(new CompatBlockStateGenerator(generator, existingFileHelper));

        // Item Model
        generator.addProvider(new CompatItemModelGenerator(generator, existingFileHelper));
    }
}
