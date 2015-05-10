package buildcraft.compat.bluepower;

import com.bluepowermod.api.tube.IPneumaticTube;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.core.EnumColor;
import buildcraft.api.transport.IInjectable;

public class BPPneumaticTubeInjectable implements IInjectable {
	public final IPneumaticTube tube;

	public BPPneumaticTubeInjectable(IPneumaticTube tube) {
		System.out.println("attempt");
		this.tube = tube;
	}

	@Override
	public boolean canInjectItems(ForgeDirection forgeDirection) {
		System.out.println("attempt");
		return true;
	}

	@Override
	public int injectItem(ItemStack stack, boolean doAdd, ForgeDirection from, EnumColor color) {
		System.out.println("attempt");
		IPneumaticTube.TubeColor tubeColor = IPneumaticTube.TubeColor.NONE;
		if (color != null) {
			tubeColor = IPneumaticTube.TubeColor.values()[color.ordinal()];
		}

		boolean injected = tube.injectStack(stack, from.getOpposite(), tubeColor, !doAdd);
		if (injected) {
			return stack.stackSize;
		} else {
			return 0;
		}
	}
}
