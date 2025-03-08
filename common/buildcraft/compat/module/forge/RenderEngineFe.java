package buildcraft.compat.module.forge;

import buildcraft.compat.BCCompatModels;
import buildcraft.lib.client.model.MutableQuad;
import buildcraft.lib.client.render.tile.RenderEngine_BC8;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderEngineFe extends RenderEngine_BC8<TileEngineFe> {
    public RenderEngineFe(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected MutableQuad[] getEngineModel(TileEngineFe engine, float partialTicks) {
        return BCCompatModels.getFeEngineQuads(engine, partialTicks);
    }
}
