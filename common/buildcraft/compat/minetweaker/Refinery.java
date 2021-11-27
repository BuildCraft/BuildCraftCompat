/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package buildcraft.compat.minetweaker;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.api.recipes.IFlexibleRecipeViewable;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 * @author asie
 */
@ZenClass("mods.buildcraft.Refinery")
@ModOnly("BuildCraft|Factory")
public class Refinery {
	@ZenMethod
	public static void addRecipe(ILiquidStack output, int energyPerMB, int ticksPerMB, ILiquidStack input1, @Optional ILiquidStack input2) {
		MineTweakerAPI.apply(new AddRecipeAction(output, energyPerMB, ticksPerMB, input1, input2));
	}
	
	@ZenMethod
	public static void removeRecipe(ILiquidStack output) {
		Fluid fluid = MineTweakerMC.getLiquidStack(output).getFluid();
		
		List<IFlexibleRecipe<FluidStack>> toRemove = new ArrayList<IFlexibleRecipe<FluidStack>>();
		for (IFlexibleRecipe<FluidStack> recipe : BuildcraftRecipeRegistry.refinery.getRecipes()) {
			if (recipe instanceof IFlexibleRecipeViewable) {
				Object recipeOutput = ((IFlexibleRecipeViewable) recipe).getOutput();
				if (recipeOutput == fluid || (recipeOutput instanceof FluidStack && ((FluidStack) recipeOutput).getFluid() == fluid)) {
					toRemove.add(recipe);
				}
			}
		}
		
		for (IFlexibleRecipe<FluidStack> recipe : toRemove) {
			MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
		}
	}
	
	//Deprecated method for backwards compability
	@ZenMethod
	public static void remove(ILiquidStack output) {
	       removeRecipe(output);
	}

	
	// ######################
	// ### Action Classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final ILiquidStack output, input1, input2;
		private final int energyPerMB, ticksPerMB;
		
		public AddRecipeAction(ILiquidStack output, int energyPerMB, int ticksPerMB, ILiquidStack input1, ILiquidStack input2) {
			this.output = output;
			this.input1 = input1;
			this.input2 = input2;
			this.energyPerMB = energyPerMB;
			this.ticksPerMB = ticksPerMB;
		}

		@Override
		public void apply() {
			if (input2 == null) {
				String name = "MineTweaker:" + input1.getName() + ":" + output.getName();
				BuildcraftRecipeRegistry.refinery.addRecipe(name, MineTweakerMC.getLiquidStack(input1),
						MineTweakerMC.getLiquidStack(output), energyPerMB, ticksPerMB);
			} else {
				String name = "MineTweaker:" + input1.getName() + ":"  + input2.getName() + ":" + output.getName();
				BuildcraftRecipeRegistry.refinery.addRecipe(name, MineTweakerMC.getLiquidStack(input1),
						MineTweakerMC.getLiquidStack(input2), MineTweakerMC.getLiquidStack(output), energyPerMB, ticksPerMB);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			String name = "MineTweaker:" + input1.getName() + ":"  + input2.getName() + ":" + output.getName();
			BuildcraftRecipeRegistry.refinery.removeRecipe(name);
		}

		@Override
		public String describe() {
			return "Adding refinery recipe for " + output;
		}

		@Override
		public String describeUndo() {
			return "Removing refinery recipe for " + output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveRecipeAction implements IUndoableAction {
		private final IFlexibleRecipe<FluidStack> recipe;
		
		public RemoveRecipeAction(IFlexibleRecipe<FluidStack> recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() { BuildcraftRecipeRegistry.refinery.removeRecipe(recipe);
		}

		// TODO: Re-add undo support
		@Override
		public boolean canUndo() {
			return false;
		}

		@Override
		public void undo() {

		}

		@Override
		public String describe() {
			return "Removing refinery recipe for "
					+ ((FluidStack) ((IFlexibleRecipeViewable) recipe).getOutput()).getLocalizedName();
		}

		@Override
		public String describeUndo() {
			return "Restoring refinery recipe for "
					+ ((FluidStack) ((IFlexibleRecipeViewable) recipe).getOutput()).getLocalizedName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
