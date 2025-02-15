package buildcraft.compat.module.rei.silicon;

import buildcraft.api.recipes.IAssemblyRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public class DisplayAssembly implements Display {
    private final List<EntryIngredient> in;
    private final EntryIngredient out;
    public final long requiredMicroJoules;

    public DisplayAssembly(IAssemblyRecipe recipe) {
        in = EntryIngredients.ofIngredients(recipe.getRequiredIngredientStacks().stream().map(ingredientStack -> ingredientStack.ingredient).toList());
        out = EntryIngredients.of(recipe.getOutput().stream().toList().get(0));
        requiredMicroJoules = recipe.getRequiredMicroJoules();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return in;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(out);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryAssemblyTable.ID;
    }
}
