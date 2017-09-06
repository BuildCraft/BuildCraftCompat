package buildcraft.compat.jei.transferhandlers;

import java.util.Map;
import javax.annotation.Nullable;
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
            Map<Integer, ? extends IGuiIngredient<ItemStack>> inputs = recipeLayout.getItemStacks().getGuiIngredients();

            for (int slot = 0; slot < 9; slot++) {
                IGuiIngredient<ItemStack> ingredient = inputs.getOrDefault(slot + 1, null);
                ItemStack stack = (ingredient == null) ? ItemStack.EMPTY : ingredient.getDisplayedIngredient();

                // TODO: wait for issue #3801: Containers needs a method for setting phantom slots.
                // container.tile.invBlueprint.setStackInSlot(slot, (stack == null) ? ItemStack.EMPTY : stack);
            }
        }

        return null;
    }
}
