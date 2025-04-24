package buildcraft.compat.module.waila;

import buildcraft.api.mj.MjAPI;
import buildcraft.lib.tile.TileBC_Neptune;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class MjStorageDataProvider {
    static class BodyProvider extends BaseWailaDataProvider.BodyProvider {
        @Override
        public void getWailaBody(List<ITextComponent> currentTip, IDataAccessor accessor, IPluginConfig iPluginConfig) {
            if (accessor.getTileEntity() instanceof TileBC_Neptune) {
                TileBC_Neptune tileBC = (TileBC_Neptune) accessor.getTileEntity();
                tileBC.getCapability(MjAPI.CAP_READABLE).ifPresent(mjReadable -> {
                    CompoundNBT nbt = accessor.getServerData();
                    if (nbt.contains("mj_storage", Constants.NBT.TAG_COMPOUND)) {
                        CompoundNBT mj_storage_Tag = nbt.getCompound("mj_storage");
                        long mj_capability = mj_storage_Tag.getLong("mj_capability");
                        long mj_stored = mj_storage_Tag.getLong("mj_stored");
                        currentTip.add(new StringTextComponent(mj_stored + " MJ/" + mj_capability + "MJ"));
                    }
                });
            }
        }
    }

    static class NBTProvider extends BaseWailaDataProvider.NBTProvider {
        @Override
        public void getNBTData(CompoundNBT nbt, ServerPlayerEntity player, World world, TileEntity tile) {
            if (tile instanceof TileBC_Neptune) {
                TileBC_Neptune tileBC = (TileBC_Neptune) tile;
                tileBC.getCapability(MjAPI.CAP_READABLE).ifPresent(mjReadable -> {
                    CompoundNBT mj_storage_Tag = new CompoundNBT();
                    mj_storage_Tag.putLong("mj_capability", mjReadable.getCapacity() / MjAPI.MJ);
                    mj_storage_Tag.putLong("mj_stored", mjReadable.getStored() / MjAPI.MJ);
                    nbt.put("mj_storage", mj_storage_Tag);
                });
            }
        }
    }
}
