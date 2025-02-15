package buildcraft.compat.module.waila;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
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
}
