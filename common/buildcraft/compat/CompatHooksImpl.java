package buildcraft.compat;

import crazypants.enderio.conduit.IConduitBundle;
import crazypants.enderio.conduit.item.IItemConduit;

import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.transport.IItemDuct;
import buildcraft.api.transport.IInjectable;
import buildcraft.compat.cofh.ItemDuctInjectable;
import buildcraft.compat.enderio.ItemConduitInjectable;
import buildcraft.core.CompatHooks;

public class CompatHooksImpl extends CompatHooks {
	@Override
	public IInjectable getInjectableWrapper(TileEntity tile, ForgeDirection from) {
		IInjectable wrapper = null;

		if (wrapper == null && Loader.isModLoaded("EnderIO")) {
			wrapper = getInjectableEnderIO(tile);
		}

		if (wrapper == null && tile instanceof IItemDuct) {
			wrapper = new ItemDuctInjectable((IItemDuct) tile);
		}

		return wrapper;
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
		return tile;
	}
}
