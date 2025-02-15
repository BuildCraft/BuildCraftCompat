package buildcraft.compat.module.crafttweaker;

import buildcraft.api.fuels.ICoolant;
import buildcraft.api.fuels.IFluidCoolant;
import buildcraft.api.fuels.ISolidCoolant;
import buildcraft.lib.misc.FluidUtilBC;
import buildcraft.lib.recipe.coolant.CoolantRegistry;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.DecomposedRecipeBuilder;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.EmptyFluid;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Optional;

@ZenRegister
@ZenCodeType.Name("mods.buildcraft.Coolant")
@IRecipeHandler.For(ICoolant.class)
public enum Coolant implements IRecipeManager<ICoolant>, IRecipeHandler<ICoolant> {
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
        } else if (getAllRecipes().stream().anyMatch(r -> r instanceof IFluidCoolant fluidCoolant && fluidCoolant.getFluid().equals(fluid))) {
            throw new IllegalArgumentException("The fluid " + fluid + " is already registered as a coolant!");
        } else if (CombustionEngine.INSTANCE.getAllRecipes().stream().anyMatch(r -> r.getFluid().equals(fluid))) {
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
        } else if (getAllRecipes().stream().anyMatch(r -> r instanceof ISolidCoolant solidCoolant && solidCoolant.getSolid().equals(item))) {
            throw new IllegalArgumentException("The item " + item + " is already registered as a coolant!");
        } else if (multiplier <= 0.0) {
            throw new IllegalArgumentException("Multiplier was less than or equal to 0!");
        } else {
            CraftTweakerAPI.apply(AddSolidCoolant.create(this, name, item, fluid, multiplier));
        }
    }

    @Override
    public RecipeType<ICoolant> getRecipeType() {
        return ICoolant.TYPE;
    }

    @Override
    public String dumpToCommandString(final IRecipeManager manager, ICoolant recipe) {
        if (recipe instanceof IFluidCoolant fluidCoolant) {
            return String.format(
                    "coolant.addFluidCoolant(%s, %s, %s);",
                    StringUtil.quoteAndEscape(recipe.getId()),
                    StringUtil.quoteAndEscape(FluidUtilBC.getRegistryName(recipe.getFluid().getFluid())),
                    fluidCoolant.getDegreesCoolingPerMB()
            );
        } else if (recipe instanceof ISolidCoolant solidCoolant) {
            return String.format(
                    "coolant.addSolidCoolant(%s, %s, %s, %s);",
                    StringUtil.quoteAndEscape(recipe.getId()),
                    ItemStackUtil.getCommandString(solidCoolant.getSolid()),
                    StringUtil.quoteAndEscape(FluidUtilBC.getRegistryName(recipe.getFluid().getFluid())),
                    solidCoolant.getMultiplier()
            );
        }
        return "This is not a fluid coolant or a solid coolant. What happened?";
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super ICoolant> iRecipeManager, ICoolant coolant, U u) {
        if (coolant instanceof IFluidCoolant && u instanceof IFluidCoolant) {
            return coolant.getFluid().getFluid() == ((IFluidCoolant) u).getFluid().getFluid();
        } else if (coolant instanceof ISolidCoolant && u instanceof ISolidCoolant) {
            return ((ISolidCoolant) coolant).getSolid().getItem() == ((ISolidCoolant) u).getSolid().getItem();
        } else {
            return false;
        }
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super ICoolant> iRecipeManager, ICoolant coolant) {
        DecomposedRecipeBuilder builder = IDecomposedRecipe.builder();
        if (coolant instanceof IFluidCoolant) {
            builder.with(BuiltinRecipeComponents.Input.FLUID_INGREDIENTS, new CTFluidIngredient.FluidStackIngredient(IFluidStack.of(coolant.getFluid())));
            builder.with(BCRecipeComponents.DEGREES_COOLING_PER_MB, ((IFluidCoolant) coolant).getDegreesCoolingPerMB());
        } else if (coolant instanceof ISolidCoolant) {
            builder.with(BuiltinRecipeComponents.Input.FLUID_INGREDIENTS, new CTFluidIngredient.FluidStackIngredient(IFluidStack.of(coolant.getFluid())));
            builder.with(BCRecipeComponents.ITEM_STACK, ((ISolidCoolant) coolant).getSolid());
            builder.with(BCRecipeComponents.MULTIPLIER, ((ISolidCoolant) coolant).getMultiplier());
        } else {
            return Optional.empty();
        }
        return Optional.of(builder.build());
    }

    @Override
    public Optional<ICoolant> recompose(IRecipeManager<? super ICoolant> iRecipeManager, ResourceLocation id, IDecomposedRecipe iDecomposedRecipe) {
        if (!iDecomposedRecipe.get(BCRecipeComponents.DEGREES_COOLING_PER_MB).isEmpty()) {
            // IFluidCoolant
            Optional<FluidStack> fluidOptional = CompatModuleCraftTweaker.ctFluidIngredient2SingleFluidStack(iDecomposedRecipe.getOrThrowSingle(BuiltinRecipeComponents.Input.FLUID_INGREDIENTS));
            if (fluidOptional.isEmpty()) {
                return Optional.empty();
            }
            FluidStack fluid = fluidOptional.get();
            float degreesCoolingPerMB = iDecomposedRecipe.getOrThrowSingle(BCRecipeComponents.DEGREES_COOLING_PER_MB);
            return Optional.of(new CoolantRegistry.FluidCoolant(id, fluid, degreesCoolingPerMB));
        } else if (!iDecomposedRecipe.get(BCRecipeComponents.MULTIPLIER).isEmpty()) {
            ItemStack solid = iDecomposedRecipe.getOrThrowSingle(BCRecipeComponents.ITEM_STACK);
            FluidStack fluid = iDecomposedRecipe.getOrThrowSingle(BuiltinRecipeComponents.Input.FLUID_INGREDIENTS).getMatchingStacks().get(0).getInternal();
            float multiplier = iDecomposedRecipe.getOrThrowSingle(BCRecipeComponents.MULTIPLIER);
            return Optional.of(new CoolantRegistry.SolidCoolant(id, solid, fluid, multiplier));
        } else {
            return Optional.empty();
        }
    }

    static final class AddFluidCoolant extends ActionAddRecipe<ICoolant> {
        private AddFluidCoolant(IRecipeManager<ICoolant> manager, IFluidCoolant recipe) {
            super(manager, recipe);
        }

        public static AddFluidCoolant create(IRecipeManager<ICoolant> manager, String name, FluidStack fluid, float degreesCoolingPerMB) {
            ResourceLocation _name = new ResourceLocation("crafttweaker", name);
            return new AddFluidCoolant(manager, new CoolantRegistry.FluidCoolant(_name, fluid, degreesCoolingPerMB));
        }

        public String describe() {
            return "Adding combustion engine coolant " + this.recipe.getFluid();
        }
    }

    static final class AddSolidCoolant extends ActionAddRecipe<ICoolant> {
        public AddSolidCoolant(IRecipeManager<ICoolant> manager, ISolidCoolant recipe) {
            super(manager, recipe);
        }

        public static AddSolidCoolant create(IRecipeManager<ICoolant> manager, String name, ItemStack item, FluidStack fluid, float multiplier) {
            ResourceLocation _name = new ResourceLocation("crafttweaker", name);
            return new AddSolidCoolant(manager, new CoolantRegistry.SolidCoolant(_name, item, fluid, multiplier));
        }

        public String describe() {
            return "Adding combustion engine coolant " + ((ISolidCoolant) this.recipe).getSolid();
        }
    }
}
