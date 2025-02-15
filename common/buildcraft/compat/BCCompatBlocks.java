package buildcraft.compat;

import buildcraft.compat.module.forge.BlockEnergyCompatEngine_BC8;
import buildcraft.compat.module.forge.EnumEnergyCompatEngineTypes;
import buildcraft.compat.module.forge.TileEngineFe;
import buildcraft.core.item.ItemEngine_BC8;
import buildcraft.lib.block.BlockPropertiesCreator;
import buildcraft.lib.registry.RegistrationHelper;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class BCCompatBlocks {
    public static final RegistrationHelper HELPER = new RegistrationHelper(BCCompat.MODID);
    public static final RegistryObject<BlockEnergyCompatEngine_BC8> engineFe;
    public static final RegistryObject<BlockEntityType<TileEngineFe>> engineFeTile;

    static {
        engineFe = HELPER.addBlockAndItem("block.engine.bc." + EnumEnergyCompatEngineTypes.FE.getSerializedName(),
                BlockPropertiesCreator.metal()
                        .strength(5.0F, 10.0F)
                        .sound(SoundType.METAL)
                        .noOcclusion()
                , (idBC, properties) -> new BlockEnergyCompatEngine_BC8(idBC, properties, EnumEnergyCompatEngineTypes.FE, TileEngineFe::new), ItemEngine_BC8::new);
        engineFeTile = HELPER.registerTile("tile.engine.fe", TileEngineFe::new, engineFe);
    }

    public static void fmlPreInit() {
    }
}
