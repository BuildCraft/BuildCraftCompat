package buildcraft.compat.module.waila;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.robots.EntityRobotBase;
import buildcraft.robotics.BCRobotics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IProgressStyle;
import snownee.jade.impl.ui.ProgressElement;
import snownee.jade.impl.ui.ProgressStyle;

public class RobotPowerDataProvider {
    static class BodyProvider extends BaseWailaDataProvider.BodyProviderEntity {
        private static final IProgressStyle PROGRESS_STYLE = new ProgressStyle()
                .color(-15731468, -16721961)
                .textColor(DyeColor.WHITE.getTextColor());

        @Override
        public void getWailaBody(ITooltip currentTip, EntityAccessor accessor, IPluginConfig iPluginConfig) {
            if (accessor.getEntity() instanceof EntityRobotBase) {
                CompoundTag nbt = accessor.getServerData();
                if (nbt.contains("mj_storage", Tag.TAG_COMPOUND)) {
                    CompoundTag mj_storage_Tag = nbt.getCompound("mj_storage");
                    long mj_capability = mj_storage_Tag.getLong("mj_capability");
                    long mj_stored = mj_storage_Tag.getLong("mj_stored");
                    currentTip.add(new ProgressElement((float) (((double) mj_stored) / ((double) mj_capability)), Component.literal(mj_stored + " MJ/" + mj_capability + "MJ"), PROGRESS_STYLE, BoxStyle.DEFAULT, false));
                }
            }
        }

        @Override
        public ResourceLocation getUid() {
            return ResourceLocation.tryBuild(BCRobotics.MODID, "robot_power_client_data");
        }
    }

    static class NBTProvider extends BaseWailaDataProvider.NBTProviderEntity {
        @Override
        public void getNBTData(CompoundTag nbt, ServerPlayer player, Level world, Entity entity, boolean showDetails) {
            if (entity instanceof EntityRobotBase) {
                CompoundTag mj_storage_Tag = new CompoundTag();
                mj_storage_Tag.putLong("mj_capability", ((EntityRobotBase) entity).getBattery().getCapacity() / MjAPI.MJ);
                mj_storage_Tag.putLong("mj_stored", ((EntityRobotBase) entity).getBattery().getStored() / MjAPI.MJ);
                nbt.put("mj_storage", mj_storage_Tag);
            }
        }

        @Override
        public ResourceLocation getUid() {
            return ResourceLocation.tryBuild(BCRobotics.MODID, "robot_power_server_data");
        }
    }
}
