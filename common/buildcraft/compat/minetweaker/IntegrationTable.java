/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package buildcraft.compat.minetweaker;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;

import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.CraftingResult;
import buildcraft.api.recipes.IIntegrationRecipe;
import buildcraft.silicon.TileIntegrationTable;
import buildcraft.transport.recipes.IntegrationTableRecipe;

/**
 *
 * @author Stan
 */
@ZenClass("mods.buildcraft.IntegrationTable")
@ModOnly("BuildCraft|Silicon")
public class IntegrationTable {
	@ZenMethod
	public static void addRecipe(String name, IItemStack output, int energy, int craftingTime, IIngredient inputA, IIngredient inputB, @Optional IIntegrationRecipeFunction function) {
		MineTweakerAPI.apply(new AddRecipeAction(name, output, energy, craftingTime, inputA, inputB, function));
	}
	
	// ######################
	// ### Action Classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final MTIntegrationRecipe recipe;

		public AddRecipeAction(String name, IItemStack output, int energy, int craftingTime, IIngredient inputA, IIngredient inputB, IIntegrationRecipeFunction function) {
			recipe = new MTIntegrationRecipe(name, output, energy, craftingTime, inputA, inputB, function);
		}

		@Override
		public void apply() {
			BuildcraftRecipeRegistry.integrationTable.addRecipe(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftRecipeRegistry.integrationTable.getRecipes().remove(recipe);
		}

		@Override
		public String describe() {
			return "Adding integration table recipe for " + recipe.output;
		}

		@Override
		public String describeUndo() {
			return "Removing integration table recipe for " + recipe.output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private static class MTIntegrationRecipe extends IntegrationTableRecipe {
		private final IIngredient inputA;
		private final IIngredient inputB;
		private final IIntegrationRecipeFunction function;

		private MTIntegrationRecipe(String id, IItemStack output, int energy, int craftingTime, IIngredient inputA, IIngredient inputB, IIntegrationRecipeFunction function) {
			this.inputA = inputA;
			this.inputB = inputB;
			this.function = function;
			setContents(id, MineTweakerMC.getItemStack(output), energy, craftingTime);
		}

		@Override
		public boolean isValidInputA(ItemStack is) {
			return inputA.matches(MineTweakerMC.getIItemStack(is));
		}

		@Override
		public boolean isValidInputB(ItemStack is) {
			return inputB.matches(MineTweakerMC.getIItemStack(is));
		}

		@Override
		public CraftingResult<ItemStack> craft(TileIntegrationTable crafter, boolean preview, ItemStack inputA,
											   ItemStack inputB) {
			if (inputA == null) {
				return null;
			}

			CraftingResult<ItemStack> result = super.craft(crafter, preview, inputA, inputB);
			if (result == null) {
				return null;
			}

			IItemStack out = function.recipe(MineTweakerMC.getIItemStack(output),
					MineTweakerMC.getIItemStack(inputA), MineTweakerMC.getIItemStack(inputB));

			result.crafted = MineTweakerMC.getItemStack(out);
			return result;
		}
	}
}
