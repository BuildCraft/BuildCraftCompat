/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package buildcraft.compat.minetweaker;

import buildcraft.api.core.StackKey;
import buildcraft.api.fuels.BuildcraftFuelRegistry;
import buildcraft.api.fuels.ICoolant;
import buildcraft.api.fuels.IFuel;
import buildcraft.api.fuels.ISolidCoolant;
import java.util.HashSet;
import java.util.Set;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import net.minecraft.item.ItemStack;

/**
 *
 * @author Stan
 * @author asie
 */
@ZenClass("mods.buildcraft.Fuels")
@ModOnly("BuildCraft|Energy")
public class Fuels {
	@ZenMethod
	public static void addCombustionEngineFuel(ILiquidStack fuel, int powerPerCycle, int totalBurningTime) {
		MineTweakerAPI.apply(new AddFuelAction(fuel, powerPerCycle, totalBurningTime));
	}
	
	@ZenMethod
	public static void removeCombustionEngineFuel(ILiquidStack fuel) {
		Fluid fluid = MineTweakerMC.getLiquidStack(fuel).getFluid();
		if (BuildcraftFuelRegistry.fuel.getFuel(fluid) != null) {
			MineTweakerAPI.apply(new RemoveFuelAction(fluid.getName(), BuildcraftFuelRegistry.fuel.getFuel(fluid)));
		} else {
			MineTweakerAPI.logWarning("No such iron engine fuel: " + fluid.getName());
		}
	}
	
	@ZenMethod
	public static void addCombustionEngineCoolant(ILiquidStack coolant, float coolingPerMB) {
		MineTweakerAPI.apply(new AddLiquidCoolantAction(MineTweakerMC.getLiquidStack(coolant), coolingPerMB));
	}
	
	@ZenMethod
	public static void addCoolantItem(IItemStack coolantItem, ILiquidStack coolantLiquid) {
		MineTweakerAPI.apply(new AddCoolantItemAction(new StackKey(MineTweakerMC.getItemStack(coolantItem)), MineTweakerMC.getLiquidStack(coolantLiquid), 1.0F));
	}

	@ZenMethod
	public static void addCoolantItemWithMultiplier(IItemStack coolantItem, ILiquidStack coolantLiquid, float multiplier) {
		MineTweakerAPI.apply(new AddCoolantItemAction(new StackKey(MineTweakerMC.getItemStack(coolantItem)), MineTweakerMC.getLiquidStack(coolantLiquid), multiplier));
	}
	
	@ZenMethod
	public static void removeCombustionEngineCoolant(ILiquidStack coolantFluid) {
		FluidStack fluid = MineTweakerMC.getLiquidStack(coolantFluid);
		ICoolant coolant = BuildcraftFuelRegistry.coolant.getCoolant(fluid.getFluid());

		if (coolant != null) {
			MineTweakerAPI.apply(new RemoveLiquidCoolantAction(coolant));
		} else {
			MineTweakerAPI.logWarning("No such iron engine coolant: " + fluid.getFluid().getName());
		}
	}
	
	@ZenMethod
	public static void removeCoolantItem(IIngredient itemI) {
		ItemStack stack = MineTweakerMC.getItemStack(itemI);
		String name = stack.getDisplayName();

		if (BuildcraftFuelRegistry.coolant.getSolidCoolant(new StackKey(stack)) != null) {
			MineTweakerAPI.apply(new RemoveCoolantItemAction(name, BuildcraftFuelRegistry.coolant.getSolidCoolant(new StackKey(stack))));
		} else {
			MineTweakerAPI.logWarning("No such iron engine coolant: " + name);
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddFuelAction implements IUndoableAction {
		private final ILiquidStack fuel;
		private final int powerPerCycle, totalBurningTime;
		
		public AddFuelAction(ILiquidStack fuel, int powerPerCycle, int totalBurningTime) {
			this.fuel = fuel;
			this.powerPerCycle = powerPerCycle;
			this.totalBurningTime = totalBurningTime;
		}

		@Override
		public void apply() {
			BuildcraftFuelRegistry.fuel.addFuel(MineTweakerMC.getLiquidStack(fuel).getFluid(), powerPerCycle, totalBurningTime);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			Set<IFuel> fuelsToRemove = new HashSet<IFuel>();
			for (IFuel fuel : BuildcraftFuelRegistry.fuel.getFuels()) {
				if (fuel != null && fuel.getFluid() == fuel.getFluid() && fuel.getPowerPerCycle() == powerPerCycle
						&& fuel.getTotalBurningTime() == totalBurningTime) {
					fuelsToRemove.add(fuel);
				}
			}
			for (IFuel fuel : fuelsToRemove) {
				BuildcraftFuelRegistry.fuel.getFuels().remove(fuel);
			}
		}

		@Override
		public String describe() {
			return "Adding iron engine fuel " + fuel.getName();
		}

		@Override
		public String describeUndo() {
			return "Removing iron engine fuel " + fuel.getName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveFuelAction implements IUndoableAction {
		private final String fuelName;
		private final IFuel fuel;
		
		public RemoveFuelAction(String fuelName, IFuel fuel) {
			this.fuelName = fuelName;
			this.fuel = fuel;
		}

		@Override
		public void apply() {
			BuildcraftFuelRegistry.fuel.getFuels().remove(fuel);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftFuelRegistry.fuel.addFuel(fuel);
		}

		@Override
		public String describe() {
			return "Removing iron engine fuel " + fuelName;
		}

		@Override
		public String describeUndo() {
			return "Restoring iron engine fuel " + fuelName;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class AddLiquidCoolantAction implements IUndoableAction {
		private final FluidStack liquid;
		private final float degreesCooling;
		
		public AddLiquidCoolantAction(FluidStack liquid, float degreesCooling) {
			this.liquid = liquid;
			this.degreesCooling = degreesCooling;
		}

		@Override
		public void apply() {
			BuildcraftFuelRegistry.coolant.addCoolant(liquid.getFluid(), degreesCooling);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftFuelRegistry.coolant.getCoolants().remove(BuildcraftFuelRegistry.coolant.getCoolant(liquid.getFluid()));
		}

		@Override
		public String describe() {
			return "Adding iron engine coolant " + liquid;
		}

		@Override
		public String describeUndo() {
			return "Removing iron engine coolant " + liquid;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class AddCoolantItemAction implements IUndoableAction {
		private final StackKey item;
		private final FluidStack liquid;
		private final float mul;

		public AddCoolantItemAction(StackKey item, FluidStack liquid, float mul) {
			this.item = item;
			this.liquid = liquid;
			this.mul = mul;
		}

		@Override
		public void apply() {
			BuildcraftFuelRegistry.coolant.addSolidCoolant(item, new StackKey(liquid), mul);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftFuelRegistry.coolant.getSolidCoolants().remove(BuildcraftFuelRegistry.coolant.getCoolant(liquid.getFluid()));
		}

		@Override
		public String describe() {
			return "Adding iron engine coolant item " + MineTweakerMC.getIItemStack(item.stack);
		}

		@Override
		public String describeUndo() {
			return "Removing iron engine coolant item " + MineTweakerMC.getIItemStack(item.stack);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveLiquidCoolantAction implements IUndoableAction {
		private final ICoolant coolant;
		
		public RemoveLiquidCoolantAction(ICoolant coolant) {
			this.coolant = coolant;
		}

		@Override
		public void apply() {
			BuildcraftFuelRegistry.coolant.getCoolants().remove(coolant);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftFuelRegistry.coolant.addCoolant(coolant);
		}

		@Override
		public String describe() {
			return "Removing iron engine coolant " + coolant.getFluid().getName();
		}

		@Override
		public String describeUndo() {
			return "Restoring iron engine coolant " + coolant.getFluid().getName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveCoolantItemAction implements IUndoableAction {
		private final String name;
		private final ISolidCoolant coolant;
		
		public RemoveCoolantItemAction(String name, ISolidCoolant coolant) {
			this.name = name;
			this.coolant = coolant;
		}

		@Override
		public void apply() {
			BuildcraftFuelRegistry.coolant.getSolidCoolants().remove(coolant);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftFuelRegistry.coolant.addSolidCoolant(coolant);
		}

		@Override
		public String describe() {
			return "Removing iron engine coolant item " + name;
		}

		@Override
		public String describeUndo() {
			return "Restoring iron engine coolant item " + name;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
