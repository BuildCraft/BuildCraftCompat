package buildcraft.compat.network;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.network.IGuiHandler;

import buildcraft.compat.BCCompat;
import buildcraft.compat.CompatUtils;

// Half-decent class (inspired by how forestry does things)
// This really wants fleshing out and moving into mainline buildcraft though
// Along with a sensible way of dealing with pluggables etc
public enum CompatGui {

    FORESTRY_PROPOLIS_PIPE(IGuiTarget.TILE);

    static final CompatGui[] VALUES = values();

    @SidedProxy(modId = BCCompat.MODID)
    public static CommonProxy guiHandlerProxy;

    public final IGuiTarget target;

    private CompatGui(IGuiTarget target) {
        this.target = target;
    }

    public void openGui(EntityPlayer player) {
        openGui(player, 0, 0, 0, 0);
    }

    public void openGui(EntityPlayer player, BlockPos pos) {
        openGui(player, pos.getX(), pos.getY(), pos.getZ(), 0);
    }

    public void openGui(EntityPlayer player, int x, int y, int z) {
        openGui(player, x, y, z, 0);
    }

    public void openGui(EntityPlayer player, int data) {
        openGui(player, 0, 0, 0, data);
    }

    public void openGui(EntityPlayer player, BlockPos pos, int data) {
        openGui(player, pos.getX(), pos.getY(), pos.getZ(), data);
    }

    public void openGui(EntityPlayer player, int x, int y, int z, int data) {
        player.openGui(BCCompat.instance, packGui(this, data), player.world, x, y, z);
    }

    protected static int packGui(Enum<?> gui, int data) {
        if (data < 0 || data > 0xFF_FF_FF) {
            throw new IllegalArgumentException("Data must be between 0 and 0xFF_FF_FF (inclusive)");
        }
        return (data << 8) | gui.ordinal();
    }

    @Nullable
    protected static CompatGui getGui(int id) {
        id &= 0xFF;
        if (id < 0 || id >= CompatGui.VALUES.length) {
            return null;
        }
        return CompatGui.VALUES[id];
    }

    protected static int getData(int id) {
        return id >>> 8;
    }

    @FunctionalInterface
    public interface IGuiTarget {
        public static final IGuiTarget TILE = (player, world, x, y, z, data) -> {
            TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
            if (tile instanceof IGuiCreator) {
                return (IGuiCreator) tile;
            }
            if (tile != null) {
                return tile.getCapability(CompatUtils.CAP_GUI_CREATOR, null);
            }
            return null;
        };

        @Nullable
        IGuiCreator getCreator(EntityPlayer player, World world, int x, int y, int z, int data);
    }

    public static abstract class CommonProxy implements IGuiHandler {

        @Nullable
        protected static IGuiCreator getGuiCreator(int id, EntityPlayer player, World world, int x, int y, int z) {
            CompatGui type = getGui(id);
            int data = getData(id);
            if (type == null) {
                return null;
            }
            IGuiCreator creator = type.target.getCreator(player, world, x, y, z, data);
            if (creator == null || creator.getGuiType() != type) {
                return null;
            }
            return creator;
        }

        @Override
        @Nullable
        public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
            IGuiCreator creator = getGuiCreator(id, player, world, x, y, z);
            if (creator == null) {
                return null;
            }
            return creator.getServerGuiElement(getData(id), player);
        }
    }

    public static class ServerProxy extends CommonProxy {

        @Override
        @Nullable
        public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
            return null;
        }
    }

    public static class ClientProxy extends CommonProxy {

        @Override
        @Nullable
        public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
            IGuiCreator creator = getGuiCreator(id, player, world, x, y, z);
            if (creator == null) {
                return null;
            }
            return creator.getClientGuiElement(getData(id), player);
        }
    }

}
