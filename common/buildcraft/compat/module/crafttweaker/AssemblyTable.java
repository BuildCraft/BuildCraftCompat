package buildcraft.compat.module.crafttweaker;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IAssemblyRecipe;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.lib.recipe.assembly.AssemblyRecipe;
import buildcraft.lib.recipe.assembly.AssemblyRecipeBasic;
import buildcraft.lib.recipe.assembly.IFacadeAssemblyRecipes;
import buildcraft.silicon.recipe.FacadeAssemblyRecipes;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByName;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.stream.Collectors;

//@ZenClass("mods.buildcraft.AssemblyTable")
//@ModOnly("buildcraftsilicon")
@ZenRegister
@ZenCodeType.Name("mods.buildcraft.AssemblyTable")
@IRecipeHandler.For(IAssemblyRecipe.class)
//public class AssemblyTable
public enum AssemblyTable implements IRecipeManager<IAssemblyRecipe>, IRecipeHandler<IAssemblyRecipe> {
    @ZenCodeGlobals.Global("assemblyTable")
    INSTANCE;

    private static int ids;

//    // @ZenMethod
//    @ZenCodeType.Method
//    // public static void addRecipe(IItemStack output, int power, IIngredient[] ingredients)
//    public void addRecipe(IItemStack output, int power, IIngredient[] ingredients) {
//        addRecipe0("auto_" + ids++, output, power, ingredients);
//    }

    // @ZenMethod
    @ZenCodeType.Method
//    public static void addRecipe(String name, IItemStack output, int power, IIngredient[] ingredients)
    public void addRecipe(String name, IItemStack output, int power, IIngredient[] ingredients) {
        addRecipe0("custom/" + name, output, power, ingredients);
    }

    // @ZenMethod
    @ZenCodeType.Method
//    public static void addRecipe(String name, IItemStack output, int power, IIngredient[] ingredients)
    public void enableFacadeAssembly() {
        enableFacadeAssembly0();
    }

    // private static void addRecipe0(String name, IItemStack output, int power, IIngredient[] ingredients)
    private void addRecipe0(String name, IItemStack output, int power, IIngredient[] ingredients) {
//        CraftTweakerAPI.apply(new AddRecipeAction(name, output, power, ingredients));
        CraftTweakerAPI.apply(AddRecipeAction.create(this, name, output, power, ingredients));
    }

    // private static void addRecipe0(String name, IItemStack output, int power, IIngredient[] ingredients)
    private void enableFacadeAssembly0() {
//        CraftTweakerAPI.apply(new AddRecipeAction(name, output, power, ingredients));
        CraftTweakerAPI.apply(AddRecipeAction.createFacadeRecipe(this));
    }

    // @ZenMethod
    @ZenCodeType.Method
    @Override
    // public static void removeByName(String name)
    public void removeByName(String name) {
//        CraftTweakerAPI.apply(new RemoveRecipeByNameAction(new ResourceLocation(name)));
        CraftTweakerAPI.apply(new RemoveRecipeByNameAction(this, new ResourceLocation(name)));
    }

    @Override
    public RecipeType<IAssemblyRecipe> getRecipeType() {
        return IAssemblyRecipe.TYPE;
    }

    @Override
    public String dumpToCommandString(final IRecipeManager manager, IAssemblyRecipe recipe) {
        if (recipe instanceof IFacadeAssemblyRecipes) {
            return String.format(
                    "assemblyTable.enableFacadeAssembly();"
            );
        } else {
            return String.format(
                    "assemblyTable.addRecipe(%s, %s, %s, %s);",
                    StringUtil.quoteAndEscape(recipe.getId()),
                    ItemStackUtil.getCommandString(recipe.getOutputPreviews().stream().toList().get(0)),
                    recipe.getRequiredMicroJoules(),
                    recipe.getRequiredIngredientStacks().stream()
                            .map(i -> IIngredient.fromIngredient(i.ingredient))
                            .map(IIngredient::getCommandString)
                            .collect(Collectors.joining(", ", "[", "]"))
            );
        }
    }

    // private static class RemoveRecipeByNameAction implements IAction
    private static class RemoveRecipeByNameAction extends ActionRemoveRecipeByName<IAssemblyRecipe> {
        private final ResourceLocation name;

        // RemoveRecipeByNameAction(ResourceLocation name)
        RemoveRecipeByNameAction(IRecipeManager<IAssemblyRecipe> manager, ResourceLocation name) {
            super(manager);
            this.name = name;
        }

        public void apply() {
//            AssemblyRecipeRegistry.REGISTRY.remove(this.name);
            getManager().removeByName(this.name.toString());
        }

        public String describe() {
            return "Removing assembly table recipe " + this.name;
        }
    }

    // private static class AddRecipeAction implements IAction
    private static class AddRecipeAction extends ActionAddRecipe<IAssemblyRecipe> {
//        private final ItemStack output;
//        private final ResourceLocation name;
//        private final long requiredMj;
//        private final ImmutableSet<IngredientStack> requiredStacks;

        // public AddRecipeAction(IRecipeManager<AssemblyRecipe> manager, String name, IItemStack output, int power, IIngredient[] ingredients)
        private AddRecipeAction(IRecipeManager<IAssemblyRecipe> manager, AssemblyRecipe recipe) {
            super(manager, recipe);
//            this.output = CraftTweakerMC.getItemStack(output);
//            ImmutableSet.Builder<IngredientStack> stacks = ImmutableSet.builder();
//
//            for (int i = 0; i < ingredients.length; ++i) {
//                IIngredient ctIng = ingredients[i];
//                Ingredient ingredient = CraftTweakerMC.getIngredient(ctIng);
//                stacks.add(new IngredientStack(ingredient, Math.max(1, ctIng.getAmount())));
//            }
//
//            this.requiredStacks = stacks.build();
//            this.requiredMj = (long) power * MjAPI.MJ;
//            this.name = new ResourceLocation("crafttweaker", name);
        }

        public static AddRecipeAction create(IRecipeManager<IAssemblyRecipe> manager, String name, IItemStack output, int power, IIngredient[] ingredients) {
//            ItemStack output = CraftTweakerMC.getItemStack(output);
            ItemStack _output = output.getImmutableInternal();
            ImmutableSet.Builder<IngredientStack> stacks = ImmutableSet.builder();

            for (int i = 0; i < ingredients.length; ++i) {
                IIngredient ctIng = ingredients[i];
//                Ingredient ingredient = CraftTweakerMC.getIngredient(ctIng);
                Ingredient ingredient = ctIng.asVanillaIngredient();
//                stacks.add(new IngredientStack(ingredient, Math.max(1, ctIng.getAmount())));
                stacks.add(new IngredientStack(ingredient, Math.max(1, ctIng.asIIngredientWithAmount().getAmount())));
            }

            ImmutableSet<IngredientStack> requiredStacks = stacks.build();
            long requiredMj = (long) power * MjAPI.MJ;
            ResourceLocation _name = new ResourceLocation("crafttweaker", name);
            AssemblyRecipeBasic recipe = new AssemblyRecipeBasic(_name, requiredMj, requiredStacks, _output);
            return new AddRecipeAction(manager, recipe);
        }

        public static AddRecipeAction createFacadeRecipe(IRecipeManager<IAssemblyRecipe> manager) {
            return new AddRecipeAction(manager, FacadeAssemblyRecipes.INSTANCE);
        }

//        public void apply() {
//            AssemblyRecipeRegistry.REGISTRY.put(this.name, new AssemblyRecipeBasic(this.name, this.requiredMj, this.requiredStacks, this.output));
//        }

        public String describe() {
//            return "Adding assembly table recipe for " + this.output;
            return "Adding assembly table recipe for " + this.recipe.getOutputPreviews().stream().toList().get(0);
        }
    }
}
