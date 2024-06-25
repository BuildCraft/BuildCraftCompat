package buildcraft.compat.module.jei.transferhandlers;

import buildcraft.factory.container.ContainerAutoCraftItems;
import com.google.common.collect.Lists;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiIngredient;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class AutoCraftItemsTransferHandler implements IRecipeTransferHandler<ContainerAutoCraftItems> {
    public AutoCraftItemsTransferHandler() {
    }

    @Override
    public Class<ContainerAutoCraftItems> getContainerClass() {
        return ContainerAutoCraftItems.class;
    }

    @Override
    @Nullable
    public IRecipeTransferError transferRecipe(ContainerAutoCraftItems container, IRecipeLayout recipeLayout, PlayerEntity player, boolean maxTransfer, boolean doTransfer) {
        if (doTransfer) {
            transferRecipe((itemStacks) ->
                    {
                        container.sendSetPhantomSlots((container.tile).invBlueprint, itemStacks);
                    },
                    recipeLayout
            );
        }

        return null;
    }

    static void transferRecipe(Consumer<List<ItemStack>> callback, IRecipeLayout recipeLayout) {
        Map<Integer, ? extends IGuiIngredient<ItemStack>> inputs = recipeLayout.getItemStacks().getGuiIngredients();

        List<ItemStack> stacks = Lists.newArrayList();
        for (int slot = 0; slot < 9; slot++) {
            IGuiIngredient<ItemStack> ingredient = inputs.getOrDefault(slot + 1, null);
            ItemStack stack = (ingredient == null) ? ItemStack.EMPTY : ingredient.getDisplayedIngredient();
            stacks.add((stack == null) ? ItemStack.EMPTY : stack);
        }

        callback.accept(stacks);
    }
}
