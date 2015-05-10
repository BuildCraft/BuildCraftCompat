package buildcraft.compat.nei;

import buildcraft.api.recipes.IFlexibleRecipeViewable;

public class NEIIntegrationBC {
    protected static boolean isValid(IFlexibleRecipeViewable recipe) {
        // detect invalid recipes
        for (Object o : (recipe).getInputs()) {
            if (o == null) {
                return false;
            }
        }
        return recipe.getOutput() != null;
    }
}
