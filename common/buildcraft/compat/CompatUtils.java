package buildcraft.compat;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;

import net.minecraftforge.common.capabilities.Capability;

import buildcraft.api.core.CapabilitiesHelper;

import buildcraft.lib.tile.item.ItemHandlerSimple;

import buildcraft.compat.network.IGuiCreator;

public class CompatUtils {

    public static final Capability<IGuiCreator> CAP_GUI_CREATOR =
        CapabilitiesHelper.registerCapability(IGuiCreator.class);

    private CompatUtils() {}

    public static List<ItemStack> compactInventory(ItemHandlerSimple inventory) {
        List<ItemStack> stacks = Lists.newArrayList();

        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (stack.isEmpty()) {
                continue;
            }

            boolean handled = false;
            for (ItemStack existing : stacks) {
                if (existing.isItemEqual(stack)) {
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
}
