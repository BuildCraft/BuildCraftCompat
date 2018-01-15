package buildcraft.compat.module.waila;

import static buildcraft.compat.module.waila.HWYLAPlugin.WAILA_MOD_ID;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

@Optional.InterfaceList({
        @Optional.Interface(modid = WAILA_MOD_ID, iface = "mcp.mobius.waila.api.IWailaDataProvider")
})
class BaseWailaDataProvider implements IWailaDataProvider {
    @Nonnull
    @Override
    @Optional.Method(modid = WAILA_MOD_ID)
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    @Optional.Method(modid = WAILA_MOD_ID)
    public List<String> getWailaHead(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currentTip;
    }

    @Nonnull
    @Override
    @Optional.Method(modid = WAILA_MOD_ID)
    public List<String> getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currentTip;
    }

    @Nonnull
    @Override
    @Optional.Method(modid = WAILA_MOD_ID)
    public List<String> getWailaTail(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currentTip;
    }

    @Nonnull
    @Override
    @Optional.Method(modid = WAILA_MOD_ID)
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        return tag;
    }
}
