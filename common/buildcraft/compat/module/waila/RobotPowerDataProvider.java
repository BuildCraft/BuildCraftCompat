package buildcraft.compat.module.waila;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.robots.EntityRobotBase;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class RobotPowerDataProvider {
    static class BodyProvider extends BaseWailaDataProvider.BodyProviderEntity {
        @Override
        public void getWailaBody(List<ITextComponent> currentTip, IEntityAccessor accessor, IPluginConfig iPluginConfig) {
            if (accessor.getEntity() instanceof EntityRobotBase) {
                CompoundNBT nbt = accessor.getServerData();
                if (nbt.contains("mj_storage", Constants.NBT.TAG_COMPOUND)) {
                    CompoundNBT mj_storage_Tag = nbt.getCompound("mj_storage");
                    long mj_capability = mj_storage_Tag.getLong("mj_capability");
                    long mj_stored = mj_storage_Tag.getLong("mj_stored");
                    currentTip.add(new StringTextComponent(mj_stored + " MJ/" + mj_capability + "MJ"));
                }
            }
        }
    }

    static class NBTProvider extends BaseWailaDataProvider.NBTProviderEntity {
        @Override
        public void getNBTData(CompoundNBT nbt, ServerPlayerEntity player, World world, Entity entity) {
            if (entity instanceof EntityRobotBase) {
                CompoundNBT mj_storage_Tag = new CompoundNBT();
                mj_storage_Tag.putLong("mj_capability", ((EntityRobotBase) entity).getBattery().getCapacity() / MjAPI.MJ);
                mj_storage_Tag.putLong("mj_stored", ((EntityRobotBase) entity).getBattery().getStored() / MjAPI.MJ);
                nbt.put("mj_storage", mj_storage_Tag);
            }
        }
    }
}
