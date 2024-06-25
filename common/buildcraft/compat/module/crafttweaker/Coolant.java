package buildcraft.compat.module.crafttweaker;

import buildcraft.api.fuels.ICoolant;
import buildcraft.api.fuels.IFluidCoolant;
import buildcraft.api.fuels.IFuel;
import buildcraft.api.fuels.ISolidCoolant;
import buildcraft.lib.recipe.coolant.CoolantRegistry;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.helper.ItemStackHelper;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.buildcraft.Coolant")
@IRecipeHandler.For(ICoolant.class)
public enum Coolant implements IRecipeManager, IRecipeHandler<ICoolant> {
    @ZenCodeGlobals.Global("coolant")
    INSTANCE;

    @ZenCodeType.Method
    public void addFluidCoolant(String name, IFluidStack liquid, float degreesCoolingPerMB) {
        addFluidCoolant0("custom/" + name, liquid, degreesCoolingPerMB);
    }

    private void addFluidCoolant0(String name, IFluidStack liquid, float degreesCoolingPerMB) {
        FluidStack fluid = liquid.getImmutableInternal();
        if (fluid == null) {
            throw new IllegalArgumentException("Fluid was null!");
        } else if (getAllRecipes().stream().anyMatch(r -> r.getRecipe() instanceof IFluidCoolant && ((IFluidCoolant) r.getRecipe()).getFluid().equals(fluid))) {
            throw new IllegalArgumentException("The fluid " + fluid + " is already registered as a coolant!");
        } else if (CombustionEngine.INSTANCE.getAllRecipes().stream().anyMatch(r -> ((IFuel) r.getRecipe()).getFluid().equals(fluid))) {
            throw new IllegalArgumentException("The fluid " + fluid + " is already registered as a fuel - so it won't work very well if you add it as a coolant too!");
        } else if (degreesCoolingPerMB <= 0.0) {
            throw new IllegalArgumentException("Degrees cooling per MB was less than or equal to 0!");
        } else {
            CraftTweakerAPI.apply(AddFluidCoolant.create(this, name, fluid, degreesCoolingPerMB));
        }
    }

    // @ZenMethod
    @ZenCodeType.Method
    public void addSolidCoolant(String name, IItemStack item, IFluidStack lFuel, float multiplier) {
        addSolidCoolant0("custom/" + name, item, lFuel, multiplier);
    }

    private void addSolidCoolant0(String name, IItemStack itemIn, IFluidStack fluidIn, float multiplier) {
        ItemStack item = itemIn.getImmutableInternal();
        FluidStack fluid = fluidIn.getImmutableInternal();
        if (item.getItem() == Items.AIR) {
            throw new IllegalArgumentException("Coolant item was air!");
        } else if (fluid.getFluid() == null || fluid.getFluid() instanceof EmptyFluid) {
            throw new IllegalArgumentException("Fluid was null or empty!");
        } else if (getAllRecipes().stream().anyMatch(r -> r.getRecipe() instanceof ISolidCoolant && ((ISolidCoolant) r.getRecipe()).getSolid().equals(item))) {
            throw new IllegalArgumentException("The item " + item + " is already registered as a coolant!");
        } else if (multiplier <= 0.0) {
            throw new IllegalArgumentException("Multiplier was less than or equal to 0!");
        } else {
            CraftTweakerAPI.apply(AddSolidCoolant.create(this, name, item, fluid, multiplier));
        }
    }

    @Override
    public IRecipeType<ICoolant> getRecipeType() {
        return ICoolant.TYPE;
    }

    @Override
    public String dumpToCommandString(final IRecipeManager manager, ICoolant recipe) {
        if (recipe instanceof IFluidCoolant) {
            IFluidCoolant fluidCoolant = (IFluidCoolant) recipe;
            return String.format(
                    "coolant.addFluidCoolant(%s, %s, %s);",
                    StringUtils.quoteAndEscape(recipe.getId()),
                    StringUtils.quoteAndEscape(recipe.getFluid().getFluid().getRegistryName()),
                    fluidCoolant.getDegreesCoolingPerMB()
            );
        } else if (recipe instanceof ISolidCoolant) {
            ISolidCoolant solidCoolant = (ISolidCoolant) recipe;
            return String.format(
                    "coolant.addSolidCoolant(%s, %s, %s, %s);",
                    StringUtils.quoteAndEscape(recipe.getId()),
                    ItemStackHelper.getCommandString(solidCoolant.getSolid()),
                    StringUtils.quoteAndEscape(recipe.getFluid().getFluid().getRegistryName()),
                    solidCoolant.getMultiplier()
            );
        }
        return "This is not a fluid coolant or a solid coolant. What happened?";
    }

    static final class AddFluidCoolant extends ActionAddRecipe {
        private AddFluidCoolant(IRecipeManager manager, IFluidCoolant recipe) {
            super(manager, recipe);
        }

        public static AddFluidCoolant create(IRecipeManager manager, String name, FluidStack fluid, float degreesCoolingPerMB) {
            ResourceLocation _name = new ResourceLocation("crafttweaker", name);
            return new AddFluidCoolant(manager, new CoolantRegistry.FluidCoolant(_name, fluid, degreesCoolingPerMB));
        }

        public String describe() {
            return "Adding combustion engine coolant " + ((ICoolant) this.recipe).getFluid();
        }
    }

    static final class AddSolidCoolant extends ActionAddRecipe {
        public AddSolidCoolant(IRecipeManager manager, ISolidCoolant recipe) {
            super(manager, recipe);
        }

        public static AddSolidCoolant create(IRecipeManager manager, String name, ItemStack item, FluidStack fluid, float multiplier) {
            ResourceLocation _name = new ResourceLocation("crafttweaker", name);
            return new AddSolidCoolant(manager, new CoolantRegistry.SolidCoolant(_name, item, fluid, multiplier));
        }

        public String describe() {
            return "Adding combustion engine coolant " + ((ISolidCoolant) this.recipe).getSolid();
        }
    }
}
