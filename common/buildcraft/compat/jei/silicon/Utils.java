package buildcraft.compat.jei.silicon;

import java.util.List;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import buildcraft.api.recipes.StackDefinition;

public final class Utils {
    public static List<ItemStack> getItemStacks(StackDefinition definition) {
        List<ItemStack> list = Lists.newArrayList();

        if (definition.filter != null) {
            for (ItemStack stack : definition.filter.getExamples()) {
                ItemStack sizedStack = stack.copy();
                sizedStack.setCount(definition.count);
                list.add(sizedStack);
            }
        }

        return list;
    }
}
