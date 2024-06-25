package buildcraft.compat.module.jei.transferhandlers;

import buildcraft.silicon.container.ContainerAdvancedCraftingTable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;

public class AdvancedCraftingItemsTransferHandler implements IRecipeTransferHandler<ContainerAdvancedCraftingTable> {
    public AdvancedCraftingItemsTransferHandler() {
    }

    @Override
    public Class<ContainerAdvancedCraftingTable> getContainerClass() {
        return ContainerAdvancedCraftingTable.class;
    }

    @Override
    @Nullable
    public IRecipeTransferError transferRecipe(ContainerAdvancedCraftingTable container, IRecipeLayout recipeLayout, PlayerEntity player, boolean maxTransfer, boolean doTransfer) {
        if (doTransfer) {
            AutoCraftItemsTransferHandler.transferRecipe((itemStacks) ->
                    {
                        container.sendSetPhantomSlots(container.tile.invBlueprint, itemStacks);
                    },
                    recipeLayout
            );
        }

        return null;
    }
}
