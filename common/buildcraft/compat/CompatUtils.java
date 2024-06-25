package buildcraft.compat;

import buildcraft.api.core.CapabilitiesHelper;
import buildcraft.compat.network.IGuiCreator;
import buildcraft.lib.tile.item.ItemHandlerSimple;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

public class CompatUtils {
    public static Capability<IGuiCreator> CAP_GUI_CREATOR;

    private CompatUtils() {
    }

    public static List<ItemStack> compactInventory(ItemHandlerSimple inventory) {
        List<ItemStack> stacks = Lists.newArrayList();
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (stack.isEmpty()) {
                continue;
            }

            boolean handled = false;
            for (ItemStack existing : stacks) {
                if (existing.sameItem(stack)) {
                    existing.grow(stack.getCount());
                    handled = true;
                    break;
                }
            }
            if (!handled) {
                stacks.add(stack.copy());
            }
        }

        return stacks;
    }

    public static void regCaps() {
        CAP_GUI_CREATOR = CapabilitiesHelper.registerCapability(IGuiCreator.class);
    }
}
