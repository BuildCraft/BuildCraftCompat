package buildcraft.compat.nei;

import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.api.recipes.IFlexibleRecipeViewable;
import buildcraft.compat.CompatModuleNEI;
import buildcraft.transport.ItemFacade;
import net.minecraft.item.ItemStack;

public class NEIIntegrationBC {
    protected static boolean isValid(IFlexibleRecipeViewable recipe) {
        if (recipe.getOutput() == null) {
            return false;
        }

        if (CompatModuleNEI.disableFacadeNEI && ((IFlexibleRecipe) recipe).getId().startsWith("buildcraft:facade")) {
            return false;
        }

        for (Object o : (recipe).getInputs()) {
            if (o == null) {
                return false;
            }
        }

        return true;
    }
}
