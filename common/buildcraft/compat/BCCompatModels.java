package buildcraft.compat;

import buildcraft.api.enums.EnumPowerStage;
import buildcraft.compat.module.forge.EnumEnergyCompatEngineTypes;
import buildcraft.compat.module.forge.RenderEngineFe;
import buildcraft.compat.module.forge.TileEngineFe;
import buildcraft.core.block.BlockEngine_BC8;
import buildcraft.lib.client.model.ModelHolderVariable;
import buildcraft.lib.client.model.ModelItemSimple;
import buildcraft.lib.client.model.MutableQuad;
import buildcraft.lib.engine.TileEngineBase_BC8;
import buildcraft.lib.expression.DefaultContexts;
import buildcraft.lib.expression.FunctionContext;
import buildcraft.lib.expression.node.value.NodeVariableDouble;
import buildcraft.lib.expression.node.value.NodeVariableObject;
import buildcraft.lib.misc.ExpressionCompat;
import buildcraft.lib.misc.data.ModelVariableData;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.util.LazyLoadedValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class BCCompatModels {
    private static final NodeVariableDouble ENGINE_PROGRESS;
    private static final NodeVariableObject<EnumPowerStage> ENGINE_STAGE;
    private static final NodeVariableObject<Direction> ENGINE_FACING;

    private static final ModelHolderVariable ENGINE_FE;

    static {
        FunctionContext fnCtx = new FunctionContext(ExpressionCompat.ENUM_POWER_STAGE, DefaultContexts.createWithAll());
        ENGINE_PROGRESS = fnCtx.putVariableDouble("progress");
        ENGINE_STAGE = fnCtx.putVariableObject("stage", EnumPowerStage.class);
        ENGINE_FACING = fnCtx.putVariableObject("direction", Direction.class);
        // TODO: Item models from "item/engine_stone.json"
        ENGINE_FE = new ModelHolderVariable(
                "buildcraftcompat:models/tile/engine_fe.jsonbc",
                fnCtx
        );
        BlockEngine_BC8.setModel(EnumEnergyCompatEngineTypes.FE, ENGINE_FE);
    }

    public static void fmlPreInit() {
        // 1.18.2: following events are IModBusEvent
        IEventBus modEventBus = ((FMLModContainer) ModList.get().getModContainerById(BCCompat.MODID).get()).getEventBus();
        modEventBus.register(BCCompatModels.class);
    }

    @SubscribeEvent
    public static void onTesrReg(EntityRenderersEvent.RegisterRenderers event) {
        BlockEntityRenderers.register(BCCompatBlocks.engineFeTile.get(), RenderEngineFe::new);
    }

    // Calen 1.20.1
    private static final List<Runnable> spriteTasks = Lists.newLinkedList();

    // Calen 1.20.1
    @SubscribeEvent
    public static void onTextureStitchEvent$Post(TextureStitchEvent.Post event) {
        if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
            spriteTasks.forEach(Runnable::run);
        }
    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        ENGINE_PROGRESS.value = 0.2;
        ENGINE_STAGE.value = EnumPowerStage.BLUE;
        ENGINE_FACING.value = Direction.UP;
        ModelVariableData varData = new ModelVariableData();
        varData.setNodes(ENGINE_FE.createTickableNodes());
        varData.tick();
        varData.refresh();
        event.getModels().put(
                new ModelResourceLocation(BCCompatBlocks.engineFe.getId(), "inventory"),
                new ModelItemSimple(
                        new LazyLoadedValue<>(
                                () -> Arrays.stream(ENGINE_FE.getCutoutQuads())
                                        .map(MutableQuad::toBakedItem)
                                        .collect(Collectors.toList())
                        ),
                        ModelItemSimple.TRANSFORM_BLOCK,
                        true,
                        spriteTasks::add
                )
        );
    }

    private static MutableQuad[] getEngineQuads(ModelHolderVariable model,
            TileEngineBase_BC8 tile,
            float partialTicks) {
        ENGINE_PROGRESS.value = tile.getProgressClient(partialTicks);
        ENGINE_STAGE.value = tile.getPowerStage();
        ENGINE_FACING.value = tile.getCurrentFacing();
        if (tile.clientModelData.hasNoNodes()) {
            tile.clientModelData.setNodes(model.createTickableNodes());
        }
        tile.clientModelData.refresh();
        return model.getCutoutQuads();
    }

    public static MutableQuad[] getFeEngineQuads(TileEngineFe tile, float partialTicks) {
        return getEngineQuads(ENGINE_FE, tile, partialTicks);
    }
}
