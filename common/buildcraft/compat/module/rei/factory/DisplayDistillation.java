package buildcraft.compat.module.rei.factory;

import buildcraft.api.recipes.IRefineryRecipeManager;
import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public class DisplayDistillation implements Display {
    private final EntryIngredient in;
    private final EntryIngredient outGas;
    private final EntryIngredient outLiquid;
    public final long powerRequired;

    public DisplayDistillation(IRefineryRecipeManager.IDistillationRecipe recipe) {
        in = EntryIngredients.of(FluidStack.create(recipe.in().getRawFluid(), recipe.in().getAmount()));
        outGas = EntryIngredients.of(FluidStack.create(recipe.outGas().getRawFluid(), recipe.outGas().getAmount()));
        outLiquid = EntryIngredients.of(FluidStack.create(recipe.outLiquid().getRawFluid(), recipe.outLiquid().getAmount()));
        powerRequired = recipe.powerRequired();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(in);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(outGas, outLiquid);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryDistiller.ID;
    }
}
