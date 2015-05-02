package buildcraft.compat.nei;

import java.util.ArrayList;
import codechicken.nei.PositionedStack;
import java.util.Collection;
import java.util.List;
import codechicken.nei.NEIServerUtils;
import buildcraft.api.facades.IFacadeItem;
import net.minecraft.item.ItemStack;
import buildcraft.api.recipes.IFlexibleRecipeViewable;
import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import codechicken.lib.gui.GuiDraw;
import net.minecraft.util.StatCollector;
import codechicken.nei.api.API;
import buildcraft.silicon.gui.GuiIntegrationTable;
import net.minecraft.client.gui.inventory.GuiContainer;

public class RecipeHandlerIntegrationTable extends RecipeHandlerBase
{
    private static Class<? extends GuiContainer> guiClass;
    
    @Override
    public void prepare() {
        API.setGuiOffset((Class)(RecipeHandlerIntegrationTable.guiClass = (Class<? extends GuiContainer>)GuiIntegrationTable.class), 5, 25);
    }
    
    public String getRecipeName() {
        return StatCollector.translateToLocal("tile.integrationTableBlock.name");
    }
    
    @Override
    public String getRecipeID() {
        return "buildcraft.integrationTable";
    }
    
    public String getGuiTexture() {
        return "buildcraftsilicon:textures/gui/integration_table.png";
    }
    
    public void loadTransferRects() {
        this.addTransferRect(89, 7, 6, 72);
    }
    
    public Class<? extends GuiContainer> getGuiClass() {
        return RecipeHandlerIntegrationTable.guiClass;
    }
    
    public int recipiesPerPage() {
        return 2;
    }
    
    public void drawBackground(final int recipe) {
        this.changeToGuiTexture();
        GuiDraw.drawTexturedModalRect(0, 5, 5, 25, 166, 43);
    }
    
    public void drawExtras(final int recipe) {
        GuiDraw.drawStringC(((CachedIntegrationTableRecipe) this.arecipes.get(recipe)).energy + " RF", 104, 8, 8421504, false);
    }
    
    @Override
    public void loadAllRecipes() {
        for (final IFlexibleRecipe<ItemStack> recipe : BuildcraftRecipeRegistry.integrationTable.getRecipes()) {
            if (recipe instanceof IFlexibleRecipeViewable) {
                if (!NEIIntegrationBC.isValid((IFlexibleRecipeViewable)recipe)) {
                    return;
                }
                if (!(((IFlexibleRecipeViewable)recipe).getOutput() instanceof ItemStack)) {
                    continue;
                }
                final ItemStack output = (ItemStack)((IFlexibleRecipeViewable)recipe).getOutput();
                if (output.getItem() instanceof IFacadeItem) {
                    continue;
                }
                this.arecipes.add(new CachedIntegrationTableRecipe((IFlexibleRecipeViewable)recipe, true));
            }
        }
    }
    
    @Override
    public void loadCraftingRecipes(final ItemStack result) {
        for (final IFlexibleRecipe<ItemStack> recipe : BuildcraftRecipeRegistry.integrationTable.getRecipes()) {
            if (recipe instanceof IFlexibleRecipeViewable) {
                if (!NEIIntegrationBC.isValid((IFlexibleRecipeViewable)recipe)) {
                    return;
                }
                final IFlexibleRecipeViewable recipeViewable = (IFlexibleRecipeViewable)recipe;
                if (recipeViewable.getOutput() instanceof ItemStack) {
                    final ItemStack output = (ItemStack)recipeViewable.getOutput();
                    if ((output.stackTagCompound == null || !NEIServerUtils.areStacksSameType(output, result)) && (output.stackTagCompound != null || !NEIServerUtils.areStacksSameTypeCrafting(output, result))) {
                        continue;
                    }
                    this.arecipes.add(new CachedIntegrationTableRecipe((IFlexibleRecipeViewable)recipe, true));
                }
                else {
                    if (!(recipeViewable.getOutput() instanceof List)) {
                        continue;
                    }
                    for (final Object o : (List)recipeViewable.getOutput()) {
                        final ItemStack output2 = (ItemStack)o;
                        if ((output2.stackTagCompound != null && NEIServerUtils.areStacksSameType(output2, result)) || (output2.stackTagCompound == null && NEIServerUtils.areStacksSameTypeCrafting(output2, result))) {
                            this.arecipes.add(new CachedIntegrationTableRecipe((IFlexibleRecipeViewable)recipe, true));
                            break;
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void loadUsageRecipes(final ItemStack ingredient) {
        for (final IFlexibleRecipe<ItemStack> recipe : BuildcraftRecipeRegistry.integrationTable.getRecipes()) {
            if (recipe instanceof IFlexibleRecipeViewable) {
                if (!NEIIntegrationBC.isValid((IFlexibleRecipeViewable)recipe)) {
                    return;
                }
                final CachedIntegrationTableRecipe crecipe = new CachedIntegrationTableRecipe((IFlexibleRecipeViewable) recipe);
                if (!crecipe.contains((Collection)crecipe.inputs, ingredient)) {
                    continue;
                }
                crecipe.generatePermutations();
                crecipe.setIngredientPermutation((Collection)crecipe.inputs, ingredient);
                this.arecipes.add(crecipe);
            }
        }
    }
    
    public class CachedIntegrationTableRecipe extends CachedBaseRecipe
    {
        public List<PositionedStack> inputs;
        public PositionedStack output;
        public int energy;
        
        public CachedIntegrationTableRecipe(IFlexibleRecipeViewable recipe, boolean generatePermutations) {
            this.inputs = new ArrayList<PositionedStack>();
            if (recipe.getInputs() instanceof List) {
                this.setIngredients((List<Object>)recipe.getInputs());
            }
            if (recipe.getOutput() != null) {
                this.output = new PositionedStack(recipe.getOutput(), 138, 24);
            }
            this.energy = recipe.getEnergyCost();
            if (generatePermutations) {
                this.generatePermutations();
            }
        }

        public CachedIntegrationTableRecipe(IFlexibleRecipeViewable recipe) {
            this(recipe, false);
        }
        
        public void setIngredients(final List<Object> inputs) {
            int i = 0;
            for (final Object o : inputs) {
                this.inputs.add(new PositionedStack(o, 12 + i * 36, 8, false));
                ++i;
            }
        }
        
        public List<PositionedStack> getIngredients() {
            return (List<PositionedStack>)this.getCycledIngredients(RecipeHandlerIntegrationTable.this.cycleticks / 20, (List)this.inputs);
        }
        
        public PositionedStack getResult() {
            return this.output;
        }
        
        public void generatePermutations() {
            for (final PositionedStack p : this.inputs) {
                p.generatePermutations();
            }
            this.output.generatePermutations();
        }
    }
}
