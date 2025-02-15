package buildcraft.compat.module.jei.transferhandlers;

import buildcraft.silicon.BCSiliconMenuTypes;
import buildcraft.silicon.container.ContainerAdvancedCraftingTable;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.CraftingRecipe;

import javax.annotation.Nullable;
import java.util.Optional;

public class AdvancedCraftingItemsTransferHandler implements IRecipeTransferHandler<ContainerAdvancedCraftingTable, CraftingRecipe> {
    public AdvancedCraftingItemsTransferHandler() {
    }

    @Override
    public Class<ContainerAdvancedCraftingTable> getContainerClass() {
        return ContainerAdvancedCraftingTable.class;
    }

    @Override
    public Optional<MenuType<ContainerAdvancedCraftingTable>> getMenuType() {
        return Optional.of(BCSiliconMenuTypes.ADVANCED_CRAFTING_TABLE);
    }

    @Override
    public RecipeType<CraftingRecipe> getRecipeType() {
        return RecipeTypes.CRAFTING;
    }

    @Override
    @Nullable
//    public IRecipeTransferError transferRecipe(ContainerAdvancedCraftingTable container, IRecipeLayout recipeLayout, Player player, boolean maxTransfer, boolean doTransfer)
    public IRecipeTransferError transferRecipe(ContainerAdvancedCraftingTable container, CraftingRecipe recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer) {
        if (doTransfer) {
            AutoCraftItemsTransferHandler.transferRecipe((itemStacks) ->
                    {
                        container.sendSetPhantomSlots(container.tile.invBlueprint, itemStacks);
                    },
//                    recipeLayout
                    recipe,
                    recipeSlots
            );
        }

        return null;
    }
}
