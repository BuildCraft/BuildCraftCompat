/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package buildcraft.compat.minetweaker;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IProgrammingRecipe;
import buildcraft.core.lib.inventory.StackHelper;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.buildcraft.ProgrammingTable")
@ModOnly("BuildCraft|Silicon")
public class ProgrammingTable {
	@ZenMethod
	public static void addRecipe(IItemStack input, int energy, IItemStack[] options, @Optional boolean programmableOnce) {
		MineTweakerAPI.apply(new AddRecipeAction(input, energy, options, programmableOnce));
	}

	private static class ProgrammingRecipeMT implements IProgrammingRecipe {
		private final String id;
		private final int energyCost;
		private final IItemStack input;
		private final List<ItemStack> options;
		private final boolean programmableOnce;

		public ProgrammingRecipeMT(String id, int energyCost, IItemStack input, IItemStack[] options, boolean programmableOnce) {
			this.id = id;
			this.energyCost = energyCost;
			this.input = input;
			this.options = new ArrayList<ItemStack>();
			for (IItemStack option : options) {
				this.options.add(MineTweakerMC.getItemStack(option));
			}
			this.programmableOnce = programmableOnce;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public List<ItemStack> getOptions(int w, int h) {
			return options;
		}

		@Override
		public int getEnergyCost(ItemStack itemStack) {
			return energyCost;
		}

		@Override
		public boolean canCraft(ItemStack stack) {
			if (input.matches(MineTweakerMC.getIItemStack(stack))) {
				return true;
			}

			if (!programmableOnce) {
				for (ItemStack option : options) {
					if (StackHelper.isEqualItem(option, stack)) {
						return true;
					}
				}
			}

			return false;
		}

		@Override
		public ItemStack craft(ItemStack input, ItemStack option) {
			return option.copy();
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final String id;
		private final ProgrammingRecipeMT progRecipe;

		public AddRecipeAction(IItemStack input, int energy, IItemStack[] options, boolean programmableOnce) {
			this.id = "MineTweaker:" + input.getName() + ":" + input.getDamage();
			this.progRecipe = new ProgrammingRecipeMT(id, energy, input, options, programmableOnce);
		}

		@Override
		public void apply() {
			BuildcraftRecipeRegistry.programmingTable.addRecipe(progRecipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftRecipeRegistry.programmingTable.removeRecipe(id);
		}

		@Override
		public String describe() {
			return "Adding programming table recipe " + id;
		}

		@Override
		public String describeUndo() {
			return "Removing programming table recipe " + id;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
