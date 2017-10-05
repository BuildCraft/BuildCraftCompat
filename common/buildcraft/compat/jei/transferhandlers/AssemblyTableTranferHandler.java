package buildcraft.compat.jei.transferhandlers;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import buildcraft.silicon.container.ContainerAssemblyTable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;

public class AssemblyTableTranferHandler implements IRecipeTransferHandler<ContainerAssemblyTable> {
    @Override
    public Class<ContainerAssemblyTable> getContainerClass() {
        return ContainerAssemblyTable.class;
    }

    @Nullable
    @Override
    public IRecipeTransferError transferRecipe(ContainerAssemblyTable container, IRecipeLayout recipeLayout, EntityPlayer player, boolean maxTransfer, boolean doTransfer) {


        return null;
    }
}
