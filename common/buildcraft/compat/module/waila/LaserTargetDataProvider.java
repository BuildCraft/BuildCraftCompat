package buildcraft.compat.module.waila;

import static buildcraft.compat.module.waila.HWYLAPlugin.WAILA_MOD_ID;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Optional;
import buildcraft.api.mj.ILaserTarget;
import buildcraft.api.mj.MjAPI;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

class LaserTargetDataProvider extends BaseWailaDataProvider {
    @Nonnull
    @Override
    @Optional.Method(modid = WAILA_MOD_ID)
    public List<String> getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileEntity tile = accessor.getTileEntity();
        if (tile instanceof ILaserTarget) {
            NBTTagCompound nbt = accessor.getNBTData();
            if (nbt.hasKey("required_power", Constants.NBT.TAG_LONG)) {
                long power = nbt.getLong("required_power");
                if (power > 0) {
                    currentTip.add(TextFormatting.WHITE + "Waiting from laser: " + TextFormatting.AQUA + MjAPI.formatMj(power) + " MJ");
                }
            }
        } else {
            currentTip.add(TextFormatting.RED + "{wrong tile entity}");
        }
        return currentTip;
    }

    @Nonnull
    @Override
    @Optional.Method(modid = WAILA_MOD_ID)
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        NBTTagCompound nbt = super.getNBTData(player, te, tag, world, pos);

        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof ILaserTarget) {
            ILaserTarget target = ILaserTarget.class.cast(tile);
            nbt.setLong("required_power", target.getRequiredLaserPower());
        }

        return nbt;
    }
}
