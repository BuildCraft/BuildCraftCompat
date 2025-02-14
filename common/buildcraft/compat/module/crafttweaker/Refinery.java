package buildcraft.compat.module.crafttweaker;

import net.minecraftforge.fluids.FluidStack;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.buildcraft.Refinery")
@ModOnly("buildcraftfactory")
public class Refinery {

    @ZenMethod
    public static void addHeatable(ILiquidStack in, ILiquidStack out, int heatFrom, int heatTo) {
        FluidStack flIn = CraftTweakerMC.getLiquidStack(in);
        if (flIn == null) {
            throw new IllegalArgumentException("Input was null!");
        }
        FluidStack flOut = CraftTweakerMC.getLiquidStack(out);
        if (flOut == null) {
            throw new IllegalArgumentException("Output was null!");
        }
        if (BuildcraftRecipeRegistry.refineryRecipes.getHeatableRegistry().getRecipeForInput(flIn) != null) {
            throw new IllegalArgumentException("The fluid " + flOut + " is already registered as a heatable fluid!");
        }
        if (heatFrom < -10 || heatFrom > +20) {
            throw new IllegalArgumentException("Heat From (" + heatFrom + ") was out of range [-10, +20] - buildcraft itself only uses 0,1,2,3.");
        }
        if (heatTo < -10 || heatTo > +20) {
            throw new IllegalArgumentException("Heat To (" + heatTo + ") was out of range [-10, +20] - buildcraft itself only uses 0,1,2,3.");
        }
        if (heatFrom >= heatTo) {
            throw new IllegalArgumentException(
                "Heat From (" + heatFrom + ") is greater than or equal to Heat To (" + heatTo
                    + "), which is incorrect for a heatable recipe (the output fluid should be hotter than the input)"
            );
        }
        CraftTweakerAPI.apply(new AddHeatable(flIn, flOut, heatFrom, heatTo));
    }

    @ZenMethod
    public static void addCoolable(ILiquidStack in, ILiquidStack out, int heatFrom, int heatTo) {
        FluidStack flIn = CraftTweakerMC.getLiquidStack(in);
        if (flIn == null) {
            throw new IllegalArgumentException("Input was null!");
        }
        FluidStack flOut = CraftTweakerMC.getLiquidStack(out);
        if (flOut == null) {
            throw new IllegalArgumentException("Output was null!");
        }
        if (BuildcraftRecipeRegistry.refineryRecipes.getCoolableRegistry().getRecipeForInput(flIn) != null) {
            throw new IllegalArgumentException("The fluid " + flOut + " is already registered as a coolable fluid!");
        }
        if (heatFrom < -10 || heatFrom > +20) {
            throw new IllegalArgumentException("Heat From (" + heatFrom + ") was out of range [-10, +20] - buildcraft itself only uses 0,1,2,3.");
        }
        if (heatTo < -10 || heatTo > +20) {
            throw new IllegalArgumentException("Heat To (" + heatTo + ") was out of range [-10, +20] - buildcraft itself only uses 0,1,2,3.");
        }
        if (heatFrom <= heatTo) {
            throw new IllegalArgumentException(
                "Heat From (" + heatFrom + ") is less than or equal to Heat To (" + heatTo
                    + "), which is incorrect for a coolable recipe (the output fluid should be colder than the input)"
            );
        }
        CraftTweakerAPI.apply(new AddCoolable(flIn, flOut, heatFrom, heatTo));
    }

    // @ZenMethod
    // public static void addLiquidCoolant(FluidStack coolant) {
    //
    // }
    //
    // @ZenMethod
    // public static void addSolidCoolant(ItemStack stack, FluidStack coolant) {
    //
    // }

    // ######################
    // ### Action classes ###
    // ######################

    static abstract class AddHeatTransfer {
        final FluidStack input, output;
        final int heatFrom, heatTo;

        public AddHeatTransfer(FluidStack input, FluidStack output, int heatFrom, int heatTo) {
            this.input = input;
            this.output = output;
            this.heatFrom = heatFrom;
            this.heatTo = heatTo;
        }
    }

    static final class AddHeatable extends AddHeatTransfer implements IAction {

        public AddHeatable(FluidStack input, FluidStack output, int heatFrom, int heatTo) {
            super(input, output, heatFrom, heatTo);
        }

        @Override
        public void apply() {
            BuildcraftRecipeRegistry.refineryRecipes.addHeatableRecipe(input, output, heatFrom, heatTo);
        }

        @Override
        public String describe() {
            return "Adding heatable fluid " + input;
        }
    }

    static final class AddCoolable extends AddHeatTransfer implements IAction {

        public AddCoolable(FluidStack input, FluidStack output, int heatFrom, int heatTo) {
            super(input, output, heatFrom, heatTo);
        }

        @Override
        public void apply() {
            BuildcraftRecipeRegistry.refineryRecipes.addCoolableRecipe(input, output, heatFrom, heatTo);
        }

        @Override
        public String describe() {
            return "Adding coolable fluid " + input;
        }
    }
}
