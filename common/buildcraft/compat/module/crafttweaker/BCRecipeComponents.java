package buildcraft.compat.module.crafttweaker;

import buildcraft.api.BCModules;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.lib.misc.StackUtil;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Objects;

public class BCRecipeComponents {
    public static final IRecipeComponent<Long> MJ = IRecipeComponent.simple(
            ResourceLocation.tryBuild(BCModules.BUILDCRAFT, "mj"),
            new TypeToken<>() {},
            Objects::equals
    );

    public static final IRecipeComponent<IngredientStack> INGREDIENT_STACK = IRecipeComponent.composite(
            ResourceLocation.tryBuild(BCModules.BUILDCRAFT, "ingredient_stack"),
            new TypeToken<>() {},
            IngredientStack::equals,
            List::of,
            ingredientStacks -> ingredientStacks.stream().findFirst().orElse(new IngredientStack(Ingredient.EMPTY))
    );

    public static final IRecipeComponent<Float> DEGREES_COOLING_PER_MB = IRecipeComponent.simple(
            ResourceLocation.tryBuild(BCModules.BUILDCRAFT, "degrees_cooling_per_mb"),
            new TypeToken<>() {},
            Objects::equals
    );

    public static final IRecipeComponent<Float> MULTIPLIER = IRecipeComponent.simple(
            ResourceLocation.tryBuild(BCModules.BUILDCRAFT, "multiplier"),
            new TypeToken<>() {},
            Objects::equals
    );

    public static final IRecipeComponent<ItemStack> ITEM_STACK = IRecipeComponent.composite(
            ResourceLocation.tryBuild(BCModules.BUILDCRAFT, "item_stack"),
            new TypeToken<>() {},
            ItemStack::equals,
            List::of,
            itemStacks -> itemStacks.stream().findFirst().orElse(StackUtil.EMPTY)
    );
}
