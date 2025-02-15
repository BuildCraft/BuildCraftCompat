package buildcraft.compat.module.rei.factory;

import buildcraft.api.recipes.IRefineryRecipeManager;
import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public class DisplayHeatable implements Display {
    private final EntryIngredient in;
    private final EntryIngredient out;

    public DisplayHeatable(IRefineryRecipeManager.IHeatableRecipe recipe) {
        in = EntryIngredients.of(FluidStack.create(recipe.in().getRawFluid(), recipe.in().getAmount()));
        out = EntryIngredients.of(FluidStack.create(recipe.out().getRawFluid(), recipe.out().getAmount()));
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(in);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(out);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryHeatable.ID;
    }
}
