package buildcraft.compat;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.transport.IItemDuct;
import buildcraft.api.transport.IInjectable;
import buildcraft.compat.cofh.ItemDuctInjectable;

public class CompatHooksImpl extends CompatHooks {
	@Override
	public IInjectable getInjectableWrapper(TileEntity tile, ForgeDirection from) {
		if (tile instanceof IItemDuct) {
			return new ItemDuctInjectable((IItemDuct) tile);
		}

		return null;
	}
}
