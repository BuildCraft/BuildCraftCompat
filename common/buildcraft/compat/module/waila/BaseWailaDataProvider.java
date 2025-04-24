package buildcraft.compat.module.waila;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

//class BaseWailaDataProvider implements IWailaDataProvider
public interface BaseWailaDataProvider {

    static abstract class BodyProvider implements IBlockComponentProvider {
        @Override
        public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
            getWailaBody(iTooltip, blockAccessor, iPluginConfig);
        }

        abstract void getWailaBody(ITooltip iTooltip, BlockAccessor accessor, IPluginConfig iPluginConfig);
    }

    //    static abstract class NBTProvider implements IServerDataProvider<BlockEntity>
    static abstract class NBTProvider implements IServerDataProvider<BlockAccessor> {
        @Override
//        public void appendServerData(CompoundTag tag, ServerPlayer player, Level blockAccessor, BlockEntity blockEntity, boolean showDetails)
        public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
            getNBTData(tag, accessor);
        }

        //        abstract void getNBTData(CompoundTag tag, ServerPlayer player, Level blockAccessor, BlockEntity blockEntity, boolean showDetails);
        abstract void getNBTData(CompoundTag tag, BlockAccessor accessor);
    }

    static abstract class BodyProviderEntity implements IEntityComponentProvider {
        @Override
        public void appendTooltip(ITooltip iTooltip, EntityAccessor blockAccessor, IPluginConfig iPluginConfig) {
            getWailaBody(iTooltip, blockAccessor, iPluginConfig);
        }

        abstract void getWailaBody(ITooltip iTooltip, EntityAccessor accessor, IPluginConfig iPluginConfig);
    }

    static abstract class NBTProviderEntity implements IServerDataProvider<EntityAccessor> {
        @Override
        public void appendServerData(CompoundTag tag, EntityAccessor entityAccessor) {
            getNBTData(tag, (ServerPlayer) entityAccessor.getPlayer(), entityAccessor.getLevel(), entityAccessor.getEntity(), entityAccessor.showDetails());
        }

        abstract void getNBTData(CompoundTag tag, ServerPlayer player, Level world, Entity entity, boolean showDetails);
    }
}
