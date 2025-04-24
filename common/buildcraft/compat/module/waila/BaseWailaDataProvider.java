package buildcraft.compat.module.waila;

import mcp.mobius.waila.api.*;
import mcp.mobius.waila.api.config.IPluginConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

//class BaseWailaDataProvider implements IWailaDataProvider
public interface BaseWailaDataProvider {

    static abstract class BodyProvider implements IComponentProvider {
        @Override
        public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
            getWailaBody(iTooltip, blockAccessor, iPluginConfig);
        }

        abstract void getWailaBody(ITooltip iTooltip, BlockAccessor accessor, IPluginConfig iPluginConfig);
    }

    static abstract class NBTProvider implements IServerDataProvider<BlockEntity> {
        @Override
        public void appendServerData(CompoundTag tag, ServerPlayer player, Level blockAccessor, BlockEntity blockEntity, boolean showDetails) {
            getNBTData(tag, player, blockAccessor, blockEntity, showDetails);
        }

        abstract void getNBTData(CompoundTag tag, ServerPlayer player, Level blockAccessor, BlockEntity blockEntity, boolean showDetails);
    }

    static abstract class BodyProviderEntity implements IEntityComponentProvider {
        @Override
        public void appendTooltip(ITooltip iTooltip, EntityAccessor blockAccessor, IPluginConfig iPluginConfig) {
            getWailaBody(iTooltip, blockAccessor, iPluginConfig);
        }

        abstract void getWailaBody(ITooltip iTooltip, EntityAccessor accessor, IPluginConfig iPluginConfig);
    }

    static abstract class NBTProviderEntity implements IServerDataProvider<Entity> {
        @Override
        public void appendServerData(CompoundTag tag, ServerPlayer player, Level world, Entity entity, boolean showDetails) {
            getNBTData(tag, player, world, entity, showDetails);
        }

        abstract void getNBTData(CompoundTag tag, ServerPlayer player, Level world, Entity entity, boolean showDetails);
    }
}
