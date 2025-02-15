package buildcraft.compat.datagen;

import buildcraft.api.core.BCLog;
import buildcraft.core.BCCore;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.util.List;

@Mod.EventBusSubscriber(modid = BCCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCCompatDataGenerators {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        DataGenerator.PackGenerator packGenerator = generator.getVanillaPack(true);

        // BlockState and Block Model
        packGenerator.addProvider(packOutput -> new CompatBlockStateGenerator(packOutput, existingFileHelper));

        // Item Model
        packGenerator.addProvider(packOutput -> new CompatItemModelGenerator(packOutput, existingFileHelper));

        // Calen 1.20.1
        enableShouldExecute(generator);
    }

    // Calen 1.20.1 for datagen
    private static void enableShouldExecute(DataGenerator generator) {
        try {
            Field f_dataGeneratorConfig = DatagenModLoader.class.getDeclaredField("dataGeneratorConfig");
            f_dataGeneratorConfig.setAccessible(true);
            GatherDataEvent.DataGeneratorConfig config = (GatherDataEvent.DataGeneratorConfig) f_dataGeneratorConfig.get(null);
            Field f_generators = GatherDataEvent.DataGeneratorConfig.class.getDeclaredField("generators");
            f_generators.setAccessible(true);
            List<DataGenerator> generators = (List<DataGenerator>) f_generators.get(config);
            if (!generators.contains(generator)) {
                generators.add(generator);
            }
        } catch (Exception e) {
            BCLog.logger.error(e);
        }
    }
}
