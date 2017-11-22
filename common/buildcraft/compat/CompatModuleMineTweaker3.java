package buildcraft.compat;

import buildcraft.compat.minetweaker.AssemblyTable;
import buildcraft.compat.minetweaker.Fuels;
import buildcraft.compat.minetweaker.ProgrammingTable;
import buildcraft.compat.minetweaker.Refinery;

import buildcraft.core.recipes.FlexibleRecipeIngredientOreStack;
import cpw.mods.fml.relauncher.ReflectionHelper;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IngredientStack;
import minetweaker.api.oredict.IngredientOreDict;

import java.lang.reflect.Field;

public class CompatModuleMineTweaker3 extends CompatModuleBase {
    // MT3 probably won't get an update... so we have to reflect
    private static Field ingStackIngredient;

    public static Object getFlexibleRecipeObject(IIngredient ingredient) {
        if (ingredient instanceof IngredientStack) {
            try {
                if (ingStackIngredient == null) {
                    ingStackIngredient = ReflectionHelper.findField(IngredientStack.class, "ingredient");
                }
                IIngredient parent = (IIngredient) ingStackIngredient.get(ingredient);
                Object internal = parent.getInternal();
                if (internal instanceof String) {
                    return new FlexibleRecipeIngredientOreStack((String) internal, ingredient.getAmount());
                }
            } catch (Exception e) {

            }
        }

        Object internal = ingredient.getInternal();
        if (internal == null) {
            throw new IllegalArgumentException("Not a valid assembly table ingredient");
        } else {
            return internal;
        }
    }

    @Override
    public String name() {
        return "MineTweaker3";
    }
    
    @Override
    public void postInit() {
        MineTweakerAPI.registerClass(AssemblyTable.class);
        MineTweakerAPI.registerClass(Fuels.class);
		MineTweakerAPI.registerClass(ProgrammingTable.class);
        MineTweakerAPI.registerClass(Refinery.class);
    }
}
