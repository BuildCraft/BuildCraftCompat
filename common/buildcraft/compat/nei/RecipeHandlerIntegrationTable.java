package buildcraft.compat.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import buildcraft.api.facades.IFacadeItem;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.api.recipes.IFlexibleRecipeViewable;
import buildcraft.silicon.gui.GuiIntegrationTable;

public class RecipeHandlerIntegrationTable extends RecipeHandlerBase {
    
    private static Class<? extends GuiContainer> guiClass;

    @Override
    public void prepare() {
        guiClass = GuiIntegrationTable.class;
        API.setGuiOffset(guiClass, 5, 25);
    }
    
    public class CachedIntegrationTableRecipe extends CachedBaseRecipe {
        
        public List<PositionedStack> inputs = new ArrayList<PositionedStack>();
        public PositionedStack output;
        public int energy;
        
        public CachedIntegrationTableRecipe(IFlexibleRecipeViewable recipe, boolean generatePermutations) {
            if (recipe.getInputs() instanceof List) {
                this.setIngredients((List<Object>) recipe.getInputs());
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
        
        public void setIngredients(List<Object> inputs) {
            int i = 0;
            for (Object o : inputs) {
                this.inputs.add(new PositionedStack(o, 12 + (i * 36), 8, false));
                i++;
            }
        }
        
        @Override
        public List<PositionedStack> getIngredients() {
            return this.getCycledIngredients(RecipeHandlerIntegrationTable.this.cycleticks / 20, this.inputs);
        }
        
        @Override
        public PositionedStack getResult() {
            return this.output;
        }
        
        public void generatePermutations() {
            for (PositionedStack p : this.inputs) {
                p.generatePermutations();
            }
            output.generatePermutations();
        }
        
    }
    
    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("tile.integrationTableBlock.name");
    }
    
    @Override
    public String getRecipeID() {
        return "buildcraft.integrationTable";
    }
    
    @Override
    public String getGuiTexture() {
        return "buildcraftsilicon:textures/gui/integration_table.png";
    }
    
    @Override
    public void loadTransferRects() {
        this.addTransferRect(89, 7, 6, 72);
    }
    
    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return guiClass;
    }
    
    @Override
    public int recipiesPerPage() {
        return 2;
    }
    
    @Override
    public void drawBackground(int recipe) {
        this.changeToGuiTexture();
        GuiDraw.drawTexturedModalRect(0, 5, 5, 25, 166, 43);
    }
    
    @Override
    public void drawExtras(int recipe) {
        //this.drawProgressBar(90, 7, 176, 17, 4, 71, 100, 3);
        GuiDraw.drawStringC(((CachedIntegrationTableRecipe) this.arecipes.get(recipe)).energy + " RF", 104, 8, 0x808080, false);
    }
    
    @Override
    public void loadAllRecipes() {
        for (IFlexibleRecipe<ItemStack> recipe : BuildcraftRecipeRegistry.integrationTable.getRecipes()) {
            if (recipe instanceof IFlexibleRecipeViewable) {
                if (!NEIIntegrationBC.isValid((IFlexibleRecipeViewable) recipe)) {
                    return;
                }
                if (((IFlexibleRecipeViewable) recipe).getOutput() instanceof ItemStack) {
                    ItemStack output = (ItemStack) ((IFlexibleRecipeViewable) recipe).getOutput();
                    if (!(output.getItem() instanceof IFacadeItem)) {
                        this.arecipes.add(new CachedIntegrationTableRecipe((IFlexibleRecipeViewable) recipe, true));
                    }
                }
            }
        }
    }
    
    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (IFlexibleRecipe<ItemStack> recipe : BuildcraftRecipeRegistry.integrationTable.getRecipes()) {
            if (recipe instanceof IFlexibleRecipeViewable) {
                if (!NEIIntegrationBC.isValid((IFlexibleRecipeViewable) recipe)) {
                    return;
                }
                IFlexibleRecipeViewable recipeViewable = (IFlexibleRecipeViewable) recipe;
                if (recipeViewable.getOutput() instanceof ItemStack) {
                    ItemStack output = (ItemStack) recipeViewable.getOutput();
                    if (output.stackTagCompound != null && NEIServerUtils.areStacksSameType(output, result) || output.stackTagCompound == null && NEIServerUtils.areStacksSameTypeCrafting(output, result)) {
                        this.arecipes.add(new CachedIntegrationTableRecipe((IFlexibleRecipeViewable) recipe, true));
                    }
                } else if (recipeViewable.getOutput() instanceof List) {
                    for (Object o : ((List) recipeViewable.getOutput())) {
                        ItemStack output = (ItemStack) o;
                        if (output.stackTagCompound != null && NEIServerUtils.areStacksSameType(output, result) || output.stackTagCompound == null && NEIServerUtils.areStacksSameTypeCrafting(output, result)) {
                            this.arecipes.add(new CachedIntegrationTableRecipe((IFlexibleRecipeViewable) recipe, true));
                            break;
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (IFlexibleRecipe<ItemStack> recipe : BuildcraftRecipeRegistry.integrationTable.getRecipes()) {
            if (recipe instanceof IFlexibleRecipeViewable) {
                if (!NEIIntegrationBC.isValid((IFlexibleRecipeViewable) recipe)) {
                    return;
                }
                CachedIntegrationTableRecipe crecipe = new CachedIntegrationTableRecipe((IFlexibleRecipeViewable) recipe);
                if (crecipe.contains(crecipe.inputs, ingredient)) {
                    crecipe.generatePermutations();
                    crecipe.setIngredientPermutation(crecipe.inputs, ingredient);
                    this.arecipes.add(crecipe);
                }
            }
        }
    }
}
