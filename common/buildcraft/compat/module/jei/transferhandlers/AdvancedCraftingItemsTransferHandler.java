package buildcraft.compat.module.jei.transferhandlers;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import buildcraft.silicon.container.ContainerAdvancedCraftingTable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;

public class AdvancedCraftingItemsTransferHandler implements IRecipeTransferHandler<ContainerAdvancedCraftingTable> {
    @Override
    public Class<ContainerAdvancedCraftingTable> getContainerClass() {
        return ContainerAdvancedCraftingTable.class;
    }

    @Nullable
    @Override
    public IRecipeTransferError transferRecipe(ContainerAdvancedCraftingTable container, IRecipeLayout recipeLayout, EntityPlayer player, boolean maxTransfer, boolean doTransfer) {
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
}
