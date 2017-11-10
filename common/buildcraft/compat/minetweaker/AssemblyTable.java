/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package buildcraft.compat.minetweaker;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.api.recipes.IFlexibleRecipeViewable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import buildcraft.compat.CompatModuleMineTweaker3;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import net.minecraft.item.ItemStack;

/**
 *
 * @author Stan
 */
@ZenClass("mods.buildcraft.AssemblyTable")
@ModOnly("BuildCraft|Silicon")
public class AssemblyTable {
	@ZenMethod
	public static void addRecipe(IItemStack output, int energy, IIngredient[] ingredients) {
		MineTweakerAPI.apply(new AddRecipeAction(output, energy, ingredients));
	}
	
	@ZenMethod
	public static void remove(IIngredient output) {
		removeRecipe(output, null, false);
	}

	@ZenMethod
	public static void removeByName(String name) {
		MineTweakerAPI.apply(new RemoveRecipeByNameAction(name));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output, @Optional IIngredient[] ingredients, @Optional boolean wildcard) {

		List<IFlexibleRecipe<ItemStack>> toRemove = new ArrayList<IFlexibleRecipe<ItemStack>>();
		
		for (IFlexibleRecipe<ItemStack> recipe : BuildcraftRecipeRegistry.assemblyTable.getRecipes()) {
			if (recipe instanceof IFlexibleRecipeViewable) {
				if (output.matches(MineTweakerMC.getIItemStack((ItemStack) ((IFlexibleRecipeViewable) recipe).getOutput()))
						&& ingredientsMatch(((IFlexibleRecipeViewable) recipe).getInputs(), ingredients, wildcard)) {
					toRemove.add(recipe);
				}
			}
		}
		
		for (IFlexibleRecipe<ItemStack> recipe : toRemove) {
			MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
		}
	}
	
	/**
	 * Checks if ingredients from the recipe match with the given ingredients.
	 * 
	 * @param recipeIngs recipe ingredients to check
	 * @param ingredients required ingredients
	 * @param wildcard true if there can be more ingredients in the recipe than given, false otherwise
	 * @return matching result
	 */
	private static boolean ingredientsMatch(Collection<Object> recipeIngs, IIngredient[] ingredients, boolean wildcard) {
		if (ingredients == null)
			return true;
		
		int matchedIngredients = 0;
		boolean[] matched = new boolean[ingredients.length];
		checkIngredient: for (Object ingredientObject: recipeIngs) {
			if (ingredientObject instanceof Number)
				continue;
			
			IIngredient recipeIngredient = MineTweakerMC.getIIngredient(ingredientObject);
			
			for (int j = 0; j < ingredients.length; j++) {
				if (!matched[j] && ingredients[j].contains(recipeIngredient)) {
					matched[j] = true;
					matchedIngredients++;
					continue checkIngredient;
				}
			}
			
			if (!wildcard)
				return false;
		}
		
		return matchedIngredients == ingredients.length;
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final IItemStack output;
		private final String name;
		private final int energy;
		private final Object[] mcIngredients;

		public AddRecipeAction(IItemStack output, int energy, IIngredient[] ingredients) {
			this.output = output;

			String _name = "MineTweaker:" + this.output.getName() + ":" + this.output.getDamage();
			
			mcIngredients = new Object[ingredients.length];
			for (int i = 0; i < ingredients.length; i++) {
				mcIngredients[i] = CompatModuleMineTweaker3.getFlexibleRecipeObject(ingredients[i]);
				_name += ":" + ingredients[i].toString();
			}

			this.energy = energy;
			this.name = _name;
		}

		@Override
		public void apply() {
			BuildcraftRecipeRegistry.assemblyTable.addRecipe(name, energy, MineTweakerMC.getItemStack(output), mcIngredients);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftRecipeRegistry.assemblyTable.removeRecipe(name);
		}

		@Override
		public String describe() {
			return "Adding assembly table recipe for " + output;
		}

		@Override
		public String describeUndo() {
			return "Removing assembly table recipe for " + output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveRecipeAction implements IUndoableAction {
		private final IFlexibleRecipe<ItemStack> recipe;

		public RemoveRecipeAction(IFlexibleRecipe<ItemStack> recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			BuildcraftRecipeRegistry.assemblyTable.removeRecipe(recipe);
		}

		// TODO: RESTORE UNDO
		@Override
		public boolean canUndo() {
			return false;
		}

		@Override
		public void undo() {
			//((AssemblyRecipeManager) BuildcraftRecipes.assemblyTable).getRecipes().add(recipe);
		}

		@Override
		public String describe() {
			return "Removing assembly table recipe for " + ((ItemStack) ((IFlexibleRecipeViewable) recipe).getOutput()).getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring assembly table recipe for " + ((ItemStack) ((IFlexibleRecipeViewable) recipe).getOutput()).getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private static class RemoveRecipeByNameAction implements IUndoableAction {
		private final String name;

		public RemoveRecipeByNameAction(String name) {
			this.name = name;
		}

		@Override
		public void apply() {
			BuildcraftRecipeRegistry.assemblyTable.removeRecipe(name);
		}

		@Override
		public boolean canUndo() {
			return false;
		}

		@Override
		public void undo() {
		}

		@Override
		public String describe() {
			return "Removing assembly table recipe " + name;
		}

		@Override
		public String describeUndo() {
			return "Restoring assembly table recipe " + name;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
