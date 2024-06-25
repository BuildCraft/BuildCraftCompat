package buildcraft.compat.module.jei.transferhandlers;

import buildcraft.silicon.container.ContainerAssemblyTable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;

// Calen: never used in 1.12.2?
public class AssemblyTableTransferHandler implements IRecipeTransferHandler<ContainerAssemblyTable> {

    @Override
    public Class<ContainerAssemblyTable> getContainerClass() {
        return ContainerAssemblyTable.class;
    }

    @Nullable
    public IRecipeTransferError transferRecipe(ContainerAssemblyTable container, IRecipeLayout recipeLayout, PlayerEntity player, boolean maxTransfer, boolean doTransfer) {
        return null;
    }
}
