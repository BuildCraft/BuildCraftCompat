package buildcraft.compat.module.rei.energy.combustionengine;

import buildcraft.api.fuels.IFuel;
import buildcraft.api.fuels.IFuelManager;
import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public class DisplayCombustionEngine implements Display {
    private final EntryIngredient fluid;
    private final EntryIngredient residue;
    public final int totalBurningTime;
    public final long powerPerCycle;

    public DisplayCombustionEngine(IFuel recipe) {
        totalBurningTime = recipe.getTotalBurningTime();
        powerPerCycle = recipe.getPowerPerCycle();
        fluid = EntryIngredients.of(FluidStack.create(recipe.getFluid().getRawFluid(), recipe.getFluid().getAmount()));
        if (recipe instanceof IFuelManager.IDirtyFuel dirtyFuel) {
            residue = EntryIngredients.of(FluidStack.create(dirtyFuel.getResidue().getRawFluid(), dirtyFuel.getResidue().getAmount()));
        } else {
            residue = null;
        }
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(fluid);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        if (residue == null) {
            return List.of();
        } else {
            return List.of(residue);
        }
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryCombustionEngine.ID;
    }
}
