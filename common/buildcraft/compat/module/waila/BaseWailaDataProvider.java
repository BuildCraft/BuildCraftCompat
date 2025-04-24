package buildcraft.compat.module.waila;

import mcp.mobius.waila.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

//class BaseWailaDataProvider implements IWailaDataProvider
public interface BaseWailaDataProvider {

    static abstract class BodyProvider implements IComponentProvider {
        @Override
        public void appendBody(List<ITextComponent> currentTip, IDataAccessor accessor, IPluginConfig iPluginConfig) {
            getWailaBody(currentTip, accessor, iPluginConfig);
        }

        abstract void getWailaBody(List<ITextComponent> currentTip, IDataAccessor accessor, IPluginConfig iPluginConfig);
    }

    static abstract class NBTProvider implements IServerDataProvider<TileEntity> {
        @Override
        public void appendServerData(CompoundNBT nbt, ServerPlayerEntity player, World world, TileEntity tile) {
            getNBTData(nbt, player, world, tile);
        }

        abstract void getNBTData(CompoundNBT nbt, ServerPlayerEntity player, World world, TileEntity tile);
    }

    static abstract class BodyProviderEntity implements IEntityComponentProvider {
        @Override
        public void appendBody(List<ITextComponent> iTooltip, IEntityAccessor blockAccessor, IPluginConfig iPluginConfig) {
            getWailaBody(iTooltip, blockAccessor, iPluginConfig);
        }

        abstract void getWailaBody(List<ITextComponent> iTooltip, IEntityAccessor accessor, IPluginConfig iPluginConfig);
    }

    static abstract class NBTProviderEntity implements IServerDataProvider<Entity> {
        @Override
        public void appendServerData(CompoundNBT tag, ServerPlayerEntity player, World world, Entity entity) {
            getNBTData(tag, player, world, entity);
        }

        abstract void getNBTData(CompoundNBT tag, ServerPlayerEntity player, World world, Entity entity);
    }
}
