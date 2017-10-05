package buildcraft.compat.jei.transferhandlers;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import buildcraft.factory.container.ContainerAutoCraftItems;
import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;

public class AutoCraftItemsTransferHandler implements IRecipeTransferHandler<ContainerAutoCraftItems> {
    @Override
    public Class<ContainerAutoCraftItems> getContainerClass() {
        return ContainerAutoCraftItems.class;
    }

    @Nullable
    @Override
    public IRecipeTransferError transferRecipe(ContainerAutoCraftItems container, IRecipeLayout recipeLayout, EntityPlayer player, boolean maxTransfer, boolean doTransfer) {
        if (doTransfer) {
//            Map<Integer, ? extends IGuiIngredient<ItemStack>> inputs = recipeLayout.getItemStacks().getGuiIngredients();
//
//            for (int slot = 0; slot < 9; slot++) {
//                IGuiIngredient<ItemStack> ingredient = inputs.getOrDefault(slot + 1, null);
//                ItemStack stack = (ingredient == null) ? ItemStack.EMPTY : ingredient.getDisplayedIngredient();
//
//                container.sendSetPhantomSlot(container.tile.invBlueprint, slot, (stack == null) ? ItemStack.EMPTY : stack);
//            }

            AutoCraftItemsTransferHandler.transferRecipe(
                    itemStacks -> container.sendSetPhantomSlots(container.tile.invBlueprint, itemStacks),
                    recipeLayout);
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
