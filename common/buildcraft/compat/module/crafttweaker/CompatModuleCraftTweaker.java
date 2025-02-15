package buildcraft.compat.module.crafttweaker;

import buildcraft.compat.CompatModuleBase;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public class CompatModuleCraftTweaker extends CompatModuleBase {
    public CompatModuleCraftTweaker() {
    }

    public String compatModId() {
        return "crafttweaker";
    }

    public void preInit() {
//        CraftTweakerAPI.registerClass(AssemblyTable.class);
//        CraftTweakerAPI.getRegistry()..registerClass(AssemblyTable.class);
//        CraftTweakerAPI.registerClass(CombustionEngine.class);
    }

    public static Optional<FluidStack> ctFluidIngredient2SingleFluidStack(CTFluidIngredient fluidIngredient) {
        List<IFluidStack> fluidStacks = fluidIngredient.getMatchingStacks();
        return iFluidStack2SingleFluidStack(fluidStacks);
    }

    public static Optional<FluidStack> iFluidStack2SingleFluidStack(List<IFluidStack> fluidStacks) {
        if (!fluidStacks.isEmpty()) {
            IFluidStack ctFluidStack = fluidStacks.get(0);
            FluidStack fluidStack = ctFluidStack.getInternal();
            return Optional.of(fluidStack);
        }
        return Optional.empty();
    }
}
