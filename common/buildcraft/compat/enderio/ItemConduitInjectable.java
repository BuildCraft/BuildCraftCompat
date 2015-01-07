package buildcraft.compat.enderio;

import crazypants.enderio.conduit.item.IItemConduit;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.transport.IItemDuct;
import buildcraft.api.core.EnumColor;
import buildcraft.api.transport.IInjectable;
import buildcraft.compat.cofh.ItemDuctInjectable;

public class ItemConduitInjectable extends ItemDuctInjectable {
	public ItemConduitInjectable(IItemDuct duct) {
		super(duct);
	}

	@Override
	public boolean canInjectItems(ForgeDirection forgeDirection) {
		return ((IItemConduit) duct).isConnectedTo(forgeDirection);
	}
}
