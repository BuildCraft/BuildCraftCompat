package buildcraft.compat.module.rei.silicon;

import buildcraft.api.recipes.IIntegrationRecipeProvider;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;

import java.util.List;

public class DisplayIntegration implements Display {

    public DisplayIntegration(IIntegrationRecipeProvider recipe) {
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of();
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        throw new RuntimeException();
    }
}
