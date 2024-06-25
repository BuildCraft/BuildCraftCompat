package buildcraft.compat.module.waila;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
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
}
