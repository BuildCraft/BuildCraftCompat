package buildcraft.compat;

import com.bluepowermod.part.tube.PneumaticTube;

import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.transport.IItemDuct;
import buildcraft.BuildCraftCompat;
import buildcraft.api.transport.IInjectable;
import buildcraft.compat.bluepower.BPPneumaticTubeInjectable;
import buildcraft.compat.cofh.ItemDuctInjectable;
import buildcraft.compat.enderio.ItemConduitInjectable;
import buildcraft.core.CompatHooks;
import crazypants.enderio.conduit.IConduitBundle;
import crazypants.enderio.conduit.item.IItemConduit;
import uk.co.qmunity.lib.part.IPart;
import uk.co.qmunity.lib.part.ITilePartHolder;
import uk.co.qmunity.lib.part.compat.MultipartCompatibility;

public class CompatHooksImpl extends CompatHooks {
	@Override
	public IInjectable getInjectableWrapper(TileEntity tile, ForgeDirection from) {
		if (tile == null) {
			return null;
		}

		IInjectable wrapper = null;

		if (wrapper == null && Loader.isModLoaded("bluepower")) {
			wrapper = getInjectableBluePower(tile);
		}

		if (wrapper == null && Loader.isModLoaded("EnderIO")) {
			wrapper = getInjectableEnderIO(tile);
		}

		if (wrapper == null && tile instanceof IItemDuct) {
			wrapper = new ItemDuctInjectable((IItemDuct) tile);
		}

		return wrapper;
	}

	@Optional.Method(modid = "bluepower")
	public IInjectable getInjectableBluePower(TileEntity tile) {
		ITilePartHolder holder = MultipartCompatibility.getPartHolder(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
		if (holder != null) {
			for (IPart p : holder.getParts()) {
				if (p instanceof PneumaticTube) {
					return new BPPneumaticTubeInjectable(((PneumaticTube) p).getLogic());
				}
			}
		}
		return null;
	}

	@Optional.Method(modid = "EnderIO")
	public IInjectable getInjectableEnderIO(TileEntity tile) {
		if (tile instanceof IConduitBundle) {
			IConduitBundle bundle = ((IConduitBundle) tile);
			IItemConduit conduit = bundle.getConduit(IItemConduit.class);
			if (conduit != null) {
				return new ItemConduitInjectable(conduit);
			}
		}

		return null;
	}

	@Override
	public Object getEnergyProvider(TileEntity tile) {
		if (BuildCraftCompat.hasModule("factorization")) {
			Object output = CompatModuleFactorization.getEnergyProvider(tile);
			if (output != null) {
				return output;
			}
		}

		return tile;
	}
}
