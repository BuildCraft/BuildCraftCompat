package buildcraft.compat.module.forestry.list;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import buildcraft.api.lists.ListMatchHandler;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

public class ListMatchGenome extends ListMatchHandler {

    @Override
    public boolean matches(Type type, ItemStack compare, ItemStack target, boolean precise) {
        IIndividual infoCompare = AlleleManager.alleleRegistry.getIndividual(compare);
        IIndividual infoTarget = AlleleManager.alleleRegistry.getIndividual(target);
        if (infoCompare == null || infoTarget == null) {
            return false;
        }
        switch (type) {
            case MATERIAL: {
                return matchesMaterial(compare, target, infoCompare, infoTarget, precise);
            }
            case TYPE: {
                return matchesType(compare, target, infoCompare, infoTarget, precise);
            }
            case CLASS: {
                return matchesMaterial(compare, target, infoCompare, infoTarget, precise)
                    && matchesType(compare, target, infoCompare, infoTarget, precise);
            }
            default: {
                throw new IllegalArgumentException("Unknown type " + type);
            }
        }
    }

    private static boolean matchesMaterial(ItemStack compare, ItemStack target, IIndividual infoCompare,
        IIndividual infoTarget, boolean precise) {
        // Ensures that both individuals have the same species
        // If precise is true then also ensure that the secondary species is the same
        IAlleleSpecies speciesCompare = infoCompare.getGenome().getPrimary();
        IAlleleSpecies speciesTarget = infoTarget.getGenome().getPrimary();
        if (speciesCompare != speciesTarget) {
            return false;
        }
        if (precise) {
            IAlleleSpecies inactiveCompare = infoCompare.getGenome().getSecondary();
            IAlleleSpecies inactiveTarget = infoTarget.getGenome().getSecondary();
            if (inactiveCompare != inactiveTarget) {
                return false;
            }
        }
        return true;
    }

    private static boolean matchesType(ItemStack compare, ItemStack target, IIndividual infoCompare,
        IIndividual infoTarget, boolean precise) {
        ISpeciesRoot speciesRootCompare = infoCompare.getGenome().getSpeciesRoot();
        ISpeciesRoot speciesRootTarget = infoTarget.getGenome().getSpeciesRoot();
        if (speciesRootCompare != speciesRootTarget) {
            return false;
        }
        // Ensure that both fully match (both princesses or both drones etc)
        if (speciesRootCompare.getType(compare) != speciesRootTarget.getType(target)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidSource(Type type, @Nonnull ItemStack stack) {
        return AlleleManager.alleleRegistry.getIndividual(stack) != null;
    }

    @Override
    @Nullable
    public NonNullList<ItemStack> getClientExamples(Type type, @Nonnull ItemStack stack) {
        IIndividual individual = AlleleManager.alleleRegistry.getIndividual(stack);
        if (individual == null) {
            return null;
        }

        NonNullList<ItemStack> list = NonNullList.create();
        boolean isType = type != Type.MATERIAL;
        boolean isMaterial = type != Type.TYPE;
        return list;
    }
}
