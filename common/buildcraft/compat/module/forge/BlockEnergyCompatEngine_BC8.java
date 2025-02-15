package buildcraft.compat.module.forge;

import buildcraft.lib.engine.BlockEngineBase_BC8;
import buildcraft.lib.engine.TileEngineBase_BC8;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;

public class BlockEnergyCompatEngine_BC8 extends BlockEngineBase_BC8<EnumEnergyCompatEngineTypes> {
    public BlockEnergyCompatEngine_BC8(String idBC, BlockBehaviour.Properties properties, EnumEnergyCompatEngineTypes type, BiFunction<BlockPos, BlockState, ? extends TileEngineBase_BC8> engineTileConstructor) {
        super(idBC, properties, type, engineTileConstructor);
    }
}
