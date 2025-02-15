package buildcraft.compat.module.waila;

import buildcraft.api.mj.ILaserTarget;
import buildcraft.api.mj.MjAPI;
import buildcraft.silicon.BCSilicon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

abstract class LaserTargetDataProvider {

    static class BodyProvider extends BaseWailaDataProvider.BodyProvider {
        @Override
        public void getWailaBody(ITooltip currentTip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
            BlockEntity tile = accessor.getBlockEntity();
            if (tile instanceof ILaserTarget) {
                CompoundTag nbt = accessor.getServerData();
                if (nbt.contains("required_power", Tag.TAG_LONG)) {
                    long power = nbt.getLong("required_power");
                    if (power > 0L) {
                        currentTip.add(Component.translatable("buildcraft.waila.waiting_for_laser", MjAPI.formatMj(power)));
                    }
                }
            }
//            else {
//                currentTip.add(new TextComponent(ChatFormatting.RED + "{wrong tile entity}"));
//            }
        }

        @Override
        public ResourceLocation getUid() {
            return ResourceLocation.tryBuild(BCSilicon.MODID, "laser_target_client_data");
        }
    }

    static class NBTProvider extends BaseWailaDataProvider.NBTProvider {
        @Override
        public void getNBTData(CompoundTag nbt, BlockAccessor accessor) {
            if (accessor.getBlockEntity() instanceof ILaserTarget target) {
                nbt.putLong("required_power", target.getRequiredLaserPower());
            }
        }

        @Override
        public ResourceLocation getUid() {
            return ResourceLocation.tryBuild(BCSilicon.MODID, "laser_target_server_data");
        }
    }
}
